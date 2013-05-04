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
package com.turbogerm.hellhopper.game.platforms;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;

final class FlamePlatform extends PlatformBase {
    
    private static final float FLAME_X_OFFSET = 0.0f;
    private static final float FLAME_Y_OFFSET = PlatformData.PLATFORM_HEIGHT;
    
    private final ParticleEffect mFlameEffect;
    
    public FlamePlatform(PlatformData platformData, int startStep, AssetManager assetManager) {
        super(platformData, platformData.getPlatformPositions(startStep), ResourceNames
                .getRandomPlatformNormalTexture(), assetManager);
        
        mFlameEffect = new ParticleEffect((ParticleEffect) assetManager.get(ResourceNames.PARTICLE_PLATFORM_FLAME));
    }
    
    @Override
    protected void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        super.updateImpl(delta, c1, c2, collisionData);
    }
    
    @Override
    public void render(SpriteBatch batch, float delta) {
        Vector2 position = getPosition();
        mFlameEffect.setPosition(position.x + FLAME_X_OFFSET, position.y + FLAME_Y_OFFSET);
        mFlameEffect.draw(batch, delta);
        
        super.render(batch, delta);
    }
}
