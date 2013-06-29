package com.turbogerm.hellhopper.game.items;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFont.TextBounds;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.dataaccess.ItemData;
import com.turbogerm.hellhopper.game.GameAreaUtils;
import com.turbogerm.hellhopper.util.GameUtils;

public abstract class ItemBase {
    
    public static final int FART_EFFECT = 0;
    public static final int SHIELD_EFFECT = 1;
    public static final int HIGH_JUMP_EFFECT = 2;
    public static final int EXTRA_LIFE_EFFECT = 3;
    public static final int SCORE_EFFECT = 4;
    public static final int SIGNET_EFFECT = 5;
    
    private static final int EXISTING_STATE = 0;
    private static final int TEXT_STATE = 1;
    private static final int GONE_STATE = 2;
    
    private static final float TEXT_COUNTDOWN_DURATION = 3.0f;
    
    protected final Sprite mSprite;
    private final Vector2 mInitialPosition;
    private final Vector2 mOffsetFromPlatform;
    
    protected final Vector2 mPosition;
    protected final Vector2 mSize;
    protected final float mRadius;
    
    private int mItemState;
    
    private float mTextCountdown;
    
    private String mPickedUpText;
    private boolean mIsPickedUpTextBoundsDirty;
    private final Vector2 mPickedUpTextBounds;
    
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
        GameUtils.setSpriteOriginCenter(mSprite);
        
        mItemState = EXISTING_STATE;
        
        mTextCountdown = TEXT_COUNTDOWN_DURATION;
        
        mPickedUpTextBounds = new Vector2();
    }
    
    public final void update(float delta) {
        if (mItemState == EXISTING_STATE) {
            updateImpl(delta);
        } else if (mItemState == TEXT_STATE) {
            mTextCountdown -= delta;
            if (mTextCountdown <= 0.0f) {
                mTextCountdown = 0.0f;
                mItemState = GONE_STATE;
            }
        }
    }
    
    protected void updateImpl(float delta) {
    }
    
    public final void render(SpriteBatch batch) {
        if (mItemState == EXISTING_STATE) {
            mSprite.draw(batch);
        }
    }
    
    public final void renderText(SpriteBatch batch, float visibleAreaPosition, BitmapFont itemFont) {
        if (mItemState == TEXT_STATE) {
            if (mIsPickedUpTextBoundsDirty ) {
                TextBounds textBounds = itemFont.getBounds(mPickedUpText);
                mPickedUpTextBounds.set(textBounds.width, textBounds.height);
                mIsPickedUpTextBoundsDirty = false;
            }
            float alpha = mTextCountdown / TEXT_COUNTDOWN_DURATION;
            Color c = itemFont.getColor();
            itemFont.setColor(c.r, c.g, c.b, alpha);
            float textX = (mPosition.x + mSize.x / 2.0f) * GameAreaUtils.METER_TO_PIXEL -
                    mPickedUpTextBounds.x / 2.0f;
            float textY = (mPosition.y + mSize.y / 2.0f - visibleAreaPosition) * GameAreaUtils.METER_TO_PIXEL +
                    mPickedUpTextBounds.y / 2.0f;
            itemFont.draw(batch, mPickedUpText, textX, textY);
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
        mItemState = TEXT_STATE;
    }
    
    public boolean isCollision(Rectangle rect) {
        return false;
    }
    
    public boolean isExisting() {
        return mItemState == EXISTING_STATE;
    }
    
    public abstract int getEffect();
    
    public abstract Object getValue();
    
    public void setPickedUpText(String pickedUpText) {
        mPickedUpText = pickedUpText;
        mIsPickedUpTextBoundsDirty = true;
    }
}
