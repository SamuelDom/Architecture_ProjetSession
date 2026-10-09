package Créatures;

public class Creature {
    private String nom;
    private Stats.Health health;
    private Stats.CapaciteArmure capaciteArmure;
    private Dice.dice dice;
    private int xp;
    private int initiative;
    private int baseAttack;
    private String weaponName;
    private CreaturesWeapons creaturesWeapons;

    public Creature(String nom, int maxHealth, int initialArmor, int xp, int initiative, int baseAttack, String weaponName) {
        this.nom = nom;
        this.dice = new Dice.dice();
        this.health = new Stats.Health(maxHealth);
        this.capaciteArmure = new Stats.CapaciteArmure(initialArmor);
        this.xp = xp;
        this.weaponName = weaponName;
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

    public int getWeaponDamage() {
        return creaturesWeapons.getWeaponDamage(weaponName);
    }

    public void takeDamage(int damage) {
        health.takeDamage(damage);
    }

    public void heal(int amount) {
        health.heal(amount);
    }

    public boolean isAlive() {
        return health.getCurrentHealth() > 0;
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

    public int rollDice(int rollSize) {
        return dice.rollDice(rollSize);
    }

    public int testAttack() {
        int attack = getWeaponDamage() + baseAttack + rollDice(6) + getBaseAttack();
        return attack;
    }

    // TODO: faire une fonction qui annalyse la vie des joueurs actifs
    // prendre la liste
    // et faire un test sur la vie de chaque joueur
}
