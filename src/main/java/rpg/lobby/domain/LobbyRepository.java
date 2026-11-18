package rpg.lobby.domain;

import java.util.List;
import java.util.Optional;

public interface LobbyRepository {

    List<Lobby> findAll();

    Optional<Lobby> findById(String lobbyId);

    void save(Lobby lobby);
}