package rpg.hero.domain;

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

    public int strength() { return strength; }
    public int dexterity() { return dexterity; }
    public int constitution() { return constitution; }
    public int intelligence() { return intelligence; }
    public int wisdom() { return wisdom; }
    public int charisma() { return charisma; }

    public int modifierOf(int value) {
        return Math.floorDiv(value - 10, 2);
    }

    public int strengthModifier() { return modifierOf(strength); }
    public int dexterityModifier() { return modifierOf(dexterity); }
    public int constitutionModifier() { return modifierOf(constitution); }
}
