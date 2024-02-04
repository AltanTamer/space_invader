package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HighScores {
    private static final String HIGH_SCORES_FILE = "highscores.txt";
    private Map<String, Integer> scores;

    public HighScores() {
        scores = loadScores();
    }

    private Map<String, Integer> loadScores() {
        Map<String, Integer> loadedScores = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(HIGH_SCORES_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    String username = parts[0];
                    int score = Integer.parseInt(parts[1]);
                    loadedScores.put(username, score);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return loadedScores;
    }

    private void saveScores() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HIGH_SCORES_FILE))) {
            for (Map.Entry<String, Integer> entry : scores.entrySet()) {
                String username = entry.getKey();
                int score = entry.getValue();
                writer.println(username + "," + score);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getScore(String username) {
        return scores.getOrDefault(username, 0);
    }

    public void updateScore(String username, int score) {
        int currentScore = getScore(username);
        if (score > currentScore) {
            scores.put(username, score);
            saveScores();
        }
    }

    public void addNewUser(String username) {
        scores.put(username, null);
    }
    
    public Map<String, Integer> getScores() {
        return scores;
    }
}