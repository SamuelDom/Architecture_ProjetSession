package domain.item;

public class Armor implements Item {

    private final String name;
    private final int baseArmorClass;
    private final boolean usesDexterityModifier;

    public Armor(String name, int baseArmorClass, boolean usesDexterityModifier) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Le nom de l'armure est obligatoire.");
        }

        if (baseArmorClass <= 0) {
            throw new IllegalArgumentException("La classe d'armure doit être supérieure à 0.");
        }

        this.name = name;
        this.baseArmorClass = baseArmorClass;
        this.usesDexterityModifier = usesDexterityModifier;
    }

    @Override
    public String getName() {
        return name;
    }

    public int getBaseArmorClass() {
        return baseArmorClass;
    }

    public boolean usesDexterityModifier() {
        return usesDexterityModifier;
    }
}