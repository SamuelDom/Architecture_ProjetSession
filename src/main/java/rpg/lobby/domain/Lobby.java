package rpg.lobby.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class Lobby {
    public static final int MAX_PLAYERS = 4;

    private final String id;
    private final Map<String, String> selectedHeroByPlayer = new LinkedHashMap<>();
    private boolean started;

    public Lobby(String id, String hostPlayerId) {
        this.id = requireText(id, "L’identifiant du lobby est requis.");
        selectedHeroByPlayer.put(
                requireText(hostPlayerId, "L’identifiant de l’hôte est requis."), null);
    }

    public synchronized void join(String playerId) {
        ensureNotStarted();
        String validPlayerId = requireText(playerId, "L’identifiant du joueur est requis.");
        if (selectedHeroByPlayer.containsKey(validPlayerId)) {
            throw new IllegalArgumentException("Le joueur est déjà dans ce lobby.");
        }
        if (selectedHeroByPlayer.size() == MAX_PLAYERS) {
            throw new IllegalStateException("Le lobby est complet.");
        }
        selectedHeroByPlayer.put(validPlayerId, null);
    }

    public synchronized void leave(String playerId) {
        ensureNotStarted();
        ensureMember(playerId);
        selectedHeroByPlayer.remove(playerId);
    }

    public synchronized void selectHero(String playerId, String heroId) {
        ensureNotStarted();
        ensureMember(playerId);
        selectedHeroByPlayer.put(
                playerId, requireText(heroId, "L’identifiant du héros est requis."));
    }

    public synchronized List<String> readyHeroIds(String requesterId) {
        ensureNotStarted();
        ensureHost(requesterId);
        if (selectedHeroByPlayer.isEmpty()
                || selectedHeroByPlayer.size() > MAX_PLAYERS
                || selectedHeroByPlayer.values().stream().anyMatch(Objects::isNull)) {
            throw new LobbyException.PlayersNotReady();
        }
        return List.copyOf(new ArrayList<>(selectedHeroByPlayer.values()));
    }

    public synchronized void markStarted(String requesterId) {
        ensureNotStarted();
        ensureHost(requesterId);
        if (selectedHeroByPlayer.isEmpty()
                || selectedHeroByPlayer.size() > MAX_PLAYERS
                || selectedHeroByPlayer.values().stream().anyMatch(Objects::isNull)) {
            throw new LobbyException.PlayersNotReady();
        }
        started = true;
    }

    public String id() {
        return id;
    }

    public synchronized boolean isStarted() {
        return started;
    }

    public synchronized int playerCount() {
        return selectedHeroByPlayer.size();
    }

    private void ensureNotStarted() {
        if (started) {
            throw new LobbyException.GameAlreadyStarted();
        }
    }

    private void ensureHost(String requesterId) {
        String hostId = selectedHeroByPlayer.keySet().stream().findFirst().orElse(null);
        if (hostId == null || !hostId.equals(requesterId)) {
            throw new LobbyException.NotHost();
        }
    }

    private void ensureMember(String playerId) {
        if (!selectedHeroByPlayer.containsKey(playerId)) {
            throw new IllegalArgumentException("Le joueur n’est pas membre de ce lobby.");
        }
    }

    private static String requireText(String value, String message) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }
}