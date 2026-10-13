package CombatSystem;
import FakeHeores.heroes;
import Créatures.Creature;

public class DefenseEffect extends EffectsTemplate {
    private int defenseBonus;

    public DefenseEffect(int duration, int defenseBonus) {
        super("Defense", duration);
        this.defenseBonus = defenseBonus;
    }

    @Override
    public void applyEffect(Creature creature) {
        System.out.println(creature.getNom() + " est en position défensive. Sa CA a augmenté de " + defenseBonus + " pour ce tour.");
        creature.increaseArmor(defenseBonus);
    }

    @Override
    public void applyEffect(heroes hero) {
        System.out.println(hero.getNom() + " est en position défensive. Sa CA a augmenté de " + defenseBonus + " pour ce tour.");
        hero.getCapaciteArmure().increaseArmor(defenseBonus); //À changer
    }

    @Override
    public void onRemove(Creature creature) {
        creature.decreaseArmor(defenseBonus);
        System.out.println(creature.getNom() + " n'est plus en position défensive.");
    }

    @Override
    public void onRemove(heroes hero) {
        hero.getCapaciteArmure().decreaseArmor(defenseBonus);//À changer
        System.out.println(hero.getNom() + " n'est plus en position défensive.");
    }
    
}
