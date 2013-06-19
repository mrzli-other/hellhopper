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

public abstract class RiseSectionDataBase {
    
    // private static final String INITIAL_TYPE = "initial";
    // private static final String GENERIC_TYPE = "generic";
    
    private static final String TRANSITION_TYPE = "transition";
    
    private static final String NORMAL_TYPE = "normal";
    private static final String MOVING_TYPE = "moving";
    private static final String REPOSITION_TYPE = "reposition";
    
    private static final String ENEMY_TYPE = "enemy";
    
    private static final String JUMP_BOOST_TYPE = "jumpboost";
    private static final String VISIBLE_ON_JUMP_TYPE = "visibleonjump";
    private static final String CRUMBLE_TYPE = "crumble";
    
    private final String mType;
    private final String mName;
    private final int mDifficulty;
    
    public RiseSectionDataBase(String type, String name, int difficulty) {
        mType = type;
        mName = name;
        mDifficulty = difficulty;
    }
    
    public abstract boolean isMetadata();
    
    public String getType() {
        return mType;
    }
    
    public String getName() {
        return mName;
    }
    
    public int getDifficulty() {
        return mDifficulty;
    }
    
    public static boolean isTransitionType(String type) {
        return TRANSITION_TYPE.endsWith(type);
    }
    
    public static boolean isStandardType(String type) {
        return NORMAL_TYPE.equals(type) || MOVING_TYPE.equals(type) || REPOSITION_TYPE.equals(type);
    }
    
    public static boolean isEnemyType(String type) {
        return ENEMY_TYPE.equals(type);
    }
    
    public static boolean isSpecialType(String type) {
        return JUMP_BOOST_TYPE.equals(type) || VISIBLE_ON_JUMP_TYPE.equals(type) || CRUMBLE_TYPE.equals(type);
    }
}
