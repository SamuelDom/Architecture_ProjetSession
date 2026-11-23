package lobbies.application;

import lobbies.domain.Lobby;

public class LobbyOutputMapper {
    public LobbyOutput toOutput(Lobby lobby) {
        return new LobbyOutput(
                lobby.id().toString(),
                lobby.hostId().toString(),
                lobby.members().stream().map(Object::toString).toList(),
                lobby.availableSeats(),
                lobby.status().name()
        );
    }
}
