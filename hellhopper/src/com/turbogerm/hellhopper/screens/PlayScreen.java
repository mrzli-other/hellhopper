package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton.ImageButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.debug.DebugData;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.game.RisePositionScroll;

public final class PlayScreen extends ScreenBase {
    
    private final GameArea mGameArea;
    
    private final Label mScoreLabel;
    
    private ImageButton mPlayPauseButton;
    private ImageButtonStyle mPlayButtonStyle;
    private ImageButtonStyle mPauseButtonStyle;
    private boolean mIsPaused;
    
    // TODO: remove, only for testing
    private final Label mDebugLabel;
    long startTime = 0l;
    
    private final RisePositionScroll mRisePositionScroll;
    
    public PlayScreen(HellHopper game) {
        super(game);
        
        mClearColor = Color.BLACK;
        
        mGuiStage.addListener(getStageInputListener());
        
        mGameArea = new GameArea(mAssetManager, mResources.getItemFont());
        
        // labels
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.getFont("xxxl-font");
        
        final float scoreLabelWidth = 60.0f;
        final float scoreLabelHeight = 42.0f;
        final float scoreLabelX = HellHopper.VIEWPORT_WIDTH - scoreLabelWidth;
        final float scoreLabelY = HellHopper.VIEWPORT_HEIGHT - scoreLabelHeight;
        
        mScoreLabel = new Label("", mGuiSkin);
        mScoreLabel.setBounds(scoreLabelX, scoreLabelY, scoreLabelWidth, scoreLabelHeight);
        mScoreLabel.setStyle(labelStyle);
        mScoreLabel.setAlignment(Align.right);
        mGuiStage.addActor(mScoreLabel);
        
        createPlayPauseButton();
        mGuiStage.addActor(mPlayPauseButton);
        
        // TODO: remove, only for testing
        LabelStyle fpsLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        fpsLabelStyle.font = mGuiSkin.getFont("medium-font");
        mDebugLabel = new Label("", mGuiSkin);
        mDebugLabel.setBounds(0.0f, 0.0f, 20.0f, 72.0f);
        mDebugLabel.setStyle(fpsLabelStyle);
        mDebugLabel.setAlignment(Align.left);
        mGuiStage.addActor(mDebugLabel);
        
        mRisePositionScroll = new RisePositionScroll(mAssetManager);
    }
    
    @Override
    public void show() {
        super.show();
        mGameArea.reset();
        setPaused(false);
    }
    
    @Override
    public void render(float delta) {
        renderImpl(delta);
        
        mGuiStage.act(delta);
        mGuiStage.draw();
    }
    
    @Override
    public void renderImpl(float delta) {
        
        if (mGameArea.isGameOver()) {
            mGameData.setScore(mGameArea.getScore());
            mGame.setScreen(HellHopper.GAME_OVER_SCREEN_NAME);
        }
        
        if (!mIsPaused) {
            mGameArea.update(delta);
        }
        
        mClearColor = mGameArea.getBackgroundColor();
        Gdx.gl.glClearColor(mClearColor.r, mClearColor.g, mClearColor.b, mClearColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mScoreLabel.setText(String.valueOf(mGameArea.getScore()));
        
        // if (!mIsPaused) {
        mGameArea.render(delta);
        // }
        
        mBatch.begin();
        mRisePositionScroll.setRiseHeight(mGameArea.getRiseHeight());
        mRisePositionScroll.render(mBatch, mGameArea.getVisibleAreaPosition());
        mBatch.end();
        
        // TODO: remove, only for testing
        if (System.currentTimeMillis() - startTime > 100) {
            DebugData debugData = mGameArea.getDebugData();
            mDebugLabel.setText(debugData.toString());
            //mDebugLabel.setText("");
            startTime = System.currentTimeMillis();
        }
    }
    
    @Override
    public void hide() {
        super.hide();
    }
    
    @Override
    public void pause() {
        setPaused(true);
    }
    
    @Override
    public void resume() {
    }
    
    @Override
    public void dispose() {
        super.dispose();
    }
    
    private void togglePause() {
        setPaused(!mIsPaused);
    }
    
    private void setPaused(boolean isPaused) {
        mIsPaused = isPaused;
        if (mIsPaused) {
            mPlayPauseButton.setStyle(mPlayButtonStyle);
        } else {
            mPlayPauseButton.setStyle(mPauseButtonStyle);
        }
    }
    
    private void createPlayPauseButton() {
        TextureRegion pauseUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_PAUSE_UP_TEXTURE));
        Drawable pauseUpDrawable = new TextureRegionDrawable(pauseUpTextureRegion);
        TextureRegion pauseDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_PAUSE_DOWN_TEXTURE));
        Drawable pauseDownDrawable = new TextureRegionDrawable(pauseDownTextureRegion);
        mPauseButtonStyle = new ImageButtonStyle(null, null, null, pauseUpDrawable, pauseDownDrawable, null);
        
        TextureRegion playUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_PLAY_UP_TEXTURE));
        Drawable playUpDrawable = new TextureRegionDrawable(playUpTextureRegion);
        TextureRegion playDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_PLAY_DOWN_TEXTURE));
        Drawable playDownDrawable = new TextureRegionDrawable(playDownTextureRegion);
        mPlayButtonStyle = new ImageButtonStyle(null, null, null, playUpDrawable, playDownDrawable, null);
        
        mPlayPauseButton = new ImageButton(pauseUpDrawable, pauseDownDrawable);
        
        final float pauseButtonSize = 48.0f;
        final float pauseButtonY = HellHopper.VIEWPORT_HEIGHT - pauseButtonSize;
        mPlayPauseButton.setBounds(0.0f, pauseButtonY, pauseButtonSize, pauseButtonSize);
        
        mPlayPauseButton.addListener(getPlayPauseInputListener(mPlayPauseButton));
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return false;
            }
        };
    }
    
    private InputListener getPlayPauseInputListener(final Actor actor) {
        
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                if (actor.hit(x, y, true) != null) {
                    togglePause();
                }
            }
        };
    }
}