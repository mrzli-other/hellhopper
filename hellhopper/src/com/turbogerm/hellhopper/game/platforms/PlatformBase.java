package com.turbogerm.hellhopper.game.platforms;

import java.util.Comparator;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.Pools;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.dataaccess.PlatformFeatureData;
import com.turbogerm.hellhopper.game.CollisionEffects;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.PlatformToCharCollisionData;
import com.turbogerm.hellhopper.game.character.GameCharacter;
import com.turbogerm.hellhopper.game.items.ItemBase;
import com.turbogerm.hellhopper.game.platforms.features.PlatformFeatureBase;
import com.turbogerm.hellhopper.game.platforms.features.PlatformFeatureFactory;
import com.turbogerm.hellhopper.game.platforms.features.PlatformModifier;
import com.turbogerm.hellhopper.game.platforms.movement.PlatformMovementBase;
import com.turbogerm.hellhopper.game.platforms.movement.PlatformMovementFactory;
import com.turbogerm.hellhopper.resources.ResourceNames;

public abstract class PlatformBase {
    
    private static final int ATTACHED_ITEMS_INITIAL_CAPACITY = 2;
    
    private static final float DEFAULT_COLOR_VALUE = 0.5f;
    
    private static final Comparator<PlatformFeatureBase> PLATFORM_FEATURE_RENDER_COMPARATOR;
    
    static {
        PLATFORM_FEATURE_RENDER_COMPARATOR = new Comparator<PlatformFeatureBase>() {
            @Override
            public int compare(PlatformFeatureBase f1, PlatformFeatureBase f2) {
                if (f1.getRenderPrecedence() < f2.getRenderPrecedence()) {
                    return -1;
                } else if (f1.getRenderPrecedence() > f2.getRenderPrecedence()) {
                    return 1;
                } else {
                    return 0;
                }
            }
        };
    }
    
    private final int mRiseSectionId;
    private final int mPlatformId;
    private final Vector2 mInitialPosition;
    
    protected final Sprite mSprite;
    protected final PlatformModifier mPlatformModifier;
    
    private final PlatformMovementBase mPlatformMovement;
    private final boolean mHasVerticalMovement;
    
    private final Array<PlatformFeatureBase> mPlatformFeatures;
    private final Array<PlatformFeatureBase> mPlatformFeaturesForRendering;
    
    private final Array<ItemBase> mAttachedItems;
    
    public PlatformBase(int riseSectionId, PlatformData platformData, int startStep,
            AssetManager assetManager) {
        
        mRiseSectionId = riseSectionId;
        mPlatformId = platformData.getId();
        
        mInitialPosition =  platformData.getPosition(startStep);
        
        TextureAtlas platformsAtlas = assetManager.get(ResourceNames.PLATFORMS_ATLAS);
        mSprite = platformsAtlas.createSprite(getImageName(platformData));
        mSprite.setBounds(mInitialPosition.x, mInitialPosition.y,
                PlatformData.PLATFORM_WIDTH, PlatformData.PLATFORM_HEIGHT);
        mPlatformModifier = new PlatformModifier();
        
        mPlatformMovement = PlatformMovementFactory.create(platformData.getMovementData(), mInitialPosition,
                assetManager);
        mHasVerticalMovement = mPlatformMovement.hasVerticalMovement();
        
        Array<PlatformFeatureBase> platformFeatures = getPlatformFeatures(platformData.getFeaturesData(), assetManager);
        mPlatformFeatures = platformFeatures != null ? platformFeatures : new Array<PlatformFeatureBase>(true, 0);
        
        mPlatformFeaturesForRendering = new Array<PlatformFeatureBase>(mPlatformFeatures);
        mPlatformFeaturesForRendering.sort(PLATFORM_FEATURE_RENDER_COMPARATOR);
        
        mAttachedItems = new Array<ItemBase>(ATTACHED_ITEMS_INITIAL_CAPACITY);
    }
    
    public final void update(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        // if platform can move up, additional platform to char collision must be checked
        if (mHasVerticalMovement && collisionData.isEnabled) {
            Vector2 position = getPosition();
            Vector2 p1 = Pools.obtainVector();
            p1.set(position.x, position.y + PlatformData.PLATFORM_HEIGHT);
            
            updateImpl(delta, c1, c2, collisionData);
            
            position = getPosition();
            Vector2 p2 = Pools.obtainVector();
            p2.set(position.x, position.y + PlatformData.PLATFORM_HEIGHT);
            
            // only check for collision when platform is going up, and character is going down
            if (p2.y > p1.y) {
                collisionData.isCollision = Intersector.intersectSegments(
                        c1, c2, p1, p2, collisionData.collisionPoint);
                if (collisionData.isCollision) {
                    collisionData.collisionPlatform = this;
                    collisionData.collisionPoint.y = p2.y;
                }
            }
            
            Pools.freeVector(p1);
            Pools.freeVector(p2);
        } else {
            updateImpl(delta, c1, c2, collisionData);
        }
    }
    
