package domain.hero;

public enum Species {

    HUMAN(1, 1, 1, 1, 1, 1),
    ELF(0, 2, 0, 1, 0, 0),
    DWARF(1, 0, 2, 0, 0, 0),
    ORC(2, 0, 1, -1, 0, 0);

    private final int strengthBonus;
    private final int dexterityBonus;
    private final int constitutionBonus;
    private final int intelligenceBonus;
    private final int wisdomBonus;
    private final int charismaBonus;

    Species(
            int strengthBonus,
            int dexterityBonus,
            int constitutionBonus,
            int intelligenceBonus,
            int wisdomBonus,
            int charismaBonus) {

        this.strengthBonus = strengthBonus;
        this.dexterityBonus = dexterityBonus;
        this.constitutionBonus = constitutionBonus;
        this.intelligenceBonus = intelligenceBonus;
        this.wisdomBonus = wisdomBonus;
        this.charismaBonus = charismaBonus;
    }

    public int strengthBonus() {
    return strengthBonus;
    }

    public int dexterityBonus() {
        return dexterityBonus;
    }

    public int constitutionBonus() {
        return constitutionBonus;
    }

    public int intelligenceBonus() {
        return intelligenceBonus;
    }

    public int wisdomBonus() {
        return wisdomBonus;
    }

    public int charismaBonus() {
        return charismaBonus;
    }
}