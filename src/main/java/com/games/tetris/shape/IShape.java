package com.games.tetris.shape;

import java.awt.Color;

import static com.games.tetris.common.Constants.COL_NUM;
import static com.games.tetris.common.Constants.ROW_NUM;

public final class IShape extends AbstractShape {

    private static final Color color = new Color(160, 82, 45);

    public IShape(Point rotatePoint, RotationState state) {
        super(rotatePoint, state, color);
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
            return fillingColors[rotationPoint.x - 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x + 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x + 2][rotationPoint.y] == Color.WHITE;
        }

        return fillingColors[rotationPoint.x][rotationPoint.y - 1] == Color.WHITE
                && fillingColors[rotationPoint.x][rotationPoint.y + 1] == Color.WHITE
                && fillingColors[rotationPoint.x][rotationPoint.y + 2] == Color.WHITE;
    }
}