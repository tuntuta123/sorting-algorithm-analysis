package view.components;
import view.components.StatsBarGraphPanel;
import model.SortStats;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

import java.awt.event.*;

public class StatsWindow extends JFrame {

    public StatsWindow(SortStats... stats) {
        setTitle("Performance Statistics");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        buildUI(stats);
        setVisible(true);
    }

    private void buildUI(SortStats[] stats) {
        Color background = new Color(34, 40, 49);
        Color panelColor = new Color(57, 62, 70);
        Color accent     = new Color(0, 173, 181);
        Color textColor  = Color.WHITE;
        Color valueColor = new Color(200, 230, 255);

        getContentPane().setBackground(background);
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("Performance Statistics", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setForeground(textColor);
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, stats.length, 15, 0));
        contentPanel.setBackground(background);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        for (SortStats s : stats) {
            JPanel card = new JPanel();
            card.setBackground(panelColor);
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBorder(BorderFactory.createCompoundBorder(
                    new LineBorder(accent, 1, true),
                    BorderFactory.createEmptyBorder(20, 25, 20, 25)
            ));

            JLabel algoLabel = new JLabel(s.getAlgorithmName(), SwingConstants.CENTER);
            algoLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
            algoLabel.setForeground(accent);
            algoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel genLabel = new JLabel("Generator: " + s.getGeneratorLabel(), SwingConstants.CENTER);
            genLabel.setFont(new Font("SansSerif", Font.ITALIC, 12));
            genLabel.setForeground(new Color(160, 160, 160));
            genLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

            card.add(algoLabel);
            card.add(Box.createVerticalStrut(4));
            card.add(genLabel);
            card.add(Box.createVerticalStrut(12));
            card.add(makeSeparator());
            card.add(Box.createVerticalStrut(16));
            card.add(makeRow("Comparisons", String.format("%,d", s.getComparisons()), panelColor, textColor, valueColor));
            card.add(Box.createVerticalStrut(10));
            card.add(makeRow("Swaps",       String.format("%,d", s.getSwaps()),       panelColor, textColor, valueColor));
            card.add(Box.createVerticalStrut(10));
            card.add(makeRow("Accesses",    String.format("%,d", s.getAccesses()),    panelColor, textColor, valueColor));
            card.add(makeRow("Time",        s.getElapsedMs() + " ms",                panelColor, textColor, valueColor));

            contentPanel.add(card);
        }

	JPanel bottomContainer = new JPanel(new BorderLayout());
	bottomContainer.setBackground(background);

	JPanel graphsPanel = new JPanel();
	graphsPanel.setLayout(new GridLayout(2,1,10,10));
	graphsPanel.setBackground(background);

	RealtimeGraphPanel comparisonsGraph = new RealtimeGraphPanel("comparisons", stats);
	RealtimeGraphPanel swapsGraph = new RealtimeGraphPanel("swaps", stats);
	graphsPanel.add(comparisonsGraph);
	graphsPanel.add(swapsGraph);
	graphsPanel.add(new StatsBarGraphPanel("time", stats));
	graphsPanel.add(new StatsBarGraphPanel("accesses", stats));
	bottomContainer.add(graphsPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(background);
        JButton closeBtn = new JButton("Close");
        closeBtn.setBackground(accent);
        closeBtn.setForeground(Color.WHITE);
        closeBtn.setFocusPainted(false);
        closeBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        closeBtn.setPreferredSize(new Dimension(100, 35));
        closeBtn.addActionListener(e -> dispose());
        bottomPanel.add(closeBtn);
        bottomContainer.add(bottomPanel, BorderLayout.SOUTH);
        
	JPanel mainPanel = new JPanel();
	mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
	mainPanel.setBackground(background);
	mainPanel.add(contentPanel);
	mainPanel.add(Box.createVerticalStrut(20));
	mainPanel.add(bottomContainer);
	add(mainPanel, BorderLayout.CENTER);



	setSize(stats.length == 1 ? 1000 : 1500, 1000);

	addWindowListener(new WindowAdapter() {
	    @Override
	    public void windowClosing(WindowEvent e) {
		comparisonsGraph.stopTimer();
		swapsGraph.stopTimer();
	    }

	    @Override
	    public void windowClosed(WindowEvent e) {
		comparisonsGraph.stopTimer();
		swapsGraph.stopTimer();
	    }
	});

    }

    private JPanel makeRow(String labelText, String value, Color bg, Color fg, Color valueColor) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(bg);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        JLabel label = new JLabel(labelText + ":");
        label.setFont(new Font("SansSerif", Font.PLAIN, 13));
        label.setForeground(fg);
        JLabel valueLabel = new JLabel(value, SwingConstants.RIGHT);
        valueLabel.setFont(new Font("Monospaced", Font.BOLD, 14));
        valueLabel.setForeground(valueColor);
        row.add(label, BorderLayout.WEST);
        row.add(valueLabel, BorderLayout.EAST);
        return row;
    }

    private JSeparator makeSeparator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(80, 90, 100));
        return sep;
    }
}
