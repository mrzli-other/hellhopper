/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump.game.generator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.helljump.dataaccess.EnemyData;
import com.turbogerm.helljump.dataaccess.ItemData;
import com.turbogerm.helljump.dataaccess.PlatformData;
import com.turbogerm.helljump.dataaccess.RiseSectionData;
import com.turbogerm.helljump.dataaccess.RiseSectionDataBase;
import com.turbogerm.helljump.dataaccess.RiseSectionMetadata;
import com.turbogerm.helljump.dataaccess.RiseSectionMetadataReader;
import com.turbogerm.helljump.dataaccess.RiseSectionsData;
import com.turbogerm.helljump.dataaccess.RiseSectionsDataReader;
import com.turbogerm.helljump.dataaccess.RiseSectionsMetadata;
import com.turbogerm.helljump.game.GameAreaUtils;
import com.turbogerm.helljump.game.Rise;
import com.turbogerm.helljump.game.RiseSection;
import com.turbogerm.helljump.game.enemies.EnemyBase;
import com.turbogerm.helljump.game.enemies.EnemyFactory;
import com.turbogerm.helljump.game.items.ItemBase;
import com.turbogerm.helljump.game.items.ItemFactory;
import com.turbogerm.helljump.game.platforms.PlatformBase;
import com.turbogerm.helljump.game.platforms.PlatformFactory;
import com.turbogerm.helljump.resources.ResourceNames;

public final class RiseGenerator {
    
    private static final int RISE_HEIGHT_STEPS = 5000;
    private static final int RISE_TRESHOLD_4 = 800;
    private static final int RISE_TRESHOLD_10 = 4000;
    private static final int RISE_LOWER_DIFFICULTY_STEP = RISE_TRESHOLD_4 / 5;
    private static final int RISE_HIGHER_DIFFICULTY_STEP = (RISE_TRESHOLD_10 - RISE_TRESHOLD_4) / 6;
    
    private static final float STANDARD_SECTION_WEIGHT = 1.0f;
    private static final float ENEMY_SECTION_WEIGHT = 1.0f;
    private static final float SPECIAL_SECTION_WEIGHT = 1.0f;
    private static final float STANDARD_SECTION_CUMULATIVE_FRACTION;
    private static final float ENEMY_SECTION_CUMULATIVE_FRACTION;
    
    private static final int RISE_SECTIONS_INITIAL_CAPACITY = 400;
    
    private static final RiseSectionsData PREBUILT_RISE_SECTIONS;
    private static final RiseSectionsMetadata RISE_SECTIONS_METADATA;
    
    private static final Array<RiseSectionDataBase> TRANSITION_RISE_SECTIONS;
    private static final Array<RiseSectionDataBase> STANDARD_RISE_SECTIONS;
    private static final Array<RiseSectionDataBase> ENEMY_RISE_SECTIONS;
    private static final Array<RiseSectionDataBase> SPECIAL_RISE_SECTIONS;
    
    private static final int RISE_SECTION_TYPE_INITIAL_CAPACITY = 10;
    
    private static final Array<RiseSectionDataBase> RISE_SECTION_SELECTION_LIST;
    private static final int RISE_SECTION_SELECTION_LIST_INITIAL_CAPACITY = 10;
    
    private static final int TRANSITION_SECTION_TYPE = 0;
    private static final int STANDARD_SECTION_TYPE = 1;
    private static final int ENEMY_SECTION_TYPE = 2;
    private static final int SPECIAL_SECTION_TYPE = 3;
    
