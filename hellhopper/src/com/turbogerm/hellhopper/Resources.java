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
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turbogerm.hellhopper.util.ParticleEffectLoader;

public final class Resources {
    
    private final AssetManager mAssetManager;
    private final Skin mGuiSkin;
    
    public Resources() {
        mAssetManager = new AssetManager();
        mAssetManager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        
        TextureParameter textureParameterLinear = new TextureParameter();
        textureParameterLinear.minFilter = TextureFilter.Linear;
        textureParameterLinear.magFilter = TextureFilter.Linear;
        textureParameterLinear.genMipMaps = false;
        
        TextureParameter textureParameterNearest = new TextureParameter();
        textureParameterNearest.minFilter = TextureFilter.Nearest;
        textureParameterNearest.magFilter = TextureFilter.Nearest;
        textureParameterNearest.genMipMaps = false;
        
        // GUI
        mAssetManager.load(ResourceNames.GUI_BLACK_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BACKGROUND_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_START_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_START_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_HIGH_SCORE_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_INFO_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_INFO_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PAUSE_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PAUSE_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PLAY_UP_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PLAY_DOWN_TEXTURE, Texture.class, textureParameterLinear);
        
        mAssetManager.load(ResourceNames.GAME_END_LINE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_LINE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_LINE_ABOVE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_BOX_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GAME_POSITION_SCROLL_END_LINE_TEXTURE, Texture.class, textureParameterLinear);
        
        // CHARACTER
        mAssetManager.load(ResourceNames.CHARACTER_BODY_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.CHARACTER_HEAD_TEXTURE, Texture.class, textureParameterLinear);
        
        for (int i = 0; i < ResourceNames.CHARACTER_EYES_TEXTURE_COUNT; i++) {
            mAssetManager.load(ResourceNames.getCharacterEyesTexture(i), Texture.class, textureParameterLinear);
        }
        
        // PLATFORMS
        for (int i = 0; i < ResourceNames.PLATFORM_NORMAL_TEXTURE_COUNT; i++) {
            mAssetManager.load(ResourceNames.getPlatformNormalTexture(i), Texture.class, textureParameterLinear);
        }
        
        mAssetManager.load(ResourceNames.PLATFORM_CRUMBLE_TEXTURE, Texture.class, textureParameterLinear);
        
        mAssetManager.load(ResourceNames.PLATFORM_FIRE_TEXTURE_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_NORMAL_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_ENGINE_REPOSITION_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_LOW_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_LOW_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_MEDIUM_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_MEDIUM_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_CRATER_HIGH_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.PLATFORM_JUMP_BOOST_DISCHARGE_HIGH_TEXTURE, Texture.class, textureParameterLinear);
        
        // ENEMIES
        mAssetManager.load(ResourceNames.ENEMY_SAW_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.ENEMY_IMP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.ENEMY_LOCO_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.ENEMY_KNIGHT_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.ENEMY_EVIL_TWIN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.ENEMY_COOL_CLERK_TEXTURE, Texture.class, textureParameterNearest);
        
        // BACKGROUND
        mAssetManager.load(ResourceNames.BACKGROUND_TEXTURE, Texture.class, textureParameterNearest);
        
        for (int i = 0; i < ResourceNames.BACKGROUND_CLOUD_TEXTURE_COUNT; i++) {
            mAssetManager.load(ResourceNames.getBackgroundCloudTexture(i), Texture.class, textureParameterNearest);
        }
        
        for (int i = 0; i < ResourceNames.BACKGROUND_ROCK_TEXTURE_COUNT; i++) {
            mAssetManager.load(ResourceNames.getBackgroundRockTexture(i), Texture.class, textureParameterNearest);
        }
        
        // PARTICLES
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_NORMAL, ParticleEffect.class);
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_REPOSITION, ParticleEffect.class);
        
        // SOUNDS
        mAssetManager.load(ResourceNames.SOUND_JUMP, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_JUMP_BOOST, Sound.class);
        
        mAssetManager.finishLoading();
        
        mGuiSkin = new Skin(Gdx.files.internal(ResourceNames.GUI_SKIN));
        mGuiSkin.getFont("default-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("small-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("medium-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("large-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xxl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        mGuiSkin.getFont("xxxl-font").getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
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
}
