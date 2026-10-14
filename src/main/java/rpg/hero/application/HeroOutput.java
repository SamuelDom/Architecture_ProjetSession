package rpg.hero.application;

public record HeroOutput(
        String id, String name, String species, String heroClass,
        int level, int experience,
        int currentHitPoints, int maxHitPoints,
        int currentMagicPoints, int maxMagicPoints) {
}
