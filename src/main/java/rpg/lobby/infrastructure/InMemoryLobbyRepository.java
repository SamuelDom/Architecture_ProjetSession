package rpg.lobby.infrastructure;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import rpg.lobby.domain.Lobby;
import rpg.lobby.domain.LobbyRepository;

public final class InMemoryLobbyRepository implements LobbyRepository {
    private final ConcurrentMap<String, Lobby> lobbies = new ConcurrentHashMap<>();

    @Override
    public Optional<Lobby> findById(String lobbyId) {
        return Optional.ofNullable(lobbies.get(lobbyId));
    }

    @Override
    public void save(Lobby lobby) {
        Objects.requireNonNull(lobby, "lobby");
        lobbies.put(lobby.id(), lobby);
    }
}