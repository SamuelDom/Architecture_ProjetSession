package rpg.lobby.application;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Supplier;
import rpg.game.domain.Game;
import rpg.game.domain.GameRepository;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroId;
import rpg.hero.domain.HeroRepository;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyRepository;

public final class StartGameUseCase {
    private final LobbyRepository lobbies;
    private final HeroRepository heroes;
    private final GameRepository games;
    private final Supplier<String> gameIdGenerator;

    public StartGameUseCase(
            LobbyRepository lobbies, HeroRepository heroes, GameRepository games) {
        this(lobbies, heroes, games, () -> "g-" + UUID.randomUUID());
    }

    public StartGameUseCase(
            LobbyRepository lobbies,
            HeroRepository heroes,
            GameRepository games,
            Supplier<String> gameIdGenerator) {
        this.lobbies = Objects.requireNonNull(lobbies);
        this.heroes = Objects.requireNonNull(heroes);
        this.games = Objects.requireNonNull(games);
        this.gameIdGenerator = Objects.requireNonNull(gameIdGenerator);
    }

    public Game execute(String lobbyId, String requesterId) {
        Lobby lobby = lobbies.findById(lobbyId)
                .orElseThrow(() -> new LobbyNotFoundException(lobbyId));

        synchronized (lobby) {
            List<String> selectedHeroIds = lobby.readyHeroIds(requesterId);
            List<Hero> party = selectedHeroIds.stream()
                    .map(this::findHero)
                    .toList();
            Game game = new Game(gameIdGenerator.get(), lobby.id(), party);
            lobby.markStarted(requesterId);
            games.save(game);
            return game;
        }
    }

    private Hero findHero(String heroId) {
        UUID id;
        try {
            id = UUID.fromString(heroId);
        } catch (IllegalArgumentException exception) {
            throw new HeroNotFoundException(heroId);
        }
        return heroes.findById(new HeroId(id))
                .orElseThrow(() -> new HeroNotFoundException(heroId));
    }

    public static final class LobbyNotFoundException extends RuntimeException {
        public LobbyNotFoundException(String lobbyId) {
            super("Lobby introuvable : " + lobbyId);
        }
    }

    public static final class HeroNotFoundException extends RuntimeException {
        public HeroNotFoundException(String heroId) {
            super("Héros introuvable : " + heroId);
        }
    }
}