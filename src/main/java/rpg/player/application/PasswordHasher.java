package rpg.player.application;

public interface PasswordHasher {
    String hash(String password);

    boolean verify(String password, String hash);
}
