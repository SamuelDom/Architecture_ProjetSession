package config;

import hero.application.CreateHeroUseCase;
import hero.application.HeroOutputMapper;
import hero.domain.HeroFactory;
import hero.domain.HeroRepository;
import hero.infrastructure.InMemoryHeroRepository;

/** À remplacer plus tard par l'injection du framework). */
public class ApplicationContext {

    private final HeroRepository heroRepository = new InMemoryHeroRepository();
    private final HeroFactory heroFactory = new HeroFactory(heroRepository);
    private final CreateHeroUseCase createHeroUseCase =
            new CreateHeroUseCase(heroFactory, heroRepository, new HeroOutputMapper());

    public CreateHeroUseCase createHeroUseCase() {
        return createHeroUseCase;
    }
}
