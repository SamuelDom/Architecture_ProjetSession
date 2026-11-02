package hero.infrastructure;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import config.ApplicationContext;
import hero.application.CreateHeroCommand;
import hero.application.CreateHeroUseCase;
import hero.application.GetHeroUseCase;
import hero.application.HeroOutput;
import hero.domain.HeroClass;
import hero.domain.Species;
import hero.domain.exception.DomainException;
import hero.domain.exception.HeroNameAlreadyTakenException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class HeroHttpServer {

    private final HttpServer server;
    private final CreateHeroUseCase createHeroUseCase;
    private final GetHeroUseCase getHeroUseCase;

    public HeroHttpServer(int port, CreateHeroUseCase createHeroUseCase,
                          GetHeroUseCase getHeroUseCase) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        this.createHeroUseCase = createHeroUseCase;
        this.getHeroUseCase = getHeroUseCase;
        server.createContext("/heroes", this::handleRequest);
    }

    public void start() {
        server.start();
    }

    public static void main(String[] args) throws IOException {
        int port = args.length == 0 ? 8080 : Integer.parseInt(args[0]);
        ApplicationContext context = new ApplicationContext();
        HeroHttpServer server = new HeroHttpServer(
                port, context.createHeroUseCase(), context.getHeroUseCase());
        server.start();
        System.out.println("API Héros disponible sur http://localhost:" + port);
    }

    private void handleRequest(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String method = exchange.getRequestMethod();

        try {
            if ("POST".equals(method) && "/heroes".equals(path)) {
                createHero(exchange);
                return;
            }
            if ("GET".equals(method) && path.startsWith("/heroes/")) {
                getHero(exchange, path.substring("/heroes/".length()));
                return;
            }
            if ("GET".equals(method) || "POST".equals(method)) {
                sendError(exchange, 404, "Route introuvable.");
                return;
            }
            exchange.getResponseHeaders().set("Allow", "GET, POST");
            sendError(exchange, 405, "Méthode non autorisée.");
        } catch (HeroNameAlreadyTakenException exception) {
            sendError(exchange, 409, exception.getMessage());
        } catch (DomainException | IllegalArgumentException exception) {
            sendError(exchange, 400, exception.getMessage());
        }
    }

    private void createHero(HttpExchange exchange) throws IOException {
        Map<String, Object> body = JsonObject.parse(
                new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8));
        CreateHeroCommand command = new CreateHeroCommand(
                stringField(body, "name"),
                Species.valueOf(stringField(body, "species")),
                HeroClass.valueOf(stringField(body, "heroClass")),
                integerField(body, "strength"),
                integerField(body, "dexterity"),
                integerField(body, "constitution"),
                integerField(body, "intelligence"),
                integerField(body, "wisdom"),
                integerField(body, "charisma"));

        HeroOutput hero = createHeroUseCase.execute(command);
        exchange.getResponseHeaders().set("Location", "/heroes/" + hero.id());
        sendJson(exchange, 201, serialize(hero));
    }

    private void getHero(HttpExchange exchange, String id) throws IOException {
        if (id.isEmpty() || id.contains("/")) {
            sendError(exchange, 400, "Identifiant de héros invalide.");
            return;
        }
        Optional<HeroOutput> hero = getHeroUseCase.execute(id);
        if (hero.isEmpty()) {
            sendError(exchange, 404, "Héros introuvable.");
            return;
        }
        sendJson(exchange, 200, serialize(hero.get()));
    }

    private static String stringField(Map<String, Object> body, String name) {
        Object value = body.get(name);
        if (!(value instanceof String text)) {
            throw new IllegalArgumentException("Le champ '" + name + "' doit être une chaîne.");
        }
        return text;
    }

    private static int integerField(Map<String, Object> body, String name) {
        Object value = body.get(name);
        if (!(value instanceof Integer number)) {
            throw new IllegalArgumentException("Le champ '" + name + "' doit être un entier.");
        }
        return number;
    }

    private static String serialize(HeroOutput hero) {
        return "{"
                + "\"id\":" + quote(hero.id()) + ","
                + "\"name\":" + quote(hero.name()) + ","
                + "\"species\":" + quote(hero.species()) + ","
                + "\"heroClass\":" + quote(hero.heroClass()) + ","
                + "\"level\":" + hero.level() + ","
                + "\"experience\":" + hero.experience() + ","
                + "\"currentHitPoints\":" + hero.currentHitPoints() + ","
                + "\"maxHitPoints\":" + hero.maxHitPoints() + ","
                + "\"currentMagicPoints\":" + hero.currentMagicPoints() + ","
                + "\"maxMagicPoints\":" + hero.maxMagicPoints()
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

    private static void sendError(HttpExchange exchange, int status, String message) throws IOException {
        sendJson(exchange, status, "{\"error\":" + quote(message == null ? "Requête invalide." : message) + "}");
    }

    private static void sendJson(HttpExchange exchange, int status, String body) throws IOException {
        byte[] response = body.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=utf-8");
        exchange.sendResponseHeaders(status, response.length);
        try (OutputStream output = exchange.getResponseBody()) {
            output.write(response);
        }
    }

    private static final class JsonObject {
        private final String source;
        private int index;

        private JsonObject(String source) {
            this.source = source;
        }

        static Map<String, Object> parse(String source) {
            return new JsonObject(source).parseObject();
        }

        private Map<String, Object> parseObject() {
            Map<String, Object> values = new HashMap<>();
            skipWhitespace();
            expect('{');
            skipWhitespace();
            if (consume('}')) {
                ensureFinished();
                return values;
            }
            while (true) {
                skipWhitespace();
                String key = parseString();
                skipWhitespace();
                expect(':');
                skipWhitespace();
                Object value = peek() == '"' ? parseString() : parseInteger();
                if (values.putIfAbsent(key, value) != null) {
                    throw invalidJson();
                }
                skipWhitespace();
                if (consume('}')) {
                    ensureFinished();
                    return values;
                }
                expect(',');
            }
        }

        private String parseString() {
            expect('"');
            StringBuilder value = new StringBuilder();
            while (index < source.length()) {
                char character = source.charAt(index++);
                if (character == '"') {
                    return value.toString();
                }
                if (character < 0x20) {
                    throw invalidJson();
                }
                if (character != '\\') {
                    value.append(character);
                    continue;
                }
                if (index >= source.length()) {
                    throw invalidJson();
                }
                char escaped = source.charAt(index++);
                switch (escaped) {
                    case '"', '\\', '/' -> value.append(escaped);
                    case 'b' -> value.append('\b');
                    case 'f' -> value.append('\f');
                    case 'n' -> value.append('\n');
                    case 'r' -> value.append('\r');
                    case 't' -> value.append('\t');
                    case 'u' -> value.append(parseUnicodeEscape());
                    default -> throw invalidJson();
                }
            }
            throw invalidJson();
        }

        private char parseUnicodeEscape() {
            if (index + 4 > source.length()) {
                throw invalidJson();
            }
            try {
                char value = (char) Integer.parseInt(source.substring(index, index + 4), 16);
                index += 4;
                return value;
            } catch (NumberFormatException exception) {
                throw invalidJson();
            }
        }

        private Integer parseInteger() {
            int start = index;
            if (peek() == '-') {
                index++;
            }
            while (index < source.length() && Character.isDigit(source.charAt(index))) {
                index++;
            }
            if (start == index || (source.charAt(start) == '-' && start + 1 == index)) {
                throw invalidJson();
            }
            try {
                return Integer.valueOf(source.substring(start, index));
            } catch (NumberFormatException exception) {
                throw invalidJson();
            }
        }

        private void skipWhitespace() {
            while (index < source.length()) {
                char character = source.charAt(index);
                if (character != ' ' && character != '\n' && character != '\r' && character != '\t') {
                    return;
                }
                index++;
            }
        }

        private char peek() {
            return index < source.length() ? source.charAt(index) : '\0';
        }

        private boolean consume(char expected) {
            if (peek() != expected) {
                return false;
            }
            index++;
            return true;
        }

        private void expect(char expected) {
            if (!consume(expected)) {
                throw invalidJson();
            }
        }

        private void ensureFinished() {
            skipWhitespace();
            if (index != source.length()) {
                throw invalidJson();
            }
        }

        private IllegalArgumentException invalidJson() {
            return new IllegalArgumentException("Le corps doit être un objet JSON valide.");
        }
    }
}