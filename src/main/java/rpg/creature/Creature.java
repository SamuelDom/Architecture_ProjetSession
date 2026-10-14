package rpg.creature;

import java.util.Objects;
import rpg.combat.Combatant;
import rpg.combat.Dice;

public final class Creature implements Combatant {
    private final String name;
    private final int maxHitPoints;
    private final int experienceReward;
    private final int initiative;
    private final int baseAttack;
    private final int weaponDamage;
    private final String weaponName;
    private final Dice dice;
    private int currentHitPoints;
    private int initialArmor;
    private int currentArmor;
    private boolean stunned;

    public Creature(String name, int maxHitPoints, int initialArmor, int experienceReward,
                    int initiative, int baseAttack, String weaponName) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Le nom de la créature est requis.");
        if (maxHitPoints < 1 || initialArmor < 0 || experienceReward < 0) {
            throw new IllegalArgumentException("Les PV, l'armure et l'expérience de la créature sont invalides.");
        }
        this.name = name;
        this.maxHitPoints = maxHitPoints;
        this.currentHitPoints = maxHitPoints;
        this.initialArmor = initialArmor;
        this.currentArmor = initialArmor;
        this.experienceReward = experienceReward;
        this.initiative = initiative;
        this.baseAttack = baseAttack;
        this.weaponName = Objects.requireNonNull(weaponName);
        this.weaponDamage = new CreatureWeapons().getWeaponDamage(weaponName);
        this.dice = new Dice();
    }

    public String getNom() { return name; }
    @Override public String nameForCombat() { return name; }
    public int getXp() { return experienceReward; }
    public int getInitiative() { return initiative; }
    public int getCurrentHealth() { return currentHitPoints; }
    public int getMaxHealth() { return maxHitPoints; }
    public int getCurrentArmor() { return currentArmor; }
    public int getInitialArmor() { return initialArmor; }
    @Override public int currentHitPoints() { return currentHitPoints; }
    @Override public int currentArmor() { return currentArmor; }
    @Override public boolean isAlive() { return currentHitPoints > 0; }
    @Override public boolean isStunned() { return stunned; }
    @Override public void setStunned(boolean stunned) { this.stunned = stunned; }

    public void takeDamage(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Les dégâts ne peuvent pas être négatifs.");
        currentHitPoints = Math.max(0, currentHitPoints - amount);
    }

    public void heal(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Le soin ne peut pas être négatif.");
        currentHitPoints = Math.min(maxHitPoints, currentHitPoints + amount);
    }

    @Override
    public void increaseArmor(int amount) {
        if (amount < 0) throw new IllegalArgumentException("Le bonus d'armure ne peut pas être négatif.");
        currentArmor = Math.addExact(currentArmor, amount);
    }

    @Override
    public void decreaseArmor(int amount) {
        if (amount < 0) throw new IllegalArgumentException("La réduction d'armure ne peut pas être négative.");
        currentArmor = Math.max(0, currentArmor - amount);
    }

    public void resetArmor() { currentArmor = initialArmor; }

    public int testAttack() {
        return weaponDamage + dice.roll(6) + baseAttack;
    }

    public Creature copy() {
        return new Creature(name, maxHitPoints, initialArmor, experienceReward,
                initiative, baseAttack, weaponName);
    }
}
