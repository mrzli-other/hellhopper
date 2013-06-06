/*
 * Copyright (c) 2013 Goran Mrzljak
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *
 */
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
    
    public static RiseSectionData read(String name, FileHandle fileHandle) {
        
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
        
        return new RiseSectionData(name, stepRange, difficulty, platformDataList, enemyDataList);
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
}
