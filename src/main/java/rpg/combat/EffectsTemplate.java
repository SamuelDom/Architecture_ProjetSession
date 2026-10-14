package rpg.combat;

public abstract class EffectsTemplate {
    private final String name;
    private int duration;

    protected EffectsTemplate(String name, int duration) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Le nom de l'effet est requis.");
        if (duration < 1) throw new IllegalArgumentException("La durée de l'effet doit être positive.");
        this.name = name;
        this.duration = duration;
    }

    public String getName() { return name; }
    public int getDuration() { return duration; }
    public boolean tick() { return --duration <= 0; }
    public void applyEffect(Combatant target) {}
    public void onTurnStart(Combatant target) {}
    public void onRemove(Combatant target) {}
}
