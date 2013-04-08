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
package com.turbogerm.hellhopper.screens;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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
import com.turbogerm.hellhopper.GameArea;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.SuchyBlocks;
import com.turbogerm.hellhopper.gui.GameControlButton;

public final class PlayScreen extends ScreenBase {
    
    private static final float GAME_AREA_BORDER_SIZE = 10.0f;
    private static final float CONTROL_BUTTON_SIZE = 100.0f;
    private static final float CONTROL_BUTTON_PADDING = 10.0f;
    private static final float NEXT_SQUARE_SIZE = 20.0f;
    private static final float NEXT_DISPLAY_SIZE = NEXT_SQUARE_SIZE * 4.0f;
    
    private final GameArea mGameArea;
    private final Rectangle mGameAreaRectangle;
    
    private final Texture mBackgroundTexture;
    private final Texture mGameAreaBorderTexture;
    private final Texture mGameAreaBackgroundTexture;
    
    private final Vector2 mNextDisplayPosition;
    
    private final Label mScoreValueLabel;
    private final Label mLinesValueLabel;
    private final Label mLevelValueLabel;
    
    private final GameControlButton mLeftButton;
    private final GameControlButton mRightButton;
    private final GameControlButton mRotateButton;
    private final GameControlButton mDownButton;
    
    private ImageButton mPlayPauseButton;
    private ImageButtonStyle mPlayButtonStyle;
    private ImageButtonStyle mPauseButtonStyle;
    private boolean mIsPaused;
    
    public PlayScreen(SuchyBlocks game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        Vector2 gameAreaPosition = new Vector2(
                GAME_AREA_BORDER_SIZE,
                CONTROL_BUTTON_SIZE + 2.0f * CONTROL_BUTTON_PADDING + GAME_AREA_BORDER_SIZE);
        mGameArea = new GameArea(mAssetManager, mBatch, gameAreaPosition);
        mGameAreaRectangle = mGameArea.getGameAreaRectangle();
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.GUI_BACKGROUND_TEXTURE);
        mGameAreaBorderTexture = mAssetManager.get(ResourceNames.GUI_GAME_AREA_BORDER_TEXTURE);
        mGameAreaBackgroundTexture = mAssetManager.get(ResourceNames.GUI_GAME_AREA_BACKGROUND_TEXTURE);
        
        float leftHorizontalSpace = SuchyBlocks.VIEWPORT_WIDTH - mGameAreaRectangle.width -
                2.0f * GAME_AREA_BORDER_SIZE;
        float nextDisplayX = SuchyBlocks.VIEWPORT_WIDTH - leftHorizontalSpace / 2.0f - NEXT_DISPLAY_SIZE / 2.0f;
        float nextDisplayY = mGameAreaRectangle.y + mGameAreaRectangle.height - NEXT_DISPLAY_SIZE;
        mNextDisplayPosition = new Vector2(nextDisplayX, nextDisplayY);
        
        // labels
        LabelStyle labelStyle = new LabelStyle(mGuiSkin.get(LabelStyle.class));
        labelStyle.font = mGuiSkin.get("medium-font", BitmapFont.class); // mResources.getFont("medium");
        
        final float labelSmallVerticalStride = 30.0f;
        final float labelLargeVerticalStride = 40.0f;
        
        final float labelX = SuchyBlocks.VIEWPORT_WIDTH - leftHorizontalSpace;
        final float labelWidth = leftHorizontalSpace;
        final float labelHeight = 24.0f;
        
        final float scoreLabelY = nextDisplayY - 80.0f;
        Label scoreLabel = new Label("SCORE", mGuiSkin);
        scoreLabel.setBounds(labelX, scoreLabelY, labelWidth, labelHeight);
        scoreLabel.setStyle(labelStyle);
        scoreLabel.setAlignment(Align.center);
        mGuiStage.addActor(scoreLabel);
        
        final float scoreValueLabelY = scoreLabelY - labelSmallVerticalStride;
        mScoreValueLabel = new Label("", mGuiSkin);
        mScoreValueLabel.setBounds(labelX, scoreValueLabelY, labelWidth, labelHeight);
        mScoreValueLabel.setStyle(labelStyle);
        mScoreValueLabel.setAlignment(Align.center);
        mGuiStage.addActor(mScoreValueLabel);
        
