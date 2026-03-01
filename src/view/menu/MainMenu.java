package view.menu;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends AbstractMenu {

    public MainMenu() {
        super("Sorting Algorithms Analyzer", 600, 420);
        buildUI();
        setVisible(true);
    }

    @Override
    public void buildUI() {
        getContentPane().setBackground(BG);
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
        centerPanel.setBackground(BG);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(40, 150, 40, 150));

        JButton singleAlgoBtn = makeAccentButton("Visualize and analyze 1 algorithm", 300, 45);
        JButton compareBtn    = makeAccentButton("Compare and visualize 2 algorithms", 300, 45);
        JButton exitBtn       = makeAccentButton("Exit", 300, 45);

        for (JButton btn : new JButton[]{singleAlgoBtn, compareBtn, exitBtn}) {
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(300, 45));
            centerPanel.add(btn);
            centerPanel.add(Box.createVerticalStrut(20));
        }

        add(centerPanel, BorderLayout.CENTER);

        singleAlgoBtn.addActionListener(e -> { new SingleAlgoMenu(); dispose(); });
        compareBtn.addActionListener(e -> { new CompareMenu(); dispose(); });
        exitBtn.addActionListener(e -> System.exit(0));
    }

    @Override
    public void open() { 
    	setVisible(true); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}
