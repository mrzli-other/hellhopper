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
package com.turbogerm.hellhopper;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntArray;
import com.turbogerm.hellhopper.tetrominos.Tetromino;
import com.turbogerm.hellhopper.tetrominos.TetrominoRotationsReader;
import com.turbogerm.hellhopper.util.IntPair;

public final class GameArea {
    
    public static final float SQUARE_SIZE = 30.0f;
    public static final int GAME_AREA_ROWS = 20;
    public static final int GAME_AREA_COLUMNS = 10;
    
    private static final float SOFT_DROP_SPEED = 30.0f;
    private static final float REPEAT_START_OFFSET = 0.5f;
    private static final float REPEAT_INTERVAL = 0.05f;
    
    private static final int LINES_TO_REMOVE_CAPACITY = 5;
    
    private final AssetManager mAssetManager;
    private final SpriteBatch mBatch;
    private final Vector2 mGameAreaPosition;
    private final Rectangle mGameAreaRectangle;
    
    private final Texture[] mSquareTextures;
    private final Tetromino[] mTetrominos;
    
    private final Texture[] mEmptySquareTextures;
    
    private int mActiveTetromino;
    
    private final int[][] mGameAreaSquares;
    
    private int mScore;
    private int mLines;
    private int mLevel;
    private int mNextTetromino;
    
    private float mSpeed;
    private float mDistanceRemainder;
    
    private boolean mIsSoftDrop;
    
    private boolean mIsRotating;
    private float mRotatingCountdown;
    private boolean mIsMovingLeft;
    private float mMovingLeftCountdown;
    private boolean mIsMovingRight;
    private float mMovingRightCountdown;
    
    private boolean mIsGameOver;
    
    private final IntArray mLinesToRemove;
    
    public GameArea(AssetManager assetManager, SpriteBatch batch, Vector2 gameAreaPosition) {
        
        mAssetManager = assetManager;
        mBatch = batch;
        mGameAreaPosition = gameAreaPosition;
        mGameAreaRectangle = new Rectangle(mGameAreaPosition.x, mGameAreaPosition.y,
                GAME_AREA_COLUMNS * SQUARE_SIZE, GAME_AREA_ROWS * SQUARE_SIZE);
        
        mSquareTextures = getSquareTextures(mAssetManager);
        
        IntPair[][][] tetrominoRotations = TetrominoRotationsReader.read(
                Gdx.files.internal(ResourceNames.TETROMINO_ROTATIONS_DATA));
        mTetrominos = new Tetromino[Tetromino.COUNT];
        for (int i = 0; i < Tetromino.COUNT; i++) {
            mTetrominos[i] = new Tetromino(tetrominoRotations[i], mSquareTextures[i], i);
        }
        
        mEmptySquareTextures = new Texture[2];
        mEmptySquareTextures[0] = mAssetManager.get(ResourceNames.SQUARES_EMPTY_1_TEXTURE);
        mEmptySquareTextures[1] = mAssetManager.get(ResourceNames.SQUARES_EMPTY_2_TEXTURE);
        
        mGameAreaSquares = new int[GAME_AREA_ROWS][GAME_AREA_COLUMNS];
        
        mLinesToRemove = new IntArray(false, LINES_TO_REMOVE_CAPACITY);
        
        reset();
    }
    
