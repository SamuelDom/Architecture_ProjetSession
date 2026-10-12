package domain.hero;

public final class Characteristics {

    private final int strength;
    private final int dexterity;
    private final int constitution;
    private final int intelligence;
    private final int wisdom;
    private final int charisma;

    public Characteristics(int strength, int dexterity, int constitution,
                            int intelligence, int wisdom, int charisma) {
        this.strength = strength;
        this.dexterity = dexterity;
        this.constitution = constitution;
        this.intelligence = intelligence;
        this.wisdom = wisdom;
        this.charisma = charisma;
    }

    public int constitution() {
        return constitution;
    }

   
    public int modifierOf(int rawValue) {
        return Math.floorDiv(rawValue - 10, 2);
    }

    public int constitutionModifier() {
        return modifierOf(constitution);
    }
}
