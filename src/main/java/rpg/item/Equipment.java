package rpg.item;

import java.util.Objects;
import rpg.hero.domain.HeroClass;

public final class Equipment {
    private Weapon mainWeapon;
    private Armor mainArmor;

    public void equip(Weapon weapon, Inventory inventory, HeroClass heroClass) {
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(heroClass);
        if (weapon == null || !inventory.contains(weapon)) {
            throw new IllegalStateException("Le héros ne possède pas cette arme.");
        }
        if (!heroClass.canUse(weapon.getCategory())) {
            throw new IllegalStateException("La classe ne permet pas d'utiliser cette arme.");
        }
        mainWeapon = weapon;
    }

    public void equip(Armor armor, Inventory inventory, HeroClass heroClass) {
        Objects.requireNonNull(inventory);
        Objects.requireNonNull(heroClass);
        if (armor == null || !inventory.contains(armor)) {
            throw new IllegalStateException("Le héros ne possède pas cette armure.");
        }
        if (!heroClass.canUse(armor.getCategory())) {
            throw new IllegalStateException("La classe ne permet pas d'utiliser cette armure.");
        }
        mainArmor = armor;
    }

    public Weapon getMainWeapon() { return mainWeapon; }
    public Armor getMainArmor() { return mainArmor; }
}
