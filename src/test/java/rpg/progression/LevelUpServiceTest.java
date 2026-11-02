package rpg.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import rpg.hero.domain.Characteristics;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroClass;
import rpg.hero.domain.HeroFactory;
import rpg.hero.domain.HeroName;
import rpg.hero.domain.Species;
import rpg.hero.infrastructure.InMemoryHeroRepository;

class LevelUpServiceTest {
    @Test
    void awardsHitPointsWhenExperienceCrossesLevelThresholds() {
        var repository = new InMemoryHeroRepository();
        Hero hero = new HeroFactory(repository).create(
                new HeroName("Thorin"), Species.DWARF, HeroClass.WARRIOR,
                new Characteristics(14, 12, 16, 8, 10, 13));
        hero.takeDamage(5);
        hero.gainXp(900);

        LevelUpResult result = new LevelUpService(new LevelTable()).checkLevelUp(hero);

        assertTrue(result.leveledUp());
        assertEquals(3, result.newLevel());
        assertEquals(20, result.hpGained());
        assertEquals(36, hero.maxHitPoints());
        assertEquals(31, hero.currentHitPoints());
    }
}
