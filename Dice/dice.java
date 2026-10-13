package Dice;

public class dice {
    public int rollDice(int max) {
        int min = 1;
        int result = (int) (Math.random() * (max - min + 1) + min);
        return result;
    }

    public int rollDiceFour() {
        return rollDice(4);
    }

    public int rollDiceSix() {
        return rollDice(6);
    }

    public int rollDiceEight() {
        return rollDice(8);
    }

    public int rollDiceTen() {
        return rollDice(10);
    }

    public int rollDiceTwenty(){
        return rollDice(20);
    }
}
