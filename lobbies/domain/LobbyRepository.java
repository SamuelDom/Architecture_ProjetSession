package lobbies.domain;

import heros.domain.HeroId;
import java.util.Optional;

public interface LobbyRepository {
    LobbyId nextIdentity();
    void save(Lobby lobby);
    Optional<Lobby> findById(LobbyId id);
    boolean existsOpenLobbyWithMember(HeroId playerId);
}
