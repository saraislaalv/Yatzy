package Yatzy;

import java.util.Random;

public class Dice implements Rollable{
    private int value;
    private boolean held = false;

    public Dice(){
        roll();
    }

    public void roll(){
        if(!held){
            Random rand = new Random();
            this.value= rand.nextInt(6) + 1;
        }
    }

    public int getValue() {
        return value;
    }

    public boolean isHeld() {
        return held;
    }
    
    public void setHeld(boolean held) {
        this.held = held;
    }
}
