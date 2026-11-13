package rpg.player.infrastructure;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import rpg.player.domain.PlayerAccount;
import rpg.player.domain.PlayerRepository;

public final class InMemoryPlayerRepository implements PlayerRepository {
    private final Map<String, PlayerAccount> accountsByEmail = new HashMap<>();
    private final Set<String> pseudonyms = new HashSet<>();

    @Override
    public synchronized Optional<PlayerAccount> findByEmail(String email) {
        return Optional.ofNullable(accountsByEmail.get(email.toLowerCase(Locale.ROOT)));
    }

    @Override
    public synchronized RegistrationResult register(PlayerAccount account) {
        String email = account.email().toLowerCase(Locale.ROOT);
        String pseudonym = account.pseudonym().toUpperCase(Locale.ROOT).toLowerCase(Locale.ROOT);
        if (pseudonyms.contains(pseudonym)) {
            return RegistrationResult.PSEUDONYM_TAKEN;
        }
        if (accountsByEmail.containsKey(email)) {
            return RegistrationResult.EMAIL_TAKEN;
        }
        accountsByEmail.put(email, account);
        pseudonyms.add(pseudonym);
        return RegistrationResult.CREATED;
    }
}
