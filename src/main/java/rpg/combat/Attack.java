package rpg.combat;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import rpg.creature.Creature;
import rpg.hero.domain.Hero;
import rpg.item.Weapon;

public final class Attack {
    private final Dice dice;
    private final Scanner scanner;

    public Attack(Dice dice, Scanner scanner) {
        this.dice = Objects.requireNonNull(dice);
        this.scanner = Objects.requireNonNull(scanner);
    }

    public void targetChoice(Hero hero, List<Creature> creatures) {
        List<Creature> availableTargets = creatures.stream().filter(Creature::isAlive).toList();
        if (availableTargets.isEmpty()) return;
        while (true) {
            for (int index = 0; index < availableTargets.size(); index++) {
                Creature target = availableTargets.get(index);
                System.out.printf("%d. %s (PV %d/%d, CA %d)%n",
                        index + 1, target.getNom(), target.getCurrentHealth(),
                        target.getMaxHealth(), target.getCurrentArmor());
            }
            System.out.print("Choisissez une créature à attaquer : ");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("La saisie a été interrompue pendant le combat.");
            }
            String input = scanner.nextLine().trim();
            try {
                int choice = Integer.parseInt(input);
                if (choice < 1 || choice > availableTargets.size()) {
                    System.out.println("Choix invalide. Réessayez.");
                    continue;
                }
                attack(hero, availableTargets.get(choice - 1));
                return;
            } catch (NumberFormatException exception) {
                System.out.println("Entrez le numéro d'une cible.");
            }
        }
    }

    public void attack(Hero hero, Creature target) {
        Objects.requireNonNull(hero);
        Objects.requireNonNull(target);
        if (!target.isAlive()) throw new IllegalStateException("Une créature vaincue ne peut pas être attaquée.");
        int roll = dice.rollDiceTwenty();
        Weapon weapon = hero.equipment().getMainWeapon();
        int attackRoll = roll + hero.attackModifier();
        if (roll == 1 || (roll != 20 && attackRoll < target.currentArmor())) {
            System.out.printf("%s rate son attaque contre %s.%n", hero.name(), target.getNom());
            return;
        }

        int damageDice = weapon == null ? 8 : weapon.getDamageDice();
        int damage = dice.rollDice(damageDice) + hero.attackModifier();
        if (roll == 20) {
            damage += dice.rollDice(damageDice);
            System.out.println("Coup critique !");
        }
        damage = Math.max(1, damage);
        target.takeDamage(damage);
        System.out.printf("%s inflige %d dégâts à %s (PV %d/%d).%n",
                hero.name(), damage, target.getNom(), target.getCurrentHealth(), target.getMaxHealth());
    }
}
