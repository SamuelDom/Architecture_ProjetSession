package com.rpg.progression;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


// fais avec IA pour tester

class ExperienceDistributorTest {

    // A fake hero that only exists for testing
    static class FakeHero implements XpReceiver {
        private int xp = 0;

        @Override
        public int getTotalXp() { return xp; }

        @Override
        public void gainXp(int amount) { xp += amount; }
    }

    private final ExperienceDistributor distributor = new ExperienceDistributor();

    @Test
    void splitsXpEquallyWhenDivisible() {
        FakeHero a = new FakeHero();
        FakeHero b = new FakeHero();

        int share = distributor.distribute(100, List.of(a, b));

        assertEquals(50, share);
        assertEquals(50, a.getTotalXp());
        assertEquals(50, b.getTotalXp());
    }

    @Test
    void remainderIsLost() {
        FakeHero a = new FakeHero();
        FakeHero b = new FakeHero();
        FakeHero c = new FakeHero();

        int share = distributor.distribute(100, List.of(a, b, c));

        assertEquals(33, share);
        assertEquals(33, a.getTotalXp());
        assertEquals(33, b.getTotalXp());
        assertEquals(33, c.getTotalXp());
    }

    @Test
    void singleHeroGetsEverything() {
        FakeHero a = new FakeHero();
        distributor.distribute(300, List.of(a));
        assertEquals(300, a.getTotalXp());
    }

    @Test
    void xpAddsUpOverSeveralCombats() {
        FakeHero a = new FakeHero();
        distributor.distribute(100, List.of(a));
        distributor.distribute(50, List.of(a));
        assertEquals(150, a.getTotalXp());
    }

    @Test
    void emptyParticipantListIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> distributor.distribute(100, List.of()));
    }

    @Test
    void negativeXpIsRejected() {
        assertThrows(IllegalArgumentException.class,
                () -> distributor.distribute(-5, List.of(new FakeHero())));
    }
}