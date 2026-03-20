package view.window;

import controller.VisualizerController;
import model.SortStats;
import view.components.BarPanel;
import view.components.LiveStatsPanel;
import view.components.StatsWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class VisualizerWindow extends AbstractVisualizer {

    private BarPanel barPanel;
    private LiveStatsPanel liveStatsPanel;
    private VisualizerController controller;
    private final String algorithmName;

    public VisualizerWindow(String algorithmName, String genType, double entropy, int initialSize) {
        super(algorithmName + " - " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy),
              1500, 800, initialSize);
        this.algorithmName = algorithmName;
        buildUI();
        this.controller = new VisualizerController(this, algorithmName, genType, entropy, initialSize);
        controller.generateData();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(6, 6));

        JLabel titleLabel = new JLabel("Visualizer of " + algorithmName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        add(titleLabel, BorderLayout.NORTH);

        barPanel = new BarPanel();
        barPanel.setBackground(Color.WHITE);
        barPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(barPanel, BorderLayout.CENTER);

        liveStatsPanel = new LiveStatsPanel();

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(liveStatsPanel, BorderLayout.NORTH);
        southPanel.add(buildControlPanel(), BorderLayout.SOUTH);
        add(southPanel, BorderLayout.SOUTH);
    }

    public void startLiveStats(SortStats stats) {
        liveStatsPanel.start(stats);
    }

    @Override
    public void reset() {
        liveStatsPanel.reset();
        controller.reset();
    }

    @Override
    public void generateData(){ 
    	controller.generateData(); 
    }

    @Override
    public void startSorting(){ 
    	controller.start(); 
    }

    @Override
    public void togglePause(){ 
    	controller.pause(); 
    }
    @Override
    protected void openStatsWindow(){ 
    	new StatsWindow(controller.getStats()); 
    }

    @Override
    protected void onSizeChanged(int newSize) { 
    	controller.setArraySize(newSize); 
    }

    public void setLiveData(List<Integer> data) {
        barPanel.setLiveData(data);
    }

    public void updateBars(List<Integer> data, int i1, int i2) {
        barPanel.setLiveData(data);
        barPanel.highlight(i1, i2);
    }

    public void highlightBars(int i1, int i2) {
        barPanel.highlight(i1, i2);
    }

    public void onSortingDone(List<Integer> data, SortStats stats) {
        liveStatsPanel.stop();
        doneButtons();
        barPanel.update(data, -1, -1);
    }

    private void doneButtons() {
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        statsBtn.setEnabled(true);
    }
}
