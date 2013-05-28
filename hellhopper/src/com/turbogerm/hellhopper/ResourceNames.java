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
    public static final String RISE_SECTIONS_METADATA = RISE_SECTIONS_DIR + "risesectionsmeta.xml";
    
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
    public static final String PLATFORM_CRUMBLE_TEXTURE = PLATFORMS_DIR + "crumbleplatform.png";
    
    public static final String PLATFORM_FIRE_TEXTURE_ATLAS = PLATFORMS_DIR + "platformfire.atlas";
    public static final String PLATFORM_ENGINE_NORMAL_TEXTURE = PLATFORMS_DIR + "enginenormal.png";
    public static final String PLATFORM_ENGINE_REPOSITION_TEXTURE = PLATFORMS_DIR + "enginereposition.png";
    public static final String PLATFORM_JUMP_BOOST_CRATER_LOW_TEXTURE = PLATFORMS_DIR + "jumpboostcraterlow.png";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_LOW_TEXTURE = PLATFORMS_DIR + "jumpboostdischargelow.png";
    public static final String PLATFORM_JUMP_BOOST_CRATER_MEDIUM_TEXTURE = PLATFORMS_DIR + "jumpboostcratermedium.png";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_MEDIUM_TEXTURE = PLATFORMS_DIR + "jumpboostdischargemedium.png";
    public static final String PLATFORM_JUMP_BOOST_CRATER_HIGH_TEXTURE = PLATFORMS_DIR + "jumpboostcraterhigh.png";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_HIGH_TEXTURE = PLATFORMS_DIR + "jumpboostdischargehigh.png";
    
    public static final String BACKGROUND_DIR = GAME_DIR + "background/";
    public static final String BACKGROUND_TEXTURE = BACKGROUND_DIR + "background.png";
    private static final String BACKGROUND_CLOUD_TEXTURE_NAME_FORMAT = BACKGROUND_DIR + "cloud%02d.png";
    public static final int BACKGROUND_CLOUD_TEXTURE_COUNT = 3;
    private static final String BACKGROUND_ROCK_TEXTURE_NAME_FORMAT = BACKGROUND_DIR + "rock%02d.png";
    public static final int BACKGROUND_ROCK_TEXTURE_COUNT = 4;
    
    public static final String OBJECTS_DIR = GAME_DIR + "objects/";
    public static final String OBJECT_LAVA_ROCK_TEXTURE = OBJECTS_DIR + "lavarock.png";
    
    public static final String PARTICLES_DIR = "particles/";
    public static final String PARTICLE_ENGINE_NORMAL = PARTICLES_DIR + "enginenormal.p";
    public static final String PARTICLE_ENGINE_REPOSITION = PARTICLES_DIR + "enginereposition.p";
    
    public static final String SOUNDS_DIR = "sounds/";
    public static final String SOUND_JUMP = SOUNDS_DIR + "jump.mp3";
    public static final String SOUND_JUMP_BOOST = SOUNDS_DIR + "jumpboost.mp3";
    
    public static String getRiseSectionPath(String name) {
        return String.format("%s%s.xml", RISE_SECTIONS_DIR, name);
    }
    
    public static String getPlatformNormalTexture(int index) {
        return String.format(ResourceNames.PLATFORM_NORMAL_TEXTURE_NAME_FORMAT, index);
    }
    
    public static String getRandomPlatformNormalTexture() {
        int index = MathUtils.random(PLATFORM_NORMAL_TEXTURE_COUNT - 1);
        return getPlatformNormalTexture(index);
    }
    
    public static String getBackgroundCloudTexture(int index) {
        return String.format(ResourceNames.BACKGROUND_CLOUD_TEXTURE_NAME_FORMAT, index);
    }
    
    public static String getRandomBackgroundCloudTexture() {
        int index = MathUtils.random(BACKGROUND_CLOUD_TEXTURE_COUNT - 1);
        return getBackgroundCloudTexture(index);
    }
    
    public static String getBackgroundRockTexture(int index) {
        return String.format(ResourceNames.BACKGROUND_ROCK_TEXTURE_NAME_FORMAT, index);
    }
    
    public static String getRandomBackgroundRockTexture() {
        int index = MathUtils.random(BACKGROUND_ROCK_TEXTURE_COUNT - 1);
        return getBackgroundRockTexture(index);
    }
}
