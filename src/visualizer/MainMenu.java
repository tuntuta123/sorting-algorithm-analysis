package visualizer;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    private JList<String> algoList;
    private JList<String> genList;

    private final String[] algorithms = {
        "Bubble Sort",
        "Insertion Sort",
        "Merge Sort",
        "Quick Sort",
        "Bucket Sort"
    };

    private final String[] generators = {
        "Sorted",
        "10% swapped",
        "50% swapped",
        "100% swapped",
        "Entropy 0.0 (sorted)",
        "Entropy 0.25",
        "Entropy 0.5",
        "Entropy 0.75",
        "Entropy 1.0 (random)"
    };

    public MainMenu() {
        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 420);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        setLayout(new BorderLayout(8, 8));

        JLabel title = new JLabel("Sorting Algorithm Visualizer", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(12, 0, 8, 0));
        add(title, BorderLayout.NORTH);

        JPanel center = new JPanel(new GridLayout(1, 2, 15, 0));
        center.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        algoList = new JList<>(algorithms);
        algoList.setSelectedIndex(0);
        algoList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel algoPanel = new JPanel(new BorderLayout(4, 4));
        algoPanel.add(new JLabel("Algorithm:"), BorderLayout.NORTH);
        algoPanel.add(new JScrollPane(algoList), BorderLayout.CENTER);

        genList = new JList<>(generators);
        genList.setSelectedIndex(3);
        genList.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JPanel genPanel = new JPanel(new BorderLayout(4, 4));
        genPanel.add(new JLabel("Input data:"), BorderLayout.NORTH);
        genPanel.add(new JScrollPane(genList), BorderLayout.CENTER);

        center.add(algoPanel);
        center.add(genPanel);
        add(center, BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 10));

        JButton startBtn = new JButton("Visualize");
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        startBtn.setPreferredSize(new Dimension(140, 36));
        startBtn.addActionListener(e -> openVisualizer());

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        exitBtn.setPreferredSize(new Dimension(90, 36));
        exitBtn.addActionListener(e -> System.exit(0));

        bottom.add(startBtn);
        bottom.add(exitBtn);
        add(bottom, BorderLayout.SOUTH);
    }

    private void openVisualizer() {
        String algo = algoList.getSelectedValue();
        String gen = genList.getSelectedValue();

        if (algo == null || gen == null) {
            JOptionPane.showMessageDialog(this, "Please select both an algorithm and a generator.");
            return;
        }

        new VisualizerWindow(algo, gen);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
