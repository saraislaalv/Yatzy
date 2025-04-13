package Yatzy;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HighscoreManager {
    private static final String filename = "highscore.txt";
    private String highscoreName = "Ingen";
    private int highscore = 0;

    public HighscoreManager(){
        loadHighscore();
    }

    public void saveHighscore(String name, int score){
        highscore = score;
        highscoreName = name;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            writer.write(name + ": " + score);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadHighscore(){
        Path path = Paths.get(filename);
        if(!Files.exists(path)) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line = reader.readLine();
            if (line != null && line.contains(":")) {
                String[] parts = line.split(":");
                highscoreName = parts[0].trim();
                highscore = Integer.parseInt(parts[1].trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public String getHighscorename(){
        return highscoreName;
    }

    public int getHighscore(){
        return highscore;
    }
}
