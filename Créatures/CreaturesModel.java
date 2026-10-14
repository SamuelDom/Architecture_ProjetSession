package Créatures;
import java.util.ArrayList;

public class CreaturesModel {
    private Creature Gobelin;
    private Creature Orc;
    private Creature Troll;
    private ArrayList<Creature> GobelinPack;
    private ArrayList<Creature> OrcCamp;
    private ArrayList<Creature> TrollLair;

    public CreaturesModel() {
        // Création des créatures avec leurs statistiques
        Gobelin = new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague");
        Orc = new Creature("Orc", 15, 13, 100, 1, 4, "Hache");
        Troll = new Creature("Troll", 35, 15, 300, 0, 6, "Massue");
        GobelinPack = new ArrayList<>();
        GobelinPack.add(new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague"));
        GobelinPack.add(new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague"));
        GobelinPack.add(new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague"));
        OrcCamp = new ArrayList<>();
        OrcCamp.add(new Creature("Orc", 15, 13, 100, 1, 4, "Hache"));
        OrcCamp.add(new Creature("Orc", 15, 13, 100, 1, 4, "Hache"));
        OrcCamp.add(new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague"));
        TrollLair = new ArrayList<>();
        TrollLair.add(new Creature("Troll", 35, 15, 300, 0, 6, "Massue"));
    }

    public Creature getGobelin() {
        return Gobelin;
    }

    public Creature getOrc() {
        return Orc;
    }

    public Creature getTroll() {
        return Troll;
    }

    public ArrayList<Creature> getGobelinPack() {
        return GobelinPack;
    }

    public ArrayList<Creature> getOrcCamp() {
        return OrcCamp;
    }

    public ArrayList<Creature> getTrollLair() {
        return TrollLair;
    }

}
