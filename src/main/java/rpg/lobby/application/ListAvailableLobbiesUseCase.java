package rpg.lobby.application;

import java.util.List;
import java.util.Objects;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyRepository;

public final class ListAvailableLobbiesUseCase {

    private final LobbyRepository lobbies;

    public ListAvailableLobbiesUseCase(LobbyRepository lobbies) {
        this.lobbies = Objects.requireNonNull(lobbies, "lobbies");
    }

    public List<LobbySummary> execute() {
        return lobbies.findAll().stream()
                .filter(Lobby::isAvailable)
                .map(lobby -> new LobbySummary(
                        lobby.id(),
                        lobby.hostId(),
                        lobby.playerCount(),
                        Lobby.MAX_PLAYERS))
                .toList();
    }

    public record LobbySummary(
            String id,
            String hostId,
            int playerCount,
            int maxPlayers) {
    }
}