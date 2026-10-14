package rpg.hero.domain;

import java.util.Optional;

/** Port : le domaine décrit ce dont il a besoin, l'infrastructure fournit l'implémentation. */
public interface HeroRepository {

    void save(Hero hero);

    Optional<Hero> findById(HeroId id);

    boolean existsByName(HeroName name);
}
