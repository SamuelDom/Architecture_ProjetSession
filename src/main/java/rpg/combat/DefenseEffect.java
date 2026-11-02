package rpg.combat;

public final class DefenseEffect extends EffectsTemplate {
    private final int defenseBonus;

    public DefenseEffect(int duration, int defenseBonus) {
        super("Défense", duration);
        if (defenseBonus < 0) throw new IllegalArgumentException("Le bonus de défense ne peut pas être négatif.");
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void applyEffect(Combatant target) {
        target.increaseArmor(defenseBonus);
        System.out.println(target.nameForCombat() + " adopte une position défensive (+"
                + defenseBonus + " CA).");
    }

    @Override
    public void onRemove(Combatant target) {
        target.decreaseArmor(defenseBonus);
    }
}
