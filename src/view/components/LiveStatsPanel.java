package view.components;

import model.SortStats;
import javax.swing.*;
import java.awt.*;


/**
 * Class that shows sorting statistics in real time.
 */
public class LiveStatsPanel extends JPanel {

    private final JLabel comparisonsLabel;
    private final JLabel swapsLabel;
    private final JLabel accessesLabel;
    private final JLabel timeLabel;

    private SortStats stats;
    private Timer refreshTimer;

    private static final Color BG = new Color(45, 52, 62);
    private static final Color FG = new Color(200, 220, 255);
    private static final Color ACCENT = new Color(0, 173, 181);

    /**
     * Creates the panel and sets up the labels and the refresh timer.
     */
    public LiveStatsPanel() {
        setBackground(BG);
        setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
        setLayout(new FlowLayout(FlowLayout.CENTER, 30, 4));

        comparisonsLabel = makeLabel("Comparisons: -");
        swapsLabel = makeLabel("Swaps: -");
        accessesLabel = makeLabel("Accesses: -");
        timeLabel = makeLabel("Time: -");

        add(comparisonsLabel);
        add(swapsLabel);
        add(accessesLabel);
        add(timeLabel);

        refreshTimer = new Timer(100, e -> refresh());
    }

    /**
     * Starts the live update timer with the given stats object to track.
     *
     * @param stats It holds the current sorting statistics.
     */
    public void start(SortStats stats) {
        this.stats = stats;
        refreshTimer.start();
    }


    /**
     * Stops the timer and does one final refresh to show the most recent values.
     */
    public void stop() {
        refreshTimer.stop();
        refresh(); 
    }

    /**
     * Stops the timer and resets all labels back to their default state.
     */
    public void reset() {
        refreshTimer.stop();
        comparisonsLabel.setText("Comparisons: -");
        swapsLabel.setText("Swaps: -");
        accessesLabel.setText("Accesses: -");
        timeLabel.setText("Time: -");
    }

    /**
     * Reads the latest values from stats and updates all labels, oterwise does nothing if stats is null.
     */
    private void refresh() {
        if (stats == null) return;
        SwingUtilities.invokeLater(() -> {
            comparisonsLabel.setText("Comparisons: " + String.format("%,d", stats.getComparisons()));
            swapsLabel.setText("Swaps: " + String.format("%,d", stats.getSwaps()));
            accessesLabel.setText("Accesses: " + String.format("%,d", stats.getAccesses()));
            timeLabel.setText("Time: " + stats.getElapsedMs() + " ms");
        });
    }

    /**
     * Creates a styled JLabel with the monospaced font and the panel's foreground color.
     *
     * @param text The initial text to display on the label.
     * @return A styled JLabel ready to be added to the panel.
     */
    private JLabel makeLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Monospaced", Font.PLAIN, 12));
        label.setForeground(FG);
        return label;
    }
}
