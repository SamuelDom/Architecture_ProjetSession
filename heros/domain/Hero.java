package hero.domain;

import java.util.Objects;

public final class Hero {

    private final HeroId id;
    private final HeroName name;
    private final Species species;
    private final HeroClass heroClass;
    private final Characteristics characteristics;

    private int level;
    private int experience;

    private final int maxHitPoints;
    private int currentHitPoints;
    private final int maxMagicPoints;
    private int currentMagicPoints;

    Hero(HeroId id, HeroName name, Species species, HeroClass heroClass, Characteristics characteristics) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.species = Objects.requireNonNull(species);
        this.heroClass = Objects.requireNonNull(heroClass);
        this.characteristics = Objects.requireNonNull(characteristics);

        this.level = 1;
        this.experience = 0;

        // PV max niveau 1 = PV de base + modificateur de Constitution, minimum 1
        this.maxHitPoints = Math.max(1, heroClass.basePv() + characteristics.constitutionModifier());
        this.currentHitPoints = this.maxHitPoints;

        this.maxMagicPoints = heroClass.baseMagic();
        this.currentMagicPoints = this.maxMagicPoints;
    }

    public HeroId id() { return id; }
    public HeroName name() { return name; }
    public Species species() { return species; }
    public HeroClass heroClass() { return heroClass; }
    public int level() { return level; }
    public int experience() { return experience; }
    public int maxHitPoints() { return maxHitPoints; }
    public int currentHitPoints() { return currentHitPoints; }
    public int maxMagicPoints() { return maxMagicPoints; }
    public int currentMagicPoints() { return currentMagicPoints; }

    public boolean isDefeated() {
        return currentHitPoints == 0;
    }

    public void takeDamage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Les dégâts ne peuvent pas être négatifs.");
        }
        currentHitPoints = Math.max(0, currentHitPoints - amount);
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Le soin ne peut pas être négatif.");
        }
        currentHitPoints = Math.min(maxHitPoints, currentHitPoints + amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Hero other)) return false;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
