package com.turbogerm.helljump.dataaccess;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.germlibrary.util.Logger;

public final class RiseSectionMetadataReader {
    
    public static RiseSectionsMetadata read(FileHandle fileHandle) {
        
        XmlReader reader = new XmlReader();
        
        Element riseSectionsMetaNode = null;
        try {
            riseSectionsMetaNode = reader.parse(fileHandle);
        } catch (IOException e) {
            Logger.error(e.getMessage());
            return null;
        }
        
        Element riseSectionsNode = riseSectionsMetaNode.getChildByName("risesections");
        
        int numRiseSections = riseSectionsNode.getChildCount();
        Array<RiseSectionMetadata> riseSectionMetadataList = new Array<RiseSectionMetadata>(true, numRiseSections);
        for (int i = 0; i < numRiseSections; i++) {
            Element riseSectionNode = riseSectionsNode.getChild(i);
            RiseSectionMetadata riseSection = getRiseSectionMetadata(riseSectionNode);
            riseSectionMetadataList.add(riseSection);
        }
        
        return new RiseSectionsMetadata(riseSectionMetadataList);
    }
    
    private static RiseSectionMetadata getRiseSectionMetadata(Element riseSectionNode) {
        String generatorType = riseSectionNode.getAttribute("generatortype");
        String type = riseSectionNode.getAttribute("type");
        String name = riseSectionNode.getAttribute("name");
        int minStepRange = ReaderUtilities.getIntAttribute(riseSectionNode, "minsteprange");
        int maxStepRange = ReaderUtilities.getIntAttribute(riseSectionNode, "maxsteprange");
        int minStepDistance = ReaderUtilities.getIntAttribute(riseSectionNode, "minstepdistance");
        int maxStepDistance = ReaderUtilities.getIntAttribute(riseSectionNode, "maxstepdistance");
        int difficulty = ReaderUtilities.getIntAttribute(riseSectionNode, "difficulty");
        
        ObjectMap<String, String> properties =  ReaderUtilities.getProperties(
                riseSectionNode.getChildByName("properties"));
        
        return new RiseSectionMetadata(generatorType, type, name,
                minStepRange, maxStepRange, minStepDistance, maxStepDistance,
                difficulty, properties);
    }
}
