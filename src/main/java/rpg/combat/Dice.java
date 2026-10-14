package rpg.combat;

import java.util.concurrent.ThreadLocalRandom;

public final class Dice {
    public int roll(int sides) {
        if (sides < 1) throw new IllegalArgumentException("Un dé doit avoir au moins une face.");
        return ThreadLocalRandom.current().nextInt(1, sides + 1);
    }

    public int roll(String notation) {
        if (notation == null || !notation.matches("\\d+d\\d+")) {
            throw new IllegalArgumentException("Notation de dé invalide : " + notation);
        }
        String[] parts = notation.toLowerCase().split("d");
        int count;
        int sides;
        try {
            count = Integer.parseInt(parts[0]);
            sides = Integer.parseInt(parts[1]);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("Notation de dé invalide : " + notation, exception);
        }
        if (count < 1 || sides < 1) {
            throw new IllegalArgumentException("Le nombre de dés et de faces doit être positif.");
        }
        int total = 0;
        for (int die = 0; die < count; die++) total = Math.addExact(total, roll(sides));
        return total;
    }
}
