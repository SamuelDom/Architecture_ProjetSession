package com.rpg.progression;

public class LevelTable {

    public static final int MAX_LEVEL = 5;

    // (i = 0) = lvl 1
    private static final int[] XP_REQUIRED = {0, 300, 900, 2700, 6500};

    public int levelForXp(int totalXp) {
        if (totalXp < 0) {
            throw new IllegalArgumentException("XP peux pas etre negatif ???");
        }
        int level = 1;
        for (int i = 0; i < XP_REQUIRED.length; i++) {
            if (totalXp >= XP_REQUIRED[i]) {
                level = i + 1;
            }
        }
        return level;
    }

    public int xpRequiredForLevel(int level) {
        if (level < 1 || level > MAX_LEVEL) {
            throw new IllegalArgumentException("Level doit estre entre un et  " + MAX_LEVEL);
        }
        return XP_REQUIRED[level - 1];
    }
}