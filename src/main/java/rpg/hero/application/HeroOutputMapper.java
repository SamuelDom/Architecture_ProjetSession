package rpg.hero.application;

import rpg.hero.domain.Hero;

public final class HeroOutputMapper {
    public HeroOutput toOutput(Hero hero) {
        return new HeroOutput(
                hero.id().toString(), hero.name().value(), hero.species().name(),
                hero.heroClass().name(), hero.level(), hero.experience(),
                hero.currentHitPoints(), hero.maxHitPoints(),
                hero.currentMagicPoints(), hero.maxMagicPoints());
    }
}
