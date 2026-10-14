package rpg.hero.application;

import rpg.hero.domain.HeroClass;
import rpg.hero.domain.Species;

public record CreateHeroCommand(
        String name, Species species, HeroClass heroClass,
        int strength, int dexterity, int constitution,
        int intelligence, int wisdom, int charisma) {
}
