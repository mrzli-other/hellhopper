package com.turbogerm.hellhopper.game;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.util.ColorPositionPair;
import com.turbogerm.germlibrary.util.Pools;
import com.turbogerm.germlibrary.util.SpectrumColorInterpolator;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.dataaccess.PlatformData;
import com.turbogerm.hellhopper.debug.DebugData;
import com.turbogerm.hellhopper.game.background.EndBackgroundScene;
import com.turbogerm.hellhopper.game.character.GameCharacter;
import com.turbogerm.hellhopper.game.enemies.EnemyBase;
import com.turbogerm.hellhopper.game.generator.RiseGenerator;
import com.turbogerm.hellhopper.game.items.ItemBase;
import com.turbogerm.hellhopper.game.platforms.PlatformBase;
import com.turbogerm.hellhopper.resources.ResourceNames;

public final class GameArea {
    
    public static final float GAME_AREA_WIDTH = HellHopper.VIEWPORT_WIDTH * GameAreaUtils.PIXEL_TO_METER;
    public static final float GAME_AREA_HEIGHT = HellHopper.VIEWPORT_HEIGHT * GameAreaUtils.PIXEL_TO_METER;
    
    public static final float GAME_AREA_WIDTH_OFFSETS = GAME_AREA_WIDTH / GameAreaUtils.OFFSET_WIDTH;
    
    private static final float CHARACTER_POSITION_AREA_FRACTION = 0.4f;
    private static final float VISIBLE_AREA_MINIMUM_DISTANCE_TO_RISE = 2.0f;
    
    private static final float DEFAULT_HORIZONTAL_SPEED = 10.0f;
    private static final float ACCELEROMETER_SPEED_MULTIPLIER = 3.75f;
    
    private static final float END_BACKGROUND_APPEARANCE_DISTANCE_FROM_END =
            500.0f * GameAreaUtils.PIXEL_TO_METER + GAME_AREA_HEIGHT;
    
    private static final int ACTIVE_RISE_SECTIONS_INITIAL_CAPACITY = 5;
    private static final int VISIBLE_PLATFORMS_INITIAL_CAPACITY = 50;
    private static final int VISIBLE_ENEMIES_INITIAL_CAPACITY = 10;
    private static final int VISIBLE_ITEMS_INITIAL_CAPACITY = 5;
    
    private static final float VISIBLE_PLATFORMS_AREA_PADDING = 2.0f;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final DebugData mDebugData;
    private final BitmapFont mItemFont;
    
    private Rise mRise;
    private float mRiseHeight;
    
    private int mRiseScore;
    
    private float mVisibleAreaPosition;
    private final GameCharacter mCharacter;
    private final PlatformToCharCollisionData mPlatformToCharCollisionData;
    
    private final Array<RiseSection> mActiveRiseSections;
    private final Array<PlatformBase> mVisiblePlatforms;
    private final Array<EnemyBase> mVisibleEnemies;
    private final Array<ItemBase> mVisibleItems;
    
    private boolean mIsGameOver;
    
    private final Texture mBackgroundTexture;
    private final Vector2 mBackgroundTextureSize;
    
    private final EndBackgroundScene mEndBackgroundScene;
    
    private final SpectrumColorInterpolator mSpectrumColorInterpolator;
    private final Color mBackgroundColor;
    
    public GameArea(AssetManager assetManager, BitmapFont itemFont) {
        
        mAssetManager = assetManager;
        mBatch = new SpriteBatch();
        mDebugData = new DebugData();
        mItemFont = itemFont;
        
        mCharacter = new GameCharacter(mAssetManager);
        mPlatformToCharCollisionData = new PlatformToCharCollisionData();
        
        mActiveRiseSections = new Array<RiseSection>(true, ACTIVE_RISE_SECTIONS_INITIAL_CAPACITY);
        mVisiblePlatforms = new Array<PlatformBase>(true, VISIBLE_PLATFORMS_INITIAL_CAPACITY);
        mVisibleEnemies = new Array<EnemyBase>(true, VISIBLE_ENEMIES_INITIAL_CAPACITY);
        mVisibleItems = new Array<ItemBase>(true, VISIBLE_ITEMS_INITIAL_CAPACITY);
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.BACKGROUND_TEXTURE);
        mBackgroundTextureSize = new Vector2(
                mBackgroundTexture.getWidth() * GameAreaUtils.PIXEL_TO_METER,
                mBackgroundTexture.getHeight() * GameAreaUtils.PIXEL_TO_METER);
        
