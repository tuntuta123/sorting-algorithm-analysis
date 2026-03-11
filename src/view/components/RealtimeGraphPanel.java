package view.components;

import model.SortStats;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RealtimeGraphPanel extends JPanel {

    private SortStats[] stats;
    private String metric;
    private Timer repaintTimer;

    public RealtimeGraphPanel(String metric, SortStats... stats) {
        this.metric = metric;
        this.stats = stats;

        setPreferredSize(new Dimension(700,250));
        setBackground(new Color(57,62,70));

        repaintTimer = new Timer(40, e -> repaint());
        repaintTimer.start();
    }

    public void stopTimer() {
        if (repaintTimer != null) {
            repaintTimer.stop();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        int w = getWidth();
        int h = getHeight();

        int margin = 50;

        g2.setColor(Color.WHITE);
        g2.drawLine(margin, h - margin, w - margin, h - margin);
        g2.drawLine(margin, margin, margin, h - margin);

        Color[] colors = {Color.RED, Color.BLUE};

        for (int s = 0; s < stats.length; s++) {
            List<Long> times;
            List<Long> values;

            if (metric.equals("comparisons")) {
                times = stats[s].getComparisonTimes();
                values = stats[s].getComparisonValues();
            } else {
                times = stats[s].getSwapTimes();
                values = stats[s].getSwapValues();
            }

            if (times == null || values == null || times.size() < 2) {
                continue;
            }

            long maxTime = 1;
            long maxVal = 1;

            for (SortStats st : stats) {
                List<Long> t;
                List<Long> v;

                if (metric.equals("comparisons")) {
                    t = st.getComparisonTimes();
                    v = st.getComparisonValues();
                } else {
                    t = st.getSwapTimes();
                    v = st.getSwapValues();
                }

                if (t != null && !t.isEmpty()) {
                    maxTime = Math.max(maxTime, t.get(t.size() - 1));
                }
                if (v != null && !v.isEmpty()) {
                    maxVal = Math.max(maxVal, v.get(v.size() - 1));
                }
            }

            int prevX = -1;
            int prevY = -1;

            g2.setColor(colors[s % colors.length]);

            for (int i = 0; i < times.size(); i++) {
                int x = margin + (int) ((double) times.get(i) / maxTime * (w - 2 * margin));
                int y = h - margin - (int) ((double) values.get(i) / maxVal * (h - 2 * margin));

                if (prevX != -1) {
                    g2.drawLine(prevX, prevY, x, y);
                }

                prevX = x;
                prevY = y;
            }

	if(prevX != -1) {

	    int finalX = margin + (int)((double)maxTime/maxTime*(w-2*margin));

	    g2.drawLine(prevX, prevY, finalX, prevY);
	}

		    g2.drawString(stats[s].getAlgorithmName(), w - 140, margin + s * 18);
		}

		g2.setColor(Color.WHITE);
		g2.drawString(metric.toUpperCase() + " vs TIME", margin, 30);
	    }
}
