package Yatzy;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public class YatzyController {
    @FXML private TextField nameField;
    @FXML private Button dice0, dice1, dice2, dice3, dice4;
    @FXML private Label rollsLeftLabel, totalScoreLabel, highscoreLabel;
    @FXML private Button scoreButton;
    @FXML private Button rollButton;
    @FXML private Label points;
    @FXML private Label holdLabel;
    @FXML private ListView<String> scoreList;
    @FXML private HBox topBox;
    @FXML private Label gameOverLabel;

    private YatzyGame game;
    private String playerName;
    private boolean firstRollDone = false;
    private HighscoreManager highscoreManager = new HighscoreManager();
    private final List<Button> diceButtons = new ArrayList<>();

    @FXML
    public void initialize() {
        diceButtons.addAll(List.of(dice0, dice1, dice2, dice3, dice4));

        diceButtons.forEach(b -> b.setVisible(false));
        rollsLeftLabel.setVisible(false);
        scoreButton.setVisible(false);
        scoreList.setVisible(false);
        points.setVisible(false);
        holdLabel.setVisible(false);
        rollButton.setVisible(false);
        gameOverLabel.setVisible(false);

    }

    @FXML
    public void handleStartGame() {
        playerName = nameField.getText().trim();
        if (playerName.isEmpty()) return;
        topBox.setVisible(false);
        rollButton.setVisible(true);
        holdLabel.setVisible(true);
        points.setVisible(true);
        firstRollDone = false;

        game = new YatzyGame();
        nameField.setDisable(true);
        scoreList.getItems().clear();

        List<String> kategorier = List.of("Enere", "Toere", "Treere", "Firere", "Femmere", "Seksere", "Ett par", "To par", "Tre like", "Fire like", "Liten straight", "Stor straight", "Hus", "Sjanse", "Yatzy");

        for (int i = 0; i < 15; i++) {
            scoreList.getItems().add(kategorier.get(i));
        }

        diceButtons.forEach(b -> b.setVisible(true));
        rollsLeftLabel.setVisible(true);
        scoreButton.setVisible(true);
        scoreList.setVisible(true);

        updateGUI();
        updateHighscoreDisplay();
    }

    @FXML
    public void handleRollDice() {
        if (game == null) return;
        game.getHand().roll();
        game.roll();
        firstRollDone = true;
        updateGUI();
    }

    @FXML
    public void handleToggleDice(MouseEvent event) {
        if (game == null) return;
        Button clicked = (Button) event.getSource();
        int index = diceButtons.indexOf(clicked);

        if (index >= 0) {
            Dice die = game.getHand().getDice().get(index);
            die.setHeld(!die.isHeld());
            updateDiceDisplay();
        }
    }

    @FXML
    public void handleScoreRound() {
        if (game == null) return;

        firstRollDone = false;
        game.scoreCurrentRound();
        game.getHand().getDice().forEach(d -> d.setHeld(false));
        updateScoreList();
        updateGUI();

        if (game.isGameOver()) {
            int currentScore = game.getTotalScore();
            int bestScore = highscoreManager.getHighscore();
            
            diceButtons.forEach(b -> b.setVisible(false));
            rollsLeftLabel.setVisible(false);
            scoreButton.setVisible(false);
            holdLabel.setVisible(false);
            rollButton.setVisible(false);
            
            gameOverLabel.setVisible(true);


            if (currentScore > bestScore) {
                highscoreManager.saveHighscore(playerName, currentScore);
            }
            updateHighscoreDisplay();
        }
    }

    private void updateGUI() {
        updateDiceDisplay();
        rollsLeftLabel.setText("Antall kast igjen: " + game.getRollsLeft());
        totalScoreLabel.setText("Poengsum: " + game.getTotalScore());
        scoreButton.setDisable(game.getRollsLeft() > 0);
        rollButton.setDisable(game.getRollsLeft() <= 0);
        highlightCurrentRound();
    }

    private void updateDiceDisplay() {
        if (game == null) return;
    
        List<Dice> dice = game.getHand().getDice();
    
        for (int i = 0; i < diceButtons.size(); i++) {
            Button button = diceButtons.get(i);
            Dice d = dice.get(i);
    
            if (!firstRollDone) {
                button.setText("?");
                button.setStyle("-fx-font-size: 24px;");
            } else {
                String[] diceEmojis = {"⚀", "⚁", "⚂", "⚃", "⚄", "⚅"};
                button.setText(diceEmojis[d.getValue() - 1]);
                button.setStyle(
                    d.isHeld()
                        ? "-fx-background-color: gold; -fx-font-size: 24px; -fx-font-weight: bold;"
                        : "-fx-font-size: 24px;"
                );
            }
        }
    }
    

    private void updateScoreList() {
        List<Integer> scores = game.getScorePerCategory();
        scoreList.getItems().clear();

        List<String> kategorier = List.of("Enere", "Toere", "Treere", "Firere", "Femmere", "Seksere", "Ett par", "To par", "Tre like", "Fire like", "Liten straight", "Stor straight", "Hus", "Sjanse", "Yatzy");

        for (int i = 0; i < scores.size(); i++) {
            String scoreText = scores.get(i) != null ? scores.get(i).toString() : "-";
            scoreList.getItems().add(kategorier.get(i) + ": " + scoreText);
        }
    }

    private void highlightCurrentRound() {
        scoreList.getSelectionModel().clearSelection();
        scoreList.getSelectionModel().select(game.getCurrentRound());
    }

    private void updateHighscoreDisplay() {
        int score = highscoreManager.getHighscore();
        String name = highscoreManager.getHighscorename();
        highscoreLabel.setText("Highscore: " + name + " : " + score);
    }
}
