package view.components;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * This class is used to display sorting data as bars.
 * Bars are colored differently depending on whether they are
 * being compared(red), sorted(green), or normal(blue).
 */
public class BarPanel extends JPanel {

    private List<Integer> data = new ArrayList<>();
    private int hi1 = -1;
    private int hi2 = -1;

    /**
     * Updates the data and repaints the panel.
     *
     * @param newData The new list of values to display.
     * @param i1 Index of the first colored bar.
     * @param i2 Index of the second colored bar.
     */
    public void update(List<Integer> newData, int i1, int i2) {
        this.data = new ArrayList<>(newData);
        this.hi1 = i1;
        this.hi2 = i2;
        SwingUtilities.invokeLater(this::repaint);
    }

    /**
     * Sets the data list directly.
     *
     * @param liveData The list to display in real time.
     */
    public void setLiveData(List<Integer> liveData) {
        this.data = liveData;
    }

    /**
     * Sets which two bars should be colored, then repaints the panel.
     *
     * @param i1 Index of the first bar to be colored.
     * @param i2 Index of the second bar to be colored.
     */
    public void highlight(int i1, int i2) {
        this.hi1 = i1;
        this.hi2 = i2;
        SwingUtilities.invokeLater(this::repaint);
    }

    /**
     * Draws all the bars on the panel.
     * Compared bars are red, sorted bars are green, and the rest are blue.
     *
     * @param g The Graphics object used to draw on the panel.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (data == null || data.isEmpty()) 
        	return;

        int w = getWidth();
        int h = getHeight();
        int pad = 15;

        int n = data.size();
        int maxVal = Collections.max(data);
        if (maxVal == 0) maxVal = 1;

        int availW = w - 2 * pad;
        int availH = h - 2 * pad;
        int barW = Math.max(1, (availW - (n - 1)) / n);

        boolean sorted = isSorted();

        for (int i = 0; i < n; i++) {
            int val = data.get(i);
            int barH = (int) ((double) val / maxVal * availH);
            int x = pad + i * (barW + 1);
            int y = h - pad - barH;

            if (i == hi1 || i == hi2) {
                g.setColor(Color.RED);
            } else if (sorted) {
                g.setColor(new Color(60, 179, 113));
            } else {
                g.setColor(new Color(70, 130, 180));
            }

            g.fillRect(x, y, barW, barH);
        }
    }

    /**
     * Checks if the current data list is sorted in ascending order.
     *
     * @return True if the data is sorted, false otherwise.
     */
    private boolean isSorted() {
        for (int i = 1; i < data.size(); i++) {
            if (data.get(i - 1) > data.get(i)) 
            	return false;
        }
        return true;
    }

    /**
     * Returns the preferred size of the panel.
     *
     * @return A Dimension of 900x500 pixels.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(900, 500);
    }
    
}
