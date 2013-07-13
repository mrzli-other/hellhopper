package com.turbogerm.helljump.resources;

import com.badlogic.gdx.math.MathUtils;

public final class ResourceNames {
    
    // data
    private static final String DATA_DIR = "data/";
    public static final String CREDITS = DATA_DIR + "credits.txt";
    
    private static final String RISE_SECTIONS_DIR = DATA_DIR + "risesections/";
    public static final String RISE_SECTIONS_DATA = RISE_SECTIONS_DIR + "risesections.txt";
    public static final String RISE_SECTIONS_METADATA = RISE_SECTIONS_DIR + "risesectionsmeta.xml";
    
    public static String getRiseSectionPath(String name) {
        return String.format("%s%s.xml", RISE_SECTIONS_DIR, name);
    }
    
    // graphics
    private static final String GRAPHICS_PACKED_DIR = "graphics/packed/";
    
    public static final String GRAPHICS_GUI_ATLAS = GRAPHICS_PACKED_DIR + "gui.atlas"; 
    
    private static final String GUI_GENERAL_IMAGE_NAME_PREFIX = "general/";
    public static final String GUI_GENERAL_BLACK_IMAGE_NAME = GUI_GENERAL_IMAGE_NAME_PREFIX + "black";
    public static final String GUI_GENERAL_WHITE_IMAGE_NAME = GUI_GENERAL_IMAGE_NAME_PREFIX + "white";
    public static final String GUI_GENERAL_TURBO_GERM_LOGO_IMAGE_NAME = GUI_GENERAL_IMAGE_NAME_PREFIX + "turbogermlogo";
    public static final String GUI_GENERAL_LIBGDX_LOGO_IMAGE_NAME = GUI_GENERAL_IMAGE_NAME_PREFIX + "libgdxlogo";
    
    private static final String GUI_SPLASH_IMAGE_NAME_PREFIX = "splash/";
    public static final String GUI_SPLASH_TITLE_IMAGE_NAME = GUI_SPLASH_IMAGE_NAME_PREFIX + "title";
    public static final String GUI_SPLASH_PLATFORM_IMAGE_NAME = GUI_SPLASH_IMAGE_NAME_PREFIX + "platform";
    
    private static final String GUI_MAIN_MENU_IMAGE_NAME_PREFIX = "mainmenu/";
    public static final String GUI_MAIN_MENU_TITLE_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "title";
    public static final String GUI_MAIN_MENU_BUTTON_START_UP_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttonstartup";
    public static final String GUI_MAIN_MENU_BUTTON_START_DOWN_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttonstartdown";
    public static final String GUI_MAIN_MENU_BUTTON_HIGH_SCORE_UP_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttonhighscoreup";
    public static final String GUI_MAIN_MENU_BUTTON_HIGH_SCORE_DOWN_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttonhighscoredown";
    public static final String GUI_MAIN_MENU_BUTTON_CREDITS_UP_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttoncreditsup";
    public static final String GUI_MAIN_MENU_BUTTON_CREDITS_DOWN_IMAGE_NAME = GUI_MAIN_MENU_IMAGE_NAME_PREFIX + "buttoncreditsdown";
    
    private static final String GUI_PLAY_IMAGE_NAME_PREFIX = "play/";
    public static final String GUI_PLAY_PAUSE_UP_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "pauseup";
    public static final String GUI_PLAY_PAUSE_DOWN_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "pausedown";
    public static final String GUI_PLAY_PLAY_UP_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "playup";
    public static final String GUI_PLAY_PLAY_DOWN_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "playdown";
    public static final String GUI_PLAY_LIVES_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "lives";
    public static final String GUI_PLAY_END_LINE_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "endline";
    public static final String GUI_PLAY_POSITION_SCROLL_LINE_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "positionscrollline";
    public static final String GUI_PLAY_POSITION_SCROLL_LINE_ABOVE_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "positionscrolllineabove";
    public static final String GUI_PLAY_POSITION_SCROLL_BOX_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "positionscrollbox";
    public static final String GUI_PLAY_POSITION_SCROLL_END_LINE_IMAGE_NAME = GUI_PLAY_IMAGE_NAME_PREFIX + "positionscrollendline";
    
