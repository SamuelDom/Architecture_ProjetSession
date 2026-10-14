package rpg.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class InventoryTest {
    @Test
    void enforcesCapacityAndSupportsAddingAndRemovingItems() {
        Inventory inventory = new Inventory();
        Weapon sword = new Weapon("Épée longue", "1d8", "FOR");
        inventory.add(sword);
        assertTrue(inventory.contains(sword));
        inventory.remove(sword);
        assertFalse(inventory.contains(sword));

        for (int index = 0; index < 10; index++) {
            inventory.add(new Potion("Potion de soin", "2d4", 2));
        }
        assertTrue(inventory.isFull());
        assertEquals(10, inventory.size());
        assertThrows(IllegalStateException.class,
                () -> inventory.add(new Weapon("Dague", "1d4", "DEX")));
    }
}
