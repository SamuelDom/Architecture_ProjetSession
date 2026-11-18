package hero.infrastructure;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import rpg.lobby.application.GetLobbyDetailsUseCase;
import rpg.lobby.application.ListAvailableLobbiesUseCase;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyRepository;
import rpg.lobby.infrastructure.InMemoryLobbyRepository;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LobbyHttpHandlerTest {

    private HttpServer server;
    private HttpClient client;
    private String baseUrl;

    @BeforeEach
    void setUp() throws IOException {
        LobbyRepository repository = new InMemoryLobbyRepository();

        repository.save(new Lobby(
                "lobby-open",
                "player-host",
                Lobby.State.OPEN,
                List.of(new Lobby.Member(
                        "player-host",
                        "Karlens",
                        Optional.of(new Lobby.SelectedHero("hero-1", "Aria"))))
        ));

        repository.save(new Lobby(
                "lobby-closed",
                "player-closed",
                Lobby.State.CLOSED,
                List.of(new Lobby.Member(
                        "player-closed",
                        "ClosedHost",
                        Optional.empty()))
        ));

        repository.save(new Lobby(
                "lobby-full",
                "player-full",
                Lobby.State.OPEN,
                List.of(
                        new Lobby.Member("player-1", "Player1", Optional.empty()),
                        new Lobby.Member("player-2", "Player2", Optional.empty()),
                        new Lobby.Member("player-3", "Player3", Optional.empty()),
                        new Lobby.Member("player-4", "Player4", Optional.empty()))
        ));

        var listLobbies = new ListAvailableLobbiesUseCase(repository);
        var getLobbyDetails = new GetLobbyDetailsUseCase(repository);

        server = HttpServer.create(new InetSocketAddress("localhost", 0), 0);
        server.createContext(
                "/",
                new LobbyHttpHandler(listLobbies, getLobbyDetails));
        server.start();

        client = HttpClient.newHttpClient();
        baseUrl = "http://localhost:" + server.getAddress().getPort();
    }

    @AfterEach
    void tearDown() {
        if (server != null) {
            server.stop(0);
        }
    }

    @Test
    void listsOnlyAvailableLobbies() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/lobbies");

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("lobby-open"));
        assertTrue(response.body().contains("player-host"));
        assertTrue(response.body().contains("playerCount"));
        assertTrue(response.body().contains("maxPlayers"));
        assertFalse(response.body().contains("lobby-closed"));
        assertFalse(response.body().contains("lobby-full"));
    }

    @Test
    void returnsLobbyDetailsWithoutEmail() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/lobbies/lobby-open");

        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("lobby-open"));
        assertTrue(response.body().contains("Karlens"));
        assertTrue(response.body().contains("Aria"));
        assertTrue(response.body().contains("hero-1"));
        assertFalse(response.body().contains("email"));
    }

    @Test
    void returnsNotFoundForUnknownLobby() throws IOException, InterruptedException {
        HttpResponse<String> response = get("/lobbies/inexistant");

        assertEquals(404, response.statusCode());
    }

    private HttpResponse<String> get(String path)
            throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(baseUrl + path))
                .GET()
                .build();

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}