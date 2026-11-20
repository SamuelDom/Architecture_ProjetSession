package rpg.lobby.domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Salle d'attente d'une partie.
 *
 * PLACEHOLDER : la création du lobby et "rejoindre" (L1-L3) sont faites par les coéquipiers.
 * Ici, join() et start() existent seulement pour pouvoir tester L4 et L5.
 * Les identifiants sont des String pour l'instant (ex. "p-1", "h-3").
 */
public final class Lobby {
    private final String id;
    // LinkedHashMap garde l'ORDRE D'ARRIVÉE : le premier joueur de la liste est l'hôte.
    // Clé = joueur, valeur = héros choisi (null tant qu'il n'en a pas choisi).
    private final Map<String, String> selectedHeroByPlayer = new LinkedHashMap<>();
    private boolean started = false;

    public Lobby(String id, String hostPlayerId) {
        this.id = id;
        selectedHeroByPlayer.put(hostPlayerId, null);
    }

    // ---------- PLACEHOLDERS (travail des autres) ----------
    public void join(String playerId) { selectedHeroByPlayer.put(playerId, null); }
    public void start() { started = true; }
    // -------------------------------------------------------

    /** L4 — Quitter le lobby. */
    public void leave(String requesterId, String playerId) {
        ensureNotStarted();
        ensureIsMember(playerId);
        ensureSelf(requesterId, playerId, "Un joueur ne peut retirer que lui-même.");
        selectedHeroByPlayer.remove(playerId); // sa place se libère ET son héros n'est plus sélectionné
    }

    /** L5 — Sélectionner un héros (remplace le choix précédent). */
    public void selectHero(String requesterId, String playerId, String heroId) {
        ensureNotStarted();
        ensureIsMember(playerId);
        ensureSelf(requesterId, playerId, "Un joueur ne peut sélectionner un héros que pour lui-même.");
        selectedHeroByPlayer.put(playerId, heroId);
    }

    // ---------- Lecture ----------
    public String id() { return id; }
    public boolean isStarted() { return started; }
    public boolean isEmpty() { return selectedHeroByPlayer.isEmpty(); }
    public List<String> players() { return new ArrayList<>(selectedHeroByPlayer.keySet()); }

    /** Le plus ancien joueur encore présent. Si l'hôte part, le suivant "monte" tout seul. */
    public Optional<String> host() { return selectedHeroByPlayer.keySet().stream().findFirst(); }

    public Optional<String> selectedHeroOf(String playerId) {
        return Optional.ofNullable(selectedHeroByPlayer.get(playerId));
    }

    // ---------- Règles privées ----------
    private void ensureNotStarted() {
        if (started) throw new LobbyException.GameAlreadyStarted();
    }
    private void ensureIsMember(String playerId) {
        if (!selectedHeroByPlayer.containsKey(playerId)) throw new LobbyException.PlayerNotInLobby(playerId);
    }
    private void ensureSelf(String requesterId, String playerId, String message) {
        if (!requesterId.equals(playerId)) throw new LobbyException.Forbidden(message);
    }
}
