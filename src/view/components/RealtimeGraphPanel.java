package view.components;

import model.SortStats;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RealtimeGraphPanel extends JPanel {

    private SortStats[] stats;
    private String metric;
    private Timer repaintTimer;

    private static final Color BG = new Color(34, 40, 49);
    private static final Color AXIS_COLOR = new Color(200, 200, 200);
    private static final Color GRID_COLOR = new Color(70, 80, 90);
    private static final Color[] COLORS = { new Color(220, 80, 80), new Color(80, 140, 220) };

    private static final int MARGIN_LEFT = 75;
    private static final int MARGIN_RIGHT = 160;
    private static final int MARGIN_TOP = 40;
    private static final int MARGIN_BOTTOM = 50;
    private static final int Y_TICKS = 5;
    private static final int X_TICKS = 5;

    public RealtimeGraphPanel(String metric, SortStats... stats) {
        this.metric = metric;
        this.stats  = stats;
        setPreferredSize(new Dimension(700, 280));
        setBackground(BG);
        repaintTimer = new Timer(40, e -> repaint());
        repaintTimer.start();
    }

    public void stopTimer() {
        if (repaintTimer != null) repaintTimer.stop();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();
        int plotX = MARGIN_LEFT;
        int plotY = MARGIN_TOP;
        int plotW = w - MARGIN_LEFT - MARGIN_RIGHT;
        int plotH = h - MARGIN_TOP - MARGIN_BOTTOM;

        long maxTime = 1;
        long maxVal  = 1;
        for (SortStats st : stats) {
            List<Long> t = getTimes(st);
            List<Long> v = getValues(st);
            if (t != null && !t.isEmpty()) maxTime = Math.max(maxTime, t.get(t.size() - 1));
            if (v != null && !v.isEmpty()) maxVal  = Math.max(maxVal,  v.get(v.size() - 1));
        }

        g2.setStroke(new BasicStroke(0.5f));
        g2.setColor(GRID_COLOR);
        for (int i = 0; i <= Y_TICKS; i++) {
            int y = plotY + plotH - (int)((double) i / Y_TICKS * plotH);
            g2.drawLine(plotX, y, plotX + plotW, y);
        }
        for (int i = 0; i <= X_TICKS; i++) {
            int x = plotX + (int)((double) i / X_TICKS * plotW);
            g2.drawLine(x, plotY, x, plotY + plotH);
        }

        g2.setStroke(new BasicStroke(1.5f));
        g2.setColor(AXIS_COLOR);
        g2.drawLine(plotX, plotY,          plotX,          plotY + plotH); // Y axis
        g2.drawLine(plotX, plotY + plotH,  plotX + plotW,  plotY + plotH); // X axis

        g2.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g2.setColor(AXIS_COLOR);
        for (int i = 0; i <= Y_TICKS; i++) {
            long val = (long)((double) i / Y_TICKS * maxVal);
            int  y   = plotY + plotH - (int)((double) i / Y_TICKS * plotH);
            String label = formatNumber(val);
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, plotX - fm.stringWidth(label) - 5, y + 4);
            g2.drawLine(plotX - 3, y, plotX, y);
        }

        for (int i = 0; i <= X_TICKS; i++) {
            long ms = (long)((double) i / X_TICKS * maxTime);
            int  x  = plotX + (int)((double) i / X_TICKS * plotW);
            String label = ms + "ms";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(label, x - fm.stringWidth(label) / 2, plotY + plotH + 15);
            g2.drawLine(x, plotY + plotH, x, plotY + plotH + 3);
        }

        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        g2.setColor(AXIS_COLOR);
        Graphics2D g2r = (Graphics2D) g2.create();
        g2r.rotate(-Math.PI / 2, 14, plotY + plotH / 2);
        g2r.drawString(yAxisLabel(), 14 - g2r.getFontMetrics().stringWidth(yAxisLabel()) / 2,
                plotY + plotH / 2 + 4);
        g2r.dispose();

        g2.setFont(new Font("SansSerif", Font.BOLD, 11));
        String xLabel = "Time (ms)";
        g2.drawString(xLabel, plotX + plotW / 2 - g2.getFontMetrics().stringWidth(xLabel) / 2,
                plotY + plotH + MARGIN_BOTTOM - 5);

        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        String title = yAxisLabel() + " vs Time";
        g2.drawString(title, plotX + plotW / 2 - g2.getFontMetrics().stringWidth(title) / 2, plotY - 10);

        g2.setStroke(new BasicStroke(1.8f));
        for (int s = 0; s < stats.length; s++) {
            List<Long> times  = getTimes(stats[s]);
            List<Long> values = getValues(stats[s]);

            if (times == null || values == null || times.size() < 2) continue;

            g2.setColor(COLORS[s % COLORS.length]);

            int prevX = -1, prevY = -1;
            for (int i = 0; i < times.size(); i++) {
                int x = plotX + (int)((double) times.get(i)  / maxTime * plotW);
                int y = plotY + plotH - (int)((double) values.get(i) / maxVal  * plotH);
                if (prevX != -1) g2.drawLine(prevX, prevY, x, y);
                prevX = x;
                prevY = y;
            }
            if (prevX != -1) {
                g2.drawLine(prevX, prevY, plotX + plotW, prevY);
            }
        }

        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        int legendX = plotX + plotW + 10;
        int legendY = plotY + 20;
        for (int s = 0; s < stats.length; s++) {
            g2.setColor(COLORS[s % COLORS.length]);
            g2.fillRect(legendX, legendY + s * 20, 12, 12);
            g2.setColor(AXIS_COLOR);
            g2.drawString(stats[s].getAlgorithmName(), legendX + 16, legendY + s * 20 + 11);
        }
    }

    private List<Long> getTimes(SortStats st) {
        switch (metric) {
            case "comparisons": 
            	return st.getComparisonTimes();
            case "swaps":       
            	return st.getSwapTimes();
            default:            
            	return st.getComparisonTimes();
        }
    }

    private List<Long> getValues(SortStats st) {
        switch (metric) {
            case "comparisons": 
            	return st.getComparisonValues();
            case "swaps":       
            	return st.getSwapValues();
            default:            
            	return st.getComparisonValues();
        }
    }

    private String yAxisLabel() {
        switch (metric) {
            case "comparisons": 
            	return "Comparisons";
            case "swaps":       
            	return "Swaps";
            default:            
            	return metric;
        }
    }

    private String formatNumber(long val) {
        if (val >= 1_000_000) 
        	return String.format("%.1fM", val / 1_000_000.0);
        if (val >= 1_000)     
        	return String.format("%.1fk", val / 1_000.0);
        return String.valueOf(val);
    }
}
