package rpg.item;

public final class Potion implements Item {
    private final String name;
    private final int healingDice;
    private final int healingBonus;

    public Potion(String name, int healingDice, int healingBonus) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Le nom de la potion est obligatoire.");
        if (healingDice < 1) throw new IllegalArgumentException("Le dé de soin doit être de 2 minimum.");
        if (healingBonus < 0) throw new IllegalArgumentException("Le bonus de soin ne peut pas être négatif.");
        this.name = name;
        this.healingDice = healingDice;
        this.healingBonus = healingBonus;
    }

    @Override public String getName() { return name; }
    public int getHealingDice() { return healingDice; }
    public int getHealingBonus() { return healingBonus; }
}
