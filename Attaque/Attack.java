package Attaque;

public class Attack {
    dice dice = new dice();
    public int attackCrit(int bonusAttack) {
        int attack = dice.rollDice(8) + dice.rollDice(8) + bonusAttack; //rajouter les modifcateurs des héros
        return attack;
    }

    public int attack(int bonusAttack) {
        int attack = dice.rollDice(8) + bonusAttack; //rajouter les modifcateurs des héros
        return attack;
    }

    public void attackLand(int ca){
        int diceRoll = dice.rollDice(20);
        if(diceRoll == 20){
            System.out.println("Coup critique !");
            attackCrit(3 ); //rajouter les modifcateurs des héros et le ciblage d'ennemmies
        }
        if(diceRoll > ca){
            System.out.println("Attaque réussie !");
            attack(3);
        }
        else{
            System.out.println("Attaque ratée !");
        } //rajouter les modifcateurs des héros
    }
}
