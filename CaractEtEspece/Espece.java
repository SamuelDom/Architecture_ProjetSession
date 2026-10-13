package CaractEtEspece;

import java.util.HashMap;
import java.util.Map;

public class Espece {
    static Map<String, HashMap<String, Integer>> modificateursMap;

    public static final HashMap<String, Integer> humainModif = new HashMap<String, Integer>() {{
        put("Force", 1);
        put("Dextérité", 1);
        put("Constitution", 1);
        put("Intelligence", 1);
        put("Sagesse", 1);
        put("Charisme", 1);
    }};
    public static final HashMap<String, Integer> elfeModif = new HashMap<String, Integer>() {{
        put("Force", 0);
        put("Dextérité", 2);
        put("Constitution", 0);
        put("Intelligence", 1);
        put("Sagesse", 0);
        put("Charisme", 0);
    }};
    public static final HashMap<String, Integer> nainModif = new HashMap<String, Integer>() {{
        put("Force", 1);
        put("Dextérité", 0);
        put("Constitution", 2);
        put("Intelligence", 0);
        put("Sagesse", 0);
        put("Charisme", 0);
    }};
    public static final HashMap<String, Integer> orcModif = new HashMap<String, Integer>() {{
        put("Force", 2);
        put("Dextérité", 0);
        put("Constitution", 1);
        put("Intelligence", -1);
        put("Sagesse", 0);
        put("Charisme", 0);
    }};

    static {
        modificateursMap = new HashMap<>();
        modificateursMap.put("Humain", humainModif);
        modificateursMap.put("Elfe", elfeModif);
        modificateursMap.put("Nain", nainModif);
        modificateursMap.put("Orc", orcModif);
    }

}
