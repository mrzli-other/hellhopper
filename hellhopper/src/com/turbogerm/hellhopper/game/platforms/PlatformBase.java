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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformData;
import com.turbogerm.hellhopper.util.Pools;

public abstract class PlatformBase {
    
    protected final Texture mTexture;
    protected final Vector2 mInitialPosition;
    
    public PlatformBase(Vector2 initialPosition, String texturePath, AssetManager assetManager) {
        mTexture = assetManager.get(texturePath);
        mInitialPosition = initialPosition;
    }
    
    public void update(float delta) {
    }
    
    public void render(SpriteBatch batch, float visibleAreaPositions) {
        Vector2 position = getPosition();
        batch.draw(mTexture, position.x, position.y - visibleAreaPositions,
                PlatformData.PLATFORM_WIDTH, PlatformData.PLATFORM_HEIGHT);
    }
    
    public boolean isVisible(float visibleAreaPositions) {
        Vector2 position = getPosition();
        return position.y + PlatformData.PLATFORM_HEIGHT >= visibleAreaPositions &&
                position.y <= visibleAreaPositions + GameArea.GAME_AREA_HEIGHT;
    }
    
    public boolean isCollision(Vector2 c1, Vector2 c2, Vector2 intersection) {
        
        Vector2 position = getPosition();
        Vector2 p1 = Pools.obtainVector();
        Vector2 p2 = Pools.obtainVector();
        
        float pY = position.y + PlatformData.PLATFORM_HEIGHT;
        p1.set(position.x - GameArea.CHARACTER_WIDTH, pY);
        p2.set(position.x + PlatformData.PLATFORM_WIDTH, pY);
        
        boolean isIntersection = Intersector.intersectSegments(c1, c2, p1, p2, intersection);
        
        Pools.freeVector(p1);
        Pools.freeVector(p2);
        
        return isIntersection;
    }
    
    public Vector2 getPosition() {
        return mInitialPosition;
    }
}
