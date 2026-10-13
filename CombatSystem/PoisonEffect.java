package CombatSystem;
import FakeHeores.heroes;
import Créatures.Creature;
import Dice.dice;

public class PoisonEffect extends EffectsTemplate {
    private dice dice = new dice();
    private int damagePerTurn = dice.rollDiceFour(); // Dégâts de poison par tour (1d4)

    public PoisonEffect(int duration, int damagePerTurn) {
        super("Poison", duration);
        this.damagePerTurn = damagePerTurn;
    }

    @Override
    public void onTurnStart(Creature creature) {
        creature.takeDamage(damagePerTurn);
        System.out.println(creature.getNom() + " subit " + damagePerTurn + " points de dégâts de poison.");
    }

    @Override
    public void onTurnStart(heroes hero) {
        hero.takeDamage(damagePerTurn);
        System.out.println(hero.getNom() + " subit " + damagePerTurn + " points de dégâts de poison.");
    }
    
}
