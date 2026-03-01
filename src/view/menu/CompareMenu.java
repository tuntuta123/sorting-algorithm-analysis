package view.menu;

import view.window.CompareWindow;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;

public class CompareMenu extends AbstractMenu {

    private JComboBox<String> algoComboBox1;
    private JComboBox<String> algoComboBox2;
    private JComboBox<String> generatorComboBox;
    private JTextField entropyField;
    private JLabel entropyLabel;

    public CompareMenu() {
        super("Compare Two Algorithms", 600, 560);
        buildUI();
        setVisible(true);
    }

    @Override
    public void buildUI() {
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(10, 10));

        add(makeTitle("Compare Two Sorting Algorithms"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(PANEL_BG);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(25, 80, 25, 80));

        algoComboBox1 = makeComboBox(ALGORITHMS);
        algoComboBox1.setSelectedIndex(0);

        algoComboBox2 = makeComboBox(ALGORITHMS);
        algoComboBox2.setSelectedIndex(1);

        algoComboBox2.setRenderer(new BasicComboBoxRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                String blocked = (String) algoComboBox1.getSelectedItem();
                if (value != null && value.equals(blocked)) {
                    setForeground(new Color(120, 120, 120));
                    setFont(getFont().deriveFont(Font.ITALIC));
                    setText(value + " (already selected)");
                } else if (!isSelected) {
                    setForeground(Color.BLACK);
                }
                return this;
            }
        });

        algoComboBox1.addActionListener(e -> { algoComboBox2.repaint(); skipBlockedItem(); });
        algoComboBox2.addActionListener(e -> skipBlockedItem());

        generatorComboBox = makeComboBox(GENERATORS);

        entropyLabel = makeFieldLabel("Enter Entropy (0.0 - 1.0):");
        entropyField = new JTextField();
        entropyField.setMaximumSize(new Dimension(250, 35));
        entropyField.setAlignmentX(Component.CENTER_ALIGNMENT);

        wireEntropyVisibility(generatorComboBox, entropyLabel, entropyField);

        centerPanel.add(makeFieldLabel("Choose Algorithm 1:"));
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(algoComboBox1);
        centerPanel.add(Box.createVerticalStrut(16));
        centerPanel.add(makeFieldLabel("Choose Algorithm 2:"));
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(algoComboBox2);
        centerPanel.add(Box.createVerticalStrut(16));
        centerPanel.add(makeFieldLabel("Choose Generator:"));
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(generatorComboBox);
        centerPanel.add(Box.createVerticalStrut(16));
        centerPanel.add(entropyLabel);
        centerPanel.add(Box.createVerticalStrut(8));
        centerPanel.add(entropyField);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BG);

        JButton startBtn = makeAccentButton("Compare", 150, 40);
        startBtn.addActionListener(e -> open());

        JButton backBtn = makeNavButton("Return to Main Menu", 200, 40);
        backBtn.addActionListener(e -> { new MainMenu(); dispose(); });

        bottomPanel.add(startBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void skipBlockedItem() {
        String sel1 = (String) algoComboBox1.getSelectedItem();
        String sel2 = (String) algoComboBox2.getSelectedItem();
        if (sel1 != null && sel1.equals(sel2)) {
            int n = algoComboBox2.getItemCount();
            int cur = algoComboBox2.getSelectedIndex();
            for (int i = 1; i < n; i++) {
                int next = (cur + i) % n;
                if (!algoComboBox2.getItemAt(next).equals(sel1)) {
                    algoComboBox2.setSelectedIndex(next);
                    break;
                }
            }
        }
    }

    @Override
    public void open() {
        String algo1 = (String) algoComboBox1.getSelectedItem();
        String algo2 = (String) algoComboBox2.getSelectedItem();
        String genType = (String) generatorComboBox.getSelectedItem();
        double entropy = 0.0;

        if (algo1.equals(algo2)) {
            JOptionPane.showMessageDialog(this, "Please choose two different algorithms.");
            return;
        }

        if ("Entropy".equals(genType)) {
            entropy = parseEntropy(entropyField.getText());
            if (entropy < 0) return;
        }

        new CompareWindow(algo1, algo2, genType, entropy);
        dispose();
    }
}
