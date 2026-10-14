package rpg.hero.domain;

import rpg.hero.domain.exception.InvalidHeroNameException;

import java.util.regex.Pattern;


public final class HeroName {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 30;
    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");

    private final String value;

    public HeroName(String value) {
        if (value == null) {
            throw new InvalidHeroNameException("Le nom du héros est requis.");
        }
        if (value.length() < MIN_LENGTH || value.length() > MAX_LENGTH) {
            throw new InvalidHeroNameException(
                    "Le nom doit contenir entre " + MIN_LENGTH + " et " + MAX_LENGTH + " caractères.");
        }
        if (value.startsWith(" ") || value.endsWith(" ")) {
            throw new InvalidHeroNameException("Le nom ne peut pas commencer ou se terminer par un espace.");
        }
        if (!VALID_CHARACTERS.matcher(value).matches()) {
            throw new InvalidHeroNameException("Le nom ne peut contenir que des lettres et des espaces.");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroName other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}
