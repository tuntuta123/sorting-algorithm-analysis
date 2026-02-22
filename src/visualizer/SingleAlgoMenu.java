package visualizer;

import javax.swing.*;
import java.awt.*;

public class SingleAlgoMenu extends JFrame {

    private JComboBox<String> algoComboBox;
    private JComboBox<String> generatorComboBox;
    private JTextField entropyField;
    private JLabel entropyLabel;

    private final String[] algorithms = {
            "Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Bucket Sort"
    };

    private final String[] generators = { "Random", "Entropy" };

    public SingleAlgoMenu() {
        setTitle("Sorting Visualizer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 480);
        setLocationRelativeTo(null);
        setResizable(false);
        buildUI();
        setVisible(true);
    }

    private void buildUI() {
        Color background = new Color(34, 40, 49);
        Color panelColor = new Color(57, 62, 70);
        Color accent = new Color(0, 173, 181);

        getContentPane().setBackground(background);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Sorting Algorithm Visualizer", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(panelColor);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel algoLabel = new JLabel("Choose Algorithm:");
        algoLabel.setForeground(Color.WHITE);
        algoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        algoComboBox = new JComboBox<>(algorithms);
        algoComboBox.setMaximumSize(new Dimension(250, 35));
        algoComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel genLabel = new JLabel("Choose Generator:");
        genLabel.setForeground(Color.WHITE);
        genLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        generatorComboBox = new JComboBox<>(generators);
        generatorComboBox.setMaximumSize(new Dimension(250, 35));
        generatorComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);

        entropyLabel = new JLabel("Enter Entropy (0.0 - 1.0):");
        entropyLabel.setForeground(Color.WHITE);
        entropyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        entropyLabel.setVisible(false);

        entropyField = new JTextField();
        entropyField.setMaximumSize(new Dimension(250, 35));
        entropyField.setAlignmentX(Component.CENTER_ALIGNMENT);
        entropyField.setVisible(false);

        generatorComboBox.addActionListener(e -> {
            boolean isEntropy = "Entropy".equals(generatorComboBox.getSelectedItem());
            entropyLabel.setVisible(isEntropy);
            entropyField.setVisible(isEntropy);
        });

        centerPanel.add(algoLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(algoComboBox);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(genLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(generatorComboBox);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(entropyLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(entropyField);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);

        JButton startBtn = new JButton("Visualize");
        startBtn.setPreferredSize(new Dimension(150, 40));
        startBtn.setBackground(accent);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        startBtn.addActionListener(e -> openVisualizer());

        JButton backBtn = new JButton("Return to Main Menu");
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.addActionListener(e -> { new MainMenu(); dispose(); });

        bottomPanel.add(startBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void openVisualizer() {
        String algo = (String) algoComboBox.getSelectedItem();
        String genType = (String) generatorComboBox.getSelectedItem();
        double entropy = 0.0;

        if ("Entropy".equals(genType)) {
            String entropyText = entropyField.getText().trim();
            if (entropyText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter an entropy value between 0.0 and 1.0.");
                return;
            }
            try {
                entropy = Double.parseDouble(entropyText);
                if (entropy < 0.0 || entropy > 1.0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Entropy must be a valid number between 0.0 and 1.0.");
                return;
            }
        }

        new VisualizerWindow(algo, genType, entropy);
        dispose();
    }
}
