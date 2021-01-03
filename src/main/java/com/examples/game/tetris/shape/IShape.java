package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BACKGROUND_COLOR;
import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;

public final class IShape extends AbstractShape {

    private static final Color COLOR = new Color(160, 82, 45);

    public IShape(Point rotatePoint, RotationState state) {
        super(rotatePoint, state, COLOR);
    }

    @Override
    public synchronized void updateRotationState() {

        if (state == RotationState.UP || state == RotationState.DOWN) {

            points[0].x = rotationPoint.x;
            points[0].y = rotationPoint.y - 1;

            points[1].x = rotationPoint.x;
            points[1].y = rotationPoint.y;

            points[2].x = rotationPoint.x;
            points[2].y = rotationPoint.y + 1;

            points[3].x = rotationPoint.x;
            points[3].y = rotationPoint.y + 2;

        } else {

            points[1].x = rotationPoint.x - 1;
            points[1].y = rotationPoint.y;

            points[0].x = rotationPoint.x;
            points[0].y = rotationPoint.y;

            points[2].x = rotationPoint.x + 2;
            points[2].y = rotationPoint.y;

            points[3].x = rotationPoint.x + 1;
            points[3].y = rotationPoint.y;
        }
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {

        boolean boundaryCheck = rotationPoint.x > 0
                && rotationPoint.y > 0
                && rotationPoint.x < COL_NUM - 2
                && rotationPoint.y < ROW_NUM - 2;

        if (!boundaryCheck) {
            return false;
        }

        if (state == RotationState.UP || state == RotationState.DOWN) {
            return fillingColors[rotationPoint.x - 1][rotationPoint.y] == BACKGROUND_COLOR
                    && fillingColors[rotationPoint.x + 1][rotationPoint.y] == BACKGROUND_COLOR
                    && fillingColors[rotationPoint.x + 2][rotationPoint.y] == BACKGROUND_COLOR;
        }

        return fillingColors[rotationPoint.x][rotationPoint.y - 1] == BACKGROUND_COLOR
                && fillingColors[rotationPoint.x][rotationPoint.y + 1] == BACKGROUND_COLOR
                && fillingColors[rotationPoint.x][rotationPoint.y + 2] == BACKGROUND_COLOR;
    }
}