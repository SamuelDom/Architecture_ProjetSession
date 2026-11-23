package heros.application;

import heros.domain.HeroClass;
import heros.domain.Species;

public record CreateHeroCommand(String name, Species species, HeroClass heroClass,
        int strength, int dexterity, int constitution,
        int intelligence, int wisdom, int charisma) {
}
