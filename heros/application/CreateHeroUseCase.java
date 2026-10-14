package hero.application;

import hero.domain.Characteristics;
import hero.domain.Hero;
import hero.domain.HeroFactory;
import hero.domain.HeroName;
import hero.domain.HeroRepository;

/** Orchestre le cas d'utilisation : aucune règle métier ici, seulement de la coordination. */
public class CreateHeroUseCase {

    private final HeroFactory heroFactory;
    private final HeroRepository heroRepository;
    private final HeroOutputMapper outputMapper;

    public CreateHeroUseCase(HeroFactory heroFactory, HeroRepository heroRepository, HeroOutputMapper outputMapper) {
        this.heroFactory = heroFactory;
        this.heroRepository = heroRepository;
        this.outputMapper = outputMapper;
    }

    public HeroOutput execute(CreateHeroCommand command) {
        Characteristics characteristics = new Characteristics(
                command.strength(), command.dexterity(), command.constitution(),
                command.intelligence(), command.wisdom(), command.charisma());

        Hero hero = heroFactory.create(
                new HeroName(command.name()), command.species(), command.heroClass(), characteristics);

        heroRepository.save(hero);
        return outputMapper.toOutput(hero);
    }
}
