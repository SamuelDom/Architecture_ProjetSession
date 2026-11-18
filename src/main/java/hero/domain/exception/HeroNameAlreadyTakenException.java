package hero.domain.exception;

public class HeroNameAlreadyTakenException extends DomainException {
    public HeroNameAlreadyTakenException(String name) {
        super("Le nom de héros \"" + name + "\" est déjà utilisé.");
    }
}
