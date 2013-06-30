package com.turbogerm.hellhopper.resources;

import com.badlogic.gdx.math.MathUtils;

public final class ResourceNames {
    
    private static final String GUI_DIR = "gui/";
    public static final String GUI_SKIN = GUI_DIR + "uiskin.json";
    
    private static final String GUI_SPLASH_DIR = GUI_DIR + "splash/";
    public static final String GUI_SPLASH_BLACK_TEXTURE = GUI_SPLASH_DIR + "black.png";
    public static final String GUI_SPLASH_TITLE_TEXTURE = GUI_SPLASH_DIR + "title.png";
    public static final String GUI_SPLASH_PLATFORM_TEXTURE = GUI_SPLASH_DIR + "platform.png";
    
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
    
    private static final String FONTS_DIR = GUI_DIR + "fonts/";
    public static final String FONT_ITEM = FONTS_DIR + "arial-bold-18.fnt";
    
    private static final String DATA_DIR = "data/";
    private static final String RISE_SECTIONS_DIR = DATA_DIR + "risesections/";
    public static final String RISE_SECTIONS_DATA = RISE_SECTIONS_DIR + "risesections.txt";
    public static final String RISE_SECTIONS_METADATA = RISE_SECTIONS_DIR + "risesectionsmeta.xml";
    
    private static final String GAME_DIR = "game/";
    public static final String GAME_END_LINE_TEXTURE = GAME_DIR + "endline.png";
    public static final String GAME_POSITION_SCROLL_LINE_TEXTURE = GAME_DIR + "positionscrollline.png";
    public static final String GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE = GAME_DIR + "positionscrolllineabove.png";
    public static final String GAME_POSITION_SCROLL_BOX_TEXTURE = GAME_DIR + "positionscrollbox.png";
    public static final String GAME_POSITION_SCROLL_END_LINE_TEXTURE = GAME_DIR + "positionscrollendline.png";
    
    private static final String CHARACTER_DIR = GAME_DIR + "character/";
    public static final String CHARACTER_BODY_TEXTURE = CHARACTER_DIR + "body.png";
    public static final String CHARACTER_HEAD_TEXTURE = CHARACTER_DIR + "head.png";
    public static final int CHARACTER_EYES_NORMAL_TEXTURE_COUNT = 2;
    private static final String CHARACTER_EYES_NORMAL_TEXTURE_NAME_FORMAT = CHARACTER_DIR + "eyesnormal%02d.png";
    public static final String CHARACTER_EYES_STUNNED_TEXTURE = CHARACTER_DIR + "eyesstunned.png";
    public static final String CHARACTER_EYES_FART_OPENED_TEXTURE = CHARACTER_DIR + "eyesfartopened.png";
    public static final String CHARACTER_EYES_FART_CLOSED_TEXTURE = CHARACTER_DIR + "eyesfartclosed.png";
    public static final String CHARACTER_MOUTH_SMILE_TEXTURE = CHARACTER_DIR + "mouthsmile.png";
    public static final String CHARACTER_SHIELD_EFFECT_TEXTURE = CHARACTER_DIR + "shieldeffect.png";
    public static final String CHARACTER_FART_DISCHARGE_TEXTURE = CHARACTER_DIR + "fartdischarge.png";
    
    private static final String PLATFORMS_DIR = GAME_DIR + "platforms/";
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
    
    private static final String ENEMIES_DIR = GAME_DIR + "enemies/";
    public static final String ENEMY_SAW_TEXTURE = ENEMIES_DIR + "saw.png";
    public static final String ENEMY_IMP_TEXTURE = ENEMIES_DIR + "imp.png";
    public static final String ENEMY_LOCO_TEXTURE = ENEMIES_DIR + "loco.png";
    public static final String ENEMY_KNIGHT_TEXTURE = ENEMIES_DIR + "knight.png";
    public static final String ENEMY_EVIL_TWIN_TEXTURE = ENEMIES_DIR + "eviltwin.png";
    public static final String ENEMY_COOL_CLERK_TEXTURE = ENEMIES_DIR + "coolclerk.png";
    
    private static final String ITEMS_DIR = GAME_DIR + "items/";
    public static final String ITEM_BEANS_TEXTURE = ITEMS_DIR + "beans.png";
    public static final String ITEM_SHIELD_TEXTURE = ITEMS_DIR + "shield.png";
    public static final String ITEM_JUMP_SUIT_TEXTURE = ITEMS_DIR + "jumpsuit.png";
    public static final String ITEM_LIFE_TEXTURE = ITEMS_DIR + "life.png";
    public static final String ITEM_COIN_COPPER_TEXTURE = ITEMS_DIR + "coincopper.png";
    public static final String ITEM_COIN_SILVER_TEXTURE = ITEMS_DIR + "coinsilver.png";
    public static final String ITEM_COIN_GOLD_TEXTURE = ITEMS_DIR + "coingold.png";
    public static final String ITEM_SIGNET_TEXTURE = ITEMS_DIR + "signet.png";
    public static final String ITEM_BLACK_BOX_TEXTURE = ITEMS_DIR + "blackbox.png";
    
    private static final String BACKGROUND_DIR = GAME_DIR + "background/";
    public static final String BACKGROUND_TEXTURE = BACKGROUND_DIR + "background.png";
    public static final String BACKGROUND_END_SKY_TEXTURE = BACKGROUND_DIR + "endsky.png";
    public static final String BACKGROUND_END_SUN_TEXTURE = BACKGROUND_DIR + "endsun.png";
    public static final String BACKGROUND_END_CLOUDS_TEXTURE = BACKGROUND_DIR + "endclouds.png";
    public static final String BACKGROUND_END_MOUNTAINS_TEXTURE = BACKGROUND_DIR + "endmountains.png";
    public static final String BACKGROUND_END_GROUND_TEXTURE = BACKGROUND_DIR + "endground.png";
    public static final String BACKGROUND_END_SHEEP_TEXTURE = BACKGROUND_DIR + "sheep.png";
    private static final String BACKGROUND_CLOUD_TEXTURE_NAME_FORMAT = BACKGROUND_DIR + "cloud%02d.png";
    public static final int BACKGROUND_CLOUD_TEXTURE_COUNT = 3;
    private static final String BACKGROUND_ROCK_TEXTURE_NAME_FORMAT = BACKGROUND_DIR + "rock%02d.png";
    public static final int BACKGROUND_ROCK_TEXTURE_COUNT = 4;
    
    private static final String PARTICLES_DIR = "particles/";
    public static final String PARTICLE_ENGINE_NORMAL = PARTICLES_DIR + "enginenormal.p";
    public static final String PARTICLE_ENGINE_REPOSITION = PARTICLES_DIR + "enginereposition.p";
    
    private static final String SOUNDS_DIR = "sounds/";
    public static final String SOUND_JUMP = SOUNDS_DIR + "jump.mp3";
    public static final String SOUND_JUMP_BOOST = SOUNDS_DIR + "jumpboost.mp3";
    
    public static String getRiseSectionPath(String name) {
        return String.format("%s%s.xml", RISE_SECTIONS_DIR, name);
    }
    
    public static String getCharacterEyesNormalTexture(int index) {
        return String.format(ResourceNames.CHARACTER_EYES_NORMAL_TEXTURE_NAME_FORMAT, index);
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
