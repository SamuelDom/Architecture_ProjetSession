package rpg.hero.domain;

import java.util.Objects;
import java.util.UUID;

public record HeroId(UUID value) {
    public HeroId {
        Objects.requireNonNull(value, "L'identifiant du héros est requis.");
    }

    @Override
    public String toString() { return value.toString(); }
}
