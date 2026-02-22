package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import sorting.*;
import generator.*;

public class VisualizerWindow extends JFrame {

    private BarPanel barPanel;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton resetBtn;
    private JSlider speedSlider;

    private String algorithmName;
    private double entropy; 

    private List<Integer> currentData;

    private int arraySize = 80;

    private boolean running = false;
    private boolean paused = false;

    private SortRunner sortRunner;
    private VisualizationListener visListener; 

    public VisualizerWindow(String algorithmName, double entropy) {
        this.algorithmName = algorithmName;
        this.entropy = entropy;

        setTitle(algorithmName + " — Entropy " + entropy);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        buildUI();
        generateData();


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

        JLabel sizeLabel = new JLabel("Size:");
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(80, 10, 300, 10));
        sizeSpinner.setPreferredSize(new Dimension(65, 28));
        sizeSpinner.addChangeListener(e -> {
            arraySize = (int) sizeSpinner.getValue();
            if (!running) {
                generateData();
            }
        });

        JLabel speedLabel = new JLabel("Speed:");
        speedSlider = new JSlider(1, 100, 50);
        speedSlider.setPreferredSize(new Dimension(160, 30));

        controls.add(startBtn);
        controls.add(pauseBtn);
        controls.add(resetBtn);
        controls.add(Box.createHorizontalStrut(10));
        controls.add(sizeLabel);
        controls.add(sizeSpinner);
        controls.add(speedLabel);
        controls.add(speedSlider);

        add(controls, BorderLayout.SOUTH);
    }

    private void generateData() {

        NumberGenerator gen = new EntropyGenerator(entropy, arraySize);

        currentData = new ArrayList<>(gen.getList());
        barPanel.update(currentData, -1, -1);
    }

    private void startSorting() {
        if (paused) {
            paused = false;
            visListener.resume();
            startBtn.setText("Start");
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            return;
        }

        SortingListener.clearListeners();
        visListener = new VisualizationListener(this, speedSlider);
        SortingListener.addListener(visListener);

        running = true;
        paused = false;

        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);

        List<Integer> copy = new ArrayList<>(currentData);
        sortRunner = new SortRunner(copy, algorithmName, this, visListener);
        sortRunner.execute();
    }

    private void togglePause() {
        if (running && !paused) {
            paused = true;
            visListener.pause(); 
            startBtn.setText("Resume");
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        }
    }

    private void reset() {
        if (sortRunner != null) {
            sortRunner.cancel(true);
            sortRunner = null;
        }
        if (visListener != null && paused) {
            visListener.resume();
        }

        running = false;
        paused = false;

        SortingListener.clearListeners();

        startBtn.setText("Start");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);

        generateData();
    }

    public void onSortingDone() {
        running = false;
        paused = false;
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        barPanel.update(currentData, -1, -1);
    }

    public void setCurrentData(List<Integer> data) {
        this.currentData = data;
        barPanel.update(data, -1, -1);
    }

    public void highlightBars(int i1, int i2) {
        barPanel.update(currentData, i1, i2);
    }
}
