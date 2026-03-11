package view.components;

import model.SortStats;
import javax.swing.*;
import java.awt.*;

public class StatsBarGraphPanel extends JPanel {

    private SortStats[] stats;
    private String metric;

    public StatsBarGraphPanel(String metric, SortStats... stats) {
        this.metric = metric;
        this.stats = stats;
        setPreferredSize(new Dimension(600, 180));
        setBackground(new Color(57,62,70));
    }

    private long getValue(SortStats s) {

        switch(metric) {
            case "time":
                return s.getElapsedMs();
            case "comparisons":
                return s.getComparisons();
            case "swaps":
                return s.getSwaps();
        }
        return 0;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();
        int margin = 40;
        long max = 0;

        for(SortStats s : stats) {
            max = Math.max(max, getValue(s));
        }

        if(max == 0) max = 1;
        int barHeight = 25;
        int spacing = 40;
        Color[] colors = {Color.RED, Color.BLUE};
        g2.setColor(Color.WHITE);
        g2.drawString(metric.toUpperCase(), margin, 20);

        for(int i=0;i<stats.length;i++) {
            long value = getValue(stats[i]);
            int barWidth = (int)((double)value/max*(width - 200));
            int y = margin + i*spacing;
            g2.setColor(colors[i % colors.length]);
            g2.fillRect(margin, y, barWidth, barHeight);
            g2.setColor(Color.WHITE);
            g2.drawString(stats[i].getAlgorithmName(), margin, y - 5);
            g2.drawString(String.valueOf(value), margin + barWidth + 10, y + 18);
        }
    }
}
