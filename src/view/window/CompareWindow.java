package view.window;

import controller.CompareController;
import model.SortStats;
import view.components.BarPanel;
import view.components.StatsWindow;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CompareWindow extends AbstractVisualizer {

    private BarPanel barPanel1;
    private BarPanel barPanel2;
    private CompareController controller;

    private final String algo1;
    private final String algo2;

    public CompareWindow(String algo1, String algo2, String genType, double entropy, int initialSize) {
        super(algo1 + " vs " + algo2 + " - " + ("Random".equals(genType) ? "Random" : "Entropy " + entropy),
              1400, 650, initialSize);
        this.algo1 = algo1;
        this.algo2 = algo2;
        buildUI();
        this.controller = new CompareController(this, algo1, algo2, genType, entropy, initialSize);
        controller.generateData();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(6, 6));

        JPanel titlePanel = new JPanel(new GridLayout(1, 2));
        JLabel label1 = new JLabel(algo1, SwingConstants.CENTER);
        JLabel label2 = new JLabel(algo2, SwingConstants.CENTER);
        label1.setFont(new Font("SansSerif", Font.BOLD, 15));
        label2.setFont(new Font("SansSerif", Font.BOLD, 15));
        label1.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        label2.setBorder(BorderFactory.createEmptyBorder(8, 0, 4, 0));
        titlePanel.add(label1);
        titlePanel.add(label2);
        add(titlePanel, BorderLayout.NORTH);

        JPanel barsPanel = new JPanel(new GridLayout(1, 2, 6, 0));
        barPanel1 = new BarPanel();
        barPanel2 = new BarPanel();
        barPanel1.setBackground(Color.WHITE);
        barPanel2.setBackground(Color.WHITE);
        barPanel1.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        barPanel2.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        barsPanel.add(barPanel1);
        barsPanel.add(barPanel2);
        add(barsPanel, BorderLayout.CENTER);

        add(buildControlPanel(), BorderLayout.SOUTH);
    }

    @Override
    public void generateData(){ 
    	controller.generateData(); 
    }

    @Override
    public void startSorting() { 
    	controller.start(); 
    }

    @Override
    public void togglePause(){ 
    	controller.pause(); }

    @Override
    public void reset()  { 
    	controller.reset(); 
    }

    @Override
    protected void openStatsWindow() {
        new StatsWindow(controller.getStats1(), controller.getStats2());
    }

    @Override
    protected void onSizeChanged(int newSize) { 
    	controller.setArraySize(newSize);
    	 }

    public void updateBars(List<Integer> data1, List<Integer> data2) {
        barPanel1.update(data1, -1, -1);
        barPanel2.update(data2, -1, -1);
    }

    public void onBothSortsDone(List<Integer> data1, List<Integer> data2,
                                 SortStats stats1, SortStats stats2) {
        SwingUtilities.invokeLater(() -> {
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(false);
            resetBtn.setEnabled(true);
            statsBtn.setEnabled(true);
            barPanel1.update(data1, -1, -1);
            barPanel2.update(data2, -1, -1);
        });
    }

    public BarPanel getBarPanel1() { 
    	return barPanel1; 
    }
    public BarPanel getBarPanel2() { 
    	return barPanel2; 
    }
}
