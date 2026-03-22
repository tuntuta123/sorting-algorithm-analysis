package controller;

import util.Listener;
import view.window.VisualizerWindow;
import view.components.BarPanel;
import javax.swing.JSlider;

/**
 * Class that listens to sorting events and updates the visualization in real time.
 * Handles highlighting the bars being compared or swapped, controls
 * the animation speed via the slider, and supports pausing/resuming.
 * Can work with either a full VisualizerWindow or just a BarPanel,
 * which is why there are two constructors. It implements the Interface Listener.
 */
public class VisualizationListener implements Listener {

    private final VisualizerWindow frame; 
    private final BarPanel barPanel;       
    private final JSlider speedSlider;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

	/**
     * Constructor for single algorithm mode, it uses VisualizerWindow.
     *
     * @param frame       the window to highlight bars on
     * @param speedSlider the slider controlling animation speed
     */
    public VisualizationListener(VisualizerWindow frame, JSlider speedSlider) {
        this.frame = frame;
        this.barPanel = null;
        this.speedSlider = speedSlider;
    }

	/**
     * Constructor for compare mode, it uses just a BarPanel instead of the window.
     *
     * @param barPanel    the bar panel to highlight bars on
     * @param speedSlider the slider controlling animation speed
     */
    public VisualizationListener(BarPanel barPanel, JSlider speedSlider) {
        this.barPanel = barPanel;
        this.frame = null;
        this.speedSlider = speedSlider;
    }

	/**
     * Called when two elements are compared. Highlights them and waits
     * according to the current speed setting.
     */
    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        highlight(i1, i2);
        checkPauseAndDelay();
    }

	/**
     * Called when two elements are swapped. Same behaviour as onComparison.
     */
    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        highlight(i1, i2);
        checkPauseAndDelay();
    }

	/**
     * Called when a single element is accessed. Not visualized in the UI.
     */
    @Override
    public void onAccess(int index, int value) {

    }

	/**
     * Highlights the two bars at the given indices on whichever panel is active.
     *
     * @param i1 index of the first bar
     * @param i2 index of the second bar
     */
    private void highlight(int i1, int i2) {
        if (frame != null) {
            frame.highlightBars(i1, i2);
        } else if (barPanel != null) {
            barPanel.highlight(i1, i2);
        }
    }

	/**
     * Pauses the animation. The sort thread will block on the next event
     * until resume() is called.
     */
    public void pause() {
        paused = true;
    }

	/**
     * Resumes the animation after a pause.
     * Wakes up the sort thread waiting on the pauseLock.
     */
    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

	/**
     * Blocks the sort thread if paused, then sleeps for a delay based on
     * the speed slider. Higher slider value means a shorter delay and a faster animation.
     * Restores the interrupt flag and returns early if the thread is interrupted.
     */
    private void checkPauseAndDelay() {
        synchronized (pauseLock) {
            while (paused) {
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }
        try {
            int speed = speedSlider.getValue();
            Thread.sleep(101 - speed);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
