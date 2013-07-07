package com.turbogerm.hellhopper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
        
        boolean isPackTextures = true;
        if (isPackTextures) {
            packTextures();
        }
        
        InitData initData = new InitData();
        
        new LwjglApplication(new HellHopper(initData), cfg);
    }
    
    private static void packTextures() {
        Settings settings = new Settings();
        settings.pot = false;
        
        TexturePacker2.process(settings, "../hellhopper-android/assets/graphics/platforms",
                "../hellhopper-android/assets/graphics/packed", "platforms");
    }
}