    static {
        
        float totalSectionWeight = STANDARD_SECTION_WEIGHT + ENEMY_SECTION_WEIGHT + SPECIAL_SECTION_WEIGHT;
        STANDARD_SECTION_CUMULATIVE_FRACTION = STANDARD_SECTION_WEIGHT / totalSectionWeight;
        ENEMY_SECTION_CUMULATIVE_FRACTION = STANDARD_SECTION_CUMULATIVE_FRACTION + ENEMY_SECTION_WEIGHT /
                totalSectionWeight;
        
        PREBUILT_RISE_SECTIONS = RiseSectionsDataReader.read(Gdx.files.internal(ResourceNames.RISE_SECTIONS_DATA));
        RISE_SECTIONS_METADATA = RiseSectionMetadataReader.read(
                Gdx.files.internal(ResourceNames.RISE_SECTIONS_METADATA));
        
        // initialize all rise sections list
        Array<RiseSectionData> prebuiltRiseSections = PREBUILT_RISE_SECTIONS.getAllRiseSections();
        Array<RiseSectionMetadata> riseSectionsMetadata = RISE_SECTIONS_METADATA.getAllRiseSections();
        
        int allRiseSectionsCount = PREBUILT_RISE_SECTIONS.getRiseSectionCount() +
                RISE_SECTIONS_METADATA.getRiseSectionCount();
        Array<RiseSectionDataBase> allRiseSections = new Array<RiseSectionDataBase>(true, allRiseSectionsCount);
        allRiseSections.addAll(prebuiltRiseSections);
        allRiseSections.addAll(riseSectionsMetadata);
        
        // separate rise section types
        TRANSITION_RISE_SECTIONS = new Array<RiseSectionDataBase>(false, RISE_SECTION_TYPE_INITIAL_CAPACITY);
        STANDARD_RISE_SECTIONS = new Array<RiseSectionDataBase>(false, RISE_SECTION_TYPE_INITIAL_CAPACITY);
        ENEMY_RISE_SECTIONS = new Array<RiseSectionDataBase>(false, RISE_SECTION_TYPE_INITIAL_CAPACITY);
        SPECIAL_RISE_SECTIONS = new Array<RiseSectionDataBase>(false, RISE_SECTION_TYPE_INITIAL_CAPACITY);
        
        for (RiseSectionDataBase riseSectionDataBase : allRiseSections) {
            String type = riseSectionDataBase.getType();
            if (RiseSectionDataBase.isTransitionType(type)) {
                TRANSITION_RISE_SECTIONS.add(riseSectionDataBase);
            } else if (RiseSectionDataBase.isStandardType(type)) {
                STANDARD_RISE_SECTIONS.add(riseSectionDataBase);
            } else if (RiseSectionDataBase.isEnemyType(type)) {
                ENEMY_RISE_SECTIONS.add(riseSectionDataBase);
            } else if (RiseSectionDataBase.isSpecialType(type)) {
                SPECIAL_RISE_SECTIONS.add(riseSectionDataBase);
            }
        }
        
        RISE_SECTION_SELECTION_LIST = new Array<RiseSectionDataBase>(
                false, RISE_SECTION_SELECTION_LIST_INITIAL_CAPACITY);
    }
    
