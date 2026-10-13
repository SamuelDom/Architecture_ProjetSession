package domain.item;

public class InventoryTest {

    public static void main(String[] args) {

        Inventory inventory = new Inventory();

        // Test 1 : inventaire vide
        if (inventory.size() != 0) {
            throw new AssertionError("L'inventaire devrait être vide.");
        }

        // Test 2 : ajouter un objet
        Weapon sword = new Weapon("Épée longue", "1d8", "FOR");
        inventory.add(sword);

        if (!inventory.contains(sword)) {
            throw new AssertionError("L'épée devrait être dans l'inventaire.");
        }

        if (inventory.size() != 1) {
            throw new AssertionError("L'inventaire devrait contenir 1 objet.");
        }

        // Test 3 : retirer un objet
        inventory.remove(sword);

        if (inventory.contains(sword)) {
            throw new AssertionError("L'épée ne devrait plus être dans l'inventaire.");
        }

        // Test 4 : remplir l'inventaire
        for (int i = 0; i < 10; i++) {
            inventory.add(
                new Potion("Potion de soin", "2d4", 2)
            );
        }

        if (!inventory.isFull()) {
            throw new AssertionError("L'inventaire devrait être plein.");
        }

        if (inventory.size() != 10) {
            throw new AssertionError("L'inventaire devrait contenir 10 objets.");
        }

        // Test 5 : refuser le 11e objet
        try {
            inventory.add(
                new Weapon("Dague", "1d4", "DEX")
            );

            throw new AssertionError(
                "Le 11e objet aurait dû être refusé."
            );

        } catch (IllegalStateException e) {
            // Comportement attendu
        }

        System.out.println("Tous les tests Inventory sont réussis !");
    }
}