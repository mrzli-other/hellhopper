package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.game.GameAreaUtils;

public abstract class ItemBase {
    
    public static final int FART_EFFECT = 0;
    public static final int SHIELD_EFFECT = 1;
    public static final int HIGH_JUMP_EFFECT = 2;
    public static final int EXTRA_LIFE_EFFECT = 3;
    public static final int SCORE_EFFECT = 4;
    public static final int RUBY_EFFECT = 5;
    
    protected final Sprite mSprite;
    private final Vector2 mInitialPosition;
    private final Vector2 mOffsetFromPlatform;
    
    protected final Vector2 mPosition;
    protected final Vector2 mSize;
    protected final float mRadius;
    
    private boolean mIsExisting;
    
    public ItemBase(ItemData itemData, String texturePath, int startStep, AssetManager assetManager) {
        
        Texture texture = assetManager.get(texturePath);
        
        mInitialPosition = itemData.getPosition(startStep);
        mOffsetFromPlatform = new Vector2();
        
        mPosition = new Vector2(mInitialPosition);
        mSize = new Vector2(
                texture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                texture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        mRadius = mSize.x / 2.0f;
        
        mSprite = new Sprite(texture);
        mSprite.setSize(mSize.x, mSize.y);
        mSprite.setOrigin(mSize.x / 2.0f, mSize.y / 2.0f);
        
        mIsExisting = true;
    }
    
    public final void update(float delta) {
        if (mIsExisting) {
            updateImpl(delta);
        }
    }
    
    protected void updateImpl(float delta) {
    }
    
    public final void render(SpriteBatch batch) {
        if (mIsExisting) {
            mSprite.draw(batch);
        }
    }
    
    public void setOffsetFromPlatform(Vector2 platformInitialPosition) {
        mOffsetFromPlatform.set(
                mInitialPosition.x - platformInitialPosition.x,
                mInitialPosition.y - platformInitialPosition.y);
    }
    
    public final void updatePosition(Vector2 platformPosition) {
        mPosition.set(
                platformPosition.x + mOffsetFromPlatform.x,
                platformPosition.y + mOffsetFromPlatform.y);
        
        updatePositionImpl();
    }
    
    protected abstract void updatePositionImpl();
    
    public void pickUp() {
        mIsExisting = false;
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
    
    public boolean isExisting() {
        return mIsExisting;
    }
    
    public abstract int getEffect();
    
    public abstract Object getValue();
}
