package lobbies.domain.exception;

import heros.domain.exception.DomainException;

public class AlreadyInLobbyException extends DomainException {
    public AlreadyInLobbyException(String playerId) {
        super("Le joueur \"" + playerId + "\" est déjà membre d'un lobby ouvert.");
    }
}
