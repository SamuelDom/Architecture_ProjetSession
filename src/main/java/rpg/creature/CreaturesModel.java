package rpg.creature;

import java.util.ArrayList;
import java.util.List;

public final class CreaturesModel {
    public Creature getGobelin() { return new Creature("Gobelin", 7, 12, 50, 2, 3, "Dague"); }
    public Creature getOrc() { return new Creature("Orc", 15, 13, 100, 1, 4, "Hache"); }
    public Creature getTroll() { return new Creature("Troll", 35, 15, 300, 0, 6, "Massue"); }

    public ArrayList<Creature> getGobelinPack() {
        return new ArrayList<>(List.of(getGobelin(), getGobelin(), getGobelin()));
    }

    public ArrayList<Creature> getOrcCamp() {
        return new ArrayList<>(List.of(getOrc(), getOrc(), getGobelin()));
    }

    public ArrayList<Creature> getTrollLair() {
        return new ArrayList<>(List.of(getTroll()));
    }
}
