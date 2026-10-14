package com.rpg.progression;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// *generer avec IA pour tester

class LevelTableTest {

    private final LevelTable table = new LevelTable();

    @Test
    void zeroXpIsLevel1() {
        assertEquals(1, table.levelForXp(0));
    }

    @Test
    void justBelowThresholdStaysAtLowerLevel() {
        assertEquals(1, table.levelForXp(299));
    }

    @Test
    void exactThresholdReachesNextLevel() {
        assertEquals(2, table.levelForXp(300));
    }

    @Test
    void canSkipSeveralLevelsAtOnce() {
        assertEquals(3, table.levelForXp(950));
    }

    @Test
    void levelIsCappedAtFive() {
        assertEquals(5, table.levelForXp(6500));
        assertEquals(5, table.levelForXp(999999));
    }

    @Test
    void negativeXpIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> table.levelForXp(-1));
    }

    @Test
    void xpRequiredForLevelReturnsThreshold() {
        assertEquals(0, table.xpRequiredForLevel(1));
        assertEquals(2700, table.xpRequiredForLevel(4));
    }

    @Test
    void invalidLevelIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> table.xpRequiredForLevel(0));
        assertThrows(IllegalArgumentException.class, () -> table.xpRequiredForLevel(6));
    }
}