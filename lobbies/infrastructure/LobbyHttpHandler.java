package lobbies.infrastructure;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import heros.domain.exception.DomainException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import lobbies.application.CreateLobbyCommand;
import lobbies.application.CreateLobbyUseCase;
import lobbies.application.LobbyOutput;
import lobbies.domain.exception.AlreadyInLobbyException;

public final class LobbyHttpHandler implements HttpHandler {

    private final CreateLobbyUseCase createLobbyUseCase;

    public LobbyHttpHandler(CreateLobbyUseCase createLobbyUseCase) {
        this.createLobbyUseCase = createLobbyUseCase;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            if ("POST".equals(method) && "/lobbies".equals(path)) {
                createLobby(exchange);
                return;
            }
            if ("GET".equals(method) || "POST".equals(method)) {
                sendError(exchange, 404, "NOT_FOUND", "Route introuvable.");
                return;
            }
            exchange.getResponseHeaders().set("Allow", "POST");
            sendError(exchange, 405, "METHOD_NOT_ALLOWED", "Méthode non autorisée.");
        } catch (AlreadyInLobbyException exception) {
            sendError(exchange, 409, "ALREADY_IN_LOBBY", exception.getMessage());
        } catch (DomainException | IllegalArgumentException exception) {
            sendError(exchange, 400, "BAD_REQUEST", exception.getMessage());
        }
    }

    private void createLobby(HttpExchange exchange) throws IOException {
        String playerId = exchange.getRequestHeaders().getFirst("X-Player-Id");
        LobbyOutput lobby = createLobbyUseCase.execute(new CreateLobbyCommand(playerId));
        exchange.getResponseHeaders().set("Location", "/lobbies/" + lobby.id());
        sendJson(exchange, 201, serialize(lobby));
    }

    private static String serialize(LobbyOutput lobby) {
        String membersJson = lobby.members().stream()
                .map(LobbyHttpHandler::quote)
                .collect(Collectors.joining(",", "[", "]"));

        return "{"
                + "\"id\":" + quote(lobby.id()) + ","
                + "\"hostId\":" + quote(lobby.hostId()) + ","
                + "\"members\":" + membersJson + ","
                + "\"availableSeats\":" + lobby.availableSeats() + ","
                + "\"status\":" + quote(lobby.status())
                + "}";
    }

    private static String quote(String value) {
        StringBuilder json = new StringBuilder("\"");
        for (int index = 0; index < value.length(); index++) {
            char character = value.charAt(index);
            switch (character) {
                case '"' -> json.append("\\\"");
                case '\\' -> json.append("\\\\");
                case '\b' -> json.append("\\b");
                case '\f' -> json.append("\\f");
                case '\n' -> json.append("\\n");
                case '\r' -> json.append("\\r");
                case '\t' -> json.append("\\t");
                default -> {
                    if (character < 0x20) {
                        json.append(String.format("\\u%04x", (int) character));
                    } else {
                        json.append(character);
                    }
                }
            }
        }
        return json.append('"').toString();
    }

    private static void sendError(HttpExchange exchange, int status, String code, String message) throws IOException {
        String body = "{"
                + "\"error\":" + quote(code) + ","
                + "\"message\":" + quote(message == null ? "Requête invalide." : message)
                + "}";
        sendJson(exchange, status, body);
    }

    private static void sendJson(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}
