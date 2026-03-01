package view.menu;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractMenu extends JFrame implements MenuInterface {

    protected static final Color BG = new Color(34, 40, 49);
    protected static final Color PANEL_BG = new Color(57, 62, 70);
    protected static final Color ACCENT = new Color(0, 173, 181);

    protected static final String[] ALGORITHMS = {
            "Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Bucket Sort"
    };

    protected static final String[] GENERATORS = { "Random", "Entropy" };

    public AbstractMenu(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    protected JLabel makeTitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return label;
    }

    protected JLabel makeFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    protected JButton makeAccentButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    protected JButton makeNavButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    protected JComboBox<String> makeComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setMaximumSize(new Dimension(250, 35));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return combo;
    }

    protected double parseEntropy(String text) {
        if (text == null || text.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an entropy value between 0.0 and 1.0.");
            return -1;
        }
        try {
            double entropy = Double.parseDouble(text.trim());
            if (entropy < 0.0 || entropy > 1.0) 
            	throw new NumberFormatException();
            return entropy;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entropy must be a valid number between 0.0 and 1.0.");
            return -1;
        }
    }


    protected void wireEntropyVisibility(JComboBox<String> generatorCombo,
                                          JLabel entropyLabel,
                                          JTextField entropyField) {
        generatorCombo.addActionListener(e -> {
            boolean isEntropy = "Entropy".equals(generatorCombo.getSelectedItem());
            entropyLabel.setVisible(isEntropy);
            entropyField.setVisible(isEntropy);
        });
        boolean isEntropy = "Entropy".equals(generatorCombo.getSelectedItem());
        entropyLabel.setVisible(isEntropy);
        entropyField.setVisible(isEntropy);
    }
}
