package com.turbogerm.helljump.screens;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.turbogerm.germlibrary.controls.CustomButtonAction;
import com.turbogerm.germlibrary.controls.CustomImageButton;
import com.turbogerm.germlibrary.controls.CustomImageButtonStyleData;
import com.turbogerm.helljump.HellJump;
import com.turbogerm.helljump.debug.DebugData;
import com.turbogerm.helljump.game.GameArea;
import com.turbogerm.helljump.game.RisePositionScroll;
import com.turbogerm.helljump.resources.ResourceNames;

public final class PlayScreen extends ScreenBase {
    
    private static final int BUTTON_STYLE_PAUSE = 0;
    private static final int BUTTON_STYLE_PLAY = 1;
    
    private final GameArea mGameArea;
    
    private final Label mScoreLabel;
    private final Image mLivesImage;
    private final Label mLivesLabel;
    
    private CustomImageButton mPlayPauseButton;
    private boolean mIsPaused;
    
    // TODO: remove, only for testing
    private final Label mDebugLabel;
    long startTime = 0l;
    
    private final RisePositionScroll mRisePositionScroll;
    
    public PlayScreen(HellJump game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mGameArea = new GameArea(mCameraData, mAssetManager, mResources.getItemFont());
        
        LabelStyle scoreLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        scoreLabelStyle.font = mGuiSkin.getFont("xxxl-font");
        
        mScoreLabel = new Label("", mGuiSkin);
        mScoreLabel.setStyle(scoreLabelStyle);
        mScoreLabel.setAlignment(Align.right);
        mGuiStage.addActor(mScoreLabel);
        
        TextureAtlas atlas = mAssetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        TextureRegion livesRegion = atlas.findRegion(ResourceNames.GUI_PLAY_LIVES_IMAGE_NAME);
        mLivesImage = new Image(new TextureRegionDrawable(livesRegion));
        mGuiStage.addActor(mLivesImage);
        
        LabelStyle livesLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        livesLabelStyle.font = mGuiSkin.getFont("xl-font");
        
        mLivesLabel = new Label("", mGuiSkin);
        mLivesLabel.setStyle(livesLabelStyle);
        mLivesLabel.setAlignment(Align.left);
        mGuiStage.addActor(mLivesLabel);
        
        createPlayPauseButton();
        
        LabelStyle fpsLabelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        fpsLabelStyle.font = mGuiSkin.getFont("medium-font");
        mDebugLabel = new Label("", mGuiSkin);
        mDebugLabel.setStyle(fpsLabelStyle);
        mDebugLabel.setAlignment(Align.left);
        mGuiStage.addActor(mDebugLabel);
        
        mRisePositionScroll = new RisePositionScroll(mCameraData, mAssetManager);
    }
    
    @Override
    public void show() {
        super.show();
        mGameArea.reset();
        setPaused(false);
    }
    
    @Override
    protected void updateImpl(float delta) {
        if (mGameArea.isGameOver()) {
            mGameData.setScore(mGameArea.getScore());
            mGame.setScreen(HellJump.GAME_OVER_SCREEN_NAME);
        }
        
        if (!mIsPaused) {
            mGameArea.update(delta);
        }
    }
    
    @Override
    public void renderImpl() {
        
        setGuiPositions();
        
        mScoreLabel.setText(String.valueOf(mGameArea.getScore()));
        mLivesLabel.setText("x" + String.valueOf(mGameArea.getLives()));
        
        mGameArea.render();
        
        mBatch.begin();
        mRisePositionScroll.setRiseHeight(mGameArea.getRiseHeight());
        mRisePositionScroll.render(mBatch, mGameArea.getVisibleAreaPosition());
        mBatch.end();
        
        if (System.currentTimeMillis() - startTime > 100) {
            DebugData debugData = mGameArea.getDebugData();
            mDebugLabel.setText(debugData.toString());
            mDebugLabel.setText("");
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
            mPlayPauseButton.setStyle(BUTTON_STYLE_PLAY);
        } else {
            mPlayPauseButton.setStyle(BUTTON_STYLE_PAUSE);
        }
    }
    
    private void createPlayPauseButton() {
        
        TextureAtlas atlas = mAssetManager.get(ResourceNames.GRAPHICS_GUI_ATLAS);
        
        Array<CustomImageButtonStyleData> stylesData = new Array<CustomImageButtonStyleData>(true, 2);
        stylesData.add(new CustomImageButtonStyleData(
                BUTTON_STYLE_PAUSE,
                ResourceNames.GUI_PLAY_PAUSE_UP_IMAGE_NAME,
                ResourceNames.GUI_PLAY_PAUSE_DOWN_IMAGE_NAME));
        stylesData.add(new CustomImageButtonStyleData(
                BUTTON_STYLE_PLAY,
                ResourceNames.GUI_PLAY_PLAY_UP_IMAGE_NAME,
                ResourceNames.GUI_PLAY_PLAY_DOWN_IMAGE_NAME));
        
        mPlayPauseButton = new CustomImageButton(
                0.0f, 0.0f,
                atlas,
                stylesData,
                getPlayPauseAction(),
                mAssetManager);
        
        mPlayPauseButton.addToStage(mGuiStage);
    }
    
    private void setGuiPositions() {
        
        final float scoreLabelWidth = 60.0f;
        final float scoreLabelHeight = 42.0f;
        final float scoreLabelX = HellJump.VIEWPORT_WIDTH - mGuiCameraRect.x - scoreLabelWidth;
        final float scoreLabelY = HellJump.VIEWPORT_HEIGHT - scoreLabelHeight;
        mScoreLabel.setBounds(scoreLabelX, scoreLabelY, scoreLabelWidth, scoreLabelHeight);
        
        final float livesImageX = 74.0f + mGuiCameraRect.x;
        mLivesImage.setPosition(livesImageX, HellJump.VIEWPORT_HEIGHT - mLivesImage.getHeight() - 10.0f);
        
        final float livesLabelWidth = 40.0f;
        final float livesLabelHeight = 42.0f;
        final float livesLabelX = livesImageX + mLivesImage.getWidth() + 10.0f;
        final float livesLabelY = HellJump.VIEWPORT_HEIGHT - scoreLabelHeight;
        mLivesLabel.setBounds(livesLabelX, livesLabelY, livesLabelWidth, livesLabelHeight - 10.0f);
        
        final float pauseButtonSize = 64.0f;
        final float pauseButtonX = mGuiCameraRect.x;
        final float pauseButtonY = HellJump.VIEWPORT_HEIGHT - pauseButtonSize;
        mPlayPauseButton.setPosition(pauseButtonX, pauseButtonY);
        
        mDebugLabel.setBounds(0.0f, 0.0f, 20.0f, 72.0f);
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    mGame.setScreen(HellJump.MAIN_MENU_SCREEN_NAME);
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
    
    private CustomButtonAction getPlayPauseAction() {
        
        return new CustomButtonAction() {
            
            @Override
            public void invoke() {
                togglePause();
            }
        };
    }
}