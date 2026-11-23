package config;

import heros.application.CreateHeroUseCase;
import heros.application.GetHeroUseCase;
import heros.application.HeroOutputMapper;
import heros.domain.HeroFactory;
import heros.domain.HeroRepository;
import heros.infrastructure.HeroHttpServer;
import heros.infrastructure.InMemoryHeroRepository;
import java.io.IOException;
import lobbies.application.CreateLobbyUseCase;
import lobbies.application.LobbyOutputMapper;
import lobbies.domain.LobbyFactory;
import lobbies.domain.LobbyRepository;
import lobbies.infrastructure.InMemoryLobbyRepository;

public class ApplicationContext {

    private final HeroRepository heroRepository = new InMemoryHeroRepository();
    private final HeroFactory heroFactory = new HeroFactory(heroRepository);
    private final CreateHeroUseCase createHeroUseCase =
            new CreateHeroUseCase(heroFactory, heroRepository, new HeroOutputMapper());
    private final GetHeroUseCase getHeroUseCase =
            new GetHeroUseCase(heroRepository, new HeroOutputMapper());

    private final LobbyRepository lobbyRepository = new InMemoryLobbyRepository();
    private final LobbyFactory lobbyFactory = new LobbyFactory(lobbyRepository);
    private final CreateLobbyUseCase createLobbyUseCase =
            new CreateLobbyUseCase(lobbyFactory, lobbyRepository, new LobbyOutputMapper());

    public CreateHeroUseCase createHeroUseCase() {
        return createHeroUseCase;
    }

    public GetHeroUseCase getHeroUseCase() {
        return getHeroUseCase;
    }

    public CreateLobbyUseCase createLobbyUseCase() {
        return createLobbyUseCase;
    }

    public HeroHttpServer createHeroHttpServer(int port) throws IOException {
        return new HeroHttpServer(port, createHeroUseCase, getHeroUseCase, createLobbyUseCase);
    }
}
