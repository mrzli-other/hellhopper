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
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformData;

public abstract class PlatformBase {
    
    protected final Texture mTexture;
    protected final Vector2 mInitialPosition;
    
    private static final Rectangle mPlatformCollisionRect;
    private static final Vector2 mPlatformCollisionLineStart;
    private static final Vector2 mPlatformCollisionLineEnd;
    
    static {
        mPlatformCollisionRect = new Rectangle();
        mPlatformCollisionLineStart = new Vector2();
        mPlatformCollisionLineEnd = new Vector2();
    }
    
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
    
    public boolean isCollision(
            Rectangle characterCollisionRect,
            Vector2 characterCollisionLineStart,
            Vector2 characterCollisionLineEnd,
            Vector2 collisionPoint) {
        
        Vector2 position = getPosition();
        setCharacterToPlatformCollisionVariables(position);
        
        return isCollisionInternal(characterCollisionRect, characterCollisionLineStart, characterCollisionLineEnd,
                collisionPoint);
    }
    
    private static void setCharacterToPlatformCollisionVariables(Vector2 platformPosition) {
        mPlatformCollisionRect.set(platformPosition.x - GameArea.CHARACTER_WIDTH, platformPosition.y,
                PlatformData.PLATFORM_WIDTH + GameArea.CHARACTER_WIDTH, PlatformData.PLATFORM_HEIGHT);
        float platformCollisionY = platformPosition.y + PlatformData.PLATFORM_HEIGHT;
        mPlatformCollisionLineStart.set(platformPosition.x - GameArea.CHARACTER_WIDTH, platformCollisionY);
        mPlatformCollisionLineEnd.set(platformPosition.x + PlatformData.PLATFORM_WIDTH, platformCollisionY);
    }
    
    protected static boolean isCollisionInternal(
            Rectangle characterCollisionRect,
            Vector2 characterCollisionLineStart,
            Vector2 characterCollisionLineEnd,
            Vector2 collisionPoint) {
        
        return Intersector.intersectRectangles(characterCollisionRect, mPlatformCollisionRect) &&
                Intersector.intersectSegments(
                        characterCollisionLineStart, characterCollisionLineEnd,
                        mPlatformCollisionLineStart, mPlatformCollisionLineEnd,
                        collisionPoint);
    }
    
    public Vector2 getPosition() {
        return mInitialPosition;
    }
}
