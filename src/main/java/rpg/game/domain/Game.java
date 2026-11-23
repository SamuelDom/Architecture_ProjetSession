package rpg.game.domain;

import java.util.List;
import java.util.Objects;
import rpg.hero.domain.Hero;

public record Game(String id, String lobbyId, List<Hero> party) {
    public Game {
        Objects.requireNonNull(id, "id");
        Objects.requireNonNull(lobbyId, "lobbyId");
        party = List.copyOf(Objects.requireNonNull(party, "party"));
        if (party.isEmpty() || party.size() > 4) {
            throw new IllegalArgumentException("Une partie doit compter de 1 à 4 héros.");
        }
    }
}