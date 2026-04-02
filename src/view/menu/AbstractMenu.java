package view.menu;

import javax.swing.*;
import java.awt.*;

/**
 * Abstract base class for all menu screens in the application.
 * Provides shared colors, algorithm/generator lists, and factory methods.
 */
public abstract class AbstractMenu extends JFrame implements MenuInterface {

    protected static final Color BG = new Color(34, 40, 49);
    protected static final Color PANEL_BG = new Color(57, 62, 70);
    protected static final Color ACCENT = new Color(0, 173, 181);

    protected static final String[] ALGORITHMS = {
            "Comb Sort", "Bubble Sort", "Insertion Sort", "Merge Sort", "Quick Sort", "Bucket Sort", "Pancake Sort", "Cocktail Shaker Sort", "Bogo Sort"
    };

    protected static final String[] GENERATORS = { "Random", "Entropy", "Reverse Entropy" };

    /**
     * Creates the menu window with the given title and dimensions.
     *
     * @param title The title shown in the window's title bar.
     * @param width The width of the window in pixels.
     * @param height The height of the window in pixels.
     */
    public AbstractMenu(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    /**
     * Creates a bold centered title label with the standard white color.
     *
     * @param text The text to display as the title.
     * @return A styled JLabel ready to be added to the menu.
     */
    protected JLabel makeTitle(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("SansSerif", Font.BOLD, 22));
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        return label;
    }
    
    /**
     * Creates a white left-aligned label used for form field names.
     *
     * @param text The text to display on the label.
     * @return A styled JLabel ready to be added next to a form field.
     */
    protected JLabel makeFieldLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    
    /**
     * Creates a primary action button with the accent color background.
     *
     * @param text The text to display on the button.
     * @param width The preferred width of the button in pixels.
     * @param height The preferred height of the button in pixels.
     * @return A styled JButton with the accent color ready to use.
     */
    protected JButton makeAccentButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(ACCENT);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    /**
     * Creates a secondary navigation button with the default look.
     *
     * @param text The text to display on the button.
     * @param width The preferred width of the button in pixels.
     * @param height The preferred height of the button in pixels.
     * @return A styled JButton ready to use as a navigation action.
     */
    protected JButton makeNavButton(String text, int width, int height) {
        JButton btn = new JButton(text);
        btn.setPreferredSize(new Dimension(width, height));
        btn.setFocusPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 14));
        return btn;
    }

    /**
     * Creates a combo box with the given items.
     *
     * @param items The array of options to show in the dropdown.
     * @return A styled JComboBox ready to be added to the menu.
     */
    protected JComboBox<String> makeComboBox(String[] items) {
        JComboBox<String> combo = new JComboBox<>(items);
        combo.setMaximumSize(new Dimension(250, 35));
        combo.setAlignmentX(Component.CENTER_ALIGNMENT);
        return combo;
    }

    /**
     * Parses and validates an entropy value from a text input.
     * Shows an error dialog if the input is empty or outside the 0.0 to 1.0 range.
     *
     * @param text The raw string input from the entropy text field.
     * @return The parsed entropy value, or -1 if the input was invalid.
     */
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

    /**
     * Sets the entropy label and text field visibility to the selected generator type.
     * They are only shown when "Entropy" or "Reverse Entropy" is selected.
     *
     * @param generatorCombo The combo box used to select the generator type.
     * @param entropyLabel The label shown next to the entropy input field.
     * @param entropyField The text field where the user enters the entropy value.
     */
    protected void wireEntropyVisibility(JComboBox<String> generatorCombo,
                                          JLabel entropyLabel,
                                          JTextField entropyField) {
        generatorCombo.addActionListener(e -> {
            boolean needsEntropy = "Entropy".equals(generatorCombo.getSelectedItem())
                                || "Reverse Entropy".equals(generatorCombo.getSelectedItem());
            entropyLabel.setVisible(needsEntropy);
            entropyField.setVisible(needsEntropy);
        });
        boolean needsEntropy = "Entropy".equals(generatorCombo.getSelectedItem())
                            || "Reverse Entropy".equals(generatorCombo.getSelectedItem());
        entropyLabel.setVisible(needsEntropy);
        entropyField.setVisible(needsEntropy);
    }
}
