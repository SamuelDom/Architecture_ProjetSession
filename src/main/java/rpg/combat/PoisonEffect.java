package rpg.combat;

public final class PoisonEffect extends EffectsTemplate {
    private final int damagePerTurn;

    public PoisonEffect(int duration, int damagePerTurn) {
        super("Poison", duration);
        if (damagePerTurn < 0) throw new IllegalArgumentException("Les dégâts de poison ne peuvent pas être négatifs.");
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void onTurnStart(Combatant target) {
        target.takeDamage(damagePerTurn);
        System.out.println(target.nameForCombat() + " subit " + damagePerTurn + " dégâts de poison.");
    }
}
