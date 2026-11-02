package rpg.hero.infrastructure;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroId;
import rpg.hero.domain.HeroName;
import rpg.hero.domain.HeroRepository;

public final class InMemoryHeroRepository implements HeroRepository {
    private final Map<HeroId, Hero> heroes = new HashMap<>();

    @Override
    public void save(Hero hero) { heroes.put(hero.id(), hero); }

    @Override
    public Optional<Hero> findById(HeroId id) {
        return Optional.ofNullable(heroes.get(id));
    }

    @Override
    public boolean existsByName(HeroName name) {
        return heroes.values().stream().anyMatch(hero -> hero.name().equals(name));
    }
}
