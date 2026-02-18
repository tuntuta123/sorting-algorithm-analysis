package visualizer;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import sorting.*;
import generator.*;

public class VisualizerWindow extends JFrame implements sorting.Listener {

    private BarPanel barPanel;
    private JButton startBtn;
    private JButton pauseBtn;
    private JButton resetBtn;
    private JSlider speedSlider;

    private String algorithmName;
    private String generatorName;

    private List<Integer> originalData;
    private List<Integer> currentData;

    private int arraySize = 80;

    private boolean running = false;
    private boolean paused = false;
    private volatile boolean shouldPause = false;
    private final Object lock = new Object();

    private SortRunner sortRunner;

    public VisualizerWindow(String algorithmName, String generatorName) {
        this.algorithmName = algorithmName;
        this.generatorName = generatorName;

        setTitle(algorithmName + " — " + generatorName);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 650);
        setLocationRelativeTo(null);

        buildUI();
        generateData();

        SortingListener.addListener(this);
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(6, 6));

        JLabel titleLabel = new JLabel(algorithmName + "  |  " + generatorName, SwingConstants.CENTER);
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
        NumberGenerator gen;

        if (generatorName.equals("Sorted")) {
            gen = new SortedGenerator(arraySize);
        } else if (generatorName.equals("10% swapped")) {
            gen = new SwapSortGenerator(0.1, arraySize);
        } else if (generatorName.equals("50% swapped")) {
            gen = new SwapSortGenerator(0.5, arraySize);
        } else if (generatorName.equals("100% swapped")) {
            gen = new SwapSortGenerator(1.0, arraySize);
        } else if (generatorName.equals("Entropy 0.0 (sorted)")) {
            gen = new EntropyGenerator(0.0, arraySize);
        } else if (generatorName.equals("Entropy 0.25")) {
            gen = new EntropyGenerator(0.25, arraySize);
        } else if (generatorName.equals("Entropy 0.5")) {
            gen = new EntropyGenerator(0.5, arraySize);
        } else if (generatorName.equals("Entropy 0.75")) {
            gen = new EntropyGenerator(0.75, arraySize);
        } else {
            gen = new EntropyGenerator(1.0, arraySize);
        }

        originalData = new ArrayList<>(gen.getList());
        currentData = new ArrayList<>(originalData);
        barPanel.update(currentData, -1, -1);
    }

    private void startSorting() {
        if (paused) {
            synchronized (lock) {
                paused = false;
                shouldPause = false;
                lock.notifyAll();
            }
            startBtn.setEnabled(false);
            pauseBtn.setEnabled(true);
            return;
        }

        SortingListener.clearListeners();
        SortingListener.addListener(this);

        running = true;
        shouldPause = false;

        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);

        List<Integer> copy = new ArrayList<>(currentData);
        sortRunner = new SortRunner(copy, algorithmName, this);
        sortRunner.execute();
    }

    private void togglePause() {
        if (running && !paused) {
            paused = true;
            shouldPause = true;
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
        running = false;
        paused = false;
        shouldPause = false;
        synchronized (lock) { lock.notifyAll(); }

        startBtn.setText("Start");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);

        generateData();
    }

    public void onSortingDone() {
        running = false;
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        barPanel.update(currentData, -1, -1); 
    }

    public void setCurrentData(List<Integer> data) {
        this.currentData = data;
        barPanel.update(data, -1, -1);
    }

    @Override
    public void onComparison(int i1, int i2, int v1, int v2) {
        barPanel.update(currentData, i1, i2);
        delay();
    }

    @Override
    public void onSwap(int i1, int i2, int v1, int v2) {
        barPanel.update(currentData, i1, i2);
        delay();
    }

    private void delay() {
        synchronized (lock) {
            while (shouldPause && !Thread.currentThread().isInterrupted()) {
                try { lock.wait(); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
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
