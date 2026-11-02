package hero;

import config.ApplicationContext;
import hero.infrastructure.HeroHttpServer;
import java.io.IOException;

public final class Main {

    private static final int DEFAULT_PORT = 8080;

    private Main() {}

    public static void main(String[] args) throws IOException {
        int port = port(args);
        ApplicationContext context = new ApplicationContext();
        HeroHttpServer server = context.createHeroHttpServer(port);
        server.start();
        System.out.println("API Héros disponible sur http://localhost:" + port);
    }

    private static int port(String[] args) {
        if (args.length > 0) {
            return Integer.parseInt(args[0]);
        }
        String configuredPort = System.getenv("PORT");
        return configuredPort == null || configuredPort.isBlank()
                ? DEFAULT_PORT
                : Integer.parseInt(configuredPort);
    }
}
