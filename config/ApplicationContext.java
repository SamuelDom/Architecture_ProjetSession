package config;

import hero.application.CreateHeroUseCase;
import hero.application.GetHeroUseCase;
import hero.application.HeroOutputMapper;
import hero.domain.HeroFactory;
import hero.domain.HeroRepository;
import hero.infrastructure.HeroHttpServer;
import hero.infrastructure.InMemoryHeroRepository;
import java.io.IOException;
import rpg.player.application.PlayerSessions;
import rpg.player.application.RegisterPlayerUseCase;
import rpg.player.infrastructure.Argon2PasswordHasher;
import rpg.player.infrastructure.InMemoryPlayerRepository;

/** À remplacer plus tard par l'injection du framework). */
public class ApplicationContext {

    private final HeroRepository heroRepository = new InMemoryHeroRepository();
    private final HeroFactory heroFactory = new HeroFactory(heroRepository);
    private final CreateHeroUseCase createHeroUseCase =
            new CreateHeroUseCase(heroFactory, heroRepository, new HeroOutputMapper());
        private final GetHeroUseCase getHeroUseCase =
            new GetHeroUseCase(heroRepository, new HeroOutputMapper());

    private final InMemoryPlayerRepository players = new InMemoryPlayerRepository();
    private final Argon2PasswordHasher passwords = new Argon2PasswordHasher();
    private final RegisterPlayerUseCase registerPlayer = new RegisterPlayerUseCase(players, passwords);
    private final PlayerSessions sessions = new PlayerSessions(players, passwords);

    public CreateHeroUseCase createHeroUseCase() {
        return createHeroUseCase;
    }

    public GetHeroUseCase getHeroUseCase() {
        return getHeroUseCase;
    }

    public HeroHttpServer createHeroHttpServer(int port) throws IOException {
        return new HeroHttpServer(port, createHeroUseCase, getHeroUseCase, registerPlayer, sessions);
    }
}
