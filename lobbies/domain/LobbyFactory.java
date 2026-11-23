package lobbies.domain;

import heros.domain.HeroId;
import java.util.Objects;
import lobbies.domain.exception.AlreadyInLobbyException;

public class LobbyFactory {
    private final LobbyRepository lobbyRepository;

    public LobbyFactory(LobbyRepository lobbyRepository) {
        this.lobbyRepository = Objects.requireNonNull(lobbyRepository);
    }

    public Lobby create(HeroId hostId) {
        Objects.requireNonNull(hostId, "L'identifiant du joueur est requis.");
        if (lobbyRepository.existsOpenLobbyWithMember(hostId)) {
            throw new AlreadyInLobbyException(hostId.toString());
        }
        return new Lobby(lobbyRepository.nextIdentity(), hostId);
    }
}
