package hero.infrastructure;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import rpg.lobby.application.GetLobbyDetailsUseCase;
import rpg.lobby.application.ListAvailableLobbiesUseCase;

public final class LobbyHttpHandler implements HttpHandler {

    private final ListAvailableLobbiesUseCase listAvailableLobbies;
    private final GetLobbyDetailsUseCase getLobbyDetails;

    public LobbyHttpHandler(
            ListAvailableLobbiesUseCase listAvailableLobbies,
            GetLobbyDetailsUseCase getLobbyDetails) {
        this.listAvailableLobbies = listAvailableLobbies;
        this.getLobbyDetails = getLobbyDetails;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try (exchange) {
            if (!"GET".equals(exchange.getRequestMethod())) {
                exchange.getResponseHeaders().set("Allow", "GET");
                sendError(exchange, 405, "Méthode non autorisée.");
                return;
            }

            String path = exchange.getRequestURI().getPath();

            if ("/lobbies".equals(path)) {
                sendLobbyList(exchange, listAvailableLobbies.execute());
                return;
            }

            if (path.startsWith("/lobbies/")) {
                String lobbyId = path.substring("/lobbies/".length());

                if (lobbyId.isEmpty() || lobbyId.contains("/")) {
                    sendError(exchange, 400, "Identifiant de lobby invalide.");
                    return;
                }

                Optional<GetLobbyDetailsUseCase.LobbyDetails> details =
                        getLobbyDetails.execute(lobbyId);

                if (details.isEmpty()) {
                    sendError(exchange, 404, "Lobby introuvable.");
                    return;
                }

                sendJson(exchange, 200, serializeDetails(details.get()));
                return;
            }

            sendError(exchange, 404, "Route introuvable.");
        }
    }

    private static void sendLobbyList(
            HttpExchange exchange,
            List<ListAvailableLobbiesUseCase.LobbySummary> lobbies)
            throws IOException {

        StringBuilder json = new StringBuilder("[");

        for (int index = 0; index < lobbies.size(); index++) {
            if (index > 0) {
                json.append(",");
            }
            json.append(serializeSummary(lobbies.get(index)));
        }

        json.append("]");
        sendJson(exchange, 200, json.toString());
    }

    private static String serializeSummary(
            ListAvailableLobbiesUseCase.LobbySummary lobby) {
        return "{"
                + "\"id\":" + quote(lobby.id()) + ","
                + "\"hostId\":" + quote(lobby.hostId()) + ","
                + "\"playerCount\":" + lobby.playerCount() + ","
                + "\"maxPlayers\":" + lobby.maxPlayers()
                + "}";
    }

    private static String serializeDetails(
            GetLobbyDetailsUseCase.LobbyDetails lobby) {
        StringBuilder json = new StringBuilder("{")
                .append("\"id\":").append(quote(lobby.id())).append(",")
                .append("\"hostId\":").append(quote(lobby.hostId())).append(",")
                .append("\"state\":").append(quote(lobby.state())).append(",")
                .append("\"members\":[");

        for (int index = 0; index < lobby.members().size(); index++) {
            if (index > 0) {
                json.append(",");
            }

            GetLobbyDetailsUseCase.MemberDetails member = lobby.members().get(index);
            json.append("{")
                    .append("\"playerId\":").append(quote(member.playerId())).append(",")
                    .append("\"pseudonym\":").append(quote(member.pseudonym())).append(",")
                    .append("\"selectedHero\":")
                    .append(serializeSelectedHero(member.selectedHero()))
                    .append("}");
        }

        return json.append("]}").toString();
    }

    private static String serializeSelectedHero(
            Optional<rpg.lobby.domain.Lobby.SelectedHero> selectedHero) {
        if (selectedHero.isEmpty()) {
            return "null";
        }

        var hero = selectedHero.get();
        return "{"
                + "\"id\":" + quote(hero.id()) + ","
                + "\"name\":" + quote(hero.name())
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

    private static void sendError(
            HttpExchange exchange,
            int status,
            String message) throws IOException {
        sendJson(exchange, status, "{\"error\":" + quote(message) + "}");
    }

    private static void sendJson(
            HttpExchange exchange,
            int status,
            String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);

        exchange.getResponseHeaders().set(
                "Content-Type",
                "application/json; charset=utf-8");

        exchange.sendResponseHeaders(status, response.length);

        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }
}