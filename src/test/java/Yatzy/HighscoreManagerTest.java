package Yatzy;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HighscoreManagerTest {

    private final Path path = Path.of("highscore.txt");

    @Test
    public void testSaveAndLoadHighscore() throws IOException {
        Files.deleteIfExists(path);

        HighscoreManager manager = new HighscoreManager();
        manager.saveHighscore("Test", 123);

        HighscoreManager newManager = new HighscoreManager();
        assertEquals(123, newManager.getHighscore());
        assertEquals("Test", newManager.getHighscorename());

        Files.deleteIfExists(path);
    }
}
