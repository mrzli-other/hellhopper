package com.turbogerm.hellhopper.dataaccess;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.germlibrary.util.Logger;
import com.turbogerm.hellhopper.game.generator.RiseGeneratorUtils;

public final class RiseSectionDataReader {
    
    public static RiseSectionData read(String type, String name, FileHandle fileHandle) {
        
        XmlReader reader = new XmlReader();
        
        Element riseSectionNode = null;
        try {
            riseSectionNode = reader.parse(fileHandle);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return null;
        }
        
        int stepRange = ReaderUtilities.getIntAttribute(riseSectionNode, "steprange");
        int difficulty = ReaderUtilities.getIntAttribute(riseSectionNode, "difficulty");
        
        NextGeneratedId nextGeneratedId = new NextGeneratedId();
        Element platformsNode = riseSectionNode.getChildByName("platforms");
        Array<PlatformData> platformDataList = getPlatformsData(nextGeneratedId, platformsNode);
        
        Element enemiesNode = riseSectionNode.getChildByName("enemies");
        Array<EnemyData> enemyDataList = getEnemiesData(enemiesNode);
        
        Element itemsNode = riseSectionNode.getChildByName("items");
        Array<ItemData> itemDataList = getItemsData(itemsNode);
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, enemyDataList, itemDataList);
    }
    
    private static Array<PlatformData> getPlatformsData(NextGeneratedId nextGeneratedId, Element platformsNode) {
        int numPlatforms = platformsNode.getChildCount();
        Array<PlatformData> platformDataList = new Array<PlatformData>(true, numPlatforms);
        for (int i = 0; i < numPlatforms; i++) {
            Element platformNode = platformsNode.getChild(i);
            PlatformData platformData = getPlatformData(nextGeneratedId, platformNode);
            platformDataList.add(platformData);
        }
        RiseGeneratorUtils.sortPlatforms(platformDataList);
        
        return platformDataList;
    }
    
    private static PlatformData getPlatformData(NextGeneratedId nextGeneratedId, Element platformNode) {
        
        int id = ReaderUtilities.getIntAttribute(platformNode, "id", -1);
        if (id == -1) {
            id = nextGeneratedId.id;
            nextGeneratedId.id++;
        }
        
        int step = ReaderUtilities.getIntAttribute(platformNode, "step");
        int offset = ReaderUtilities.getIntAttribute(platformNode, "offset");
        String type = platformNode.getAttribute("type");
        
        PlatformMovementData movementData = getMovementData(platformNode.getChildByName("movement"));
        Array<PlatformFeatureData> featuresData = getFeaturesData(platformNode.getChildByName("features"));
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                platformNode.getChildByName("properties"));
        
        return new PlatformData(id, type, step, offset, movementData, featuresData, properties);
    }
    
    private static PlatformMovementData getMovementData(Element movementNode) {
        if (movementNode == null) {
            return null;
        }
        
        String type = movementNode.getAttribute("type");
        ObjectMap<String, String> properties =  ReaderUtilities.getProperties(
                movementNode.getChildByName("properties"));
        
        return new PlatformMovementData(type, properties);
    }
    
    private static Array<PlatformFeatureData> getFeaturesData(Element featuresNode) {
        if (featuresNode == null) {
            return null;
        }
        
        int numFeatures = featuresNode.getChildCount();
        Array<PlatformFeatureData> featureDataList = new Array<PlatformFeatureData>(true, numFeatures);
        for (int i = 0; i < numFeatures; i++) {
            Element featureNode = featuresNode.getChild(i);
            PlatformFeatureData featureData = getFeatureData(featureNode);
            featureDataList.add(featureData);
        }
        
        return featureDataList;
    }
    
    private static PlatformFeatureData getFeatureData(Element featureNode) {
        String type = featureNode.getAttribute("type");
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                featureNode.getChildByName("properties"));
        
        return new PlatformFeatureData(type, properties);
    }
    
    private static Array<EnemyData> getEnemiesData(Element enemiesNode) {
        if (enemiesNode == null) {
            return null;
        }
        
        int numEnemies = enemiesNode.getChildCount();
        Array<EnemyData> enemyDataList = new Array<EnemyData>(true, numEnemies);
        for (int i = 0; i < numEnemies; i++) {
            Element enemyNode = enemiesNode.getChild(i);
            EnemyData enemyData = getEnemyData(enemyNode);
            enemyDataList.add(enemyData);
        }
        RiseGeneratorUtils.sortEnemies(enemyDataList);
        
        return enemyDataList;
    }
    
    private static EnemyData getEnemyData(Element enemyNode) {
        String type = enemyNode.getAttribute("type");
        float step = ReaderUtilities.getFloatAttribute(enemyNode, "step");
        float offset = ReaderUtilities.getFloatAttribute(enemyNode, "offset");
        
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                enemyNode.getChildByName("properties"));
        
        return new EnemyData(type, step, offset, properties);
    }
    
    private static Array<ItemData> getItemsData(Element itemsNode) {
        if (itemsNode == null) {
            return null;
        }
        
        int numItems = itemsNode.getChildCount();
        Array<ItemData> itemDataList = new Array<ItemData>(true, numItems);
        for (int i = 0; i < numItems; i++) {
            Element itemNode = itemsNode.getChild(i);
            ItemData itemData = getItemData(itemNode);
            itemDataList.add(itemData);
        }
        RiseGeneratorUtils.sortItems(itemDataList);
        
        return itemDataList;
    }
    
    private static ItemData getItemData(Element itemNode) {
        String type = itemNode.getAttribute("type");
        float step = ReaderUtilities.getFloatAttribute(itemNode, "step");
        float offset = ReaderUtilities.getFloatAttribute(itemNode, "offset");
        float appearanceChance = ReaderUtilities.getFloatAttribute(itemNode, "appearancechance", 1.0f);
        int attachedToPlatformId = ReaderUtilities.getIntAttribute(itemNode, "attachedtoplatformid", -1);
        
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                itemNode.getChildByName("properties"));
        
        return new ItemData(type, step, offset, appearanceChance, attachedToPlatformId, properties);
    }
    
    private static class NextGeneratedId {
        private static final int FIRST_GENERATED_ID = 1000;
        
        public int id;
        
        public NextGeneratedId() {
            id = FIRST_GENERATED_ID;
        }
    }
}
