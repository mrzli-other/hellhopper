package com.turbogerm.hellhopper.dataaccess;

import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.turbogerm.hellhopper.util.Logger;

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
    
    private static RiseSectionMetadata getRiseSectionMetadata(Element riseSection) {
        String generatorType = riseSection.getAttribute("generatortype");
        String type = riseSection.getAttribute("type");
        String name = riseSection.getAttribute("name");
        int minStepRange = Integer.parseInt(riseSection.getAttribute("minsteprange"));
        int maxStepRange = Integer.parseInt(riseSection.getAttribute("maxsteprange"));
        int minStepDistance = Integer.parseInt(riseSection.getAttribute("minstepdistance"));
        int maxStepDistance = Integer.parseInt(riseSection.getAttribute("maxstepdistance"));
        int difficulty = Integer.parseInt(riseSection.getAttribute("difficulty"));
        
        ObjectMap<String, String> properties =  ReaderUtilities.getProperties(
                riseSection.getChildByName("properties"));
        
        return new RiseSectionMetadata(generatorType, type, name,
                minStepRange, maxStepRange, minStepDistance, maxStepDistance,
                difficulty, properties);
    }
}
