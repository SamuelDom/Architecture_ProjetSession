package rpg.progression;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.List;
import org.junit.jupiter.api.Test;

class ExperienceDistributorTest {
    private static final class Receiver implements XpReceiver {
        private int xp;
        @Override public int getTotalXp() { return xp; }
        @Override public void gainXp(int amount) { xp += amount; }
    }

    private final ExperienceDistributor distributor = new ExperienceDistributor();

    @Test
    void splitsExperienceEquallyAndDropsTheRemainder() {
        Receiver first = new Receiver();
        Receiver second = new Receiver();
        Receiver third = new Receiver();

        assertEquals(33, distributor.distribute(100, List.of(first, second, third)));
        assertEquals(33, first.getTotalXp());
        assertEquals(33, second.getTotalXp());
        assertEquals(33, third.getTotalXp());
    }

    @Test
    void rejectsNegativeExperienceAndAnEmptyParty() {
        assertThrows(IllegalArgumentException.class,
                () -> distributor.distribute(-1, List.of(new Receiver())));
        assertThrows(IllegalArgumentException.class,
                () -> distributor.distribute(10, List.of()));
    }
}
