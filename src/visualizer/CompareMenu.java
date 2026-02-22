package visualizer;

import javax.swing.*;
import java.awt.*;

public class CompareMenu extends JFrame {

    private JComboBox<String> algoComboBox1;
    private JComboBox<String> algoComboBox2;
    private JTextField entropyField;

    private final String[] algorithms = {
            "Bubble Sort",
            "Insertion Sort",
            "Merge Sort",
            "Quick Sort",
            "Bucket Sort"
    };

    public CompareMenu() {
        setTitle("Compare Two Algorithms");
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

        JLabel title = new JLabel("Compare Two Sorting Algorithms", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 22));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(panelColor);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        JLabel algo1Label = new JLabel("Choose Algorithm 1:");
        algo1Label.setForeground(Color.WHITE);
        algo1Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        algoComboBox1 = new JComboBox<>(algorithms);
        algoComboBox1.setMaximumSize(new Dimension(250, 35));
        algoComboBox1.setAlignmentX(Component.CENTER_ALIGNMENT);
        algoComboBox1.setSelectedIndex(0);

        JLabel algo2Label = new JLabel("Choose Algorithm 2:");
        algo2Label.setForeground(Color.WHITE);
        algo2Label.setAlignmentX(Component.CENTER_ALIGNMENT);

        algoComboBox2 = new JComboBox<>(algorithms);
        algoComboBox2.setMaximumSize(new Dimension(250, 35));
        algoComboBox2.setAlignmentX(Component.CENTER_ALIGNMENT);
        algoComboBox2.setSelectedIndex(2); 

        JLabel entropyLabel = new JLabel("Enter Entropy (0.0 - 1.0):");
        entropyLabel.setForeground(Color.WHITE);
        entropyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        entropyField = new JTextField();
        entropyField.setMaximumSize(new Dimension(250, 35));
        entropyField.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(algo1Label);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(algoComboBox1);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(algo2Label);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(algoComboBox2);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(entropyLabel);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(entropyField);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);

        JButton startBtn = new JButton("Compare");
        startBtn.setPreferredSize(new Dimension(150, 40));
        startBtn.setBackground(accent);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        startBtn.addActionListener(e -> openCompare());

        JButton backBtn = new JButton("Return to Main Menu");
        backBtn.setPreferredSize(new Dimension(200, 40));
        backBtn.setFocusPainted(false);
        backBtn.setFont(new Font("SansSerif", Font.BOLD, 14));
        backBtn.addActionListener(e -> {
            new MainMenu();
            dispose();
        });

        bottomPanel.add(startBtn);
        bottomPanel.add(backBtn);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void openCompare() {
        String algo1 = (String) algoComboBox1.getSelectedItem();
        String algo2 = (String) algoComboBox2.getSelectedItem();
        String entropyText = entropyField.getText().trim();

        if (algo1.equals(algo2)) {
            JOptionPane.showMessageDialog(this,
                    "Please choose two different algorithms.");
            return;
        }

        if (entropyText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Please enter an entropy value between 0.0 and 1.0.");
            return;
        }

        double entropy;
        try {
            entropy = Double.parseDouble(entropyText);
            if (entropy < 0.0 || entropy > 1.0)
                throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Entropy must be a valid number between 0.0 and 1.0.");
            return;
        }

        new CompareWindow(algo1, algo2, entropy);
        dispose();
    }
}
