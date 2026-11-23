package rpg.lobby.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import rpg.game.domain.Game;
import rpg.game.infrastructure.InMemoryGameRepository;
import rpg.hero.domain.Characteristics;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroFactory;
import rpg.hero.domain.HeroName;
import rpg.hero.domain.HeroRepository;
import rpg.hero.domain.HeroClass;
import rpg.hero.domain.Species;
import rpg.hero.infrastructure.InMemoryHeroRepository;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyException;
import rpg.lobby.infrastructure.InMemoryLobbyRepository;

class StartGameUseCaseTest {
    private final HeroRepository heroes = new InMemoryHeroRepository();
    private final HeroFactory heroFactory = new HeroFactory(heroes);
    private final InMemoryLobbyRepository lobbies = new InMemoryLobbyRepository();
    private final InMemoryGameRepository games = new InMemoryGameRepository();

    @Test
    void startsWithExactlyTheSelectedHeroesAndLocksTheLobby() {
        Hero first = createHero("Aria");
        Hero second = createHero("Thorin");
        Lobby lobby = new Lobby("l-1", "p-1");
        lobby.join("p-2");
        lobby.selectHero("p-1", first.id().toString());
        lobby.selectHero("p-2", second.id().toString());
        lobbies.save(lobby);

        Game game = useCase("g-5").execute("l-1", "p-1");

        assertEquals("g-5", game.id());
        assertEquals(List.of(first, second), game.party());
        assertTrue(lobby.isStarted());
        assertThrows(LobbyException.GameAlreadyStarted.class, () -> lobby.join("p-3"));
        assertThrows(LobbyException.GameAlreadyStarted.class, () -> lobby.leave("p-2"));
        assertThrows(LobbyException.GameAlreadyStarted.class,
                () -> lobby.selectHero("p-2", first.id().toString()));
        assertThrows(LobbyException.GameAlreadyStarted.class,
                () -> useCase("g-6").execute("l-1", "p-1"));
    }

    @Test
    void onlyHostCanStart() {
        Hero hero = createHero("Aria");
        Lobby lobby = readyLobby(hero);
        lobbies.save(lobby);

        assertThrows(LobbyException.NotHost.class,
                () -> useCase("g-5").execute("l-1", "p-2"));
        assertFalse(lobby.isStarted());
    }

    @Test
    void requiresEveryMemberToSelectAHero() {
        Hero hero = createHero("Aria");
        Lobby lobby = new Lobby("l-1", "p-1");
        lobby.join("p-2");
        lobby.selectHero("p-1", hero.id().toString());
        lobbies.save(lobby);

        assertThrows(LobbyException.PlayersNotReady.class,
                () -> useCase("g-5").execute("l-1", "p-1"));
        assertFalse(lobby.isStarted());
    }

    @Test
    void supportsSinglePlayerAndRejectsMoreThanFourMembers() {
        Hero hero = createHero("Aria");
        Lobby lobby = readyLobby(hero);
        lobbies.save(lobby);
        assertEquals(List.of(hero), useCase("g-5").execute("l-1", "p-1").party());

        List<Hero> fourHeroes = List.of(
                createHero("Borin"), createHero("Celia"),
                createHero("Doran"), createHero("Eira"));
        Lobby fourPlayerLobby = new Lobby("l-3", "p-1");
        for (int index = 0; index < fourHeroes.size(); index++) {
            String playerId = "p-" + (index + 1);
            if (index > 0) {
                fourPlayerLobby.join(playerId);
            }
            fourPlayerLobby.selectHero(playerId, fourHeroes.get(index).id().toString());
        }
        lobbies.save(fourPlayerLobby);
        assertEquals(fourHeroes, useCase("g-7").execute("l-3", "p-1").party());

        Lobby fullLobby = new Lobby("l-2", "p-1");
        fullLobby.join("p-2");
        fullLobby.join("p-3");
        fullLobby.join("p-4");
        assertThrows(IllegalStateException.class, () -> fullLobby.join("p-5"));
    }

    private Lobby readyLobby(Hero hero) {
        Lobby lobby = new Lobby("l-1", "p-1");
        lobby.selectHero("p-1", hero.id().toString());
        return lobby;
    }

    private Hero createHero(String name) {
        return heroFactory.create(new HeroName(name), Species.HUMAN, HeroClass.WARRIOR,
                new Characteristics(14, 12, 14, 10, 10, 10));
    }

    private StartGameUseCase useCase(String gameId) {
        return new StartGameUseCase(lobbies, heroes, games, () -> gameId);
    }
}