        final float linesLabelY = scoreValueLabelY - labelLargeVerticalStride;
        Label linesLabel = new Label("LINES", mGuiSkin);
        linesLabel.setBounds(labelX, linesLabelY, labelWidth, labelHeight);
        linesLabel.setStyle(labelStyle);
        linesLabel.setAlignment(Align.center);
        mGuiStage.addActor(linesLabel);
        
        final float linesValueLabelY = linesLabelY - labelSmallVerticalStride;
        mLinesValueLabel = new Label("", mGuiSkin);
        mLinesValueLabel.setBounds(labelX, linesValueLabelY, labelWidth, labelHeight);
        mLinesValueLabel.setStyle(labelStyle);
        mLinesValueLabel.setAlignment(Align.center);
        mGuiStage.addActor(mLinesValueLabel);
        
        final float levelLabelY = linesValueLabelY - labelLargeVerticalStride;
        Label levelLabel = new Label("LEVEL", mGuiSkin);
        levelLabel.setBounds(labelX, levelLabelY, labelWidth, labelHeight);
        levelLabel.setStyle(labelStyle);
        levelLabel.setAlignment(Align.center);
        mGuiStage.addActor(levelLabel);
        
        final float levelValueLabelY = levelLabelY - labelSmallVerticalStride;
        mLevelValueLabel = new Label("", mGuiSkin);
        mLevelValueLabel.setBounds(labelX, levelValueLabelY, labelWidth, labelHeight);
        mLevelValueLabel.setStyle(labelStyle);
        mLevelValueLabel.setAlignment(Align.center);
        mGuiStage.addActor(mLevelValueLabel);
        
        // control buttons
        final float controlButtonStride = CONTROL_BUTTON_SIZE + CONTROL_BUTTON_PADDING;
        
