package rpg.player.infrastructure;

import com.password4j.Argon2Function;
import com.password4j.Password;
import com.password4j.types.Argon2;
import rpg.player.application.PasswordHasher;

public final class Argon2PasswordHasher implements PasswordHasher {
    private static final Argon2Function ARGON2ID =
            Argon2Function.getInstance(19 * 1024, 2, 1, 32, Argon2.ID);

    @Override
    public String hash(String password) {
        return Password.hash(password).addRandomSalt(16).with(ARGON2ID).getResult();
    }

    @Override
    public boolean verify(String password, String hash) {
        return Password.check(password, hash).with(Argon2Function.getInstanceFromHash(hash));
    }
}
