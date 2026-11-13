package rpg.player.infrastructure;

import com.sun.net.httpserver.Authenticator;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpPrincipal;
import java.util.List;
import rpg.player.application.PlayerSessions;

public final class PlayerAuthentication extends Authenticator {
    private final PlayerSessions sessions;

    public PlayerAuthentication(PlayerSessions sessions) {
        this.sessions = sessions;
    }

    @Override
    public Result authenticate(HttpExchange exchange) {
        List<String> values = exchange.getRequestHeaders().get("Authorization");
        if (values != null && values.size() == 1) {
            String authorization = values.get(0);
            if (authorization.regionMatches(true, 0, "Bearer ", 0, 7)) {
                var player = sessions.findPlayerId(authorization.substring(7).trim());
                if (player.isPresent()) {
                    return new Success(new HttpPrincipal(player.get(), "rpg"));
                }
            }
        }

        exchange.getResponseHeaders().set("WWW-Authenticate", "Bearer");
        return new Failure(401);
    }
}
