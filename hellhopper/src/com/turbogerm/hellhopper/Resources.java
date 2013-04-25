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
import com.turbogerm.hellhopper.graphics.DistanceFieldShader;

public final class Resources {
    
    private AssetManager mAssetManager;
    private Skin mGuiSkin;
    
    private DistanceFieldShader mDistanceFieldShader;
    
    public Resources() {
        mAssetManager = new AssetManager();
        
        TextureParameter textureParameter = new TextureParameter();
        textureParameter.minFilter = TextureFilter.Nearest;
        textureParameter.magFilter = TextureFilter.Nearest;
        textureParameter.genMipMaps = false;
        
        mAssetManager.load(ResourceNames.GUI_BLACK_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_BACKGROUND_TEXTURE, Texture.class, textureParameter);
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
        mAssetManager.load(ResourceNames.GUI_PAUSE_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PAUSE_DOWN_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PLAY_UP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GUI_PLAY_DOWN_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.load(ResourceNames.GAME_CHARACTER_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GAME_END_LINE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_END_LINE_TEXTURE, Texture.class, textureParameter);
        
        for (int i = 0; i < ResourceNames.PLATFORM_NORMAL_TEXTURE_COUNT; i++) {
            mAssetManager.load(ResourceNames.getPlatformNormalTexture(i), Texture.class, textureParameter);
        }
        
        mAssetManager.load(ResourceNames.PLATFORM_CRUMBLE_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_TOP_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_BOTTOM_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_LEFT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_RIGHT_TEXTURE, Texture.class, textureParameter);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_CIRCULAR_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.load(ResourceNames.OBJECT_LAVA_ROCK_TEXTURE, Texture.class, textureParameter);
        
        mAssetManager.finishLoading();
        
        mGuiSkin = new Skin(Gdx.files.internal(ResourceNames.GUI_SKIN));
    }
    
    public Skin getGuiSkin() {
        return mGuiSkin;
    }
    
    public AssetManager getAssetManager() {
        return mAssetManager;
    }
    
    public void dispose() {
        mGuiSkin.dispose();
        mAssetManager.dispose();
    }
    
    public DistanceFieldShader getDistanceFieldShader() {
        return mDistanceFieldShader;
    }
}
