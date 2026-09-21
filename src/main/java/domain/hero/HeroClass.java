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

    public int calculateMaxHitPoints(int constitutionModifier) {
        return Math.max(1, basePv + constitutionModifier);
    }

    public boolean canUse(WeaponCategory weapon) {
        return switch (this) {
            case WARRIOR -> weapon.isMelee();

            case RANGER ->
                    weapon == WeaponCategory.BOW
                    || weapon == WeaponCategory.DAGGER
                    || weapon == WeaponCategory.SWORD;

            case MAGE ->
                    weapon == WeaponCategory.STAFF
                    || weapon == WeaponCategory.DAGGER;

            case CLERIC ->
                    weapon == WeaponCategory.MACE
                    || weapon == WeaponCategory.STAFF;
        };
    }

    public boolean canUse(ArmorCategory armor) {
        return switch (this) {
            case WARRIOR, CLERIC -> true;

            case RANGER ->
                    armor == ArmorCategory.LIGHT;

            case MAGE -> false;
        };
    }
}