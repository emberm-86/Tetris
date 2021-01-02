package com.games.tetris.service;

import com.games.tetris.shape.AbstractShape;

import java.awt.Color;

import static com.games.tetris.common.Constants.COL_NUM;
import static com.games.tetris.common.Constants.ROW_NUM;
import static com.games.tetris.shape.AbstractShape.ELEMENT_NUM;

/**
 * Util class to check if the current tetromino is movable
 * and if a row is completed and if it is completed then
 * the score must be updated.
 */
public class TetrisService {

    public static int clearRows(Color[][] fillingColors) {
        boolean[] isRowComplete = new boolean[ROW_NUM];
        int rowCountForClear = 0;
        int plusScore = 0;

        for (int i = 0; i < ROW_NUM; i++) {
            isRowComplete[i] = true;

            for (int j = 0; j < COL_NUM; j++) {
                isRowComplete[i] &= fillingColors[j][i] != Color.WHITE;
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

    public static boolean isShapeLanded(AbstractShape abstractShape,
            Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        Color color = abstractShape.getColor();

        boolean isLanded = points[3].y == ROW_NUM - 1;

        for (int i = 0; i < ELEMENT_NUM; i++) {
            isLanded = isLanded
                    || fillingColors[points[i].x][points[i].y + 1] != Color.WHITE;
        }

        if (!isLanded) {
            return false;
        }

        for (int i = 0; i < ELEMENT_NUM; i++) {
            if ((points[i].x >= 0) && (points[i].y >= 0)) {
                fillingColors[points[i].x][points[i].y] = color;
            }
        }

        return true;
    }

    public static boolean isInShape(AbstractShape.Point[] points, int i,
                                    int j) {
        boolean isInShape = false;

        for (int k = 0; k < ELEMENT_NUM; k++) {
            isInShape = isInShape
                    || ((points[k].x == i) && (points[k].y == j));
        }

        return isInShape;
    }

    public static void moveLeft(AbstractShape abstractShape,
                                Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        AbstractShape.Point rotationPoint = abstractShape.getRotationPoint();

        boolean movableToLeft = points[1].x > 0;

        for (int i = 0; i < ELEMENT_NUM; i++) {
            movableToLeft = movableToLeft
                    && fillingColors[points[i].x - 1][points[i].y] == Color.WHITE;
        }

        if (!movableToLeft) {
            return;
        }

        rotationPoint.x += -1;

        for (int i = 0; i < ELEMENT_NUM; i++) {
            points[i].x += -1;
        }
    }

    public static void moveRight(AbstractShape abstractShape,
                                 Color[][] fillingColors) {

        AbstractShape.Point[] points = abstractShape.getPoints();
        AbstractShape.Point rotationPoint = abstractShape.getRotationPoint();

        boolean movableToRight = points[2].x < COL_NUM - 1;

        for (int i = 0; i < ELEMENT_NUM; i++) {
            movableToRight = movableToRight
                    && fillingColors[points[i].x + 1][points[i].y] == Color.WHITE;
        }

        if (!movableToRight) {
            return;
        }

        rotationPoint.x += 1;

        for (int i = 0; i < ELEMENT_NUM; i++) {
            points[i].x += 1;
        }
    }
}
