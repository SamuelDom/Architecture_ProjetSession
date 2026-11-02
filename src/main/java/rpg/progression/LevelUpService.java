package rpg.progression;

import java.util.Objects;
import rpg.hero.domain.Hero;

public final class LevelUpService {
    private final LevelTable levelTable;

    public LevelUpService(LevelTable levelTable) {
        this.levelTable = Objects.requireNonNull(levelTable);
    }

    public LevelUpResult checkLevelUp(Hero hero) {
        Objects.requireNonNull(hero, "Le héros est requis.");
        int oldLevel = hero.level();
        int newLevel = levelTable.levelForXp(hero.getTotalXp());
        int hpGained = oldLevel < newLevel ? hero.applyLevelUp(newLevel) : 0;
        return new LevelUpResult(oldLevel, Math.max(oldLevel, newLevel), hpGained);
    }
}
