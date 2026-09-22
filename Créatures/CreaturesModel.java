package Créatures;

public class CreaturesModel {
    private Creature Gobelin;
    private Creature Orc;
    private Creature Troll;

    public CreaturesModel() {
        // Création des créatures avec leurs statistiques
        Gobelin = new Creature("Gobelin", 7, 12, 50, 2, 3);
        Orc = new Creature("Orc", 15, 13, 100, 1, 4);
        Troll = new Creature("Troll", 35, 15, 300, 0, 6);
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
