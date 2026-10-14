import java.util.ArrayList;
import java.util.List;
import CombatSystem.Combat;
import Créatures.Creature;
import Créatures.CreaturesModel;
import FakeHeores.heroes;

public class Main {
    public static void main(String[] args) {
        System.out.println("=============================================");
        System.out.println("   LANCEMENT DU JEU : COMBAT VS GOBELINPACK   ");
        System.out.println("=============================================\n");

        // 1. Initialisation des Héros (via votre méthode automatique)
        System.out.println(" Génération des héros...");
        List<heroes> listeHeros = heroes.genererListeDeHeros(); //
        for (heroes h : listeHeros) {
            System.out.println(" - " + h.getNom() + " (PV: " + h.getPv() + ")"); //
        }
        System.out.println();

        // 2. Chargement du GobelinPack depuis votre modèle
        System.out.println(" Chargement de la meute de Gobelins...");
        CreaturesModel modeleMonstres = new CreaturesModel(); //
        
        // On récupère directement le pack créé dans votre constructeur
        ArrayList<Creature> monstres = modeleMonstres.getGobelinPack(); //

        // On affiche chaque membre de la meute
        for (int i = 0; i < monstres.size(); i++) {
            Creature m = monstres.get(i);
            System.out.println(" - " + m.getNom() + " #" + (i + 1) + " (PV: " + m.getCurrentHealth() + ", CA: " + m.getCurrentArmor() + ")"); //
        }
        System.out.println();

        // 3. Test du Système de Combat - Détermination de l'initiative (Héros vs GobelinPack)
        System.out.println(" Déclenchement du combat et calcul de l'ordre d'action...");
        Combat systemeCombat = new Combat(); //
        systemeCombat.initialisationActions(monstres); //
        System.out.println();
        System.out.println();

        System.out.println("=============================================");
        System.out.println("         FIN DE LA SIMULATION MAIN           ");
        System.out.println("=============================================");
    }
}