        mEndBackgroundScene = new EndBackgroundScene(assetManager);
        
        Array<ColorPositionPair> backgroundColorSpectrum = GameAreaUtils.getBackgroundColorSpectrum();
        mSpectrumColorInterpolator = new SpectrumColorInterpolator(backgroundColorSpectrum);
        mBackgroundColor = new Color();
        
        reset();
    }
    
    public void reset() {
        mIsGameOver = false;
        
        mRise = RiseGenerator.generate(mAssetManager);
        mRiseHeight = mRise.getHeight();
        
        mRiseScore = 0;
        
        mVisibleAreaPosition = 0.0f;
        mCharacter.reset(mRiseHeight);
        
        mEndBackgroundScene.reset(mRiseHeight);
        
        mBackgroundColor.set(Color.BLACK);
    }
    
    public void update(float delta) {
        
        mIsGameOver = mCharacter.isFinished();
        if (mIsGameOver) {
            return;
        }
        
        float horizontalSpeed = getHorizontalSpeed();
        
        updateGameArea(horizontalSpeed, delta);
        
        float effectiveCharPositionY = Math.min(mCharacter.getPosition().y, mRiseHeight);
        mRiseScore = Math.max(mRiseScore, (int) (effectiveCharPositionY * GameAreaUtils.METER_TO_PIXEL));
        
        mBackgroundColor.set(mSpectrumColorInterpolator.getBackgroundColor(mVisibleAreaPosition / mRiseHeight));
        
        if (isEndBackgroundVisible()) {
            mEndBackgroundScene.update(delta);
        }
    }
    
    public void render() {
        
        mBatch.getProjectionMatrix().setToOrtho2D(0.0f, mVisibleAreaPosition, GAME_AREA_WIDTH, GAME_AREA_HEIGHT);
        mBatch.begin();
        
        mBatch.draw(mBackgroundTexture, 0.0f, mVisibleAreaPosition, mBackgroundTextureSize.x, mBackgroundTextureSize.y);
        if (isEndBackgroundVisible()) {
            mEndBackgroundScene.render(mBatch);
        }
        
        for (PlatformBase platform : mVisiblePlatforms) {
            platform.render(mBatch);
        }
        
        for (EnemyBase enemy : mVisibleEnemies) {
            enemy.render(mBatch);
        }
        
        for (ItemBase item : mVisibleItems) {
            item.render(mBatch);
        }
        
        mCharacter.render(mBatch);
        
        mBatch.end();
        
        // TODO: for debugging, remove
        mDebugData.update(mBatch, getCurrentRiseSection(), mCharacter);
        
        mBatch.getProjectionMatrix().setToOrtho2D(0.0f, 0.0f,
                HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        mBatch.begin();
        for (ItemBase item : mVisibleItems) {
            item.renderText(mBatch, mVisibleAreaPosition, mItemFont);
        }
        mBatch.end();
    }
    
    private void updateGameArea(float horizontalSpeed, float delta) {
        
        updateActiveAndVisiblePlatforms();
        
        updatePlatforms(delta);
        updateEnemies(delta);
        updateItems(delta);
        
        mCharacter.update(
                horizontalSpeed,
                mPlatformToCharCollisionData,
                mActiveRiseSections,
                mVisiblePlatforms,
                mVisibleEnemies,
                mVisibleItems,
                mVisibleAreaPosition,
                delta);
        
        mVisibleAreaPosition = MathUtils.clamp(
                mCharacter.getPosition().y - GAME_AREA_HEIGHT * CHARACTER_POSITION_AREA_FRACTION,
                mVisibleAreaPosition, mRiseHeight - VISIBLE_AREA_MINIMUM_DISTANCE_TO_RISE);
    }
    
    private float getHorizontalSpeed() {
        if (Gdx.app.getType() == ApplicationType.Desktop) {
            if (Gdx.input.isKeyPressed(Keys.LEFT) && !Gdx.input.isKeyPressed(Keys.RIGHT)) {
                return -DEFAULT_HORIZONTAL_SPEED;
            } else if (Gdx.input.isKeyPressed(Keys.RIGHT) && !Gdx.input.isKeyPressed(Keys.LEFT)) {
                return DEFAULT_HORIZONTAL_SPEED;
            } else {
                return 0.0f;
            }
        } else if (Gdx.app.getType() == ApplicationType.Android) {
            return -Gdx.input.getAccelerometerX() * ACCELEROMETER_SPEED_MULTIPLIER;
        } else {
            return 0.0f;
        }
    }
    
    private void updateActiveAndVisiblePlatforms() {
        mActiveRiseSections.clear();
        mVisiblePlatforms.clear();
        mVisibleEnemies.clear();
        mVisibleItems.clear();
        
        Array<RiseSection> allRiseSections = mRise.getRiseSections();
        
        for (RiseSection riseSection : allRiseSections) {
            if (riseSection.getEndY() > mVisibleAreaPosition &&
                    riseSection.getStartY() < mVisibleAreaPosition + GameArea.GAME_AREA_HEIGHT) {
                mActiveRiseSections.add(riseSection);
                
                Array<PlatformBase> allPlatforms = riseSection.getPlatforms();
                for (PlatformBase platform : allPlatforms) {
                    if (platform.isActive(mVisibleAreaPosition, VISIBLE_PLATFORMS_AREA_PADDING)) {
                        mVisiblePlatforms.add(platform);
                    }
                }
                
                Array<EnemyBase> allEnemies = riseSection.getEnemies();
                for (EnemyBase enemy : allEnemies) {
                    mVisibleEnemies.add(enemy);
                }
                
                Array<ItemBase> allItems = riseSection.getItems();
                for (ItemBase item : allItems) {
                    mVisibleItems.add(item);
                }
            }
        }
    }
    
    private boolean isEndBackgroundVisible() {
        return mVisibleAreaPosition >= mRiseHeight - END_BACKGROUND_APPEARANCE_DISTANCE_FROM_END;
    }
    
    // TODO: only for debugging
    private RiseSection getCurrentRiseSection() {
        Array<RiseSection> allRiseSections = mRise.getRiseSections();
        
        float characterY = mCharacter.getPosition().y;
        for (RiseSection riseSection : allRiseSections) {
            if (riseSection.getStartY() <= characterY && characterY < riseSection.getEndY()) {
                return riseSection;
            }
        }
        
        return null;
    }
    
    private void updatePlatforms(float delta) {
        Vector2 c1 = Pools.obtainVector();
        Vector2 c2 = Pools.obtainVector();
        mPlatformToCharCollisionData.reset();
        
        Vector2 charPosition = mCharacter.getPosition();
        
        c1.set(charPosition.x + GameCharacter.COLLISION_WIDTH_OFFSET - PlatformData.PLATFORM_WIDTH, charPosition.y);
        c2.set(charPosition.x + GameCharacter.COLLISION_LINE_LENGTH, charPosition.y);
        
        // only check for collision when character is going down
        mPlatformToCharCollisionData.isEnabled = mCharacter.getSpeed().y < 0.0f;
        
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<PlatformBase> platforms = riseSection.getPlatforms();
            for (PlatformBase platform : platforms) {
                platform.update(delta, c1, c2, mPlatformToCharCollisionData);
            }
        }
        
        Pools.freeVector(c1);
        Pools.freeVector(c2);
    }
    
    private void updateEnemies(float delta) {
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<EnemyBase> enemies = riseSection.getEnemies();
            for (EnemyBase enemy : enemies) {
                enemy.update(delta);
            }
        }
    }
    
    private void updateItems(float delta) {
        for (RiseSection riseSection : mActiveRiseSections) {
            Array<ItemBase> items = riseSection.getItems();
            for (ItemBase item : items) {
                item.update(delta);
            }
        }
    }
    
    public int getScore() {
        return mRiseScore + mCharacter.getScore();
    }
    
    public int getLives() {
        return mCharacter.getLives();
    }
    
    public Color getBackgroundColor() {
        return mBackgroundColor;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
    
    public float getRiseHeight() {
        return mRiseHeight;
    }
    
    public float getVisibleAreaPosition() {
        return mVisibleAreaPosition;
    }
    
    public DebugData getDebugData() {
        return mDebugData;
    }
}
