package rpg.lobby.domain;

/** Erreur métier du lobby. Le domaine ne connaît pas HTTP : il lance seulement ces exceptions. */
public abstract class LobbyException extends RuntimeException {
    protected LobbyException(String message) { super(message); }

    /** Utilisé plus tard par la couche HTTP pour répondre 403 FORBIDDEN. */
    public static final class Forbidden extends LobbyException {
        public Forbidden(String message) { super(message); }
    }

    /** 404 PLAYER_NOT_IN_LOBBY */
    public static final class PlayerNotInLobby extends LobbyException {
        public PlayerNotInLobby(String playerId) {
            super("Le joueur " + playerId + " n'est pas membre de ce lobby.");
        }
    }

    /** 409 GAME_ALREADY_STARTED */
    public static final class GameAlreadyStarted extends LobbyException {
        public GameAlreadyStarted() { super("La partie est déjà lancée."); }
    }
}
