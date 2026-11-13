package rpg.player.application;

import java.util.UUID;
import java.util.regex.Pattern;
import rpg.player.domain.PlayerAccount;
import rpg.player.domain.PlayerRepository;

public final class RegisterPlayerUseCase {
    private static final Pattern PSEUDONYM = Pattern.compile("[\\p{L}\\p{Nd}_]{3,20}");
    private static final Pattern EMAIL = Pattern.compile(
            "[a-zA-Z0-9_!#$%&'*+/=?^`{|}~-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?^`{|}~-]+)*"
                    + "@[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?"
                    + "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?)+");

    private final PlayerRepository players;
    private final PasswordHasher passwords;

    public RegisterPlayerUseCase(PlayerRepository players, PasswordHasher passwords) {
        this.players = players;
        this.passwords = passwords;
    }

    public PlayerOutput execute(String pseudonym, String email, String password) {
        if (!PSEUDONYM.matcher(pseudonym).matches() || !EMAIL.matcher(email).matches()) {
            throw new Rejected("INVALID_REQUEST");
        }
        if (password.codePointCount(0, password.length()) < 8) {
            throw new Rejected("PASSWORD_TOO_SHORT");
        }

        PlayerAccount account = new PlayerAccount(
                "p-" + UUID.randomUUID(), pseudonym, email, passwords.hash(password));
        return switch (players.register(account)) {
            case CREATED -> new PlayerOutput(account.id(), account.pseudonym(), account.email());
            case PSEUDONYM_TAKEN -> throw new Rejected("PSEUDONYM_TAKEN");
            case EMAIL_TAKEN -> throw new Rejected("EMAIL_TAKEN");
        };
    }

    public record PlayerOutput(String id, String pseudonym, String email) {}

    public static final class Rejected extends RuntimeException {
        public Rejected(String code) {
            super(code);
        }
    }
}
