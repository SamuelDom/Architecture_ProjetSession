package lobbies.domain;

import java.util.Objects;

public record LobbyId(String value) {
    public LobbyId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("L'identifiant du lobby est requis.");
        }
    }

    @Override
    public String toString() {
        return value;
    }
}
