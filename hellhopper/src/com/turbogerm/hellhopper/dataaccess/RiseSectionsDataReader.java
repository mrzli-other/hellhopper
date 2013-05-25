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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.hellhopper.ResourceNames;

public final class RiseSectionsDataReader {
    
    private static final int INITIAL_RISE_SECTION_DATA_LIST_CAPACITY = 20;
    
    public static RiseSectionsData read(FileHandle fileHandle) {
        
        Array<RiseSectionData> riseSections = new Array<RiseSectionData>(
                true, INITIAL_RISE_SECTION_DATA_LIST_CAPACITY);
        
        String riseSectionsText = fileHandle.readString();
        String[] riseSectionNames = riseSectionsText.split("\\r?\\n");
        for (String riseSectionName : riseSectionNames) {
            RiseSectionData riseSection = RiseSectionDataReader.read(riseSectionName,
                    Gdx.files.internal(ResourceNames.getRiseSectionPath(riseSectionName)));
            riseSections.add(riseSection);
        }
        
        return new RiseSectionsData(riseSections);
    }
}
