package domain.hero;

public enum WeaponCategory {

    SWORD(true),
    AXE(true),
    MACE(true),
    DAGGER(true),
    BOW(false),
    STAFF(true);

    private final boolean melee;

    WeaponCategory(boolean melee) {
        this.melee = melee;
    }

    public boolean isMelee() {
        return melee;
    }
}