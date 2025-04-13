package Yatzy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class YatzyGame implements Rollable{
    private DiceHand hand;
    private ScoreCategory scoreCategory;
    private int round;
    private int rollsLeft;
    private List<Integer> scorePerCategory;

    public YatzyGame() {
        this.hand = new DiceHand();
        this.scoreCategory = new ScoreCategory(hand);
        startNewGame();
    }

    public void startNewGame(){
        this.round=0;
        this.rollsLeft=3;
        this.hand.reset();
        this.scorePerCategory= new ArrayList<>(Collections.nCopies(15, null));
    }
    public void roll(){
        if(rollsLeft>0){
            hand.roll();
            rollsLeft--;
        }
    }
    public void scoreCurrentRound(){
        int score = getScoreForCurrentCategory();
        scorePerCategory.set(round,score);
        round++;
        rollsLeft=3;
        hand.reset();
    }
    public int getScoreForCurrentCategory() {
        switch (round) {
            case 0: return scoreCategory.ones();
            case 1: return scoreCategory.twos();
            case 2: return scoreCategory.threes();
            case 3: return scoreCategory.fours();
            case 4: return scoreCategory.fives();
            case 5: return scoreCategory.sixes();
            case 6: return scoreCategory.onePair();
            case 7: return scoreCategory.twoPairs();
            case 8: return scoreCategory.threeAlike();
            case 9: return scoreCategory.fourAlike();
            case 10: return scoreCategory.littleStraight();
            case 11: return scoreCategory.bigStraight();
            case 12: return scoreCategory.house();
            case 13: return scoreCategory.chance();
            case 14: return scoreCategory.yatzy();
            default: return 0;
        }
    }
    
    public int getTotalScore() {
        int sum = 0;
        for (Integer score : scorePerCategory) {
            if (score != null) sum += score;
        }
        return sum;
    }

    public DiceHand getHand() {
        return hand;
    }
    
    public int getRollsLeft() {
        return rollsLeft;
    }
    
    public int getCurrentRound() {
        return round;
    }
    
    public List<Integer> getScorePerCategory() {
        return scorePerCategory;
    }

    public boolean isGameOver() {
        return round >= 15;
    }
    
}
