package rpg.hero.application;

import java.util.Objects;
import rpg.hero.domain.Characteristics;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroFactory;
import rpg.hero.domain.HeroName;
import rpg.hero.domain.HeroRepository;

public final class CreateHeroUseCase {
    private final HeroFactory heroFactory;
    private final HeroRepository heroRepository;
    private final HeroOutputMapper outputMapper;

    public CreateHeroUseCase(HeroFactory heroFactory, HeroRepository heroRepository,
                             HeroOutputMapper outputMapper) {
        this.heroFactory = Objects.requireNonNull(heroFactory);
        this.heroRepository = Objects.requireNonNull(heroRepository);
        this.outputMapper = Objects.requireNonNull(outputMapper);
    }

    public HeroOutput execute(CreateHeroCommand command) {
        Objects.requireNonNull(command, "La commande de création du héros est requise.");
        Characteristics characteristics = new Characteristics(
                command.strength(), command.dexterity(), command.constitution(),
                command.intelligence(), command.wisdom(), command.charisma());
        Hero hero = heroFactory.create(
                new HeroName(command.name()), command.species(), command.heroClass(), characteristics);
        heroRepository.save(hero);
        return outputMapper.toOutput(hero);
    }
}
