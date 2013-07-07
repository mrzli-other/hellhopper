package com.turbogerm.hellhopper.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader.TextureParameter;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.turbogerm.germlibrary.util.ParticleEffectLoader;
import com.turbogerm.germlibrary.util.ParticleEffectLoader.ParticleEffectParameter;

public final class Resources {
    
    private final AssetManager mAssetManager;
    private final Skin mGuiSkin;
    private final BitmapFont mItemFont;
    
    public Resources() {
        mAssetManager = new AssetManager();
        mAssetManager.setLoader(ParticleEffect.class, new ParticleEffectLoader(new InternalFileHandleResolver()));
        
        TextureParameter textureParameterLinear = new TextureParameter();
        textureParameterLinear.minFilter = TextureFilter.Linear;
        textureParameterLinear.magFilter = TextureFilter.Linear;
        textureParameterLinear.genMipMaps = true;
        
        TextureParameter textureParameterNearest = new TextureParameter();
        textureParameterNearest.minFilter = TextureFilter.Nearest;
        textureParameterNearest.magFilter = TextureFilter.Nearest;
        textureParameterNearest.genMipMaps = false;
        
        // GENERAL
        mAssetManager.load(ResourceNames.GUI_GENERAL_BLACK_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_GENERAL_WHITE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_GENERAL_TURBO_GERM_LOGO_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_GENERAL_LIBGDX_LOGO_TEXTURE, Texture.class, textureParameterNearest);
        
        // GUI
        mAssetManager.load(ResourceNames.GUI_SPLASH_TITLE_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_SPLASH_PLATFORM_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_TITLE_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_START_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_START_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_HIGH_SCORE_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_HIGH_SCORE_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_CREDITS_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_MAIN_MENU_BUTTON_CREDITS_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_PAUSE_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_PAUSE_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_PLAY_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_PLAY_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_LIVES_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_END_LINE_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_PLAY_POSITION_SCROLL_LINE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PLAY_POSITION_SCROLL_LINE_ABOVE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PLAY_POSITION_SCROLL_BOX_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_PLAY_POSITION_SCROLL_END_LINE_TEXTURE, Texture.class, textureParameterLinear);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_BUTTON_BACK_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_UP_TEXTURE, Texture.class, textureParameterNearest);
        mAssetManager.load(ResourceNames.GUI_BUTTON_CONTINUE_DOWN_TEXTURE, Texture.class, textureParameterNearest);
        
        mAssetManager.load(ResourceNames.PLATFORMS_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.CHARACTER_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.ENEMIES_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.ITEMS_ATLAS, TextureAtlas.class);
        mAssetManager.load(ResourceNames.BACKGROUND_ATLAS, TextureAtlas.class);
        
        // PARTICLES
        ParticleEffectParameter particleEffectParameter = new ParticleEffectParameter();
        particleEffectParameter.assetManager = mAssetManager;
        particleEffectParameter.atlasName = ResourceNames.PLATFORMS_ATLAS;
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_NORMAL, ParticleEffect.class, particleEffectParameter);
        mAssetManager.load(ResourceNames.PARTICLE_ENGINE_REPOSITION, ParticleEffect.class, particleEffectParameter);
        
        // SOUNDS
        mAssetManager.load(ResourceNames.SOUND_JUMP, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_JUMP_BOOST, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FART, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_COIN, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_ITEM, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_ENEMY, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_SAW, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FIRE, Sound.class);
        mAssetManager.load(ResourceNames.SOUND_FALL, Sound.class);
        for (int i = 0; i < ResourceNames.SOUND_SHEEP_COUNT; i++) {
            mAssetManager.load(ResourceNames.getSoundSheep(i), Sound.class);
        }
        
        mAssetManager.finishLoading();
        
        // FONTS
        mItemFont = new BitmapFont(Gdx.files.internal(ResourceNames.FONT_ITEM), false);
        mItemFont.setUseIntegerPositions(false);
        mItemFont.getRegion().getTexture().setFilter(TextureFilter.Linear, TextureFilter.Linear);
        
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
    
    public BitmapFont getItemFont() {
        return mItemFont;
    }
    
    public void dispose() {
        mGuiSkin.dispose();
        mAssetManager.dispose();
        mItemFont.dispose();
    }
}
