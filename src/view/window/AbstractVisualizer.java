package view.window;

import view.menu.MainMenu;
import javax.swing.*;
import java.awt.*;

public abstract class AbstractVisualizer extends JFrame implements VisualizerInterface {

    protected JButton startBtn;
    protected JButton pauseBtn;
    protected JButton resetBtn;
    protected JButton statsBtn;
    protected JSlider speedSlider;

    public AbstractVisualizer(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
    }


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
        JSpinner sizeSpinner = new JSpinner(new SpinnerNumberModel(80, 10, 300, 10));
        sizeSpinner.setPreferredSize(new Dimension(65, 28));
        sizeSpinner.addChangeListener(e -> onSizeChanged((int) sizeSpinner.getValue()));

        JLabel speedLabel = new JLabel("Speed:");
        speedSlider = new JSlider(1, 100, 50);
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

    protected abstract void openStatsWindow();

    protected abstract void onSizeChanged(int newSize);

    public void onSortingStarted() {
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
        resetBtn.setEnabled(false);
        statsBtn.setEnabled(false);
    }

    public void onPaused() {
        startBtn.setText("Resume");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
    }

    public void onResumed() {
        startBtn.setText("Start");
        startBtn.setEnabled(false);
        pauseBtn.setEnabled(true);
    }

    public void onReset() {
        startBtn.setText("Start");
        startBtn.setEnabled(true);
        pauseBtn.setEnabled(false);
        resetBtn.setEnabled(true);
        statsBtn.setEnabled(false);
    }

    public JSlider getSpeedSlider() { 
    	return speedSlider; 
    }
}
