package main;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class HighScoresFrame extends JFrame {
    public HighScores highScores;

    public HighScoresFrame(HighScores highScores) {
        this.highScores = highScores;
        setTitle("High Scores");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panel = new JPanel(new GridLayout(highScores.getScores().size(), 1));

        for (Map.Entry<String, Integer> entry : highScores.getScores().entrySet()) {
            String username = entry.getKey();
            int score = entry.getValue();
            JLabel label = new JLabel(username + ": " + score);
            panel.add(label);
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setPreferredSize(new Dimension(300, 400));
        setContentPane(scrollPane);

        pack();
        setLocationRelativeTo(null);
    }
}
