//import domain.hero.WeaponCategory; avec Karlens
package domain.item;

public class Weapon implements Item {

    private final String name;
    private final String damageDice;
    private final String ability;

    public Weapon(String name, String damageDice, String ability) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'arme est obligatoire.");
        }

        if (damageDice == null || damageDice.isBlank()) {
            throw new IllegalArgumentException("Le dé de dégâts est obligatoire.");
        }

        if (ability == null || ability.isBlank()) {
            throw new IllegalArgumentException("La caractéristique est obligatoire.");
        }

        this.name = name;
        this.damageDice = damageDice;
        this.ability = ability;
    }

    @Override
    public String getName() {
        return name;
    }

    public String getDamageDice() {
        return damageDice;
    }

    public String getAbility() {
        return ability;
    }
}