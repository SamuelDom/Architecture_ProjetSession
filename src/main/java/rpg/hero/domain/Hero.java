package rpg.hero.domain;

import java.util.Objects;
import rpg.combat.Combatant;
import rpg.item.Armor;
import rpg.item.Equipment;
import rpg.item.Inventory;
import rpg.item.Weapon;
import rpg.progression.LevelTable;
import rpg.progression.XpReceiver;

public final class Hero implements Combatant, XpReceiver {
    private final HeroId id;
    private final HeroName name;
    private final Species species;
    private final HeroClass heroClass;
    private final Characteristics characteristics;
    private final Inventory inventory = new Inventory();
    private final Equipment equipment = new Equipment();
    private int level = 1;
    private int experience;
    private int maxHitPoints;
    private int currentHitPoints;
    private final int maxMagicPoints;
    private int currentMagicPoints;
    private int armorBonus;
    private boolean stunned;

    Hero(HeroId id, HeroName name, Species species, HeroClass heroClass,
         Characteristics characteristics) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.species = Objects.requireNonNull(species);
        this.heroClass = Objects.requireNonNull(heroClass);
        this.characteristics = Objects.requireNonNull(characteristics);
        this.maxHitPoints = heroClass.calculateMaxHitPoints(
                effectiveConstitutionModifier());
        this.currentHitPoints = maxHitPoints;
        this.maxMagicPoints = heroClass.baseMagic();
        this.currentMagicPoints = maxMagicPoints;
    }

    public HeroId id() { return id; }
    public HeroName name() { return name; }
    public Species species() { return species; }
    public HeroClass heroClass() { return heroClass; }
    public Characteristics characteristics() { return characteristics; }
    public Inventory inventory() { return inventory; }
    public Equipment equipment() { return equipment; }
    public int level() { return level; }
    @Override public int getTotalXp() { return experience; }
    public int experience() { return experience; }
    @Override public int currentHitPoints() { return currentHitPoints; }
    public int maxHitPoints() { return maxHitPoints; }
    public int currentMagicPoints() { return currentMagicPoints; }
    public int maxMagicPoints() { return maxMagicPoints; }

    @Override public String nameForCombat() { return name.value(); }
    @Override
    public int currentArmor() {
        Armor armor = equipment.getMainArmor();
        int baseArmorClass = armor == null ? 10 : armor.getBaseArmorClass();
        int dexterityBonus = armor == null || armor.usesDexterityModifier()
                ? effectiveDexterityModifier() : 0;
        return baseArmorClass + dexterityBonus + armorBonus;
    }
    @Override public boolean isStunned() { return stunned; }
    @Override public void setStunned(boolean stunned) { this.stunned = stunned; }

    public int strengthModifier() {
        return characteristics.modifierOf(characteristics.strength() + species.strengthBonus());
    }

    public int dexterityModifier() {
        return characteristics.modifierOf(characteristics.dexterity() + species.dexterityBonus());
    }

    public int abilityModifier(String ability) {
        return switch (ability.toUpperCase()) {
            case "FOR", "STR" -> strengthModifier();
            case "DEX" -> dexterityModifier();
            case "CON" -> effectiveConstitutionModifier();
            case "INT" -> characteristics.modifierOf(
                    characteristics.intelligence() + species.intelligenceBonus());
            case "SAG", "WIS" -> characteristics.modifierOf(
                    characteristics.wisdom() + species.wisdomBonus());
            case "CHA" -> characteristics.modifierOf(
                    characteristics.charisma() + species.charismaBonus());
            default -> throw new IllegalArgumentException("Caractéristique d'attaque inconnue : " + ability);
        };
    }

    public int attackModifier() {
        Weapon weapon = equipment.getMainWeapon();
        return abilityModifier(weapon == null ? "FOR" : weapon.getAbility());
    }

    private int effectiveDexterityModifier() {
        return dexterityModifier();
    }

    private int effectiveConstitutionModifier() {
        return characteristics.modifierOf(
                characteristics.constitution() + species.constitutionBonus());
    }

    @Override
    public boolean isAlive() { return currentHitPoints > 0; }

    public boolean isDefeated() { return !isAlive(); }

    @Override
    public void takeDamage(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Les dégâts ne peuvent pas être négatifs.");
        currentHitPoints = Math.max(0, currentHitPoints - amount);
    }

    public void heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Le soin ne peut pas être négatif.");
        currentHitPoints = Math.min(maxHitPoints, currentHitPoints + amount);
    }

    public void increaseArmor(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Le bonus d'armure ne peut pas être négatif.");
        armorBonus += amount;
    }

    public void decreaseArmor(int amount) {
        if (amount < 0) throw new IllegalArgumentException("La réduction d'armure ne peut pas être négative.");
        armorBonus = Math.max(0, armorBonus - amount);
    }

    @Override
    public void gainXp(int amount) {
        if (amount < 0) throw new IllegalArgumentException("L'expérience ne peut pas être négative.");
        experience = Math.addExact(experience, amount);
    }

    public int applyLevelUp(int newLevel) {
        if (newLevel < level || newLevel > LevelTable.MAX_LEVEL) {
            throw new IllegalArgumentException("Le nouveau niveau doit être supérieur ou égal au niveau actuel.");
        }
        int gainedLevels = newLevel - level;
        int hitPointsGained = gainedLevels
                * Math.max(1, heroClass.basePv() / 2 + effectiveConstitutionModifier());
        maxHitPoints += hitPointsGained;
        currentHitPoints = Math.min(maxHitPoints, currentHitPoints + hitPointsGained);
        level = newLevel;
        return hitPointsGained;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Hero hero && id.equals(hero.id);
    }

    @Override
    public int hashCode() { return id.hashCode(); }
}
