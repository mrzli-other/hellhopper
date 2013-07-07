package com.turbogerm.hellhopper.resources;

import com.badlogic.gdx.math.MathUtils;

public final class ResourceNames {
    
    // data
    private static final String DATA_DIR = "data/";
    private static final String RISE_SECTIONS_DIR = DATA_DIR + "risesections/";
    public static final String RISE_SECTIONS_DATA = RISE_SECTIONS_DIR + "risesections.txt";
    public static final String RISE_SECTIONS_METADATA = RISE_SECTIONS_DIR + "risesectionsmeta.xml";
    
    public static String getRiseSectionPath(String name) {
        return String.format("%s%s.xml", RISE_SECTIONS_DIR, name);
    }
    
    // graphics
    private static final String GRAPHICS_DIR = "graphics/";
    
    private static final String GRAPHICS_PACKED_DIR = GRAPHICS_DIR + "packed/";
    
    private static final String GRAPHICS_GENERAL_DIR = GRAPHICS_DIR + "general/";
    public static final String GENERAL_BLACK_TEXTURE = GRAPHICS_GENERAL_DIR + "black.png";
    public static final String GENERAL_WHITE_TEXTURE = GRAPHICS_GENERAL_DIR + "white.png";
    public static final String GENERAL_TURBO_GERM_LOGO_TEXTURE = GRAPHICS_GENERAL_DIR + "turbogermlogo.png";
    public static final String GENERAL_LIBGDX_LOGO_TEXTURE = GRAPHICS_GENERAL_DIR + "libgdxlogo.png";
    
    private static final String GRAPHICS_GUI_DIR = GRAPHICS_DIR + "gui/";
    
    private static final String GUI_SPLASH_DIR = GRAPHICS_GUI_DIR + "splash/";
    public static final String GUI_SPLASH_TITLE_TEXTURE = GUI_SPLASH_DIR + "title.png";
    public static final String GUI_SPLASH_PLATFORM_TEXTURE = GUI_SPLASH_DIR + "platform.png";
    
    private static final String GUI_MAIN_MENU_DIR = GRAPHICS_GUI_DIR + "mainmenu/";
    public static final String GUI_MAIN_MENU_TITLE_TEXTURE = GUI_MAIN_MENU_DIR + "title.png";
    public static final String GUI_MAIN_MENU_BUTTON_START_UP_TEXTURE = GUI_MAIN_MENU_DIR + "buttonstartup.png";
    public static final String GUI_MAIN_MENU_BUTTON_START_DOWN_TEXTURE = GUI_MAIN_MENU_DIR + "buttonstartdown.png";
    public static final String GUI_MAIN_MENU_BUTTON_HIGH_SCORE_UP_TEXTURE = GUI_MAIN_MENU_DIR + "buttonhighscoreup.png";
    public static final String GUI_MAIN_MENU_BUTTON_HIGH_SCORE_DOWN_TEXTURE = GUI_MAIN_MENU_DIR + "buttonhighscoredown.png";
    public static final String GUI_MAIN_MENU_BUTTON_CREDITS_UP_TEXTURE = GUI_MAIN_MENU_DIR + "buttoncreditsup.png";
    public static final String GUI_MAIN_MENU_BUTTON_CREDITS_DOWN_TEXTURE = GUI_MAIN_MENU_DIR + "buttoncreditsdown.png";
    
    private static final String GUI_PLAY_DIR = GRAPHICS_GUI_DIR + "play/";
    public static final String GUI_PLAY_PAUSE_UP_TEXTURE = GUI_PLAY_DIR + "pauseup.png";
    public static final String GUI_PLAY_PAUSE_DOWN_TEXTURE = GUI_PLAY_DIR + "pausedown.png";
    public static final String GUI_PLAY_PLAY_UP_TEXTURE = GUI_PLAY_DIR + "playup.png";
    public static final String GUI_PLAY_PLAY_DOWN_TEXTURE = GUI_PLAY_DIR + "playdown.png";
    public static final String GUI_PLAY_LIVES_TEXTURE = GUI_PLAY_DIR + "lives.png";
    public static final String GUI_PLAY_END_LINE_TEXTURE = GUI_PLAY_DIR + "endline.png";
    public static final String GUI_PLAY_POSITION_SCROLL_LINE_TEXTURE = GUI_PLAY_DIR + "positionscrollline.png";
    public static final String GUI_PLAY_POSITION_SCROLL_LINE_ABOVE_TEXTURE = GUI_PLAY_DIR + "positionscrolllineabove.png";
    public static final String GUI_PLAY_POSITION_SCROLL_BOX_TEXTURE = GUI_PLAY_DIR + "positionscrollbox.png";
    public static final String GUI_PLAY_POSITION_SCROLL_END_LINE_TEXTURE = GUI_PLAY_DIR + "positionscrollendline.png";
    
