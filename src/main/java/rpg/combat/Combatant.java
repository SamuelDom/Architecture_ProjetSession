package rpg.combat;

public interface Combatant {
    String nameForCombat();
    int currentHitPoints();
    int currentArmor();
    boolean isAlive();
    void takeDamage(int amount);
    boolean isStunned();
    void setStunned(boolean stunned);
    void increaseArmor(int amount);
    void decreaseArmor(int amount);
}
