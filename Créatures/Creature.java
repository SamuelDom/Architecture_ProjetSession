package Créatures;
import FakeHeroes.heroes;

import java.util.ArrayList;
import java.util.List;

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
        this.creaturesWeapons = new CreaturesWeapons();
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
        int attack = getWeaponDamage() + rollDice(6) + getBaseAttack();
        return attack;
    }

    public void comportementCombat(List<heroes> heroesList) {
        int minPV = Integer.MAX_VALUE; //Comportement du Gobelin
        int maxPV = Integer.MIN_VALUE; //Comportement de l'Orc
        ArrayList<Integer> ids = new ArrayList<>();
        heroes targetHero = null;
        System.out.println("--- État de l'équipe avant l'attaque ---");
        if(getNom().equals("Gobelin")) {
            for (heroes hero : heroesList) {
                System.out.println("PV du héros " + hero.getNom() + ": " + hero.getPv());
                
                if (hero.getPv() > 0 && hero.getPv() < minPV) {
                    minPV = hero.getPv();
                    targetHero = hero;
                }
            }
        }
        else if(getNom().equals("Orc")) {
            for (heroes hero : heroesList) {
                System.out.println("PV du héros " + hero.getNom() + ": " + hero.getPv());
                
                if (hero.getPv() > 0 && hero.getPv() > maxPV) {
                    maxPV = hero.getPv();
                    targetHero = hero;
                }
            }
        }
        else if(getNom().equals("Troll")) {
            for (heroes hero : heroesList) {
                System.out.println("PV du héros " + hero.getNom() + ": " + hero.getPv());
                
                if (hero.getPv() > 0) {
                    ids.add(hero.getId());
                }
            }
            if (!ids.isEmpty()) {
                int randomIndex = dice.rollDice(ids.size()) - 1; // -1 pour
                targetHero = heroesList.get(randomIndex);
            }
        }
        System.out.println("----------------------------------------");
        if (targetHero != null) {
            System.out.println("-> " + getNom() + " a choisi d'attaquer " + targetHero.getNom());
            targetHero.takeDamage(testAttack());
        } else {
            System.out.println("-> Aucune cible valide.");
        }
    }
}
