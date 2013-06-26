package com.turbogerm.hellhopper.game.character.graphics;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.GameUtils;

public final class ShieldEffectGraphics extends CharacterGraphicsBase {
    
    private static final float OFFSET_X = -0.125f;
    private static final float OFFSET_Y = -0.11f;
    private static final float WIDTH = 1.25f;
    private static final float HEIGHT = 1.8f;
    
    private static final float HIGH_ALPHA = 1.0f;
    private static final float LOW_ALPHA = 0.4f;
    private static final float MID_ALPHA = (HIGH_ALPHA + LOW_ALPHA) / 2.0f;
    private static final float ALPHA_HALF_RANGE = (HIGH_ALPHA - LOW_ALPHA) / 2.0f;
    private static final float ALPHA_CHANGE_PERIOD = 0.3f;
    
    private static final float SHIELD_EFFECT_FINISHING_DURATION = 3.0f;
    
    private final Sprite mSprite;
    
    private float mInternalAnimationTime;
    private float mShieldEffectRemaining;
    
    public ShieldEffectGraphics(AssetManager assetManager) {
        
        Texture texture = assetManager.get(ResourceNames.CHARACTER_SHIELD_EFFECT_TEXTURE);
        mSprite = new Sprite(texture);
        mSprite.setSize(WIDTH, HEIGHT);
    }
    
    @Override
    public void reset() {
        mInternalAnimationTime = 0.0f;
        mShieldEffectRemaining = 0.0f;
    }
    
    @Override
    public void update(float delta) {
        mInternalAnimationTime += delta;
    }
    
    @Override
    public void render(SpriteBatch batch, Vector2 characterPosition) {
        if (mShieldEffectRemaining <= 0.0f) {
            return;
        }
        
        if (mShieldEffectRemaining <= SHIELD_EFFECT_FINISHING_DURATION) {
            float alpha = MID_ALPHA + ALPHA_HALF_RANGE *
                    MathUtils.sinDeg(mInternalAnimationTime / ALPHA_CHANGE_PERIOD * 360.0f);
            GameUtils.setSpriteAlpha(mSprite, alpha);
        } else if (mSprite.getColor().a != 1.0f) {
            GameUtils.setSpriteAlpha(mSprite, 1.0f);
        }
        
        mSprite.setPosition(characterPosition.x + OFFSET_X, characterPosition.y + OFFSET_Y);
        mSprite.draw(batch);
    }
    
    public void setShieldEffectRemaining(float shieldEffectRemaining) {
        mShieldEffectRemaining = shieldEffectRemaining;
    }
}