    protected void updateImpl(float delta, Vector2 c1, Vector2 c2, PlatformToCharCollisionData collisionData) {
        for (PlatformFeatureBase platformFeature : mPlatformFeatures) {
            platformFeature.update(delta);
        }
        
        if (isMovingInternal()) {
            mPlatformMovement.update(delta);
            Vector2 position = getPosition();
            for (ItemBase item : mAttachedItems) {
                item.updatePosition(position);
            }
        }
        
        updatePlatformModifier();
    }
    
    public final void render(SpriteBatch batch) {
        
        Color spriteColor = mPlatformModifier.spriteColor;
        for (PlatformFeatureBase platformFeature : mPlatformFeaturesForRendering) {
            platformFeature.render(batch, getPosition(), spriteColor);
        }
        
        if (mPlatformModifier.isPlatformVisible) {
            Vector2 position = getPosition();
            mSprite.setPosition(position.x, position.y);
            mSprite.setColor(mPlatformModifier.spriteColor);
            mSprite.draw(batch);
        }
        
        mPlatformMovement.render(batch, spriteColor.a);
    }
    
    public boolean isCollision(Vector2 c1, Vector2 c2, Vector2 intersection) {
        
        Vector2 position = getPosition();
        Vector2 p1 = Pools.obtainVector();
        Vector2 p2 = Pools.obtainVector();
        
        float pY = position.y + PlatformData.PLATFORM_HEIGHT;
        p1.set(position.x - GameCharacter.COLLISION_LINE_LENGTH, pY);
        p2.set(position.x + PlatformData.PLATFORM_WIDTH - GameCharacter.COLLISION_WIDTH_OFFSET, pY);
        
        boolean isIntersection = Intersector.intersectSegments(c1, c2, p1, p2, intersection);
        
        Pools.freeVector(p1);
        Pools.freeVector(p2);
        
        return isIntersection;
    }
    
    public boolean isActive(float visibleAreaPosition, float visiblePlatformsAreaPadding) {
        if (!isActiveInternal()) {
            return false;
        }
        
        Vector2 position = getPosition();
        float activeRangeLower = visibleAreaPosition - PlatformData.PLATFORM_HEIGHT - visiblePlatformsAreaPadding;
        float activeRangeUpper = visibleAreaPosition + GameArea.GAME_AREA_HEIGHT + visiblePlatformsAreaPadding;
        return position.y >= activeRangeLower && position.y <= activeRangeUpper;
    }
    
    public void applyEffect(int collisionEffect) {
        mPlatformMovement.applyEffect(collisionEffect);
        
        for (PlatformFeatureBase feature : mPlatformFeatures) {
            feature.applyEffect(collisionEffect);
        }
    }
    
    public void attachItem(ItemBase item) {
        item.setOffsetFromPlatform(mInitialPosition);
        mAttachedItems.add(item);
    }
    
    protected boolean isActiveInternal() {
        return true;
    }
    
    protected boolean isMovingInternal() {
        return true;
    }
    
    private void updatePlatformModifier() {
        mPlatformModifier.reset();
        
        mPlatformModifier.spriteColor.set(DEFAULT_COLOR_VALUE, DEFAULT_COLOR_VALUE, DEFAULT_COLOR_VALUE, 1.0f);
        
        mPlatformMovement.applyModifier(mPlatformModifier);
        
        for (PlatformFeatureBase feature : mPlatformFeatures) {
            feature.applyModifier(mPlatformModifier);
        }
    }
    
    public void getCollisionEffects(float collisionPointX, CollisionEffects collisionEffects) {
        mPlatformMovement.applyContact(collisionEffects);
        
        float relativeCollisionPointX = collisionPointX - getPosition().x;
        for (PlatformFeatureBase feature : mPlatformFeatures) {
            if (feature.isContact(relativeCollisionPointX)) {
                feature.applyContact(collisionEffects);
            }
        }
        
        collisionEffects.set(CollisionEffects.VISIBLE_ON_JUMP);
    }
    
    public int getPlatformId() {
        return mPlatformId;
    }
    
    public int getRiseSectionId() {
        return mRiseSectionId;
    }
    
    public Vector2 getPosition() {
        return mPlatformMovement.getPosition();
    }
    
    private static String getImageName(PlatformData platformData) {
        String platformType = platformData.getPlatformType();
        if (PlatformData.CRUMBLE_TYPE.equals(platformType)) {
            return ResourceNames.PLATFORM_CRUMBLE_IMAGE_NAME;
        } else {
            return ResourceNames.getRandomPlatformNormalImageName();
        }
    }
    
    private static Array<PlatformFeatureBase> getPlatformFeatures(Array<PlatformFeatureData> featuresData,
            AssetManager assetManager) {
        
        if (featuresData == null) {
            return null;
        }
        
        Array<PlatformFeatureBase> platformFeatures = new Array<PlatformFeatureBase>(true, featuresData.size);
        for (PlatformFeatureData featureData : featuresData) {
            PlatformFeatureBase platformFeature = PlatformFeatureFactory.create(featureData, assetManager);
            platformFeatures.add(platformFeature);
        }
        
        return platformFeatures;
    }
}