    public static Rise generate(AssetManager assetManager) {
        Array<RiseSectionData> riseSectionsData = new Array<RiseSectionData>(true, RISE_SECTIONS_INITIAL_CAPACITY);
        
        int stepsInRise = 0;
        RiseSectionData currRiseSection;
        
//        String typePrefix = "normal";
//        for (int i = 1; i <= 5; i++) {
//            String sectionName = String.format(typePrefix + "%02d", i);
//            currRiseSection = RiseSectionGenerator.generateRiseSection(
//                    RISE_SECTIONS_METADATA.getByName(sectionName));
//            riseSectionsData.add(currRiseSection);
//            stepsInRise += currRiseSection.getStepRange();
//        }
        
//        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("transition03"));
//        riseSectionsData.add(currRiseSection);
//        stepsInRise += currRiseSection.getStepRange();
//        
//        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("visibleonjumpnormal"));
//        riseSectionsData.add(currRiseSection);
//        stepsInRise += currRiseSection.getStepRange();
//        
//        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("visibleonjumpcrumble"));
//        riseSectionsData.add(currRiseSection);
//        stepsInRise += currRiseSection.getStepRange();
//        
//        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("crumble"));
//        riseSectionsData.add(currRiseSection);
//        stepsInRise += currRiseSection.getStepRange();
        
//        currRiseSection = PREBUILT_RISE_SECTIONS.getRiseSection("testitems00");
//        riseSectionsData.add(currRiseSection);
//        stepsInRise += currRiseSection.getStepRange();

//        for (RiseSectionDataBase riseSectionData : ENEMY_RISE_SECTIONS) {
//            String name = riseSectionData.getName();
//            if (!name.startsWith("knight")) {
//                continue;
//            }
//                
//            currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("transition03"));
//            riseSectionsData.add(currRiseSection);
//            stepsInRise += currRiseSection.getStepRange();
//            
//            currRiseSection = PREBUILT_RISE_SECTIONS.getRiseSection(name);
//            riseSectionsData.add(currRiseSection);
//            stepsInRise += currRiseSection.getStepRange();
//        }
//        
//        for (RiseSectionDataBase riseSectionData : SPECIAL_RISE_SECTIONS) {
//            String name = riseSectionData.getName();
//                
//            currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("transition03"));
//            riseSectionsData.add(currRiseSection);
//            stepsInRise += currRiseSection.getStepRange();
//            
//            currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName(name));
//            riseSectionsData.add(currRiseSection);
//            stepsInRise += currRiseSection.getStepRange();
//        }
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial0"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial1"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        currRiseSection = RiseSectionGenerator.generateRiseSection(RISE_SECTIONS_METADATA.getByName("initial2"));
        riseSectionsData.add(currRiseSection);
        stepsInRise += currRiseSection.getStepRange();
        
        boolean isTransitionSection = true;
        while (stepsInRise < RISE_HEIGHT_STEPS) {
            currRiseSection = getRandomRiseSection(stepsInRise, isTransitionSection);
            riseSectionsData.add(currRiseSection);
            stepsInRise += currRiseSection.getStepRange();
            isTransitionSection = !isTransitionSection;
        }
        
        adjustLastRiseSection(riseSectionsData);
        
        // TODO: only for debugging
        String[] riseSectionNames = new String[riseSectionsData.size];
        for (int i = 0; i < riseSectionNames.length; i++) {
            riseSectionNames[i] = riseSectionsData.get(i).getName();
        }
        
        Array<RiseSection> riseSections = getRiseSections(riseSectionsData, assetManager);
        
        return new Rise(riseSections);
    }
    
    private static RiseSectionData getRandomRiseSection(int stepsInRise, boolean isTransitionSection) {
        int sectionType;
        boolean revertToStandard;
        if (isTransitionSection) {
            sectionType = TRANSITION_SECTION_TYPE;
            revertToStandard = false;
        } else {
            float randomRiseSectionNumber = MathUtils.random();
            if (randomRiseSectionNumber <= STANDARD_SECTION_CUMULATIVE_FRACTION) {
                sectionType = STANDARD_SECTION_TYPE;
                revertToStandard = false;
            } else if (randomRiseSectionNumber <= ENEMY_SECTION_CUMULATIVE_FRACTION) {
                sectionType = ENEMY_SECTION_TYPE;
                revertToStandard = true;
            } else {
                sectionType = SPECIAL_SECTION_TYPE;
                revertToStandard = true;
            }
        }
        
        Array<RiseSectionDataBase> riseSectionList = getRiseSectionList(sectionType);
        MinMaxDifficulty minMaxDifficulty = getRiseSectionMinMaxDifficulty(sectionType, stepsInRise);
        RiseSectionData riseSectionData = getRandomRiseSectionData(
                minMaxDifficulty.minDifficulty, minMaxDifficulty.maxDifficulty, riseSectionList, revertToStandard);
        
        return riseSectionData;
    }
    
    private static Array<RiseSectionDataBase> getRiseSectionList(int sectionType) {
        switch (sectionType) {
            case TRANSITION_SECTION_TYPE:
                return TRANSITION_RISE_SECTIONS;
            case STANDARD_SECTION_TYPE:
                return STANDARD_RISE_SECTIONS;
            case ENEMY_SECTION_TYPE:
                return ENEMY_RISE_SECTIONS;
            case SPECIAL_SECTION_TYPE:
                return SPECIAL_RISE_SECTIONS;
            default:
                return TRANSITION_RISE_SECTIONS;
        }
    }
    
