package Créatures;

public class Creature {
    private String nom;
    private Stats.Health health;
    private Stats.CapaciteArmure capaciteArmure;
    private int xp;
    private int initiative;
    private int baseAttack;

    public Creature(String nom, int maxHealth, int initialArmor, int xp, int initiative, int baseAttack) {
        this.nom = nom;
        this.health = new Stats.Health(maxHealth);
        this.capaciteArmure = new Stats.CapaciteArmure(initialArmor);
        this.xp = xp;
        this.initiative = initiative;
        this.baseAttack = baseAttack;
    }

    public String getNom() {
        return nom;
    }

    public int getXp() {
        return xp;
    }

    public int getInitiative() {
        return initiative;
    }

    public int getBaseAttack() {
        return baseAttack;
    }

    public void takeDamage(int damage) {
        health.takeDamage(damage);
    }

    public void heal(int amount) {
        health.heal(amount);
    }

    public void resetArmor() {
        capaciteArmure.resetArmor();
    }

    public void increaseArmor(int amount) {
        capaciteArmure.increaseArmor(amount);
    }

    public void decreaseArmor(int amount) {
        capaciteArmure.decreaseArmor(amount);
    }

    public int getCurrentHealth() {
        return health.getCurrentHealth();
    }

    public int getMaxHealth() {
        return health.getMaxHealth();
    }

    public int getCurrentArmor() {
        return capaciteArmure.getCurrentArmor();
    }

    public int getInitialArmor() {
        return capaciteArmure.getInitialArmor();
    }

    // TODO: faire une fonction qui annalyse la vie des joueurs actifs
}
