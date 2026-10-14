package com.rpg.progression;

// j'utilise record parceque ces plus cours + les infos n'ont pas besoin d'etre changer ou accessible + sa fait que retenir de l'information so ces pas si important que ca

public record LevelUpResult(int oldLevel, int newLevel, int hpGained) {

    public boolean leveledUp() {
        return newLevel > oldLevel;
    }
}