package rpg.item;

import java.util.Objects;
import rpg.hero.domain.ArmorCategory;

public final class Armor implements Item {
    private final String name;
    private final int baseArmorClass;
    private final boolean usesDexterityModifier;
    private final ArmorCategory category;

    public Armor(String name, int baseArmorClass, boolean usesDexterityModifier,
                 ArmorCategory category) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Le nom de l'armure est obligatoire.");
        if (baseArmorClass <= 0) throw new IllegalArgumentException("La classe d'armure doit être supérieure à 0.");
        this.name = name;
        this.baseArmorClass = baseArmorClass;
        this.usesDexterityModifier = usesDexterityModifier;
        this.category = Objects.requireNonNull(category);
    }

    public Armor(String name, int baseArmorClass, boolean usesDexterityModifier) {
        this(name, baseArmorClass, usesDexterityModifier,
                usesDexterityModifier ? ArmorCategory.LIGHT : ArmorCategory.HEAVY);
    }

    @Override public String getName() { return name; }
    public int getBaseArmorClass() { return baseArmorClass; }
    public boolean usesDexterityModifier() { return usesDexterityModifier; }
    public ArmorCategory getCategory() { return category; }
}
