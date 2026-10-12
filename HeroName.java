package domain.hero;

import java.util.Objects;
import java.util.regex.Pattern;

public final class HeroName {

    private static final int MIN_LENGTH = 2;
    private static final int MAX_LENGTH = 30;

    private static final Pattern VALID_CHARACTERS = Pattern.compile("^[A-Za-zÀ-ÖØ-öø-ÿ ]+$");

    private final String value;

    private HeroName(String value) {
        this.value = value;
    }

    public static HeroName of(String rawName) {
        if (rawName == null) {
            throw new IllegalArgumentException("Le nom du héros est requis.");
        }
        if (rawName.length() < MIN_LENGTH || rawName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException(
                    "Le nom doit contenir entre " + MIN_LENGTH + " et " + MAX_LENGTH + " caractères.");
        }
        if (rawName.startsWith(" ") || rawName.endsWith(" ")) {
            throw new IllegalArgumentException("Le nom ne peut pas commencer ou se terminer par un espace.");
        }
        if (!VALID_CHARACTERS.matcher(rawName).matches()) {
            throw new IllegalArgumentException("Le nom ne peut contenir que des lettres et des espaces.");
        }
        return new HeroName(rawName);
    }

    public String value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroName)) return false;
        HeroName other = (HeroName) o;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
