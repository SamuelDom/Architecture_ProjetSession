package rpg.item;

import java.util.Objects;
import rpg.hero.domain.WeaponCategory;

public final class Weapon implements Item {
    private final String name;
    private final int damageDice;
    private final String ability;
    private final WeaponCategory category;

    public Weapon(String name, int damageDice, String ability, WeaponCategory category) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Le nom de l'arme est obligatoire.");
        if ((Integer)damageDice == null || damageDice <=1) throw new IllegalArgumentException("Le dé de dégâts est obligatoire.");
        if (ability == null || ability.isBlank()) throw new IllegalArgumentException("La caractéristique est obligatoire.");
        this.name = name;
        this.damageDice = damageDice;
        this.ability = ability;
        this.category = Objects.requireNonNull(category);
    }

    public Weapon(String name, int damageDice, String ability) {
        this(name, damageDice, ability, categoryFromName(name));
    }

    private static WeaponCategory categoryFromName(String name) {
        if (name == null) throw new IllegalArgumentException("Le nom de l'arme est obligatoire.");
        String normalized = name.toLowerCase();
        if (normalized.contains("arc")) return WeaponCategory.BOW;
        if (normalized.contains("dague")) return WeaponCategory.DAGGER;
        if (normalized.contains("hache")) return WeaponCategory.AXE;
        if (normalized.contains("masse") || normalized.contains("marteau")) return WeaponCategory.MACE;
        if (normalized.contains("bâton") || normalized.contains("baton")) return WeaponCategory.STAFF;
        if (normalized.contains("épée") || normalized.contains("epee")
                || normalized.contains("sword")) return WeaponCategory.SWORD;
        throw new IllegalArgumentException(
                "La catégorie de l'arme doit être précisée pour le nom : " + name);
    }

    @Override public String getName() { return name; }
    public int getDamageDice() { return damageDice; }
    public String getAbility() { return ability; }
    public WeaponCategory getCategory() { return category; }
}
