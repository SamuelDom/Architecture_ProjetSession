package Stats;
import java.util.HashMap;

public class xp {
    int currentXp;
    int niveau;

    private HashMap<Integer, Integer> xpTable;

    public xp() {
        this.currentXp = 0;
        this.niveau = 1;
        this.xpTable = new HashMap<>();
        initializeXpTable();
    }

    private void initializeXpTable() {
        xpTable.put(1, 0);
        xpTable.put(2, 300);
        xpTable.put(3, 900);
        xpTable.put(4, 2700);
        xpTable.put(5, 6500);
    }

    public void addXp(int xpGained) {
        currentXp += xpGained;
        checkLevelUp();
    }

    private void checkLevelUp() {
        while (niveau < 5 && currentXp >= xpTable.get(niveau + 1)) {
            niveau++;
            if(currentXp > xpTable.get(niveau + 1)) {
                addXp(currentXp - xpTable.get(niveau + 1));
            }
            System.out.println("Félicitations ! Vous avez atteint le niveau " + niveau + " !");
        }
    }

    public int getCurrentXp() {
        return currentXp;
    }
}
