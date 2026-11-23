package rpg.lobby.infrastructure;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import rpg.lobby.application.StartGameUseCase;
import rpg.lobby.domain.LobbyException;

public final class LobbyHttpHandler implements HttpHandler {
    private final StartGameUseCase startGame;

    public LobbyHttpHandler(StartGameUseCase startGame) {
        this.startGame = startGame;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String prefix = "/lobbies/";
        String suffix = "/game";
        if (!"POST".equals(exchange.getRequestMethod())) {
            exchange.getResponseHeaders().set("Allow", "POST");
            sendError(exchange, 405, "METHOD_NOT_ALLOWED");
            return;
        }
        if (!path.startsWith(prefix) || !path.endsWith(suffix)) {
            sendError(exchange, 404, "ROUTE_NOT_FOUND");
            return;
        }

        String lobbyId = path.substring(prefix.length(), path.length() - suffix.length());
        if (lobbyId.isBlank() || lobbyId.contains("/")) {
            sendError(exchange, 404, "LOBBY_NOT_FOUND");
            return;
        }

        try {
            Principal principal = exchange.getPrincipal();
            if (principal == null) {
                sendError(exchange, 401, "UNAUTHENTICATED");
                return;
            }
            String requesterId = principal.getName();
            var game = startGame.execute(lobbyId, requesterId);
            exchange.getResponseHeaders().set("Location", "/games/" + game.id());
            exchange.sendResponseHeaders(201, -1);
        } catch (LobbyException.NotHost exception) {
            sendError(exchange, 403, "NOT_HOST");
        } catch (StartGameUseCase.LobbyNotFoundException exception) {
            sendError(exchange, 404, "LOBBY_NOT_FOUND");
        } catch (LobbyException.PlayersNotReady exception) {
            sendError(exchange, 409, "PLAYERS_NOT_READY");
        } catch (LobbyException.GameAlreadyStarted exception) {
            sendError(exchange, 409, "GAME_ALREADY_STARTED");
        } catch (StartGameUseCase.HeroNotFoundException exception) {
            sendError(exchange, 404, "HERO_NOT_FOUND");
        } finally {
            exchange.close();
        }
    }

    private static void sendError(HttpExchange exchange, int status, String error)
            throws IOException {
        byte[] response = ("{\"error\":\"" + error + "\"}")
                .getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}