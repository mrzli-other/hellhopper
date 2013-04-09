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

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.HellHopper;
import com.turbogerm.hellhopper.game.GameArea;
import com.turbogerm.hellhopper.splashsquares.SplashSquaresReader;
import com.turbogerm.hellhopper.tetrominos.Tetromino;

public final class SplashScreen extends ScreenBase {
    
    private static final float SPLASH_SQUARE_SIZE = 8.0f;
    
    private final Texture mBackgroundTexture;
    private final Texture[] mSquareTextures;
    
    private final int[][] mSuchySplashSquares;
    private final Vector2 mSuchySplashSquaresPosition;
    private final int[][] mBlocksSplashSquares;
    private final Vector2 mBlocksSplashSquaresPosition;
    
    public SplashScreen(HellHopper game) {
        super(game);
        
        mGuiStage.addListener(getStageInputListener());
        
        mBackgroundTexture = mAssetManager.get(ResourceNames.GUI_BACKGROUND_TEXTURE);
        
        Texture splashTexture = mAssetManager.get(ResourceNames.GUI_BLACK_TEXTURE);
        
        Image blackImage = new Image(splashTexture);
        blackImage.setBounds(0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        // blackImage.setColor(1.0f, 1.0f, 1.0f, 0.0f);
        // SequenceAction action = Actions.sequence(
        // Actions.fadeIn(1.5f), Actions.delay(1.5f), Actions.fadeOut(1.5f), getCompletedAction());
        blackImage.setColor(1.0f, 1.0f, 1.0f, 1.0f);
        SequenceAction action = Actions.sequence(
                Actions.fadeOut(2.5f), Actions.delay(2.5f), Actions.fadeIn(1.5f), getCompletedAction());
        blackImage.addAction(action);
        mGuiStage.addActor(blackImage);
        
        mSquareTextures = GameArea.getSquareTextures(mAssetManager);
        boolean[][] suchySplashSquareLocations = SplashSquaresReader.read(
                Gdx.files.internal(ResourceNames.SUCHY_SPLASH_SQUARES_DATA));
        mSuchySplashSquares = createSplashSquares(suchySplashSquareLocations);
        mSuchySplashSquaresPosition = new Vector2(20.0f, 500.0f);
        
        boolean[][] blocksSplashSquareLocations = SplashSquaresReader.read(
                Gdx.files.internal(ResourceNames.BLOCKS_SPLASH_SQUARES_DATA));
        mBlocksSplashSquares = createSplashSquares(blocksSplashSquareLocations);
        mBlocksSplashSquaresPosition = new Vector2(20.0f, 300.0f);
    }
    
    @Override
    public void renderImpl(float delta) {
        mBatch.begin();
        mBatch.draw(mBackgroundTexture, 0.0f, 0.0f, HellHopper.VIEWPORT_WIDTH, HellHopper.VIEWPORT_HEIGHT);
        renderSplashSquares(mSuchySplashSquaresPosition, mSuchySplashSquares);
        renderSplashSquares(mBlocksSplashSquaresPosition, mBlocksSplashSquares);
        mBatch.end();
    }
    
    private void renderSplashSquares(Vector2 position, int[][] splashSquares) {
        int rows = splashSquares.length;
        int columns = splashSquares[0].length;
        
        for (int i = 0; i < rows; i++) {
            float squareY = i * SPLASH_SQUARE_SIZE + position.y;
            for (int j = 0; j < columns; j++) {
                int squareIndex = splashSquares[i][j];
                float squareX = j * SPLASH_SQUARE_SIZE + position.x;
                if (squareIndex >= 0) {
                    mBatch.draw(mSquareTextures[squareIndex],
                            squareX, squareY, SPLASH_SQUARE_SIZE, SPLASH_SQUARE_SIZE);
                }
            }
        }
    }
    
    private int[][] createSplashSquares(boolean[][] splashSquareLocations) {
        int rows = splashSquareLocations.length;
        int columns = splashSquareLocations[0].length;
        
        int squareIndex = MathUtils.random(Tetromino.COUNT - 1);
        
        int[][] splashSquares = new int[rows][columns];
        for (int i = 0; i < rows; i++) {
            //int squareIndex = i % Tetromino.COUNT; // MathUtils.random(Tetromino.COUNT - 1);
            for (int j = 0; j < columns; j++) {
                if (splashSquareLocations[i][j]) {
                    splashSquares[i][j] = squareIndex;
                } else {
                    splashSquares[i][j] = -1;
                }
            }
        }
        
        return splashSquares;
    }
    
    private Action getCompletedAction() {
        return new Action() {
            
            @Override
            public boolean act(float delta) {
                mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                return true;
            }
        };
    }
    
    private InputListener getStageInputListener() {
        return new InputListener() {
            
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER || keycode == Keys.SPACE || keycode == Keys.ESCAPE) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
            
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (button == Buttons.LEFT || button == Buttons.RIGHT) {
                    mGame.setScreen(HellHopper.MAIN_MENU_SCREEN_NAME);
                    return true;
                }
                
                return false;
            }
        };
    }
}