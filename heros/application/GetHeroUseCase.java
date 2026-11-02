package hero.application;

import hero.domain.HeroId;
import hero.domain.HeroRepository;

import java.util.Optional;
import java.util.UUID;

public class GetHeroUseCase {

    private final HeroRepository heroRepository;
    private final HeroOutputMapper outputMapper;

    public GetHeroUseCase(HeroRepository heroRepository, HeroOutputMapper outputMapper) {
        this.heroRepository = heroRepository;
        this.outputMapper = outputMapper;
    }

    public Optional<HeroOutput> execute(String id) {
        HeroId heroId = new HeroId(UUID.fromString(id));
        return heroRepository.findById(heroId).map(outputMapper::toOutput);
    }
}