    public static final String GUI_BUTTON_BACK_UP_IMAGE_NAME = "buttonbackup";
    public static final String GUI_BUTTON_BACK_DOWN_IMAGE_NAME = "buttonbackdown";
    public static final String GUI_BUTTON_CONTINUE_UP_IMAGE_NAME = "buttoncontinueup";
    public static final String GUI_BUTTON_CONTINUE_DOWN_IMAGE_NAME = "buttoncontinuedown";
    
    public static final String PLATFORMS_ATLAS = GRAPHICS_PACKED_DIR + "platforms.atlas";
    public static final String PLATFORM_FIRE_IMAGE_NAME = "platformfire";
    public static final String PLATFORM_CRUMBLE_IMAGE_NAME = "crumbleplatform";
    public static final String PLATFORM_ENGINE_NORMAL_IMAGE_NAME = "enginenormal";
    public static final String PLATFORM_ENGINE_REPOSITION_IMAGE_NAME = "enginereposition";
    public static final String PLATFORM_JUMP_BOOST_CRATER_LOW_IMAGE_NAME = "jumpboostcraterlow";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_LOW_IMAGE_NAME = "jumpboostdischargelow";
    public static final String PLATFORM_JUMP_BOOST_CRATER_MEDIUM_IMAGE_NAME = "jumpboostcratermedium";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_MEDIUM_IMAGE_NAME = "jumpboostdischargemedium";
    public static final String PLATFORM_JUMP_BOOST_CRATER_HIGH_IMAGE_NAME = "jumpboostcraterhigh";
    public static final String PLATFORM_JUMP_BOOST_DISCHARGE_HIGH_IMAGE_NAME = "jumpboostdischargehigh";
    
    private static final String PLATFORM_NORMAL_IMAGE_NAME_FORMAT = "normalplatform%02d";
    public static final int PLATFORM_NORMAL_IMAGE_COUNT = 5;
    
    private static String getPlatformNormalImageName(int index) {
        return String.format(ResourceNames.PLATFORM_NORMAL_IMAGE_NAME_FORMAT, index);
    }
    
    public static String getRandomPlatformNormalImageName() {
        int index = MathUtils.random(PLATFORM_NORMAL_IMAGE_COUNT - 1);
        return getPlatformNormalImageName(index);
    }
    
    public static final String CHARACTER_ATLAS = GRAPHICS_PACKED_DIR + "character.atlas";
    public static final String CHARACTER_BODY_IMAGE_NAME = "body";
    public static final String CHARACTER_HEAD_IMAGE_NAME = "head";
    public static final String CHARACTER_EYES_STUNNED_IMAGE_NAME = "eyesstunned";
    public static final String CHARACTER_EYES_FART_OPENED_IMAGE_NAME = "eyesfartopened";
    public static final String CHARACTER_EYES_FART_CLOSED_IMAGE_NAME = "eyesfartclosed";
    public static final String CHARACTER_MOUTH_SMILE_IMAGE_NAME = "mouthsmile";
    public static final String CHARACTER_SHIELD_EFFECT_IMAGE_NAME = "shieldeffect";
    public static final String CHARACTER_FART_DISCHARGE_IMAGE_NAME = "fartdischarge";
    
    private static final String CHARACTER_EYES_NORMAL_IMAGE_NAME_FORMAT = "eyesnormal%02d";
    public static final int CHARACTER_EYES_NORMAL_IMAGE_COUNT = 2;
    
    public static String getCharacterEyesNormalImageName(int index) {
        return String.format(ResourceNames.CHARACTER_EYES_NORMAL_IMAGE_NAME_FORMAT, index);
    }
    
    public static final String ENEMIES_ATLAS = GRAPHICS_PACKED_DIR + "enemies.atlas";
    public static final String ENEMY_SAW_IMAGE_NAME = "saw";
    public static final String ENEMY_IMP_IMAGE_NAME = "imp";
    public static final String ENEMY_LOCO_IMAGE_NAME = "loco";
    public static final String ENEMY_KNIGHT_IMAGE_NAME = "knight";
    public static final String ENEMY_EVIL_TWIN_IMAGE_NAME = "eviltwin";
    public static final String ENEMY_COOL_CLERK_IMAGE_NAME = "coolclerk";
    
