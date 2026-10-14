package rpg.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class LevelTableTest {
    private final LevelTable table = new LevelTable();

    @Test
    void usesTheExperienceThresholdsAndLevelCap() {
        assertEquals(1, table.levelForXp(299));
        assertEquals(2, table.levelForXp(300));
        assertEquals(3, table.levelForXp(950));
        assertEquals(5, table.levelForXp(999999));
        assertEquals(2700, table.xpRequiredForLevel(4));
    }

    @Test
    void rejectsInvalidExperienceAndLevels() {
        assertThrows(IllegalArgumentException.class, () -> table.levelForXp(-1));
        assertThrows(IllegalArgumentException.class, () -> table.xpRequiredForLevel(0));
        assertThrows(IllegalArgumentException.class, () -> table.xpRequiredForLevel(6));
    }
}