    private static MinMaxDifficulty getRiseSectionMinMaxDifficulty(int sectionType, int stepsInRise) {
        
        int difficulty;
        if (stepsInRise <= RISE_TRESHOLD_4) {
            difficulty = MathUtils.clamp(stepsInRise / RISE_LOWER_DIFFICULTY_STEP, 1, 4);
        } else if (stepsInRise <= RISE_TRESHOLD_10) {
            difficulty = Math.min(5 + (stepsInRise - RISE_TRESHOLD_4) / RISE_HIGHER_DIFFICULTY_STEP, 10);
        } else {
            difficulty = 10;
        }
        
        int minDifficulty;
        int maxDifficulty;
        
        switch (sectionType) {
            case TRANSITION_SECTION_TYPE:
                minDifficulty = difficulty;
                maxDifficulty = difficulty;
                break;
            
            case STANDARD_SECTION_TYPE:
                if (difficulty <= 4) {
                    minDifficulty = difficulty;
                } else {
                    minDifficulty = Math.max(difficulty - 2, 4);
                }
                maxDifficulty = Math.min(difficulty, 10);
                break;
            
            case ENEMY_SECTION_TYPE:
                minDifficulty = Math.min(difficulty, 4);
                maxDifficulty = difficulty;
                break;
            
            case SPECIAL_SECTION_TYPE:
                minDifficulty = Math.min(difficulty, 4);
                maxDifficulty = difficulty;
                break;
            
            default:
                minDifficulty = 0;
                maxDifficulty = 10;
                break;
        }
        
        return new MinMaxDifficulty(minDifficulty, maxDifficulty);
    }
    
    private static RiseSectionData getRandomRiseSectionData(int minDifficulty, int maxDifficulty,
            Array<RiseSectionDataBase> riseSectionList, boolean revertToStandard) {
        
        if (maxDifficulty < 0) {
            return null;
        }
        
        RISE_SECTION_SELECTION_LIST.clear();
        
        for (RiseSectionDataBase riseSectionDataBase : riseSectionList) {
            int difficulty = riseSectionDataBase.getDifficulty();
            if (minDifficulty <= difficulty && difficulty <= maxDifficulty) {
                RISE_SECTION_SELECTION_LIST.add(riseSectionDataBase);
            }
        }
        
        if (RISE_SECTION_SELECTION_LIST.size > 0) {
            return getRiseSectionData(RISE_SECTION_SELECTION_LIST.random());
        } else {
            for (RiseSectionDataBase riseSectionDataBase : riseSectionList) {
                if (RISE_SECTION_SELECTION_LIST.size == 0) {
                    RISE_SECTION_SELECTION_LIST.add(riseSectionDataBase);
                } else {
                    if (revertToStandard) {
                        return getRandomRiseSectionData(minDifficulty, maxDifficulty, STANDARD_RISE_SECTIONS, false);
                    } else {
                        RiseSectionDataBase selectedRiseSectionDataBase = RISE_SECTION_SELECTION_LIST.first();
                        int currentDist = getDifficultyDistance(
                                selectedRiseSectionDataBase.getDifficulty(), minDifficulty, maxDifficulty);
                        int newDist = getDifficultyDistance(
                                riseSectionDataBase.getDifficulty(), minDifficulty, maxDifficulty);
                        
                        if (currentDist == newDist) {
                            RISE_SECTION_SELECTION_LIST.add(riseSectionDataBase);
                        } else if (isNewDifficultyDistanceBetter(currentDist, newDist)) {
                            RISE_SECTION_SELECTION_LIST.clear();
                            RISE_SECTION_SELECTION_LIST.add(riseSectionDataBase);
                        }
                    }
                }
            }
            return getRiseSectionData(RISE_SECTION_SELECTION_LIST.random());
        }
    }
    
    private static RiseSectionData getRiseSectionData(RiseSectionDataBase riseSectionDataBase) {
        if (riseSectionDataBase.isMetadata()) {
            return RiseSectionGenerator.generateRiseSection((RiseSectionMetadata) riseSectionDataBase);
        } else {
            return (RiseSectionData) riseSectionDataBase;
        }
    }
    
    private static int getDifficultyDistance(int difficulty, int minDifficulty, int maxDifficulty) {
        if (difficulty < minDifficulty) {
            return difficulty - minDifficulty;
        } else if (difficulty > maxDifficulty) {
            return difficulty - maxDifficulty;
        } else {
            return 0;
        }
    }
    
    private static boolean isNewDifficultyDistanceBetter(int currentDist, int newDist) {
        return (currentDist < 0 && newDist < 0 && newDist > currentDist) ||
                (currentDist > 0 && newDist > 0 && newDist < currentDist) ||
                (newDist < 0 && currentDist > 0);
    }
    
