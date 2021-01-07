package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;
import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;

public final class IShape extends AbstractShape {

    private static final Color COLOR = new Color(160, 82, 45);

    public IShape(Point rotationPoint, RotationState state) {
        super(rotationPoint, state, COLOR);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {
        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

        boolean boundaryCheck = rotPx > 0
                && rotPy > 0
                && rotPx < COL_NUM - 2
                && rotPy < ROW_NUM - 2;

        if (!boundaryCheck) {
            return false;
        }

        if (state == RotationState.UP || state == RotationState.DOWN) {

            return fillingColors[rotPx - 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx + 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx + 2][rotPy] == BG_COLOR;
        }

        return fillingColors[rotPx][rotPy - 1] == BG_COLOR
                && fillingColors[rotPx][rotPy + 1] == BG_COLOR
                && fillingColors[rotPx][rotPy + 2] == BG_COLOR;
    }

    @Override
    public synchronized void updateRotationState() {
        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

        if (state == RotationState.UP || state == RotationState.DOWN) {

            points[0].x = rotPx;
            points[0].y = rotPy - 1;

            points[1].x = rotPx;
            points[1].y = rotPy;

            points[2].x = rotPx;
            points[2].y = rotPy + 1;

            points[3].x = rotPx;
            points[3].y = rotPy + 2;

        } else {

            points[1].x = rotPx - 1;
            points[1].y = rotPy;

            points[0].x = rotPx;
            points[0].y = rotPy;

            points[2].x = rotPx + 2;
            points[2].y = rotPy;

            points[3].x = rotPx + 1;
            points[3].y = rotPy;
        }
    }
}