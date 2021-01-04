package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;
import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;

public final class LShape extends AbstractShape {

    private static final Color COLOR = Color.BLUE;

    public LShape(Point rotationPoint, RotationState state) {
        super(rotationPoint, state, COLOR);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {
        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

        boolean boundaryCheck = rotPx > 0
                && rotPy > 0
                && rotPx < COL_NUM - 1
                && rotPy < ROW_NUM - 1;

        if (!boundaryCheck) {
            return false;
        }

        if (state == RotationState.DOWN) {

            return fillingColors[rotPx + 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx - 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx - 1][rotPy + 1] == BG_COLOR;
        }

        if (state == RotationState.UP) {

            return fillingColors[rotPx - 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx + 1][rotPy] == BG_COLOR
                    && fillingColors[rotPx + 1][rotPy - 1] == BG_COLOR;
        }

        if (state == RotationState.LEFT) {

            return fillingColors[rotPx - 1][rotPy - 1] == BG_COLOR
                    && fillingColors[rotPx][rotPy - 1] == BG_COLOR
                    && fillingColors[rotPx][rotPy + 1] == BG_COLOR;
        }

        return fillingColors[rotPx][rotPy - 1] == BG_COLOR
                && fillingColors[rotPx][rotPy + 1] == BG_COLOR
                && fillingColors[rotPx + 1][rotPy + 1] == BG_COLOR;
    }

    @Override
    public synchronized void updateRotationState() {
        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

        switch (state) {

            case DOWN: {

                points[0].x = rotPx;
                points[0].y = rotPy - 1;

                points[1].x = rotPx;
                points[1].y = rotPy;

                points[3].x = rotPx;
                points[3].y = rotPy + 1;

                points[2].x = rotPx + 1;
                points[2].y = rotPy + 1;

                break;
            }

            case UP: {

                points[1].x = rotPx - 1;
                points[1].y = rotPy - 1;

                points[3].x = rotPx;
                points[3].y = rotPy + 1;

                points[2].x = rotPx;
                points[2].y = rotPy;

                points[0].x = rotPx;
                points[0].y = rotPy - 1;

                break;
            }

            case LEFT: {

                points[0].x = rotPx;
                points[0].y = rotPy;

                points[1].x = rotPx - 1;
                points[1].y = rotPy;

                points[2].x = rotPx + 1;
                points[2].y = rotPy;

                points[3].x = rotPx - 1;
                points[3].y = rotPy + 1;

                break;
            }

            case RIGHT: {

                points[1].x = rotPx - 1;
                points[1].y = rotPy;

                points[3].x = rotPx;
                points[3].y = rotPy;

                points[0].x = rotPx + 1;
                points[0].y = rotPy - 1;

                points[2].x = rotPx + 1;
                points[2].y = rotPy;

                break;
            }
        }
    }
}
