// package com.rpg.progression;

// public class LevelUpService {

//     private final LevelTable levelTable;

//     public LevelUpService(LevelTable levelTable) {
//         this.levelTable = levelTable;
//     }

//     // public LevelUpResult checkLevelUp(Levelable hero) {
//     //     int oldLevel = hero.getLevel();
//     //     int newLevel = levelTable.levelForXp(hero.getTotalXp());

//     //     if (newLevel <= oldLevel) {
//     //         return new LevelUpResult(oldLevel, oldLevel, 0);
//     //     }

//     //     int levelsGained = newLevel - oldLevel;
//     //     int hpGainPerLevel = Math.max(1, hero.getClassBaseHp() / 2 + hero.getConstitutionModifier());
//     //     int totalHpGain = hpGainPerLevel * levelsGained;

//     //     hero.applyLevelUp(newLevel, totalHpGain);
//     //     return new LevelUpResult(oldLevel, newLevel, totalHpGain);
//     // }
// }