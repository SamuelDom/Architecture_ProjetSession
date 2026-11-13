package rpg.player.domain;

public final class PlayerAccount {
    private final String id;
    private final String pseudonym;
    private final String email;
    private final String passwordHash;

    public PlayerAccount(String id, String pseudonym, String email, String passwordHash) {
        this.id = id;
        this.pseudonym = pseudonym;
        this.email = email;
        this.passwordHash = passwordHash;
    }

    public String id() { return id; }
    public String pseudonym() { return pseudonym; }
    public String email() { return email; }
    public String passwordHash() { return passwordHash; }
}
