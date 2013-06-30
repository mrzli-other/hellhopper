package com.turbogerm.hellhopper.dataaccess;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class RiseSectionsDataReader {
    
    private static final int INITIAL_RISE_SECTION_DATA_LIST_CAPACITY = 20;
    
    public static RiseSectionsData read(FileHandle fileHandle) {
        
        Array<RiseSectionData> riseSections = new Array<RiseSectionData>(
                true, INITIAL_RISE_SECTION_DATA_LIST_CAPACITY);
        
        String riseSectionsText = fileHandle.readString();
        String[] riseSectionLines = riseSectionsText.split("\\r?\\n");
        String riseSectionType = "";
        for (String riseSectionLine : riseSectionLines) {
            if (riseSectionLine.startsWith("-")) {
                riseSectionType = riseSectionLine.substring(1);
            } else {
                RiseSectionData riseSection = RiseSectionDataReader.read(
                        riseSectionType, riseSectionLine,
                        Gdx.files.internal(ResourceNames.getRiseSectionPath(riseSectionLine)));
                riseSections.add(riseSection);
            }
        }
        
        return new RiseSectionsData(riseSections);
    }
}