    private static void adjustLastRiseSection(Array<RiseSectionData> riseSectionsData) {
        RiseSectionData lastRiseSectionData = riseSectionsData.pop();
        
        int lastPlatformStep = lastRiseSectionData.getPlatformsData().peek().getStep();
        int newStepRange = lastPlatformStep + PlatformData.MAX_PLATFORM_DISTANCE_STEPS;
        
        // make sure there is maximum platform distance between last platform and end line
        // this way no platforms will be seen on end background texture
        RiseSectionData newLastRiseSectionData = new RiseSectionData(
                lastRiseSectionData.getType(),
                lastRiseSectionData.getName(),
                newStepRange,
                lastRiseSectionData.getDifficulty(),
                lastRiseSectionData.getPlatformsData(),
                lastRiseSectionData.getEnemiesData(),
                lastRiseSectionData.getItemsData());
        
        riseSectionsData.add(newLastRiseSectionData);
    }
    
    private static Array<RiseSection> getRiseSections(Array<RiseSectionData> riseSectionsData,
            AssetManager assetManager) {
        Array<RiseSection> riseSections = new Array<RiseSection>(true, riseSectionsData.size);
        
        int id = 0;
        int startStep = 0;
        for (RiseSectionData riseSectionData : riseSectionsData) {
            RiseSection riseSection = getRiseSection(id, startStep, riseSectionData, assetManager);
            riseSections.add(riseSection);
            id++;
            startStep += riseSectionData.getStepRange();
        }
        
        return riseSections;
    }
    
    private static RiseSection getRiseSection(int riseSectionId, int startStep, RiseSectionData riseSectionData,
            AssetManager assetManager) {
        
        String riseSectionName = riseSectionData.getName();
        int difficulty = riseSectionData.getDifficulty();
        
        float startY = startStep * GameAreaUtils.STEP_HEIGHT;
        float height = riseSectionData.getStepRange() * GameAreaUtils.STEP_HEIGHT;
        
        Array<PlatformData> platformsData = riseSectionData.getPlatformsData();
        Array<PlatformBase> platforms = new Array<PlatformBase>(true, platformsData.size);
        for (PlatformData platformData : platformsData) {
            PlatformBase platform = PlatformFactory.create(riseSectionId, platformData, startStep, assetManager);
            platforms.add(platform);
        }
        
        Array<EnemyData> enemiesData = riseSectionData.getEnemiesData();
        Array<EnemyBase> enemies;
        if (enemiesData != null) {
            enemies = new Array<EnemyBase>(true, enemiesData.size);
            for (EnemyData enemyData : enemiesData) {
                EnemyBase enemy = EnemyFactory.create(enemyData, startStep, assetManager);
                enemies.add(enemy);
            }
        } else {
            enemies = new Array<EnemyBase>(true, 0);
        }
        
        Array<ItemData> itemsData = riseSectionData.getItemsData();
        Array<ItemBase> items;
        if (itemsData != null) {
            items = new Array<ItemBase>(true, itemsData.size);
            for (ItemData itemData : itemsData) {
                if (MathUtils.random() > itemData.getAppearanceChance()) {
                    continue;
                }
                
                ItemBase item = ItemFactory.create(itemData, startStep, assetManager);
                int attachedToPlatformId = itemData.getAttachedToPlatformId();
                if (attachedToPlatformId >= 0) {
                    PlatformBase attachedToPlatform = getPlatform(attachedToPlatformId, platforms);
                    attachedToPlatform.attachItem(item);
                }
                items.add(item);
            }
        } else {
            items = new Array<ItemBase>(true, 0);
        }
        
        return new RiseSection(riseSectionId, riseSectionName, difficulty, startY, height, platforms, enemies, items);
    }
    
    private static PlatformBase getPlatform(int platformId, Array<PlatformBase> platforms) {
        for (PlatformBase platform : platforms) {
            if (platform.getPlatformId() == platformId) {
                return platform;
            }
        }
        
        return null;
    }
    
    private static class MinMaxDifficulty {
        public final int minDifficulty;
        public final int maxDifficulty;
        
        public MinMaxDifficulty(int minDifficulty, int maxDifficulty) {
            this.minDifficulty = minDifficulty;
            this.maxDifficulty = maxDifficulty;
        }
    }
}