    public static final String GUI_BUTTON_BACK_UP_TEXTURE = GRAPHICS_GUI_DIR + "buttonbackup.png";
    public static final String GUI_BUTTON_BACK_DOWN_TEXTURE = GRAPHICS_GUI_DIR + "buttonbackdown.png";
    public static final String GUI_BUTTON_CONTINUE_UP_TEXTURE = GRAPHICS_GUI_DIR + "buttoncontinueup.png";
    public static final String GUI_BUTTON_CONTINUE_DOWN_TEXTURE = GRAPHICS_GUI_DIR + "buttoncontinuedown.png";
    
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
    
    private static final String ENEMIES_DIR = GRAPHICS_DIR + "enemies/";
    public static final String ENEMY_SAW_TEXTURE = ENEMIES_DIR + "saw.png";
    public static final String ENEMY_IMP_TEXTURE = ENEMIES_DIR + "imp.png";
    public static final String ENEMY_LOCO_TEXTURE = ENEMIES_DIR + "loco.png";
    public static final String ENEMY_KNIGHT_TEXTURE = ENEMIES_DIR + "knight.png";
    public static final String ENEMY_EVIL_TWIN_TEXTURE = ENEMIES_DIR + "eviltwin.png";
    public static final String ENEMY_COOL_CLERK_TEXTURE = ENEMIES_DIR + "coolclerk.png";
    
    private static final String ITEMS_DIR = GRAPHICS_DIR + "items/";
    public static final String ITEM_BEANS_TEXTURE = ITEMS_DIR + "beans.png";
    public static final String ITEM_SHIELD_TEXTURE = ITEMS_DIR + "shield.png";
    public static final String ITEM_JUMP_SUIT_TEXTURE = ITEMS_DIR + "jumpsuit.png";
    public static final String ITEM_LIFE_TEXTURE = ITEMS_DIR + "life.png";
    public static final String ITEM_COIN_COPPER_TEXTURE = ITEMS_DIR + "coincopper.png";
    public static final String ITEM_COIN_SILVER_TEXTURE = ITEMS_DIR + "coinsilver.png";
    public static final String ITEM_COIN_GOLD_TEXTURE = ITEMS_DIR + "coingold.png";
    public static final String ITEM_SIGNET_TEXTURE = ITEMS_DIR + "signet.png";
    public static final String ITEM_BLACK_BOX_TEXTURE = ITEMS_DIR + "blackbox.png";
    
    private static final String BACKGROUND_DIR = GRAPHICS_DIR + "background/";
    public static final String BACKGROUND_TEXTURE = BACKGROUND_DIR + "background.png";
    public static final String BACKGROUND_END_SKY_TEXTURE = BACKGROUND_DIR + "endsky.png";
    public static final String BACKGROUND_END_SUN_TEXTURE = BACKGROUND_DIR + "endsun.png";
    public static final String BACKGROUND_END_CLOUDS_TEXTURE = BACKGROUND_DIR + "endclouds.png";
    public static final String BACKGROUND_END_MOUNTAINS_TEXTURE = BACKGROUND_DIR + "endmountains.png";
    public static final String BACKGROUND_END_GROUND_TEXTURE = BACKGROUND_DIR + "endground.png";
    public static final String BACKGROUND_END_SHEEP_TEXTURE = BACKGROUND_DIR + "sheep.png";
    
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
    public static final String SOUND_FIRE = SOUNDS_DIR + "fire.mp3";
    public static final String SOUND_FALL = SOUNDS_DIR + "fall.mp3";
    
    private static final String SOUND_SHEEP_NAME_FORMAT = SOUNDS_DIR + "sheep%02d.mp3";
    public static final int SOUND_SHEEP_COUNT = 7;
    
    public static String getSoundSheep(int index) {
        return String.format(ResourceNames.SOUND_SHEEP_NAME_FORMAT, index);
    }
}
