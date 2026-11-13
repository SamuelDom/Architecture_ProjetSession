package rpg.player.domain;

import java.util.Optional;

public interface PlayerRepository {
    enum RegistrationResult { CREATED, PSEUDONYM_TAKEN, EMAIL_TAKEN }

    Optional<PlayerAccount> findByEmail(String email);

    RegistrationResult register(PlayerAccount account);
}
