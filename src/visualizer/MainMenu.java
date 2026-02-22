package visualizer;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {

    public MainMenu() {
        setTitle("Sorting Algorithms Analyzer");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 420);
        setLocationRelativeTo(null);
        setResizable(false);

        buildUI();
        setVisible(true);
    }

    private void buildUI() {

        Color background = new Color(34, 40, 49);
        Color accent = new Color(0, 173, 181);

        getContentPane().setBackground(background);
        setLayout(new BorderLayout());

        JLabel title = new JLabel(
                "<html><center>Welcome to our Sorting Algorithms Analyzer!<br><br>" +
                "To begin a simulation, choose an action:</center></html>",
                SwingConstants.CENTER
        );
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(Color.WHITE);
        title.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(background);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        JButton singleAlgoBtn = new JButton("Visualize and analyze 1 algorithm");
        JButton compareBtn    = new JButton("Compare and visualize 2 algorithms");
        JButton exitBtn       = new JButton("Exit");

        JButton[] buttons = {singleAlgoBtn, compareBtn, exitBtn};
        for (JButton btn : buttons) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(300, 45));
            btn.setBackground(accent);
            btn.setForeground(Color.WHITE);
            btn.setFocusPainted(false);
            btn.setFont(new Font("SansSerif", Font.BOLD, 14));
            centerPanel.add(btn);
            centerPanel.add(Box.createVerticalStrut(20));
        }

        add(centerPanel, BorderLayout.CENTER);

        singleAlgoBtn.addActionListener(e -> {
            new SingleAlgoMenu();
            dispose();
        });

        compareBtn.addActionListener(e -> {
            new CompareMenu();
            dispose();
        });

        exitBtn.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
