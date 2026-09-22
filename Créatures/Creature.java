package Créatures;

public class Creature {
    private String nom;
    private Stats.Health health;
    private Stats.CapaciteArmure capaciteArmure;
    private int xp;
    private int initiative;

    public Creature(String nom, int maxHealth, int initialArmor, int xp, int initiative) {
        this.nom = nom;
        this.health = new Stats.Health(maxHealth);
        this.capaciteArmure = new Stats.CapaciteArmure(initialArmor);
        this.xp = xp;
        this.initiative = initiative;
    }

    public String getNom() {
        return nom;
    }

    public Stats.Health getHealth() {
        return health;
    }

    public Stats.CapaciteArmure getCapaciteArmure() {
        return capaciteArmure;
    }

    public int getXp() {
        return xp;
    }

    public int getInitiative() {
        return initiative;
    }
}
