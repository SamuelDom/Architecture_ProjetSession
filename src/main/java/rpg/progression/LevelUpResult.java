package rpg.progression;

public record LevelUpResult(int oldLevel, int newLevel, int hpGained) {
    public boolean leveledUp() { return newLevel > oldLevel; }
}
