package Yatzy;

import java.util.ArrayList;
import java.util.List;

public class DiceHand implements Rollable{
    private List<Dice> hand;

    public DiceHand(){
        hand = new ArrayList<>();
        for(int i=0;i<5;i++){
            hand.add(new Dice());
        }
    }

    public void roll() {
        for (Dice die : hand) {
            if (!die.isHeld()) {
                die.roll();
            }
        }
    }

    public void reset() {
        for (Dice d : hand) {
            d.setHeld(false);
            d.roll();
        }
    }

    public List <Dice> getDice(){
        return hand;
    }

    public List<Integer> getValues(){
        List<Integer> values = new ArrayList<>();
        for(Dice dice:hand){
            values.add(dice.getValue());
        }
        return values;
    }
}
