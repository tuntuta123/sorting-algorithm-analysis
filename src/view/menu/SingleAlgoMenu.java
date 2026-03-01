package view.menu;

import view.window.VisualizerWindow;
import javax.swing.*;
import java.awt.*;

public class SingleAlgoMenu extends AbstractMenu {

    private JComboBox<String> algoComboBox;
    private JComboBox<String> generatorComboBox;
    private JTextField entropyField;
    private JLabel entropyLabel;

    public SingleAlgoMenu() {
        super("Sorting Visualizer", 600, 480);
        buildUI();
        setVisible(true);
    }

    @Override
    public void buildUI() {
        getContentPane().setBackground(BG);
        setLayout(new BorderLayout(10, 10));

        add(makeTitle("Sorting Algorithm Visualizer"), BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(PANEL_BG);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(30, 80, 30, 80));

        algoComboBox = makeComboBox(ALGORITHMS);
        generatorComboBox = makeComboBox(GENERATORS);

        entropyLabel = makeFieldLabel("Enter Entropy (0.0 - 1.0):");
        entropyField = new JTextField();
        entropyField.setMaximumSize(new Dimension(250, 35));
        entropyField.setAlignmentX(Component.CENTER_ALIGNMENT);

        wireEntropyVisibility(generatorComboBox, entropyLabel, entropyField);

        centerPanel.add(makeFieldLabel("Choose Algorithm:"));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(algoComboBox);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(makeFieldLabel("Choose Generator:"));
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(generatorComboBox);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(entropyLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(entropyField);

        add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(BG);

        JButton startBtn = makeAccentButton("Visualize", 150, 40);
        startBtn.addActionListener(e -> open());

        JButton backBtn = makeNavButton("Return to Main Menu", 200, 40);
        backBtn.addActionListener(e -> { new MainMenu(); dispose(); });

        bottomPanel.add(startBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void open() {
        String algo = (String) algoComboBox.getSelectedItem();
        String genType = (String) generatorComboBox.getSelectedItem();
        double entropy = 0.0;

        if ("Entropy".equals(genType)) {
            entropy = parseEntropy(entropyField.getText());
            if (entropy < 0) 
            	return;
        }

        new VisualizerWindow(algo, genType, entropy);
        dispose();
    }
}
