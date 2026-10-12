package domain.hero;

public class HeroDemo {

    public static void main(String[] args) {
        Characteristics stats = new Characteristics(14, 12, 16, 8, 10, 13);

        Hero thorin = Hero.create("Thorin", Species.DWARF, HeroClass.WARRIOR, stats);

        System.out.println("Nom        : " + thorin.name());
        System.out.println("Espèce     : " + thorin.species());
        System.out.println("Classe     : " + thorin.heroClass());
        System.out.println("Niveau     : " + thorin.level());
        System.out.println("XP         : " + thorin.experience());
        System.out.println("PV         : " + thorin.currentHitPoints() + " / " + thorin.maxHitPoints());
        System.out.println("Magie      : " + thorin.currentMagicPoints() + " / " + thorin.maxMagicPoints());
        System.out.println("Vaincu ?   : " + thorin.isDefeated());

        
        System.out.println("\n--- Test de validation de nom ---");
        tryInvalidName("A");            
        tryInvalidName(" Aria");        
        tryInvalidName("Mage123");      
        tryInvalidName(null);          
    }

    private static void tryInvalidName(String name) {
        try {
            HeroName.of(name);
            System.out.println("[ERREUR] Le nom aurait dû être rejeté : " + name);
        } catch (IllegalArgumentException e) {
            System.out.println("Rejeté correctement (\"" + name + "\") : " + e.getMessage());
        }
    }
}
