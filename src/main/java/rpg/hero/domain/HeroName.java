package rpg.hero.domain;

import java.util.regex.Pattern;

public final class HeroName {
    private static final Pattern VALID_CHARACTERS =
            Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");

    private final String value;

    public HeroName(String value) {
        if (value == null || value.length() < 2 || value.length() > 30
                || value.startsWith(" ") || value.endsWith(" ")
                || !VALID_CHARACTERS.matcher(value).matches()) {
            throw new IllegalArgumentException(
                    "Le nom doit contenir entre 2 et 30 lettres, sans espace au début ni à la fin.");
        }
        this.value = value;
    }

    public String value() { return value; }

    @Override
    public boolean equals(Object other) {
        return other instanceof HeroName heroName && value.equals(heroName.value);
    }

    @Override
    public int hashCode() { return value.hashCode(); }

    @Override
    public String toString() { return value; }
}
