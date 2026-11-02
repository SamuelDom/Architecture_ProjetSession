package rpg.hero.domain;

import java.util.Optional;

public interface HeroRepository {
    void save(Hero hero);
    Optional<Hero> findById(HeroId id);
    boolean existsByName(HeroName name);
}
