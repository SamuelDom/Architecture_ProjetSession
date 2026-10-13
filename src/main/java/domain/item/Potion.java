package domain.item;

public class Potion implements Item {

    private final String name;
    private final String healingDice;
    private final int healingBonus;

    public Potion(String name, String healingDice, int healingBonus) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de la potion est obligatoire.");
        }

        if (healingDice == null || healingDice.isBlank()) {
            throw new IllegalArgumentException("Le dé de soin est obligatoire.");
        }

        if (healingBonus < 0) {
            throw new IllegalArgumentException("Le bonus de soin ne peut pas être négatif.");
        }

        this.name = name;
        this.healingDice = healingDice;
        this.healingBonus = healingBonus;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getHealingDice() {
        return healingDice;
    }

    public int getHealingBonus() {
        return healingBonus;
    }
}