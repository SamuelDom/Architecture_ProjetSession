package rpg.hero.domain;

import rpg.hero.domain.exception.HeroNameAlreadyTakenException;

import java.util.UUID;

/** Vérifie l'unicité du nom et génère l'identifiant. */
public class HeroFactory {

    private final HeroRepository heroRepository;

    public HeroFactory(HeroRepository heroRepository) {
        this.heroRepository = heroRepository;
    }

    public Hero create(HeroName name, Species species, HeroClass heroClass, Characteristics characteristics) {
        if (heroRepository.existsByName(name)) {
            throw new HeroNameAlreadyTakenException(name.value());
        }
        return new Hero(new HeroId(UUID.randomUUID()), name, species, heroClass, characteristics);
    }
}
