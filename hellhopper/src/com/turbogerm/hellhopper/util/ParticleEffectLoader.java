// taken from http://stackoverflow.com/questions/12261439/assetmanager-particleeffectloader-of-libgdx-android
package com.turbogerm.hellhopper.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.utils.Array;

public class ParticleEffectLoader extends
        SynchronousAssetLoader<ParticleEffect, ParticleEffectLoader.ParticleEffectParameter> {
    
    public ParticleEffectLoader(FileHandleResolver resolver) {
        super(resolver);
    }
    
    @Override
    public ParticleEffect load(AssetManager assetManager, String fileName,
            ParticleEffectParameter parameter) {
        ParticleEffect effect = new ParticleEffect();
        FileHandle effectFile = resolve(fileName);
        FileHandle imgDir = effectFile.parent();
        effect.load(effectFile, imgDir);
        return effect;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName,
            ParticleEffectParameter parameter) {
        return null;
    }
    
    static public class ParticleEffectParameter extends AssetLoaderParameters<ParticleEffect> {
        public ParticleEffectParameter() {
        }
    }
    
}
