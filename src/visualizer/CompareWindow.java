package visualizer;

import javax.swing.*;
import javax.swing.SwingWorker;
import java.awt.*;
import java.util.*;
import java.util.List;

import sorting.*;
import generator.*;

public class CompareWindow extends JFrame {

    private BarPanel barPanel1;
    private BarPanel barPanel2;

    private JButton startBtn;
    private JButton pauseBtn;
    private JButton resetBtn;
    private JSlider speedSlider;

    private final String algo1;
    private final String algo2;
    private final double entropy;

    private List<Integer> originalData;
    private List<Integer> currentData1;
    private List<Integer> currentData2;

    private int arraySize = 80;

    private boolean running = false;
    private boolean paused = false;

    private SwingWorker<Void, Void> sortRunner1;
    private SwingWorker<Void, Void> sortRunner2;

    private VisualizationListener visListener1;
    private VisualizationListener visListener2;

    private int doneCount = 0;

    public CompareWindow(String algo1, String algo2, double entropy) {
        this.algo1 = algo1;
        this.algo2 = algo2;
        this.entropy = entropy;

        setTitle(algo1 + " vs " + algo2 + " — Entropy " + entropy);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1400, 650);
        setLocationRelativeTo(null);

        buildUI();
        generateData();
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
            if (!running) 
            	generateData();
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
        originalData = new ArrayList<>(gen.getList());

        currentData1 = new ArrayList<>(originalData);
        currentData2 = new ArrayList<>(originalData);

        barPanel1.update(currentData1, -1, -1);
        barPanel2.update(currentData2, -1, -1);
    }

    private void startSorting() {
        if (paused) {
            paused = false;
            if (visListener1 != null) visListener1.resume();
            if (visListener2 != null) visListener2.resume();
            startBtn.setText("Start");
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            return;
        }

        SortingListener.clearListeners();

        visListener1 = new VisualizationListener(barPanel1, speedSlider);
        visListener2 = new VisualizationListener(barPanel2, speedSlider);

        running = true;
        paused = false;
        doneCount = 0;

        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);

        barPanel1.setLiveData(currentData1);
        barPanel2.setLiveData(currentData2);

        sortRunner1 = new CompareSortRunner(currentData1, algo1, barPanel1, visListener1, this);
        sortRunner2 = new CompareSortRunner(currentData2, algo2, barPanel2, visListener2, this);

        sortRunner1.execute();
        sortRunner2.execute();
    }

    private void togglePause() {
        if (running && !paused) {
            paused = true;
            if (visListener1 != null) 
            	visListener1.pause();
            if (visListener2 != null) 
            	visListener2.pause();
            startBtn.setText("Resume");
            startBtn.setEnabled(true);
            pauseBtn.setEnabled(false);
        }
    }

    private void reset() {
        if (sortRunner1 != null) 
        	{ sortRunner1.cancel(true); sortRunner1 = null; }
        if (sortRunner2 != null) 
        
        	{ sortRunner2.cancel(true); sortRunner2 = null; }

        if (paused) {
            if (visListener1 != null) 
            	visListener1.resume();
            if (visListener2 != null) 
            	visListener2.resume();
        }

        running = false;
        paused = false;
        doneCount = 0;

        SortingListener.clearListeners();

        startBtn.setText("Start");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);

        generateData();
    }

    public synchronized void onOneSortDone() {
        doneCount++;
        if (doneCount >= 2) {
            running = false;
            SwingUtilities.invokeLater(() -> {
                startBtn.setEnabled(false);
                pauseBtn.setEnabled(false);
                resetBtn.setEnabled(true);
                barPanel1.update(currentData1, -1, -1);
                barPanel2.update(currentData2, -1, -1);
            });
        }
    }

    public void setCurrentData1(List<Integer> data) {
        this.currentData1 = data;
    }

    public void setCurrentData2(List<Integer> data) {
        this.currentData2 = data;
    }
}
