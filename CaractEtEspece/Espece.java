import java.util.HashMap;
import java.util.Map;

public class Espece {
    static Map<String, int[]> modificateursMap;

    public static final int[] humainModif = {1, 1, 1, 1, 1, 1};
    public static final int[] elfeModif = {0, 2, 0, 1, 0, 0};
    public static final int[] nainModif = {1, 0, 2, 0, 0, 0};
    public static final int[] orcModif = {2, 0, 1, -1, 0, 0};

    static {
        modificateursMap = new HashMap<>();
        modificateursMap.put("Humain", humainModif);
        modificateursMap.put("Elfe", elfeModif);
        modificateursMap.put("Nain", nainModif);
        modificateursMap.put("Orc", orcModif);
    }

}
