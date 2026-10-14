package rpg.combat;

public final class StunEffect extends EffectsTemplate {
    public StunEffect(int duration) {
        super("Étourdissement", duration);
    }

    @Override
    public void applyEffect(Combatant target) {
        target.setStunned(true);
        System.out.println(target.nameForCombat() + " est étourdi.");
    }

    @Override
    public void onRemove(Combatant target) {
        target.setStunned(false);
        System.out.println(target.nameForCombat() + " n'est plus étourdi.");
    }
}
