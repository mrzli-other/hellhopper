/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2.Settings;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.init.InitData;

public class Main {
    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Hell Hopper";
        cfg.useGL20 = true;
        cfg.height = 800;
        cfg.width = 600;
        cfg.resizable = true;
        
        boolean isPackTextures = false;
        if (isPackTextures) {
            packTextures();
        }
        
        InitData initData = new InitData();
        
        new LwjglApplication(new HellJump(initData), cfg);
    }
    
    private static void packTextures() {
        Settings settings = new Settings();
        settings.pot = false;
        settings.maxWidth = 1024;
        settings.maxHeight = 1024;
        settings.filterMin = TextureFilter.Linear;
        settings.filterMag = TextureFilter.Linear;
        
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/background",
                "../helljump-android/assets/graphics/packed", "background");
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/platforms",
                "../helljump-android/assets/graphics/packed", "platforms");
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/character",
                "../helljump-android/assets/graphics/packed", "character");
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/enemies",
                "../helljump-android/assets/graphics/packed", "enemies");
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/items",
                "../helljump-android/assets/graphics/packed", "items");
        TexturePacker2.process(settings, "../helljump-android/assets/graphics/raw/gui",
                "../helljump-android/assets/graphics/packed", "gui");
    }
}
