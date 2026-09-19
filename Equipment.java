public class Equipment {
    private Weapon mainWeapon;
    private Armor mainArmor;

    public void equip(Weapon weapon, Inventory inventory, HeroClass heroClass) {
        if (!inventory.contains(weapon))
            throw new IllegalStateException("Le héros ne possède pas cet objet");
        if (!heroClass.canUse(weapon))
            throw new IllegalStateException("La classe ne permet pas cet objet");
        this.mainWeapon = weapon;
    }

    public void equip(Armor armor, Inventory inventory, HeroClass heroClass) {
        if (!inventory.contains(armor))
            throw new IllegalStateException("Le héros ne possède pas cet objet");
        if (!heroClass.canUse(armor))
            throw new IllegalStateException("La classe ne permet pas cet objet");
        this.mainArmor = armor;
    }

    public Weapon getMainWeapon() { return mainWeapon; }
    public Armor getMainArmor() { return mainArmor; }
}