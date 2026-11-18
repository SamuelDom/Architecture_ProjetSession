package rpg.lobby.domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Lobby {

    public static final int MAX_PLAYERS = 4;

    public enum State {
        OPEN,
        CLOSED,
        IN_PROGRESS
    }

    public record SelectedHero(String id, String name) {
        public SelectedHero {
            Objects.requireNonNull(id, "id");
            Objects.requireNonNull(name, "name");
        }
    }

    public record Member(
            String playerId,
            String pseudonym,
            Optional<SelectedHero> selectedHero) {

        public Member {
            Objects.requireNonNull(playerId, "playerId");
            Objects.requireNonNull(pseudonym, "pseudonym");
            Objects.requireNonNull(selectedHero, "selectedHero");
        }
    }

    private final String id;
    private final String hostId;
    private final State state;
    private final List<Member> members;

    public Lobby(String id, String hostId, State state, List<Member> members) {
        this.id = Objects.requireNonNull(id, "id");
        this.hostId = Objects.requireNonNull(hostId, "hostId");
        this.state = Objects.requireNonNull(state, "state");
        this.members = List.copyOf(Objects.requireNonNull(members, "members"));

        if (this.members.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException(
                    "Un lobby ne peut pas dépasser 4 joueurs.");
        }
    }

    public String id() {
        return id;
    }

    public String hostId() {
        return hostId;
    }

    public State state() {
        return state;
    }

    public List<Member> members() {
        return members;
    }

    public int playerCount() {
        return members.size();
    }

    public boolean isAvailable() {
        return state == State.OPEN && playerCount() < MAX_PLAYERS;
    }
}