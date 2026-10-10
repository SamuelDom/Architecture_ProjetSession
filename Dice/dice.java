package Dice;

public class dice {
    public int rollDice(int max) {
        int min = 1;
        int result = (int) (Math.random() * (max - min + 1) + min);
        return result;
    }
}
