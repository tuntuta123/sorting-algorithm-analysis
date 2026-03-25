package view.window;

import view.menu.MainMenu;
import util.AppConfig;
import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for all sorting visualizer windows.
 * Provides the shared control panel (start, pause, reset, stats, back buttons)
 * and common UI state methods that subclasses can call or override.
 */
public abstract class AbstractVisualizer extends JFrame implements VisualizerInterface {

    protected JButton startBtn;
    protected int initialSize;
    protected JButton pauseBtn;
    protected JButton resetBtn;
    protected JButton statsBtn;
    protected JSlider speedSlider;

    /**
     * Creates the visualizer window with the given title, size, and initial array size.
     *
     * @param title The title shown in the window's title bar.
     * @param width The width of the window in pixels.
     * @param height The height of the window in pixels.
     * @param initialSize The initial number of elements to sort.
     */
    public AbstractVisualizer(String title, int width, int height, int initialSize) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        this.initialSize = initialSize;
    }

    /**
     * Builds and returns the control panel with all the buttons, the size, and the speed slider.
     * Each button is wired to its corresponding method defined in this class or in subclasses.
     *
     * @return A JPanel containing all the controls ready to be added to the window.
     */
    protected JPanel buildControlPanel() {
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 8));

        startBtn = new JButton("Start");
        startBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        startBtn.addActionListener(e -> startSorting());

        pauseBtn = new JButton("Pause");
        pauseBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        pauseBtn.setEnabled(false);
        pauseBtn.addActionListener(e -> togglePause());

        resetBtn = new JButton("Reset");
        resetBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        resetBtn.addActionListener(e -> reset());

        statsBtn = new JButton("View Performance");
        statsBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        statsBtn.setBackground(new Color(0, 173, 181));
        statsBtn.setForeground(Color.WHITE);
        statsBtn.setFocusPainted(false);
        statsBtn.setEnabled(false);
        statsBtn.addActionListener(e -> openStatsWindow());

        JButton backBtn = new JButton("Back to Menu");
        backBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        backBtn.addActionListener(e -> { dispose(); new MainMenu(); });

        JLabel sizeLabel = new JLabel("Size:");
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(initialSize, 5, 300, 10));
        sizeSpinner.setPreferredSize(new Dimension(65, 28));
        sizeSpinner.addChangeListener(e -> onSizeChanged((int) sizeSpinner.getValue()));

        JLabel speedLabel = new JLabel("Speed:");
        speedSlider = new JSlider(1, 100, AppConfig.getAnimationSpeed());
        speedSlider.setPreferredSize(new Dimension(160, 30));

        controls.add(startBtn);
        controls.add(pauseBtn);
        controls.add(resetBtn);
        controls.add(statsBtn);
        controls.add(backBtn);
        controls.add(Box.createHorizontalStrut(10));
        controls.add(sizeLabel);
        controls.add(sizeSpinner);
        controls.add(speedLabel);
        controls.add(speedSlider);

        return controls;
    }

    /**
     * Opens the statistics window for the current sorting session.
     */
    protected abstract void openStatsWindow();

    /**
     * Called when the user changes the array size.
     *
     * @param newSize The new number of elements selected by the user.
     */
    protected abstract void onSizeChanged(int newSize);

    /**
     * Updates the control panel state when sorting starts.
     * Disables start, enables pause, and hides the stats button.
     */
    public void onSortingStarted() {
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(true);
        statsBtn.setEnabled(false);
    }

    /**
     * Updates the control panel state when sorting is paused.
     * Changes the start button to "Resume" and disables pause.
     */
    public void onPaused() {
        startBtn.setText("Resume");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
    }

    /**
     * Updates the control panel state when sorting is resumed.
     * Restores the start button label and disables it again.
     */
    public void onResumed() {
        startBtn.setText("Start");
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
    }

    /**
     * Updates the control panel state when sorting is reset.
     * Re-enables start and resets all buttons to their default state.
     */
    public void onReset() {
        startBtn.setText("Start");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        statsBtn.setEnabled(false);
    }

    /**
     * Returns the speed slider so subclasses can read its value during sorting.
     *
     * @return The JSlider used to control sorting speed.
     */
    public JSlider getSpeedSlider() { 
    	return speedSlider; 
    }
}
