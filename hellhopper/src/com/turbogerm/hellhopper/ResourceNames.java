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
    public static final String RISE_SECTION_TEST = RISE_SECTIONS_DIR + "test.xml";
    
    public static final String GAME_DIR = "game/";
    public static final String GAME_CHARACTER_TEXTURE = GAME_DIR + "character.png";
    public static final String GAME_END_LINE_TEXTURE = GAME_DIR + "endline.png";
    public static final String GAME_POSITION_SCROLL_LINE_TEXTURE = GAME_DIR + "positionscrollline.png";
    public static final String GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE = GAME_DIR + "positionscrolllineabove.png";
    public static final String GAME_POSITION_SCROLL_BOX_TEXTURE = GAME_DIR + "positionscrollbox.png";
    public static final String GAME_POSITION_SCROLL_END_LINE_TEXTURE = GAME_DIR + "positionscrollendline.png";
    
    public static final String PLATFORMS_DIR = GAME_DIR + "platforms/";
    public static final String PLATFORM_NORMAL_TEXTURE = PLATFORMS_DIR + "normalplatform.png";
    public static final String PLATFORM_HORIZONTAL_MOVEMENT_TEXTURE = PLATFORMS_DIR + "horizontalmovementplatform.png";
    public static final String PLATFORM_VERTICAL_MOVEMENT_TEXTURE = PLATFORMS_DIR + "verticalmovementplatform.png";
    public static final String PLATFORM_CIRCULAR_MOVEMENT_TEXTURE = PLATFORMS_DIR + "circularmovementplatform.png";
    public static final String PLATFORM_CRUMBLE_TEXTURE = PLATFORMS_DIR + "crumbleplatform.png";
    
    public static final String SHADERS_DIR = "shaders/";
    public static final String DISTANCE_FIELD_VS = SHADERS_DIR + "distancefield.vert";
    public static final String DISTANCE_FIELD_FS = SHADERS_DIR + "distancefield.frag";
}
