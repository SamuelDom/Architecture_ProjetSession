package rpg.hero.domain;

import java.util.Objects;
import java.util.UUID;

public final class HeroFactory {
    private final HeroRepository heroRepository;

    public HeroFactory(HeroRepository heroRepository) {
        this.heroRepository = Objects.requireNonNull(heroRepository);
    }

    public Hero create(HeroName name, Species species, HeroClass heroClass,
                       Characteristics characteristics) {
        if (heroRepository.existsByName(name)) {
            throw new IllegalArgumentException("Le nom de héros \"" + name + "\" est déjà utilisé.");
        }
        return new Hero(new HeroId(UUID.randomUUID()), name, species, heroClass, characteristics);
    }
}
