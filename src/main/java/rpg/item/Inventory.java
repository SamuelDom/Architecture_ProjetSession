package rpg.item;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Inventory {
    private static final int MAX_CAPACITY = 10;
    private final List<Item> items = new ArrayList<>();

    public void add(Item item) {
        if (item == null) throw new IllegalArgumentException("L'objet ne peut pas être null.");
        if (isFull()) throw new IllegalStateException("L'inventaire est plein.");
        items.add(item);
    }

    public void remove(Item item) {
        if (item == null) throw new IllegalArgumentException("L'objet ne peut pas être null.");
        if (!items.remove(item)) throw new IllegalStateException("L'objet n'est pas dans l'inventaire.");
    }

    public boolean contains(Item item) { return items.contains(item); }
    public int size() { return items.size(); }
    public boolean isFull() { return size() >= MAX_CAPACITY; }
    public List<Item> getItems() { return Collections.unmodifiableList(items); }
}
