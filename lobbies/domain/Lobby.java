package lobbies.domain;

import heros.domain.HeroId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class Lobby {
    public static final int MAX_CAPACITY = 4;

    private final LobbyId id;
    private final HeroId hostId;
    private final List<HeroId> members;
    private LobbyStatus status;

    Lobby(LobbyId id, HeroId hostId) {
        this.id = Objects.requireNonNull(id, "L'identifiant du lobby est requis.");
        this.hostId = Objects.requireNonNull(hostId, "L'identifiant de l'hôte est requis.");
        this.members = new ArrayList<>();
        this.members.add(hostId);
        this.status = LobbyStatus.OPEN;
    }

    public LobbyId id() { return id; }
    public HeroId hostId() { return hostId; }
    public LobbyStatus status() { return status; }

    public List<HeroId> members() {
        return Collections.unmodifiableList(members);
    }

    public int availableSeats() {
        return MAX_CAPACITY - members.size();
    }

    public boolean isOpen() {
        return status == LobbyStatus.OPEN;
    }

    public boolean hasMember(HeroId playerId) {
        return members.contains(playerId);
    }
}
