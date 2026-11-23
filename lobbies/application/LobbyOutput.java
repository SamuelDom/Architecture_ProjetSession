package lobbies.application;

import java.util.List;

public record LobbyOutput(
        String id,
        String hostId,
        List<String> members,
        int availableSeats,
        String status) {
}
