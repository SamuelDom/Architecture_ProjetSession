package CombatSystem;
import Créatures.Creature;
import FakeHeores.heroes;
import Dice.dice;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;
import java.util.ArrayList;

public class Combat {
    dice dice = new dice();
    HashMap<Object, Integer> speedIds = new HashMap<>();
    int roundCounter = 0;
    public void initialisationActions(ArrayList<Creature> creatures) {
        List<heroes> heroeslist = heroes.genererListeDeHeros(); //à remplacer par la vrai liste d'héros dans les paramètres de la fonction
        speedIds = new HashMap<>();
        for (Creature creature : creatures) {
            speedIds.put(creature.getNom() + " " + (speedIds.size() + 1), dice.rollDiceTwenty() + creature.getInitiative());
        }
        for (heroes hero : heroeslist) {
            speedIds.put(hero.getId(), dice.rollDiceTwenty() + hero.getCaract().dexteriteFinale());
        }
        roundCounter = 0;
        determineActionOrder(creatures, heroeslist);
    }

    private boolean teamCreaturesAlive(ArrayList<Creature> creatures) {
        boolean creaturesAlive = creatures.stream().anyMatch(c -> c.getCurrentHealth() > 0);
        return creaturesAlive;
    }

    private boolean teamHeroesAlive(List<heroes> heroeslist) {
        boolean heroesAlive = heroeslist.stream().anyMatch(h -> h.getPv() > 0);
        return heroesAlive;
    }
    

    private void determineActionOrder(ArrayList<Creature> creatures, List<heroes> heroeslist) {
        Map<Object, Integer> ordreAction = speedIds.entrySet()
                .stream()
                .sorted(Map.Entry.<Object, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, 
                        LinkedHashMap::new                
                ));
        System.out.println("Ordre d'action : " + ordreAction);
        CombatLoop(creatures, heroeslist, ordreAction);
    }

    private void CombatLoop(ArrayList<Creature> creatures, List<heroes> heroeslist, Map<Object, Integer> ordreAction) {;
        while (teamCreaturesAlive(creatures) && teamHeroesAlive(heroeslist)) {
            roundCounter++;
            //Rajouter un effectChecker
            System.out.println("=== ROUND " + roundCounter + " ===");
            for(Map.Entry<Object, Integer> entry : ordreAction.entrySet()) {
                Object participant = entry.getKey();
                if (participant instanceof String) {
                    String creatureName = (String) participant;
                    Creature creature = creatures.stream()
                            .filter(c -> c.getNom().equals(creatureName.split(" ")[0]))
                            .findFirst()
                            .orElse(null);
                    if (creature != null && creature.getCurrentHealth() > 0 && teamHeroesAlive(heroeslist) && !creature.stunnedStatus()) {
                        System.out.println(creature.getNom() + " agit !");
                        creature.comportementCombat(heroeslist);
                    }
                } else if (participant instanceof Integer) {
                    heroes hero = heroeslist.stream()
                            .filter(h -> h.getId() == (Integer) participant)
                            .findFirst()
                            .orElse(null);
                    if (hero != null && hero.getPv() > 0 && teamCreaturesAlive(creatures) && !hero.stunnedStatus()) {
                        System.out.println(hero.getId() + " agit !");
                        hero.actionChoice(creatures);
                    }
                    else if (hero != null && hero.getPv() <= 0) {
                        System.out.println(hero.getNom() + " est mort et ne peut pas agir.");
                    }
                }
                
            }
        }
        if(!teamCreaturesAlive(creatures)) {
            int xpGained = creatures.stream().mapToInt(Creature::getXp).sum();
            xpGained = xpGained / heroeslist.size();
            for (heroes hero : heroeslist) {
                hero.addXp(xpGained);
            }
            System.out.println("Les héros ont gagné !");
        } else if(!teamHeroesAlive(heroeslist)) {
            System.out.println("Les créatures ont gagné !");
        }
        System.out.println("=== FIN DU COMBAT ===");

    }
}
