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
package com.turbogerm.hellhopper.tetrominos;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.turbogerm.hellhopper.GameArea;
import com.turbogerm.hellhopper.ResourceNames;
import com.turbogerm.hellhopper.util.ExceptionThrower;
import com.turbogerm.hellhopper.util.GameMathUtils;
import com.turbogerm.hellhopper.util.IntPair;

public final class Tetromino {
    
    public static final int COUNT = 7;
    
    private final Texture mSquareTexture;
    private final int mTetrominoIndex;
    
    private final IntPair mStartPosition;
    private final IntPair[][] mRotations;
    
    private final IntPair mCurrentPosition;
    private int mCurrentRotation;
    
    private final IntPair mTempPosition;
    
    public Tetromino(IntPair[][] rotations, Texture squareTexture, int tetrominoIndex) {
        
        mSquareTexture = squareTexture;
        mTetrominoIndex = tetrominoIndex;
        
        mStartPosition = new IntPair(3, 17);
        mRotations = rotations;
        mCurrentPosition = new IntPair(0, 0);
        mTempPosition = new IntPair(0, 0);
        
        reset();
    }
    
    public void reset() {
        mCurrentPosition.set(mStartPosition);
        mCurrentRotation = 0;
    }
    
    public void render(SpriteBatch batch, Vector2 gameAreaPosition) {
        IntPair[] rotationData = getCurrRotation();
        for (IntPair coord : rotationData) {
            float squareX = (mCurrentPosition.x + coord.x) * GameArea.SQUARE_SIZE + gameAreaPosition.x;
            float squareY = (mCurrentPosition.y + coord.y) * GameArea.SQUARE_SIZE + gameAreaPosition.y;
            batch.draw(mSquareTexture,
                    squareX, squareY, GameArea.SQUARE_SIZE, GameArea.SQUARE_SIZE);
        }
    }
    
    public void renderNext(SpriteBatch batch, Vector2 position, float squareSize) {
        IntPair[] rotationData = mRotations[0];
        for (IntPair coord : rotationData) {
            float squareX = coord.x * squareSize + position.x;
            float squareY = coord.y * squareSize + position.y;
            batch.draw(mSquareTexture, squareX, squareY, squareSize, squareSize);
        }
    }
    
    public void rotate(int change, int[][] gameAreaSquares) {
        if (!canRotate(change, gameAreaSquares)) {
            return;
        }
        
        mCurrentRotation = GameMathUtils.getPositiveModulus(mCurrentRotation + change, mRotations.length);
    }
    
    private boolean canRotate(int change, int[][] gameAreaSquares) {
        if (mRotations.length <= 1) {
            return false;
        }
        
        int rotation = GameMathUtils.getPositiveModulus(mCurrentRotation + change, mRotations.length);
        IntPair[] rotationData = mRotations[rotation];
        return isValidPosition(rotationData, mCurrentPosition, gameAreaSquares);
    }
    
    public void moveHorizontal(int distance, int[][] gameAreaSquares) {
        int[] movementRange = getHorizontalMovementRange(gameAreaSquares);
        int clampedDistance = MathUtils.clamp(distance, movementRange[0], movementRange[1]);
        
        mCurrentPosition.x += clampedDistance;
    }
    
    public void applySquares(int[][] gameAreaSquares) {
        IntPair[] rotationData = getCurrRotation();
        for (IntPair coord : rotationData) {
            int x = mCurrentPosition.x + coord.x;
            int y = mCurrentPosition.y + coord.y;
            gameAreaSquares[y][x] = mTetrominoIndex;
        }
    }
    
    private int[] getHorizontalMovementRange(int[][] gameAreaSquares) {
        int leftMinMovement = getMinMovement(true, gameAreaSquares);
        int rightMinMovement = getMinMovement(false, gameAreaSquares);
        
        return new int[] { -leftMinMovement, rightMinMovement };
    }
    
    private int getMinMovement(boolean isLeft, int[][] gameAreaSquares) {
        int[] endSquarePositions = getEndSquarePositions(isLeft);
        int minMovement = Integer.MAX_VALUE;
        for (int i = 0; i < endSquarePositions.length; i++) {
            if (endSquarePositions[i] >= 0) {
                int y = i + mCurrentPosition.y;
                int distX;
                if (isLeft) {
                    distX = getDistanceToFirstLeftObstacle(endSquarePositions[i], gameAreaSquares[y]);
                } else {
                    distX = getDistanceToFirstRightObstacle(endSquarePositions[i], gameAreaSquares[y]);
                }
                minMovement = Math.min(minMovement, distX);
            }
        }
        
        return minMovement;
    }
    
