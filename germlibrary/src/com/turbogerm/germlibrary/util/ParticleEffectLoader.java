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
// taken from http://stackoverflow.com/questions/12261439/assetmanager-particleeffectloader-of-libgdx-android
package com.turbogerm.germlibrary.util;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
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
        if (parameter.atlasName != null) {
            effect.load(effectFile, (TextureAtlas) parameter.assetManager.get(parameter.atlasName));
        } else {
            FileHandle imgDir = effectFile.parent();
            effect.load(effectFile, imgDir);
        }
        return effect;
    }
    
    @SuppressWarnings("rawtypes")
    @Override
    public Array<AssetDescriptor> getDependencies(String fileName,
            ParticleEffectParameter parameter) {
        return null;
    }
    
    static public class ParticleEffectParameter extends AssetLoaderParameters<ParticleEffect> {
        public AssetManager assetManager = null;
        public String atlasName = null;
    }
}
