package Yatzy;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import org.junit.jupiter.api.Test;

public class ScoreCategoryTest {

    // Mock DiceHand med faste verdier
    static class MockDiceHand extends DiceHand {
        private List<Integer> fixedValues;

        public MockDiceHand(List<Integer> values) {
            this.fixedValues = values;
        }

        @Override
        public List<Integer> getValues() {
            return fixedValues;
        }
    }

    @Test
    public void testHouseValid() {
        ScoreCategory sc = new ScoreCategory(new MockDiceHand(List.of(2, 2, 3, 3, 3)));
        assertEquals(13, sc.house());
    }

    @Test
    public void testHouseInvalid() {
        ScoreCategory sc = new ScoreCategory(new MockDiceHand(List.of(2, 2, 2, 2, 5)));
        assertEquals(0, sc.house());
    }

    @Test
    public void testLittleStraight() {
        ScoreCategory sc = new ScoreCategory(new MockDiceHand(List.of(1, 2, 3, 4, 5)));
        assertEquals(15, sc.littleStraight());
    }

    @Test
    public void testBigStraight() {
        ScoreCategory sc = new ScoreCategory(new MockDiceHand(List.of(2, 3, 4, 5, 6)));
        assertEquals(20, sc.bigStraight());
    }

    @Test
    public void testChance() {
        ScoreCategory sc = new ScoreCategory(new MockDiceHand(List.of(6, 3, 2, 4, 1)));
        assertEquals(16, sc.chance());
    }
}
