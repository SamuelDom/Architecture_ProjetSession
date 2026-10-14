package rpg.combat;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import rpg.creature.Creature;
import rpg.hero.domain.Hero;
import rpg.progression.ExperienceDistributor;
import rpg.progression.LevelTable;
import rpg.progression.LevelUpResult;
import rpg.progression.LevelUpService;

public final class Combat {
    private final Dice dice;
    private final Attack attack;
    private final Scanner scanner;
    private final Map<Combatant, List<EffectsTemplate>> activeEffects = new IdentityHashMap<>();
    private int roundCounter;

    public Combat(Scanner scanner) {
        this(new Dice(), scanner);
    }

    public Combat(Dice dice, Scanner scanner) {
        this.dice = Objects.requireNonNull(dice);
        this.scanner = Objects.requireNonNull(scanner);
        this.attack = new Attack(dice, scanner);
    }

    public void applyEffect(Combatant target, EffectsTemplate effect) {
        Objects.requireNonNull(target);
        Objects.requireNonNull(effect);
        activeEffects.computeIfAbsent(target, ignored -> new ArrayList<>()).add(effect);
        effect.applyEffect(target);
    }

    public int roundCounter() { return roundCounter; }

    public void start(List<Hero> heroes, List<Creature> creatures) {
        if (heroes == null || heroes.isEmpty()) {
            throw new IllegalArgumentException("Au moins un héros est requis pour le combat.");
        }
        if (creatures == null || creatures.isEmpty()) {
            throw new IllegalArgumentException("Au moins une créature est requise pour le combat.");
        }
        if (heroes.stream().anyMatch(Objects::isNull) || creatures.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException("Les participants au combat ne peuvent pas être null.");
        }

        roundCounter = 0;
        List<Combatant> order = determineActionOrder(heroes, creatures);
        System.out.println("Début du combat !");
        while (hasLiving(heroes) && hasLiving(creatures)) {
            roundCounter++;
            System.out.println("\n=== ROUND " + roundCounter + " ===");
            for (Combatant participant : order) {
                if (!hasLiving(heroes) || !hasLiving(creatures)) break;
                if (!participant.isAlive()) continue;
                tickEffects(participant);
                if (!participant.isAlive()) continue;
                if (participant.isStunned()) {
                    System.out.println(participant.nameForCombat() + " ne peut pas agir.");
                    continue;
                }
                if (participant instanceof Hero hero) {
                    attack.targetChoice(hero, creatures);
                } else if (participant instanceof Creature creature) {
                    attackCreature(creature, heroes);
                }
            }
        }

        if (hasLiving(heroes)) {
            int totalXp = creatures.stream().mapToInt(Creature::getXp).sum();
            int share = new ExperienceDistributor().distribute(totalXp, heroes);
            LevelUpService levelUpService = new LevelUpService(new LevelTable());
            for (Hero hero : heroes) {
                LevelUpResult result = levelUpService.checkLevelUp(hero);
                System.out.printf("%s reçoit %d XP.%n", hero.name(), share);
                if (result.leveledUp()) {
                    System.out.printf("%s passe au niveau %d et gagne %d PV maximum.%n",
                            hero.name(), result.newLevel(), result.hpGained());
                }
            }
            System.out.println("Les héros ont gagné !");
        } else {
            System.out.println("Les créatures ont gagné !");
        }
        System.out.println("=== FIN DU COMBAT ===");
    }

    private List<Combatant> determineActionOrder(List<Hero> heroes, List<Creature> creatures) {
        List<Combatant> order = new ArrayList<>(heroes.size() + creatures.size());
        order.addAll(heroes);
        order.addAll(creatures);
        order.sort((first, second) ->
                Integer.compare(initiative(second), initiative(first)));
        System.out.println("Ordre d'action : "
                + String.join(", ", order.stream().map(Combatant::nameForCombat).toList()));
        return order;
    }

    private int initiative(Combatant combatant) {
        int bonus = combatant instanceof Hero hero
                ? hero.dexterityModifier()
                : ((Creature) combatant).getInitiative();
        return dice.roll(20) + bonus;
    }

    private static boolean hasLiving(List<? extends Combatant> participants) {
        return participants.stream().anyMatch(Combatant::isAlive);
    }

    private void attackCreature(Creature creature, List<Hero> heroes) {
        List<Hero> livingHeroes = heroes.stream().filter(Hero::isAlive).toList();
        if (livingHeroes.isEmpty()) return;
        Hero target = chooseCreatureTarget(creature, livingHeroes);
        int damage = creature.testAttack();
        target.takeDamage(damage);
        System.out.printf("%s attaque %s et inflige %d dégâts (PV %d/%d).%n",
                creature.getNom(), target.name(), damage,
                target.currentHitPoints(), target.maxHitPoints());
    }

    private Hero chooseCreatureTarget(Creature creature, List<Hero> livingHeroes) {
        return switch (creature.getNom()) {
            case "Gobelin" -> livingHeroes.stream()
                    .min(java.util.Comparator.comparingInt(Hero::currentHitPoints)).orElseThrow();
            case "Orc" -> livingHeroes.stream()
                    .max(java.util.Comparator.comparingInt(Hero::currentHitPoints)).orElseThrow();
            default -> livingHeroes.get(dice.roll(livingHeroes.size()) - 1);
        };
    }

    private void tickEffects(Combatant target) {
        List<EffectsTemplate> effects = activeEffects.get(target);
        if (effects == null) return;
        effects.removeIf(effect -> {
            effect.onTurnStart(target);
            if (!effect.tick()) return false;
            effect.onRemove(target);
            System.out.println("L'effet " + effect.getName() + " prend fin.");
            return true;
        });
    }
}
