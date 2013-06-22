package com.turbogerm.hellhopper.dataaccess;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.hellhopper.game.generator.RiseGeneratorUtils;
import com.turbogerm.hellhopper.util.Logger;

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
        
        String stepRangeAttribute = riseSectionNode.getAttribute("steprange");
        int stepRange = Integer.parseInt(stepRangeAttribute);
        String difficultyAttribute = riseSectionNode.getAttribute("difficulty");
        int difficulty = Integer.parseInt(difficultyAttribute);
        
        Element platformsNode = riseSectionNode.getChildByName("platforms");
        Array<PlatformData> platformDataList = getPlatformsData(platformsNode);
        
        Element enemiesNode = riseSectionNode.getChildByName("enemies");
        Array<EnemyData> enemyDataList = getEnemiesData(enemiesNode);
        
        Element itemsNode = riseSectionNode.getChildByName("items");
        Array<ItemData> itemDataList = getItemsData(itemsNode);
        
        return new RiseSectionData(type, name, stepRange, difficulty, platformDataList, enemyDataList, itemDataList);
    }
    
    private static Array<PlatformData> getPlatformsData(Element platformsNode) {
        int numPlatforms = platformsNode.getChildCount();
        Array<PlatformData> platformDataList = new Array<PlatformData>(true, numPlatforms);
        for (int i = 0; i < numPlatforms; i++) {
            Element platformNode = platformsNode.getChild(i);
            PlatformData platformData = getPlatformData(platformNode);
            platformDataList.add(platformData);
        }
        RiseGeneratorUtils.sortPlatforms(platformDataList);
        
        return platformDataList;
    }
    
    private static PlatformData getPlatformData(Element platformNode) {
        String stepAttribute = platformNode.getAttribute("step");
        int step = Integer.parseInt(stepAttribute);
        String offsetAttribute = platformNode.getAttribute("offset");
        int offset = Integer.parseInt(offsetAttribute);
        String type = platformNode.getAttribute("type");
        
        PlatformMovementData movementData = getMovementData(platformNode.getChildByName("movement"));
        Array<PlatformFeatureData> featuresData = getFeaturesData(platformNode.getChildByName("features"));
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                platformNode.getChildByName("properties"));
        
        return new PlatformData(type, step, offset, movementData, featuresData, properties);
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
        String stepAttribute = enemyNode.getAttribute("step");
        float step = Float.parseFloat(stepAttribute);
        String offsetAttribute = enemyNode.getAttribute("offset");
        float offset = Float.parseFloat(offsetAttribute);
        
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
        String stepAttribute = itemNode.getAttribute("step");
        float step = Float.parseFloat(stepAttribute);
        String offsetAttribute = itemNode.getAttribute("offset");
        float offset = Float.parseFloat(offsetAttribute);
        
        ObjectMap<String, String> properties = ReaderUtilities.getProperties(
                itemNode.getChildByName("properties"));
        
        return new ItemData(type, step, offset, properties);
    }
}
