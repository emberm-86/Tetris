package com.examples.game.tetris.service;

import com.examples.game.tetris.shape.AbstractShape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;
import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;
import static com.examples.game.tetris.shape.AbstractShape.RECT_NUM;

/**
 * Service class to check if the current tetromino is movable
 * or if a row is completed and if it is completed then
 * the score must be updated.
 */
public class TetrisService {

    /**
     * Clear completed rows and check the scores.
     */
    public int clearRows(Color[][] fillingColors) {
        boolean[] isRowComplete = new boolean[ROW_NUM];
        int rowCountForClear = 0;
        int plusScore = 0;

        for (int i = 0; i < ROW_NUM; i++) {
            isRowComplete[i] = true;

            for (int j = 0; j < COL_NUM; j++) {
                isRowComplete[i] &= fillingColors[j][i] != BG_COLOR;
            }

            if (isRowComplete[i]) {
                rowCountForClear++;

                for (int k = i; k > 0; k--) {
                    for (int l = 0; l < COL_NUM; l++) {
                        fillingColors[l][k] = fillingColors[l][k - 1];
                    }
                }
            }
        }

        switch (rowCountForClear) {
            case 1:
                plusScore = 40;
                break;

            case 2:
                plusScore = 100;
                break;

            case 3:
                plusScore = 300;
                break;

            case 4:
                plusScore = 1200;
                break;
        }

        return plusScore;
    }

    /**
     * Check if the actual tetromino is landed.
     */
    public boolean isActShapeLanded(AbstractShape abstractShape,
                                           Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        Color color = abstractShape.getColor();

        boolean isLanded = points[3].y == ROW_NUM - 1;

        for (int i = 0; i < RECT_NUM; i++) {
            int x = points[i].x;
            int y = points[i].y;

            if (y >= 0 && x < COL_NUM && y < ROW_NUM - 1) {
                isLanded = isLanded
                        || fillingColors[x][y + 1] != BG_COLOR;
            }
        }

        if (!isLanded) {
            return false;
        }

        for (int i = 0; i < RECT_NUM; i++) {
            int x = points[i].x;
            int y = points[i].y;

            if (y >= 0 && x < COL_NUM && y < ROW_NUM) {
                fillingColors[x][y] = color;
            }
        }

        return true;
    }

    /**
     * Check if the new tetromino must be drawn
     * before the game session is over.
     * Partial tetromino will not be drawn.
     */
    public boolean isActShapeCannotBeLanded(AbstractShape abstractShape,
                                                   Color[][] fillingColors) {
        boolean cannotBeLanded = false;

        AbstractShape.Point[] points = abstractShape.getPoints();

        for (int i = 0; i < RECT_NUM; i++) {
            int x = points[i].x;
            int y = points[i].y;

            if (y >= 0 && x < COL_NUM && y < ROW_NUM) {
                cannotBeLanded = cannotBeLanded
                        || fillingColors[x][y] != BG_COLOR;
            }
        }

        return cannotBeLanded;
    }

    /**
     * In repaint method it is checked,
     * if a rectangle is a part of the actual tetromino.
     */
    public boolean isInActShape(AbstractShape.Point[] points, int i,
                                       int j) {
        boolean inActShape = false;

        for (int k = 0; k < RECT_NUM; k++) {
            inActShape = inActShape
                    || (points[k].x == i && points[k].y == j);
        }

        return inActShape;
    }

    /**
     * Check if the tetromino is movable to left.
     */
    public void moveLeft(AbstractShape abstractShape,
                                Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        AbstractShape.Point rotationPoint = abstractShape.getRotationPoint();

        boolean movableToLeft = points[1].x > 0;

        for (int i = 0; i < RECT_NUM; i++) {
            int x = points[i].x;
            int y = points[i].y;

            if (x > 0 && y >= 0 && y < ROW_NUM) {
                movableToLeft = movableToLeft
                        && fillingColors[x - 1][y] == BG_COLOR;
            }
        }

        if (!movableToLeft) {
            return;
        }

        rotationPoint.x += -1;
        abstractShape.updateRotationState();
    }

    /**
     * Check if the tetromino is movable to right.
     */
    public void moveRight(AbstractShape abstractShape,
                                 Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        AbstractShape.Point rotationPoint = abstractShape.getRotationPoint();

        boolean movableToRight = points[2].x < COL_NUM - 1;

        for (int i = 0; i < RECT_NUM; i++) {
            int x = points[i].x;
            int y = points[i].y;

            if (y >= 0 && x < COL_NUM - 1 && y < ROW_NUM) {
                movableToRight = movableToRight
                        && fillingColors[x + 1][y] == BG_COLOR;
            }
        }

        if (!movableToRight) {
            return;
        }

        rotationPoint.x += 1;
        abstractShape.updateRotationState();
    }
}
