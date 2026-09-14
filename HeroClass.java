package domain.hero;

public enum HeroClass {
    WARRIOR(12, 0),
    RANGER(10, 0),
    MAGE(6, 10),
    CLERIC(10, 8);

    private final int basePv;
    private final int baseMagic;

    HeroClass(int basePv, int baseMagic) {
        this.basePv = basePv;
        this.baseMagic = baseMagic;
    }

    public int basePv() {
        return basePv;
    }

    public int baseMagic() {
        return baseMagic;
    }
}
