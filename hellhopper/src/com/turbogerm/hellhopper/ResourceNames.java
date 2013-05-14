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
package com.turbogerm.hellhopper;

import com.badlogic.gdx.math.MathUtils;

public final class ResourceNames {
    
    public static final String GUI_DIR = "gui/";
    public static final String GUI_SKIN = GUI_DIR + "uiskin.json";
    public static final String GUI_BLACK_TEXTURE = GUI_DIR + "black.png";
    public static final String GUI_BACKGROUND_TEXTURE = GUI_DIR + "background.png";
    public static final String GUI_MAIN_MENU_START_UP_TEXTURE = GUI_DIR + "mainmenustartup.png";
    public static final String GUI_MAIN_MENU_START_DOWN_TEXTURE = GUI_DIR + "mainmenustartdown.png";
    public static final String GUI_MAIN_MENU_HIGH_SCORE_UP_TEXTURE = GUI_DIR + "mainmenuhighscoreup.png";
    public static final String GUI_MAIN_MENU_HIGH_SCORE_DOWN_TEXTURE = GUI_DIR + "mainmenuhighscoredown.png";
    public static final String GUI_MAIN_MENU_INFO_UP_TEXTURE = GUI_DIR + "mainmenuinfoup.png";
    public static final String GUI_MAIN_MENU_INFO_DOWN_TEXTURE = GUI_DIR + "mainmenuinfodown.png";
    public static final String GUI_BUTTON_BACK_UP_TEXTURE = GUI_DIR + "buttonbackup.png";
    public static final String GUI_BUTTON_BACK_DOWN_TEXTURE = GUI_DIR + "buttonbackdown.png";
    public static final String GUI_BUTTON_CONTINUE_UP_TEXTURE = GUI_DIR + "buttoncontinueup.png";
    public static final String GUI_BUTTON_CONTINUE_DOWN_TEXTURE = GUI_DIR + "buttoncontinuedown.png";
    public static final String GUI_PAUSE_UP_TEXTURE = GUI_DIR + "pauseup.png";
    public static final String GUI_PAUSE_DOWN_TEXTURE = GUI_DIR + "pausedown.png";
    public static final String GUI_PLAY_UP_TEXTURE = GUI_DIR + "playup.png";
    public static final String GUI_PLAY_DOWN_TEXTURE = GUI_DIR + "playdown.png";
    
    public static final String DATA_DIR = "data/";
    public static final String RISE_SECTIONS_DIR = DATA_DIR + "risesections/";
    public static final String RISE_SECTIONS_DATA = RISE_SECTIONS_DIR + "risesections.txt";
    
    public static final String GAME_DIR = "game/";
    public static final String GAME_CHARACTER_TEXTURE = GAME_DIR + "character.png";
    public static final String GAME_END_LINE_TEXTURE = GAME_DIR + "endline.png";
    public static final String GAME_POSITION_SCROLL_LINE_TEXTURE = GAME_DIR + "positionscrollline.png";
    public static final String GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE = GAME_DIR + "positionscrolllineabove.png";
    public static final String GAME_POSITION_SCROLL_BOX_TEXTURE = GAME_DIR + "positionscrollbox.png";
    public static final String GAME_POSITION_SCROLL_END_LINE_TEXTURE = GAME_DIR + "positionscrollendline.png";
    
    public static final String PLATFORMS_DIR = GAME_DIR + "platforms/";
    private static final String PLATFORM_NORMAL_TEXTURE_NAME_FORMAT = PLATFORMS_DIR + "normalplatform%02d.png";
    public static final int PLATFORM_NORMAL_TEXTURE_COUNT = 5;
    private static final String PLATFORM_FLAME_TEXTURE_NAME_FORMAT = PLATFORMS_DIR + "flameplatform%02d.png";
    public static final int PLATFORM_FLAME_TEXTURE_COUNT = 5;
    public static final String PLATFORM_CRUMBLE_TEXTURE = PLATFORMS_DIR + "crumbleplatform.png";
    
    public static final String PLATFORM_FIRE_TEXTURE_ATLAS = PLATFORMS_DIR + "platformfire.atlas";
    public static final String PLATFORM_JUMP_BOOST_CRATER_TEXTURE = PLATFORMS_DIR + "jumpboostcrater.png";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_TEXTURE = PLATFORMS_DIR + "jumpboostdischarge.png";
    public static final String PLATFORM_ENGINE_TEXTURE = PLATFORMS_DIR + "engine.png";
    
    public static final String OBJECTS_DIR = GAME_DIR + "objects/";
    public static final String OBJECT_LAVA_ROCK_TEXTURE = OBJECTS_DIR + "lavarock.png";
    
    public static final String PARTICLES_DIR = "particles/";
    public static final String PARTICLE_ENGINE = PARTICLES_DIR + "engine.p";
    
    public static String getRiseSectionPath(String name) {
        return String.format("%s%s.xml", RISE_SECTIONS_DIR, name);
    }
    
    public static String getPlatformNormalTexture(int i) {
        return String.format(ResourceNames.PLATFORM_NORMAL_TEXTURE_NAME_FORMAT, i);
    }
    
    public static String getRandomPlatformNormalTexture() {
        int index = MathUtils.random(PLATFORM_NORMAL_TEXTURE_COUNT - 1);
        return getPlatformNormalTexture(index);
    }
    
    public static String getPlatformFlameTexture(int i) {
        return String.format(ResourceNames.PLATFORM_FLAME_TEXTURE_NAME_FORMAT, i);
    }
    
    public static String getRandomPlatformFlameTexture() {
        int index = MathUtils.random(PLATFORM_FLAME_TEXTURE_COUNT - 1);
        return getPlatformFlameTexture(index);
    }
}
