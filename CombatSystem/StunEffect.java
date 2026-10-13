package CombatSystem;
import FakeHeores.heroes;
import Créatures.Creature;

public class StunEffect extends EffectsTemplate {
    public StunEffect(int duration) {
        super("Stun", duration);
    }

    @Override
    public void applyEffect(Creature creature) {
        System.out.println(creature.getNom() + " est étourdi et ne peut pas agir ce tour.");
        creature.isStunned();
    }

    @Override
    public void applyEffect(heroes hero) {
        System.out.println(hero.getNom() + " est étourdi et ne peut pas agir ce tour.");
        hero.isStunned();
    }

    @Override 
    public void onRemove(Creature creature){
        creature.isNotStunned();
    }

    @Override 
    public void onRemove(heroes hero){
        hero.isNotStunned();
    }
}
