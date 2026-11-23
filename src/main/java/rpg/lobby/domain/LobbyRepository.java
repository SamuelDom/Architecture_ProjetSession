package rpg.lobby.domain;

import java.util.Optional;

public interface LobbyRepository {
    Optional<Lobby> findById(String lobbyId);
    void save(Lobby lobby);
}