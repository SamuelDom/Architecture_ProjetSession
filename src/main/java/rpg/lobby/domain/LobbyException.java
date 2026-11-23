package rpg.lobby.domain;

public abstract class LobbyException extends RuntimeException {
    protected LobbyException(String message) {
        super(message);
    }

    public static final class NotHost extends LobbyException {
        public NotHost() {
            super("Seul l’hôte peut lancer la partie.");
        }
    }

    public static final class PlayersNotReady extends LobbyException {
        public PlayersNotReady() {
            super("Chaque joueur doit sélectionner un héros.");
        }
    }

    public static final class GameAlreadyStarted extends LobbyException {
        public GameAlreadyStarted() {
            super("La partie est déjà lancée.");
        }
    }
}