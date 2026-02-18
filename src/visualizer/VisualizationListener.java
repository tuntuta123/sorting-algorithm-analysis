package visualizer;

import sorting.Listener;
import javax.swing.*;

public class VisualizationListener implements Listener {
    
    private VisualizerWindow frame;
    private JSlider speedSlider;
    private volatile boolean paused = false;
    private final Object pauseLock = new Object();
    
    public VisualizationListener(VisualizerWindow frame, JSlider speedSlider) {
        this.frame = frame;
        this.speedSlider = speedSlider;
    }
    
    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        checkPauseAndDelay();
    }
    
    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        checkPauseAndDelay();
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
            int delay = 101 - speed;
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
