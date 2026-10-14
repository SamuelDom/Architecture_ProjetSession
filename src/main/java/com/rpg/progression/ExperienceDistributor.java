package com.rpg.progression;

import java.util.List;

public class ExperienceDistributor {

    public int distribute (int totalXp, List<? extends XpReceiver> participants) {
        if (totalXp < 0) {
            throw new IllegalArgumentException("Xp total peux pas etre negatif");
    }
        if (participants == null || participants.isEmpty()) {
            throw new IllegalArgumentException(" minimum un participant poour recevoir l'Xp ");
    }
        int share = totalXp/participants.size();

        for (XpReceiver participant : participants) {
            participant.gainXp(share);
        }
    
        return share;

    }
} 