    private int[] getEndSquarePositions(boolean isLeft) {
        int[] squarePositions = new int[] { -1, -1, -1, -1 };
        
        IntPair[] rotationData = getCurrRotation();
        for (IntPair intPair : rotationData) {
            int y = intPair.y;
            if (squarePositions[y] < 0) {
                squarePositions[y] = intPair.x;
            } else {
                if (isLeft) {
                    squarePositions[y] = Math.min(squarePositions[y], intPair.x);
                } else {
                    squarePositions[y] = Math.max(squarePositions[y], intPair.x);
                }
            }
        }
        
        offsetSquarePositions(squarePositions, mCurrentPosition.x);
        
        return squarePositions;
    }
    
    private int getDistanceToFirstLeftObstacle(int startX, int[] gameAreaRowSquares) {
        int i;
        for (i = startX - 1; i >= 0; i--) {
            if (gameAreaRowSquares[i] >= 0) {
                break;
            }
        }
        
        return startX - 1 - i;
    }
    
    private int getDistanceToFirstRightObstacle(int startX, int[] gameAreaRowSquares) {
        int i;
        for (i = startX + 1; i < GameArea.GAME_AREA_COLUMNS; i++) {
            if (gameAreaRowSquares[i] >= 0) {
                break;
            }
        }
        
        return i - startX - 1;
    }
    
    private void offsetSquarePositions(int[] squarePositions, int offset) {
        for (int i = 0; i < squarePositions.length; i++) {
            if (squarePositions[i] >= 0) {
                squarePositions[i] += offset;
            }
        }
    }
    
    public boolean moveDown(int distance, int[][] gameAreaSquares) {
        
        boolean canMove = true;
        int i;
        for (i = 1; i <= distance; i++) {
            mTempPosition.set(mCurrentPosition.x, mCurrentPosition.y - i);
            if (!isValidPosition(getCurrRotation(), mTempPosition, gameAreaSquares)) {
                canMove = false;
                break;
            }
        }
        
        int clampedDistance = canMove ? distance : i - 1;
        mCurrentPosition.y -= clampedDistance;
        
        return canMove;
    }
    
    public boolean isValidPosition(int[][] gameAreaSquares) {
        return isValidPosition(getCurrRotation(), mCurrentPosition, gameAreaSquares);
    }
    
    private static boolean isValidPosition(IntPair[] rotationData, IntPair position, int[][] gameAreaSquares) {
        for (IntPair coord : rotationData) {
            if (!isValidCoordinate(
                    position.x + coord.x, position.y + coord.y, gameAreaSquares)) {
                return false;
            }
        }
        
        return true;
    }
    
    private static boolean isValidCoordinate(int x, int y, int[][] gameAreaSquares) {
        if (x < 0 || x >= GameArea.GAME_AREA_COLUMNS) {
            return false;
        }
        
        if (y < 0 || y >= GameArea.GAME_AREA_ROWS) {
            return false;
        }
        
        return gameAreaSquares[y][x] == -1;
    }
    
    private IntPair[] getCurrRotation() {
        return mRotations[mCurrentRotation];
    }
    
    public static String getTexturePath(int index) {
        
        switch (index) {
            case 0: // I
                return ResourceNames.SQUARES_CYAN_TEXTURE;
            case 1: // T
                return ResourceNames.SQUARES_PURPLE_TEXTURE;
            case 2: // L
                return ResourceNames.SQUARES_ORANGE_TEXTURE;
            case 3: // J
                return ResourceNames.SQUARES_BLUE_TEXTURE;
            case 4: // Z
                return ResourceNames.SQUARES_RED_TEXTURE;
            case 5: // S
                return ResourceNames.SQUARES_GREEN_TEXTURE;
            case 6: // O
                return ResourceNames.SQUARES_YELLOW_TEXTURE;
            default:
                ExceptionThrower.throwException("Invalid tetromino index: %d", index);
                return null;
        }
    }
    
    public IntPair[][] getRotations() {
        return mRotations;
    }
}
