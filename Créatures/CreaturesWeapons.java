package Créatures;
import java.util.Map;

public class CreaturesWeapons {
    Map<String, Integer> weapons = Map.of("Dague", 3, "Hache", 4, "Massue", 6);
    
    public int getWeaponDamage(String weapon) {
        return weapons.getOrDefault(weapon, 0);
    }
}

