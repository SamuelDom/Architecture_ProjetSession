package domain.hero;

import java.util.Objects;
import java.util.UUID;

public final class Hero {

    private final UUID id;
    private HeroName name;
    private final Species species;
    private final HeroClass heroClass;
    private final Characteristics characteristics;

    private int level;
    private int experience;

    private final int maxHitPoints;
    private int currentHitPoints;
    private final int maxMagicPoints;
    private int currentMagicPoints;

    private Hero(UUID id, HeroName name, Species species, HeroClass heroClass,
                  Characteristics characteristics, int maxHitPoints, int maxMagicPoints) {
        this.id = id;
        this.name = name;
        this.species = species;
        this.heroClass = heroClass;
        this.characteristics = characteristics;
        this.level = 1;
        this.experience = 0;
        this.maxHitPoints = maxHitPoints;
        this.currentHitPoints = maxHitPoints;   // règle : PV actuels = max à la création
        this.maxMagicPoints = maxMagicPoints;
        this.currentMagicPoints = maxMagicPoints; // règle : magie actuelle = max à la création
    }


    public static Hero create(String rawName, Species species, HeroClass heroClass,
                               Characteristics characteristics) {
        Objects.requireNonNull(species, "L'espèce est requise.");
        Objects.requireNonNull(heroClass, "La classe est requise.");
        Objects.requireNonNull(characteristics, "Les caractéristiques sont requises.");

        HeroName validatedName = HeroName.of(rawName);

        // PV max niveau 1 = PV de base de la classe + modificateur de Constitution, min 1.
        int constitutionModifier = characteristics.constitutionModifier();
        int maxHitPoints = Math.max(1, heroClass.basePv() + constitutionModifier);

        // Magie max niveau 1 = magie de base de la classe (0 pour Guerrier/Rôdeur).
        int maxMagicPoints = Math.max(0, heroClass.baseMagic());

        return new Hero(UUID.randomUUID(), validatedName, species, heroClass,
                characteristics, maxHitPoints, maxMagicPoints);
    }

    public UUID id() {
        return id;
    }

    public String name() {
        return name.value();
    }

    public Species species() {
        return species;
    }

    public HeroClass heroClass() {
        return heroClass;
    }

    public int level() {
        return level;
    }

    public int experience() {
        return experience;
    }

    public int maxHitPoints() {
        return maxHitPoints;
    }

    public int currentHitPoints() {
        return currentHitPoints;
    }

    public int maxMagicPoints() {
        return maxMagicPoints;
    }

    public int currentMagicPoints() {
        return currentMagicPoints;
    }

    public boolean isDefeated() {
        return currentHitPoints <= 0;
    }

    public void takeDamage(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Les dégâts ne peuvent pas être négatifs.");
        }
        this.currentHitPoints = Math.max(0, this.currentHitPoints - amount);
    }

    public void heal(int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Le soin ne peut pas être négatif.");
        }
        this.currentHitPoints = Math.min(this.maxHitPoints, this.currentHitPoints + amount);
    }
}
