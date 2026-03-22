package view.components;

import model.SortStats;
import javax.swing.*;
import java.awt.*;

/**
 * A class that represents a panel that draws a horizontal bar graph comparing one metric across sorting algorithms.
 */
public class StatsBarGraphPanel extends JPanel {

    private SortStats[] stats;
    private String metric;

    /**
     * Creates the bar graph panel for the given metric and stats objects.
     *
     * @param metric The metric to display.
     * @param stats  SortStats objects to compare on the graph.
     */
    public StatsBarGraphPanel(String metric, SortStats... stats) {
        this.metric = metric;
        this.stats = stats;
        setPreferredSize(new Dimension(600, 180));
        setBackground(new Color(57,62,70));
    }

    /**
     * Returns the value of the current metric from the given stats object.
     *
     * @param s The SortStats object to read from.
     * @return The value of the metric, or 0 if the metric is not recognized.
     */
    private long getValue(SortStats s) {
        switch(metric) {
            case "time":
                return s.getElapsedMs();
            case "comparisons":
                return s.getComparisons();
            case "swaps":
                return s.getSwaps();
            case "accesses":
            	return s.getAccesses();
        }
        return 0;
    }

    /**
     * Draws the horizontal bars, algorithm names, metric title, and value labels.
     *
     * @param g The Graphics object used to draw on the panel.
     */
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

        int availH  = height - 2 * margin;
        int spacing = availH / stats.length;
        int barHeight = (int) (spacing * 0.6);

        Color[] colors = {new Color(220, 80, 80), new Color(80, 140, 220)};
        g2.setColor(Color.WHITE);
	g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString(metric.toUpperCase(), margin, 20);

        for(int i=0;i<stats.length;i++) {
            long value = getValue(stats[i]);
            int barWidth = (int)((double)value/max*(width - 200));
            int y = margin + i * spacing + (spacing - barHeight) / 2;
            
            g2.setColor(colors[i % colors.length]);
            g2.fillRect(margin, y, barWidth, barHeight);
            
            g2.setColor(Color.WHITE);
	    g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
            g2.drawString(stats[i].getAlgorithmName(), margin, y - 4);
            
            String valueLabel = metric.equals("time") ? value + " ms" : String.valueOf(value);
            g2.drawString(valueLabel, margin + barWidth + 10, y + barHeight / 2 + 4);
        }
    }
}
