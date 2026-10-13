package CombatSystem;
import FakeHeores.heroes;
import Créatures.Creature;

public abstract class EffectsTemplate {
    private String name;
    private int duration;

    EffectsTemplate(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public boolean tick() {
        duration--;
        return duration <= 0;
    }

    public void applyEffect(Creature creature) {};
    public void applyEffect(heroes hero) {};
    public void onTurnStart(Creature creature) {};
    public void onTurnStart(heroes hero) {};
    public void onTurnEnd(Creature creature) {};
    public void onTurnEnd(heroes hero) {};
    public void onRemove(Creature creature) {};
    public void onRemove(heroes hero) {};

}
