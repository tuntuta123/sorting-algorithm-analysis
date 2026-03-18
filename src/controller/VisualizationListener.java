package controller;

import sorting.Listener;
import view.window.VisualizerWindow;
import view.components.BarPanel;
import javax.swing.JSlider;

public class VisualizationListener implements Listener {

    private final VisualizerWindow frame; 
    private final BarPanel barPanel;       
    private final JSlider speedSlider;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();

    public VisualizationListener(VisualizerWindow frame, JSlider speedSlider) {
        this.frame = frame;
        this.barPanel = null;
        this.speedSlider = speedSlider;
    }

    public VisualizationListener(BarPanel barPanel, JSlider speedSlider) {
        this.barPanel = barPanel;
        this.frame = null;
        this.speedSlider = speedSlider;
    }

    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        highlight(i1, i2);
        checkPauseAndDelay();
    }

    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        highlight(i1, i2);
        checkPauseAndDelay();
    }

    @Override
    public void onAccess(int index, int value) {

    }

    private void highlight(int i1, int i2) {
        if (frame != null) {
            frame.highlightBars(i1, i2);
        } else if (barPanel != null) {
            barPanel.highlight(i1, i2);
        }
    }

    public void pause() {
        paused = true;
    }

    public void resume() {
        synchronized (pauseLock) {
            paused = false;
            pauseLock.notifyAll();
        }
    }

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