        TextureRegion leftUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_LEFT_UP_TEXTURE));
        Drawable leftUpDrawable = new TextureRegionDrawable(leftUpTextureRegion);
        TextureRegion leftDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_LEFT_DOWN_TEXTURE));
        Drawable leftDownDrawable = new TextureRegionDrawable(leftDownTextureRegion);
        mLeftButton = new GameControlButton(leftUpDrawable, leftDownDrawable);
        mLeftButton.setBounds(CONTROL_BUTTON_PADDING, CONTROL_BUTTON_PADDING,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
        mLeftButton.addListener(getLeftInputListener());
        mGuiStage.addActor(mLeftButton);
        
        TextureRegion rightUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_RIGHT_UP_TEXTURE));
        Drawable rightUpDrawable = new TextureRegionDrawable(rightUpTextureRegion);
        TextureRegion rightDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_RIGHT_DOWN_TEXTURE));
        Drawable rightDownDrawable = new TextureRegionDrawable(rightDownTextureRegion);
        mRightButton = new GameControlButton(rightUpDrawable, rightDownDrawable);
        mRightButton.setBounds(CONTROL_BUTTON_PADDING + 1.0f * controlButtonStride, CONTROL_BUTTON_PADDING,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
        mRightButton.addListener(getRightInputListener());
        mGuiStage.addActor(mRightButton);
        
        TextureRegion rotateUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_ROTATE_UP_TEXTURE));
        Drawable rotateUpDrawable = new TextureRegionDrawable(rotateUpTextureRegion);
        TextureRegion rotateDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_ROTATE_DOWN_TEXTURE));
        Drawable rotateDownDrawable = new TextureRegionDrawable(rotateDownTextureRegion);
        mRotateButton = new GameControlButton(rotateUpDrawable, rotateDownDrawable);
        mRotateButton.setBounds(CONTROL_BUTTON_PADDING + 2.0f * controlButtonStride, CONTROL_BUTTON_PADDING,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
        mRotateButton.addListener(getRotateInputListener());
        mGuiStage.addActor(mRotateButton);
        
        TextureRegion downUpTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_DOWN_UP_TEXTURE));
        Drawable downUpDrawable = new TextureRegionDrawable(downUpTextureRegion);
        TextureRegion downDownTextureRegion = new TextureRegion(
                (Texture) mAssetManager.get(ResourceNames.GUI_DOWN_DOWN_TEXTURE));
        Drawable downDownDrawable = new TextureRegionDrawable(downDownTextureRegion);
        mDownButton = new GameControlButton(downUpDrawable, downDownDrawable);
        mDownButton.setBounds(CONTROL_BUTTON_PADDING + 3.0f * controlButtonStride, CONTROL_BUTTON_PADDING,
                CONTROL_BUTTON_SIZE, CONTROL_BUTTON_SIZE);
        mDownButton.addListener(getDownInputListener());
        mGuiStage.addActor(mDownButton);
        
        createPlayPauseButton();
        mGuiStage.addActor(mPlayPauseButton);
    }
    
    @Override
    public void show() {
        super.show();
        mGameArea.reset();
        setPaused(false);
    }
    
    @Override
    public void renderImpl(float delta) {
        
        if (mGameArea.isGameOver()) {
            mGameData.setScore(mGameArea.getScore());
            mGame.setScreen(SuchyBlocks.GAME_OVER_SCREEN_NAME);
        }
        
        if (!mIsPaused) {
            mGameArea.update(delta);
        }
        
        mScoreValueLabel.setText(String.valueOf(mGameArea.getScore()));
        mLinesValueLabel.setText(String.valueOf(mGameArea.getLines()));
        mLevelValueLabel.setText(String.valueOf(mGameArea.getLevel()));
        
        mBatch.begin();
        
        mBatch.draw(mBackgroundTexture, 0.0f, 0.0f, SuchyBlocks.VIEWPORT_WIDTH, SuchyBlocks.VIEWPORT_HEIGHT);
        
        if (!mIsPaused) {
            mBatch.draw(mGameAreaBorderTexture,
                    mGameAreaRectangle.x - GAME_AREA_BORDER_SIZE,
                    mGameAreaRectangle.y - GAME_AREA_BORDER_SIZE,
                    mGameAreaRectangle.width + 2.0f * GAME_AREA_BORDER_SIZE,
                    mGameAreaRectangle.height + 2.0f * GAME_AREA_BORDER_SIZE);
            mBatch.draw(mGameAreaBackgroundTexture, mGameAreaRectangle.x, mGameAreaRectangle.y,
                    mGameAreaRectangle.width, mGameAreaRectangle.height);
            
            mGameArea.render();
            
            mBatch.draw(mGameAreaBorderTexture,
                    mNextDisplayPosition.x - GAME_AREA_BORDER_SIZE,
                    mNextDisplayPosition.y - GAME_AREA_BORDER_SIZE,
                    NEXT_DISPLAY_SIZE + 2.0f * GAME_AREA_BORDER_SIZE,
                    NEXT_DISPLAY_SIZE + 2.0f * GAME_AREA_BORDER_SIZE);
            mGameArea.renderNext(mNextDisplayPosition, NEXT_SQUARE_SIZE);
        }
        
        mBatch.end();
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
        
        final float leftHorizontalSpace = SuchyBlocks.VIEWPORT_WIDTH - mGameAreaRectangle.width -
                2.0f * GAME_AREA_BORDER_SIZE;
        final float pauseButtonSize = 100.0f;
        final float pauseButtonX = SuchyBlocks.VIEWPORT_WIDTH - leftHorizontalSpace / 2.0f - pauseButtonSize / 2.0f;
        final float pauseButtonY = 150.0f;
        mPlayPauseButton.setBounds(pauseButtonX, pauseButtonY, pauseButtonSize, pauseButtonSize);
        
        mPlayPauseButton.addListener(getPlayPauseInputListener(mPlayPauseButton));
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            private static final float ROTATE_STEP = 50.0f;
            private static final float SOFT_DROP_THRESHOLD = 120.0f;
            private static final float HORIZONTAL_MOVE_STEP = 30.0f;
            
            private boolean mIsActionInProgress = false;
            private Vector2 mInitialTouchPoint = new Vector2();
            private boolean mIsHorizontalMovementStarted = false;
            private float mLastHorizontalMovementX = 0.0f;
            private boolean mIsRotateStarted = false;
            private float mLastRotateY = 0.0f;
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ESCAPE || keycode == Keys.BACK) {
                    mGame.setScreen(SuchyBlocks.MAIN_MENU_SCREEN_NAME);
                    return true;
                } else if (keycode == Keys.P) {
                    togglePause();
                } else if (!mIsPaused) {
                    if (keycode == Keys.UP) {
                        mGameArea.startRotate();
                        mRotateButton.setDisplayPressed(true);
                        return true;
                    } else if (keycode == Keys.LEFT) {
                        mGameArea.startMoveHorizontal(true);
                        mLeftButton.setDisplayPressed(true);
                        return true;
                    } else if (keycode == Keys.RIGHT) {
                        mGameArea.startMoveHorizontal(false);
                        mRightButton.setDisplayPressed(true);
                        return true;
                    } else if (keycode == Keys.DOWN) {
                        mGameArea.setSoftDrop(true);
                        mDownButton.setDisplayPressed(true);
                        return true;
                    }
                }
                
                return false;
            }
            
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Keys.UP) {
                    mGameArea.endRotate();
                    mRotateButton.setDisplayPressed(false);
                    return true;
                } else if (keycode == Keys.LEFT) {
                    mGameArea.endMoveHorizontal(true);
                    mLeftButton.setDisplayPressed(false);
                    return true;
                } else if (keycode == Keys.RIGHT) {
                    mGameArea.endMoveHorizontal(false);
                    mRightButton.setDisplayPressed(false);
                    return true;
                } else if (keycode == Keys.DOWN) {
                    mGameArea.setSoftDrop(false);
                    mDownButton.setDisplayPressed(false);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT && mGameArea.getGameAreaRectangle().contains(x, y) && !mIsPaused) {
                    if (mGameArea.isSoftDrop()) {
                        mGameArea.setSoftDrop(false);
                    }
                    mIsActionInProgress = true;
                    mIsHorizontalMovementStarted = false;
                    mIsRotateStarted = false;
                    mInitialTouchPoint.set(x, y);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public void touchDragged(InputEvent event, float x, float y, int pointer) {
                if (mIsActionInProgress) {
                    if (!mIsHorizontalMovementStarted && !mIsRotateStarted) {
                        float diffX = x - mInitialTouchPoint.x;
                        float diffY = y - mInitialTouchPoint.y;
                        float absX = Math.abs(diffX);
                        float absY = Math.abs(diffY);
                        
                        if (absX >= absY) {
                            if (absX >= HORIZONTAL_MOVE_STEP) {
                                mIsHorizontalMovementStarted = true;
                                mLastHorizontalMovementX = mInitialTouchPoint.x;
                                handleHorizontalMovement(x);
                            }
                        } else {
                            if (diffY >= ROTATE_STEP) {
                                mIsRotateStarted = true;
                                mLastRotateY = mInitialTouchPoint.y;
                                handleRotate(y);
                            } else if (diffY <= -SOFT_DROP_THRESHOLD) {
                                mGameArea.setSoftDrop(true);
                                mIsActionInProgress = false;
                            }
                        }
                    } else if (mIsHorizontalMovementStarted) {
                        handleHorizontalMovement(x);
                    } else if (mIsRotateStarted) {
                        handleRotate(y);
                    }
                    return;
                }
            }
            
            private void handleHorizontalMovement(float x) {
                float diffX = x - mLastHorizontalMovementX;
                
                int distance = (int) (diffX / HORIZONTAL_MOVE_STEP);
                if (distance != 0) {
                    mGameArea.moveHorizontal(distance);
                    mLastHorizontalMovementX += distance * HORIZONTAL_MOVE_STEP;
                }
            }
            
            private void handleRotate(float y) {
                float diffY = y - mLastRotateY;
                
                int change = (int) (diffY / ROTATE_STEP);
                if (change != 0) {
                    mGameArea.rotate(change);
                    mLastRotateY += change * ROTATE_STEP;
                }
            }
        };
    }
    
    private InputListener getLeftInputListener() {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mIsPaused) {
                    return false;
                }
                
                mGameArea.startMoveHorizontal(true);
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mGameArea.endMoveHorizontal(true);
            }
        };
    }
    
    private InputListener getRightInputListener() {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mIsPaused) {
                    return false;
                }
                
                mGameArea.startMoveHorizontal(false);
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mGameArea.endMoveHorizontal(false);
            }
        };
    }
    
    private InputListener getDownInputListener() {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mIsPaused) {
                    return false;
                }
                
                mGameArea.setSoftDrop(true);
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mGameArea.setSoftDrop(false);
            }
        };
    }
    
    private InputListener getRotateInputListener() {
        return new InputListener() {
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (mIsPaused) {
                    return false;
                }
                
                mGameArea.startRotate();
                return true;
            }
            
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                mGameArea.endRotate();
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