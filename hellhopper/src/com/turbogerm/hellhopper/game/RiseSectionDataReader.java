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
package com.turbogerm.hellhopper.game;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.hellhopper.util.Logger;

public final class RiseSectionDataReader {
    
    public static RiseSectionData read(FileHandle fileHandle) {
        
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
        int numPlatforms = platformsNode.getChildCount();
        Array<PlatformData> platformDataList = new Array<PlatformData>(true, numPlatforms);
        for (int i = 0; i < numPlatforms; i++) {
            Element platformNode = platformsNode.getChild(i);
            PlatformData platformData = getPlatformData(platformNode);
            platformDataList.add(platformData);
        }
        
        return new RiseSectionData(stepRange, difficulty, platformDataList);
    }
    
    private static PlatformData getPlatformData(Element platformNode) {
        String stepAttribute = platformNode.getAttribute("step");
        int step = Integer.parseInt(stepAttribute);
        String offsetAttribute = platformNode.getAttribute("offset");
        int offset = Integer.parseInt(offsetAttribute);
        
        Element propertiesNode = platformNode.getChildByName("properties");
        ObjectMap<String, String> properties = getProperties(propertiesNode);
        
        return new PlatformData(step, offset, properties);
    }
    
    private static ObjectMap<String, String> getProperties(Element propertiesNode) {
        
        if (propertiesNode == null) {
            return null;
        }
        
        int numProperties = propertiesNode.getChildCount();
        ObjectMap<String, String> properties = new ObjectMap<String, String>(numProperties);
        for (int i = 0; i < numProperties; i++) {
            Element propertyNode = propertiesNode.getChild(i);
            String name = propertyNode.getAttribute("name");
            String value = propertyNode.getAttribute("value");
            properties.put(name, value);
        }
        
        return properties;
    }
}
