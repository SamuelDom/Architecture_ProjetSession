package rpg.hero.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import rpg.hero.infrastructure.InMemoryHeroRepository;
import rpg.item.Armor;
import rpg.item.Weapon;

class HeroDomainTest {
    private final InMemoryHeroRepository repository = new InMemoryHeroRepository();
    private final HeroFactory factory = new HeroFactory(repository);

    @Test
    void createsHeroWithSpeciesBonusesAndClassHitPoints() {
        Hero hero = factory.create(
                new HeroName("Thorin"), Species.DWARF, HeroClass.WARRIOR,
                new Characteristics(14, 12, 16, 8, 10, 13));

        assertEquals(16, hero.maxHitPoints());
        assertEquals(2, hero.strengthModifier());
        assertEquals(11, hero.currentArmor());
    }

    @Test
    void rejectsDuplicateHeroNames() {
        HeroName name = new HeroName("Thorin");
        factory.create(name, Species.DWARF, HeroClass.WARRIOR,
                new Characteristics(14, 12, 16, 8, 10, 13));

        assertThrows(IllegalArgumentException.class, () -> factory.create(
                name, Species.HUMAN, HeroClass.MAGE,
                new Characteristics(10, 10, 10, 10, 10, 10)));
    }

    @Test
    void appliesClassEquipmentRestrictions() {
        Hero mage = factory.create(
                new HeroName("Aria"), Species.ELF, HeroClass.MAGE,
                new Characteristics(10, 12, 12, 16, 12, 10));
        Armor heavyArmor = new Armor("Armure lourde", 16, false);
        mage.inventory().add(heavyArmor);

        assertThrows(IllegalStateException.class,
                () -> mage.equipment().equip(heavyArmor, mage.inventory(), mage.heroClass()));
        assertThrows(IllegalArgumentException.class,
                () -> new HeroName("Mage123"));
    }
}