    public static final String ITEMS_ATLAS = GRAPHICS_PACKED_DIR + "items.atlas";
    public static final String ITEM_BEANS_IMAGE_NAME = "beans";
    public static final String ITEM_SHIELD_IMAGE_NAME = "shield";
    public static final String ITEM_JUMP_SUIT_IMAGE_NAME = "jumpsuit";
    public static final String ITEM_LIFE_IMAGE_NAME = "life";
    public static final String ITEM_COIN_COPPER_IMAGE_NAME = "coincopper";
    public static final String ITEM_COIN_SILVER_IMAGE_NAME = "coinsilver";
    public static final String ITEM_COIN_GOLD_IMAGE_NAME = "coingold";
    public static final String ITEM_SIGNET_IMAGE_NAME = "signet";
    public static final String ITEM_BLACK_BOX_IMAGE_NAME = "blackbox";
    
    public static final String BACKGROUND_ATLAS = GRAPHICS_PACKED_DIR + "background.atlas";
    public static final String BACKGROUND_IMAGE_NAME = "background";
    public static final String BACKGROUND_END_SKY_IMAGE_NAME = "endsky";
    public static final String BACKGROUND_END_SUN_IMAGE_NAME = "endsun";
    public static final String BACKGROUND_END_CLOUDS_IMAGE_NAME = "endclouds";
    public static final String BACKGROUND_END_MOUNTAINS_IMAGE_NAME = "endmountains";
    public static final String BACKGROUND_END_GROUND_IMAGE_NAME = "endground";
    public static final String BACKGROUND_END_SHEEP_IMAGE_NAME = "sheep";
    
    private static final String BACKGROUND_FOREGROUND_SIDE_ELEMENT_IMAGE_NAME_FORMAT = "foregroundsideelement%02d";
    public static final int BACKGROUND_FOREGROUND_SIDE_ELEMENT_IMAGE_COUNT = 8;
    
    public static String getBackgroundForegroundSideElementImageName(int index) {
        return String.format(ResourceNames.BACKGROUND_FOREGROUND_SIDE_ELEMENT_IMAGE_NAME_FORMAT, index);
    }
    
    // fonts
    private static final String GUI_DIR = "gui/";
    public static final String GUI_SKIN = GUI_DIR + "uiskin.json";
    
    private static final String FONTS_DIR = GUI_DIR + "fonts/";
    public static final String FONT_ITEM = FONTS_DIR + "arial-bold-18.fnt";
    
    // particles
    private static final String PARTICLES_DIR = "particles/";
    public static final String PARTICLE_ENGINE_NORMAL = PARTICLES_DIR + "enginenormal.p";
    public static final String PARTICLE_ENGINE_REPOSITION = PARTICLES_DIR + "enginereposition.p";
    
    // sounds
    private static final String SOUNDS_DIR = "sounds/";
    public static final String SOUND_JUMP = SOUNDS_DIR + "jump.mp3";
    public static final String SOUND_JUMP_BOOST = SOUNDS_DIR + "jumpboost.mp3";
    public static final String SOUND_FART = SOUNDS_DIR + "fart.mp3";
    public static final String SOUND_COIN = SOUNDS_DIR + "coin.mp3";
    public static final String SOUND_ITEM = SOUNDS_DIR + "item.mp3";
    public static final String SOUND_ENEMY = SOUNDS_DIR + "enemy.mp3";
    public static final String SOUND_SAW = SOUNDS_DIR + "saw.mp3";
    public static final String SOUND_FIRE = SOUNDS_DIR + "fire.mp3";
    public static final String SOUND_FALL = SOUNDS_DIR + "fall.mp3";
    
    private static final String SOUND_SHEEP_NAME_FORMAT = SOUNDS_DIR + "sheep%02d.mp3";
    public static final int SOUND_SHEEP_COUNT = 7;
    
    public static String getSoundSheep(int index) {
        return String.format(ResourceNames.SOUND_SHEEP_NAME_FORMAT, index);
    }
}
