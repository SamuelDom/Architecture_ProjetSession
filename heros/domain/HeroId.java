package rpg.hero.domain;

import java.util.Objects;
import java.util.UUID;

/** Value Object : identifiant d'un héros (évite de passer des UUID "nus" partout). */
public final class HeroId {

    private final UUID value;

    public HeroId(UUID value) {
        this.value = Objects.requireNonNull(value, "L'identifiant du héros est requis.");
    }

    public UUID value() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HeroId other)) return false;
        return value.equals(other.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public String toString() {
        return value.toString();
    }
}
