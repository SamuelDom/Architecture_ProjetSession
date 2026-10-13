package Attaque;
import Dice.dice;
import Créatures.Creature;
import java.util.ArrayList;

public class Attack {
    dice dice = new dice();
    private int attackCrit(int bonusAttack) {
        int attack = dice.rollDiceEight() + dice.rollDiceEight() + bonusAttack; //rajouter les modifcateurs des héros
        return attack;
    }

    private int attack(int bonusAttack) {
        int attack = dice.rollDiceEight() + bonusAttack; //rajouter les modifcateurs des héros
        return attack;
    }

    private void attackLand(Creature targetCreature){
        int diceRoll = dice.rollDiceTwenty();
        int attackChance = diceRoll + 100; //rajouter les modifcateurs des héros
        int ca = targetCreature.getCurrentArmor();
        if(diceRoll == 20){
            System.out.println("Coup critique !");
            targetCreature.takeDamage(attackCrit(3)); //rajouter les modifcateurs des héros
        }
        if(attackChance > ca){
            System.out.println("Attaque réussie !");
            targetCreature.takeDamage(attack(3)); //rajouter les modifcateurs des héros
        }
        else{
            System.out.println("Attaque ratée !");
        }
    }

    public void targetChoice(ArrayList<Creature> creatures){
        int choice;
        for (int i = 0; i < creatures.size(); i++) {
            Creature creature = creatures.get(i);
            System.out.println((i + 1) + ". " + creature.getNom() + " (PV: " + creature.getCurrentHealth() + (creature.isAlive() ? "" : " (Mort)") + ", CA: " + creature.getCurrentArmor() + ")");
        }
        System.out.print("Choisissez une créature à attaquer (1-" + creatures.size() + "): ");
        
        // Remplacement du try-with-resources par une instanciation standard
        java.util.Scanner scanner = new java.util.Scanner(System.in);
        choice = scanner.nextInt();
        
        if (choice >= 1 && choice <= creatures.size()) {
            Creature targetCreature = creatures.get(choice - 1);
            if(!targetCreature.isAlive()) {
                System.out.println("Vous ne pouvez pas attaquer une créature morte. Veuillez choisir une autre cible.");
                targetChoice(creatures);
                return;
            }
            System.out.println("Vous avez choisi d'attaquer " + targetCreature.getNom() + " (CA: " + targetCreature.getCurrentArmor() + ")");
            // Attack ou envoyer un spell
            attackLand(targetCreature);
        } else {
            System.out.println("Choix invalide. Veuillez réessayer.");
            targetChoice(creatures);
        }
    }

}
