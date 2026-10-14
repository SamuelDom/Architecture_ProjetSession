package hero.application;

import hero.domain.HeroClass;
import hero.domain.Species;

public record CreateHeroCommand(String name, Species species, HeroClass heroClass,
                                int strength, int dexterity, int constitution,
                                int intelligence, int wisdom, int charisma) {
}
