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
        GobelinPack.add(Gobelin);
        GobelinPack.add(Gobelin);
        GobelinPack.add(Gobelin);
        OrcCamp = new ArrayList<>();
        OrcCamp.add(Orc);
        OrcCamp.add(Orc);
        OrcCamp.add(Gobelin);
        TrollLair = new ArrayList<>();
        TrollLair.add(Troll);
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

}
