package rpg;

import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import rpg.combat.Combat;
import rpg.creature.CreaturesModel;
import rpg.hero.application.CreateHeroCommand;
import rpg.hero.application.CreateHeroUseCase;
import rpg.hero.application.HeroOutput;
import rpg.hero.application.HeroOutputMapper;
import rpg.hero.domain.ArmorCategory;
import rpg.hero.domain.Hero;
import rpg.hero.domain.HeroClass;
import rpg.hero.domain.HeroFactory;
import rpg.hero.domain.HeroId;
import rpg.hero.domain.HeroRepository;
import rpg.hero.domain.Species;
import rpg.hero.domain.WeaponCategory;
import rpg.hero.infrastructure.InMemoryHeroRepository;
import rpg.item.Armor;
import rpg.item.Potion;
import rpg.item.Weapon;

public final class Main {
    private Main() {}

    public static void main(String[] args) {
        HeroRepository repository = new InMemoryHeroRepository();
        CreateHeroUseCase createHero = new CreateHeroUseCase(
                new HeroFactory(repository), repository, new HeroOutputMapper());
        List<Hero> heroes = List.of(
                createHero(createHero, repository, "Thorin", Species.DWARF),
                createHero(createHero, repository, "Aria", Species.ELF),
                createHero(createHero, repository, "Borin", Species.HUMAN));

        System.out.println("=== Démonstration RPG ===");
        for (Hero hero : heroes) {
            System.out.printf("%s — %s %s — Niveau %d — PV %d/%d — CA %d%n",
                    hero.name(), hero.species(), hero.heroClass(), hero.level(),
                    hero.currentHitPoints(), hero.maxHitPoints(), hero.currentArmor());
            System.out.printf("Équipement : %s, %s — Inventaire : %d/10 objets%n",
                    hero.equipment().getMainWeapon().getName(),
                    hero.equipment().getMainArmor().getName(), hero.inventory().size());
        }
        System.out.println("Saisissez le numéro d'une cible à votre tour.");
        new Combat(new Scanner(System.in)).start(
                heroes, new CreaturesModel().getGobelinPack());
    }

    private static Hero createHero(CreateHeroUseCase createHero, HeroRepository repository,
                                   String name, Species species) {
        HeroOutput output = createHero.execute(new CreateHeroCommand(
                name, species, HeroClass.WARRIOR,
                14, 12, 16, 8, 10, 13));
        Hero hero = repository.findById(new HeroId(UUID.fromString(output.id())))
                .orElseThrow(() -> new IllegalStateException("Le héros créé est introuvable."));
        Weapon weapon = new Weapon("Épée longue", 8, "FOR", WeaponCategory.SWORD);
        Armor armor = new Armor("Armure légère", 12, true, ArmorCategory.LIGHT);
        hero.inventory().add(weapon);
        hero.inventory().add(armor);
        hero.inventory().add(new Potion("Potion de soin", "2d4", 2));
        hero.equipment().equip(weapon, hero.inventory(), hero.heroClass());
        hero.equipment().equip(armor, hero.inventory(), hero.heroClass());
        return hero;
    }
}
