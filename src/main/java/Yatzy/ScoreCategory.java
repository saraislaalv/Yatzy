package Yatzy;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreCategory{
    private DiceHand hand;

    public ScoreCategory(DiceHand hand){
        this.hand = hand;
    }

    private int sumOf(int target) {
        int sum = 0;
        for (int i : hand.getValues()) {
            if (i == target) sum += i;
        }
        return sum;
    }
    
    public int ones(){
        return sumOf(1);
    }
    public int twos(){
        return sumOf(2);

    }
    public int threes(){
        return sumOf(3);

    }
    public int fours(){
        return sumOf(4);

    }
    public int fives(){
        return sumOf(5);

    }
    public int sixes(){
        return sumOf(6);

    }
    public int onePair(){
        Map<Integer, Integer> counts = new HashMap<>();

        for(int value: hand.getValues()){
            counts.put(value, counts.getOrDefault(value, 0)+1);
        }

        int highestPair=0;

        for(int value: counts.keySet()){
            if(counts.get(value) >= 2 && value > highestPair){
                highestPair= value;
            }
        }

        return highestPair*2;
    }

    public int twoPairs() {
        Map<Integer, Integer> counts = new HashMap<>();

        for (int value : hand.getValues()) {
            counts.put(value, counts.getOrDefault(value, 0) + 1);
        }

        List<Integer> pairs = new ArrayList<>();

        for (int value : counts.keySet()) {
            if (counts.get(value) >= 2) {
                pairs.add(value);
            }
        }

        if (pairs.size() < 2) {
            return 0; 
        }

        pairs.sort(Comparator.reverseOrder());
        int pair1 = pairs.get(0);
        int pair2 = pairs.get(1);

        return (pair1 * 2) + (pair2 * 2);
    }

    public int threeAlike(){
        Map<Integer, Integer> counts = new HashMap<>();

        for(int value: hand.getValues()){
            counts.put(value, counts.getOrDefault(value, 0)+1);
        }

        int threeAlike=0;

        for(int value: counts.keySet()){
            if(counts.get(value) >= 3 && value > threeAlike){
                threeAlike= value;
            }
        }

        return threeAlike*3;
    }

    public int fourAlike(){
        Map<Integer, Integer> counts = new HashMap<>();

        for(int value: hand.getValues()){
            counts.put(value, counts.getOrDefault(value, 0)+1);
        }

        int fourAlike=0;

        for(int value: counts.keySet()){
            if(counts.get(value) >= 4 && value > fourAlike){
                fourAlike= value;
            }
        }

        return fourAlike*4;
    }
    public int littleStraight() {
        List<Integer> expected = List.of(1, 2, 3, 4, 5);
        List<Integer> values = new ArrayList<>(hand.getValues());
        values.sort(Integer::compareTo);
        return values.equals(expected) ? 15 : 0;
    }
    
    public int bigStraight() {
        List<Integer> expected = List.of(2, 3, 4, 5, 6);
        List<Integer> values = new ArrayList<>(hand.getValues());
        values.sort(Integer::compareTo);
        return values.equals(expected) ? 20 : 0;
    }
    public int house() {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int value : hand.getValues()) {
            counts.put(value, counts.getOrDefault(value, 0) + 1);
        }
    
        int three = 0;
        int two = 0;
    
        for (int value : counts.keySet()) {
            int count = counts.get(value);
            if (count >= 3 && value > three) {
                three = value;
            }
        }
    
        if (three != 0) {
            counts.put(three, counts.get(three) - 3);
            for (int value : counts.keySet()) {
                if (counts.get(value) >= 2 && value > two) {
                    two = value;
                }
            }
        }
    
        if (three != 0 && two != 0) {
            return three * 3 + two * 2;
        }
    
        return 0;
    }
    
    

    public int chance(){
        int sum = 0;
        for(int i: hand.getValues()){
            sum+=i;
        }
        return sum;

    }
    public int yatzy() {
        int first = hand.getValues().get(0);
        if (hand.getValues().stream().allMatch(i -> i == first)) {
            return 50;
        }
        return 0;
    }
    
}
