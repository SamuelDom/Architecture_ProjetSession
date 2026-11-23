package lobbies.infrastructure;

import heros.domain.HeroId;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import lobbies.domain.Lobby;
import lobbies.domain.LobbyId;
import lobbies.domain.LobbyRepository;

public class InMemoryLobbyRepository implements LobbyRepository {
    private final Map<LobbyId, Lobby> lobbies = new HashMap<>();
    private final AtomicInteger sequence = new AtomicInteger(0);

    @Override
    public LobbyId nextIdentity() {
        return new LobbyId("l-" + sequence.incrementAndGet());
    }

    @Override
    public void save(Lobby lobby) {
        lobbies.put(lobby.id(), lobby);
    }

    @Override
    public Optional<Lobby> findById(LobbyId id) {
        return Optional.ofNullable(lobbies.get(id));
    }

    @Override
    public boolean existsOpenLobbyWithMember(HeroId playerId) {
        return lobbies.values().stream()
                .anyMatch(lobby -> lobby.isOpen() && lobby.hasMember(playerId));
    }
}