    public void reset() {
        
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                mGameAreaSquares[i][j] = -1;
            }
        }
        
        mScore = 0;
        setLines(0);
        
        mNextTetromino = MathUtils.random(Tetromino.COUNT - 1);
        mActiveTetromino = -1;
        mIsGameOver = false;
        
        nextTetromino();
    }
    
    public void update(float delta) {
        
        if (mIsRotating) {
            mRotatingCountdown -= delta;
            if (mRotatingCountdown <= 0.0f) {
                rotate(1);
                mRotatingCountdown += REPEAT_INTERVAL;
            }
        }
        
        if (mIsMovingLeft && !mIsMovingRight) {
            mMovingLeftCountdown -= delta;
            if (mMovingLeftCountdown <= 0.0f) {
                moveHorizontal(-1);
                mMovingLeftCountdown += REPEAT_INTERVAL;
            }
        }
        
        if (mIsMovingRight && !mIsMovingLeft) {
            mMovingRightCountdown -= delta;
            if (mMovingRightCountdown <= 0.0f) {
                moveHorizontal(1);
                mMovingRightCountdown += REPEAT_INTERVAL;
            }
        }
        
        float speed = mIsSoftDrop ? SOFT_DROP_SPEED : mSpeed;
        mDistanceRemainder += delta * speed;
        if (mDistanceRemainder >= 1.0f) {
            int distance = (int) mDistanceRemainder;
            
            if (!getActiveTetromino().moveDown(distance, mGameAreaSquares)) {
                nextTetromino();
                if (!getActiveTetromino().isValidPosition(mGameAreaSquares)) {
                    mIsGameOver = true;
                    return;
                }
                addDropScore();
                removeLines();
            }
            
            mDistanceRemainder -= distance;
        }
    }
    
    public void render() {
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            float squareY = i * SQUARE_SIZE + mGameAreaPosition.y;
            for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                int squareIndex = mGameAreaSquares[i][j];
                float squareX = j * SQUARE_SIZE + mGameAreaPosition.x;
                if (squareIndex >= 0) {
                    mBatch.draw(mSquareTextures[squareIndex],
                            squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
                } else {
                    mBatch.draw(mEmptySquareTextures[(i + j) % 2],
                            squareX, squareY, SQUARE_SIZE, SQUARE_SIZE);
                }
            }
        }
        
        if (mActiveTetromino >= 0) {
            getActiveTetromino().render(mBatch, mGameAreaPosition);
        }
    }
    
    public void renderNext(Vector2 position, float squareSize) {
        for (int i = 0; i < 4; i++) {
            float squareY = i * squareSize + position.y;
            for (int j = 0; j < 4; j++) {
                float squareX = j * squareSize + position.x;
                mBatch.draw(mEmptySquareTextures[(i + j) % 2],
                        squareX, squareY, squareSize, squareSize);
            }
        }
        
        if (mNextTetromino >= 0) {
            getNextTetromino().renderNext(mBatch, position, squareSize);
        }
    }
    
    public void startRotate() {
        rotate(1);
        mIsRotating = true;
        mRotatingCountdown = REPEAT_START_OFFSET;
    }
    
    public void endRotate() {
        mIsRotating = false;
        mRotatingCountdown = 0.0f;
    }
    
    public void rotate(int change) {
        if (mActiveTetromino >= 0) {
            getActiveTetromino().rotate(change, mGameAreaSquares);
        }
    }
    
    public void startMoveHorizontal(boolean isLeft) {
        moveHorizontal(isLeft ? -1 : 1);
        if (isLeft) {
            mIsMovingLeft = true;
            mMovingLeftCountdown = REPEAT_START_OFFSET;
        } else {
            mIsMovingRight = true;
            mMovingRightCountdown = REPEAT_START_OFFSET;
        }
    }
    
    public void endMoveHorizontal(boolean isLeft) {
        if (isLeft) {
            mIsMovingLeft = false;
            mMovingLeftCountdown = 0.0f;
        } else {
            mIsMovingRight = false;
            mMovingRightCountdown = 0.0f;
        }
    }
    
    public void moveHorizontal(int distance) {
        if (mActiveTetromino >= 0) {
            getActiveTetromino().moveHorizontal(distance, mGameAreaSquares);
        }
    }
    
    public void setSoftDrop(boolean isSoftDrop) {
        mIsSoftDrop = isSoftDrop;
    }
    
    private void addLinesClearScore(int lines) {
        float scoreMultiplier = 1.0f + (lines - 1) * 0.5f;
        int change = (int)((mLevel + 9) * 5.0f * scoreMultiplier);
        mScore += change;
    }
    
    private void addDropScore() {
        int change = (int)((mLevel + 9) * 1.0f);
        mScore += change;
    }
    
    private void addLines(int change) {
        setLines(mLines + change);
    }
    
    private void setLines(int lines) {
        mLines = lines;
        mLevel = 1 + mLines / 10;
        mSpeed = 1.5f + (mLevel - 1) * 0.25f;
        mSpeed = Math.min(mSpeed, SOFT_DROP_SPEED);
    }
    
    private void nextTetromino() {
        mIsSoftDrop = false;
        
        endRotate();
        endMoveHorizontal(true);
        endMoveHorizontal(false);
        
        if (mActiveTetromino >= 0) {
            getActiveTetromino().applySquares(mGameAreaSquares);
            getActiveTetromino().reset();
        }
        
        mActiveTetromino = mNextTetromino;
        mDistanceRemainder = 0.0f;
        mNextTetromino = MathUtils.random(Tetromino.COUNT - 1);
    }
    
    private void removeLines() {
        mLinesToRemove.clear();
        
        for (int i = 0; i < GAME_AREA_ROWS; i++) {
            if (isRowFull(i)) {
                mLinesToRemove.add(i);
            }
        }
        
        int lineCount = mLinesToRemove.size;
        if (lineCount == 0) {
            return;
        }
        
        for (int lineIndex = 0; lineIndex < mLinesToRemove.size; lineIndex++) {
            int startRow = mLinesToRemove.items[lineIndex] - lineIndex;
            int endRow;
            if (lineIndex < mLinesToRemove.size - 1) {
                endRow = mLinesToRemove.items[lineIndex + 1] - 1 - lineIndex;
            } else {
                endRow = GAME_AREA_ROWS - 1;
            }
            int rowFall = lineIndex + 1;
            for (int i = startRow; i <= endRow; i++) {
                if (i < GAME_AREA_ROWS - rowFall) {
                    for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                        mGameAreaSquares[i][j] = mGameAreaSquares[i + rowFall][j];
                    }
                } else {
                    for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
                        mGameAreaSquares[i][j] = -1;
                    }
                }
            }
        }
        
        addLinesClearScore(lineCount);
        addLines(lineCount);
    }
    
    private boolean isRowFull(int row) {
        for (int j = 0; j < GAME_AREA_COLUMNS; j++) {
            if (mGameAreaSquares[row][j] < 0) {
                return false;
            }
        }
        
        return true;
    }
    
    private Tetromino getActiveTetromino() {
        return mTetrominos[mActiveTetromino];
    }
    
    private Tetromino getNextTetromino() {
        return mTetrominos[mNextTetromino];
    }
    
    public Rectangle getGameAreaRectangle() {
        return mGameAreaRectangle;
    }
    
    public int getScore() {
        return mScore;
    }
    
    public int getLines() {
        return mLines;
    }
    
    public int getLevel() {
        return mLevel;
    }
    
    public boolean isSoftDrop() {
        return mIsSoftDrop;
    }
    
    public boolean isGameOver() {
        return mIsGameOver;
    }
    
    public static Texture[] getSquareTextures(AssetManager assetManager) {
        Texture[] squareTextures = new Texture[Tetromino.COUNT];
        for (int i = 0; i < Tetromino.COUNT; i++) {
            squareTextures[i] = assetManager.get(Tetromino.getTexturePath(i));
        }
        
        return squareTextures;
    }
}
