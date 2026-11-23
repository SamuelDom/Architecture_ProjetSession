package rpg.game.infrastructure;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import rpg.game.domain.Game;
import rpg.game.domain.GameRepository;

public final class InMemoryGameRepository implements GameRepository {
    private final ConcurrentMap<String, Game> games = new ConcurrentHashMap<>();

    @Override
    public void save(Game game) {
        Objects.requireNonNull(game, "game");
        games.put(game.id(), game);
    }
}