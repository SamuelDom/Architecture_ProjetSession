package rpg.progression;

public final class LevelTable {
    public static final int MAX_LEVEL = 5;
    private static final int[] XP_REQUIRED = {0, 300, 900, 2700, 6500};

    public int levelForXp(int totalXp) {
        if (totalXp < 0) throw new IllegalArgumentException("L'expérience ne peut pas être négative.");
        int level = 1;
        for (int index = 0; index < XP_REQUIRED.length; index++) {
            if (totalXp >= XP_REQUIRED[index]) level = index + 1;
        }
        return level;
    }

    public int xpRequiredForLevel(int level) {
        if (level < 1 || level > MAX_LEVEL) {
            throw new IllegalArgumentException("Le niveau doit être entre 1 et " + MAX_LEVEL + ".");
        }
        return XP_REQUIRED[level - 1];
    }
}
