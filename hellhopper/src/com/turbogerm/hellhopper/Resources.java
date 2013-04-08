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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public final class Resources {
    
    private AssetManager mAssetManager;
    private Skin mGuiSkin;
    // private ObjectMap<String, BitmapFont> mFonts;
    
    public Resources() {
        mAssetManager = new AssetManager();
        
        TextureParameter textureParameter = new TextureParameter();
        textureParameter.minFilter = TextureFilter.Linear;
        textureParameter.magFilter = TextureFilter.Linear;
        textureParameter.genMipMaps = true;
        
        mAssetManager.load(ResourceNames.GUI_BLACK_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BACKGROUND_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_GAME_AREA_BORDER_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_GAME_AREA_BACKGROUND_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_START_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_START_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_INFO_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_INFO_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_LEFT_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_LEFT_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_RIGHT_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_RIGHT_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DOWN_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DOWN_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_ROTATE_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_ROTATE_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_KEYBOARD_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_KEYBOARD_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_KEYBOARD_LEFT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_KEYBOARD_RIGHT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DRAG_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DRAG_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DRAG_LEFT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_DRAG_RIGHT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PAUSE_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PAUSE_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PLAY_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PLAY_DOWN_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.load(ResourceNames.SQUARES_CYAN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_PURPLE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_ORANGE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_BLUE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_RED_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_GREEN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_YELLOW_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_EMPTY_1_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.SQUARES_EMPTY_2_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.finishLoading();
        
        mGuiSkin = new Skin(Gdx.files.internal(ResourceNames.GUI_SKIN));
        
        // mFonts = new ObjectMap<String, BitmapFont>();
        // final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        // FreeTypeFontGenerator generator = new
        // FreeTypeFontGenerator(Gdx.files.internal(ResourceNames.ARIAL_BOLD_FONT));
        // mFonts.put("default", generator.generateFont(14, characters, false));
        // mFonts.put("small", generator.generateFont(12, characters, false));
        // mFonts.put("medium", generator.generateFont(18, characters, false));
        // mFonts.put("large", generator.generateFont(24, characters, false));
        // mFonts.put("xl", generator.generateFont(32, characters, false));
        // mFonts.put("xxl", generator.generateFont(40, characters, false));
        // mFonts.put("xxxl", generator.generateFont(48, characters, false));
        // generator.dispose();
    }
    
    public Skin getGuiSkin() {
        return mGuiSkin;
    }
    
    // public BitmapFont getFont(String name) {
    // return mFonts.get(name);
    // }
    
    public AssetManager getAssetManager() {
        return mAssetManager;
    }
    
    public void dispose() {
        mGuiSkin.dispose();
        mAssetManager.dispose();
    }
}
