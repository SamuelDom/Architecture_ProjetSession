package rpg.player.application;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import rpg.player.domain.PlayerAccount;
import rpg.player.domain.PlayerRepository;

public final class PlayerSessions {
    private final PlayerRepository players;
    private final PasswordHasher passwords;
    private final String unknownAccountHash;
    private final SecureRandom random = new SecureRandom();
    private final Map<String, String> playersByToken = new ConcurrentHashMap<>();

    public PlayerSessions(PlayerRepository players, PasswordHasher passwords) {
        this.players = players;
        this.passwords = passwords;
        this.unknownAccountHash = passwords.hash(UUID.randomUUID().toString());
    }

    public Optional<Session> signIn(String email, String password) {
        Optional<PlayerAccount> account = players.findByEmail(email);
        String hash = account.map(PlayerAccount::passwordHash).orElse(unknownAccountHash);
        if (!passwords.verify(password, hash) || account.isEmpty()) {
            return Optional.empty();
        }

        String playerId = account.get().id();
        byte[] bytes = new byte[32];
        String token;
        do {
            random.nextBytes(bytes);
            token = Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
        } while (playersByToken.putIfAbsent(token, playerId) != null);
        return Optional.of(new Session(playerId, token));
    }

    public Optional<String> findPlayerId(String token) {
        return Optional.ofNullable(playersByToken.get(token));
    }

    public record Session(String playerId, String token) {}
}
