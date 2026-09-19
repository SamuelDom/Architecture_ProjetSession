public class HeroFactory {
    public Hero create(String name, Species species, HeroClass heroClass, Characteristics raw) {
        Characteristics finalStats = species.applyTo(raw);
        // TODO: build the Hero, add starting items, equip them
        return null;
    }
}