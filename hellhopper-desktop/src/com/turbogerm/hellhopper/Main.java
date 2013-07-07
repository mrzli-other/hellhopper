package com.turbogerm.hellhopper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.init.InitData;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Hell Hopper";
        cfg.useGL20 = true;
        cfg.height = 800;
        cfg.width = 450;
        cfg.resizable = false;
        
        boolean isPackTextures = false;
        if (isPackTextures) {
            packTextures();
        }
        
        InitData initData = new InitData();
        
        new LwjglApplication(new HellHopper(initData), cfg);
    }
    
    private static void packTextures() {
        Settings settings = new Settings();
        settings.pot = false;
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.filterMin = TextureFilter.Linear;
        settings.filterMag = TextureFilter.Linear;
        
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/background",
                "../hellhopper-android/assets/graphics/packed", "background");
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/platforms",
                "../hellhopper-android/assets/graphics/packed", "platforms");
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/character",
                "../hellhopper-android/assets/graphics/packed", "character");
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/enemies",
                "../hellhopper-android/assets/graphics/packed", "enemies");
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/items",
                "../hellhopper-android/assets/graphics/packed", "items");
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/raw/gui",
                "../hellhopper-android/assets/graphics/packed", "gui");
    }
}
