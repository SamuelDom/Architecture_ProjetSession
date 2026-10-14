package rpg.creature;

import java.util.Map;

public final class CreatureWeapons {
    private static final Map<String, Integer> WEAPON_DAMAGE =
            Map.of("Dague", 3, "Hache", 4, "Massue", 6);

    public int getWeaponDamage(String weapon) {
        Integer damage = WEAPON_DAMAGE.get(weapon);
        if (damage == null) throw new IllegalArgumentException("Arme de créature inconnue : " + weapon);
        return damage;
    }
}
