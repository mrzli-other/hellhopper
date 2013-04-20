package com.turbogerm.hellhopper;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
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
        
        InitData initData = new InitData();
        
        new LwjglApplication(new HellHopper(initData), cfg);
    }
}
