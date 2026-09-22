package Créatures;

public class Creature {
    private String nom;
    private Stats.Health health;
    private Stats.CapaciteArmure capaciteArmure;
    private Dice.dice dice;
    private int xp;
    private int initiative;
    private int baseAttack;

    public Creature(String nom, int maxHealth, int initialArmor, int xp, int initiative, int baseAttack) {
        this.nom = nom;
        this.dice = new Dice.dice();
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

    public int rollDice(int max) {
        return dice.rollDice(max);
    }

    public int testAttack() {
        int attack = baseAttack + rollDice(6) + 1; // Exemple : attaque de base + lancer de dé à 6 faces
        return attack;
    }

    // TODO: faire une fonction qui annalyse la vie des joueurs actifs
}
