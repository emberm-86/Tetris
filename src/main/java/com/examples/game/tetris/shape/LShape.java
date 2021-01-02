package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;

public final class LShape extends AbstractShape {

    private static final Color color = Color.BLUE;

    public LShape(Point rotatePoint, RotationState state) {
        super(rotatePoint, state, color);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {

        boolean boundaryCheck = rotationPoint.x > 0
                && rotationPoint.y > 0
                && rotationPoint.x < COL_NUM - 1
                && rotationPoint.y < ROW_NUM - 1;

        if (!boundaryCheck) {
            return false;
        }

        if (state == RotationState.DOWN) {

            return fillingColors[rotationPoint.x + 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x - 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x - 1][rotationPoint.y + 1] == Color.WHITE;
        }

        if (state == RotationState.UP) {

            return fillingColors[rotationPoint.x - 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x + 1][rotationPoint.y] == Color.WHITE
                    && fillingColors[rotationPoint.x + 1][rotationPoint.y - 1] == Color.WHITE;
        }

        if (state == RotationState.LEFT) {

            return fillingColors[rotationPoint.x - 1][rotationPoint.y - 1] == Color.WHITE
                    && fillingColors[rotationPoint.x][rotationPoint.y - 1] == Color.WHITE
                    && fillingColors[rotationPoint.x][rotationPoint.y + 1] == Color.WHITE;
        }

        return fillingColors[rotationPoint.x][rotationPoint.y - 1] == Color.WHITE
                && fillingColors[rotationPoint.x][rotationPoint.y + 1] == Color.WHITE
                && fillingColors[rotationPoint.x + 1][rotationPoint.y + 1] == Color.WHITE;
    }

    @Override
    public synchronized void updateRotationState() {

        switch (state) {

            case DOWN: {

                points[0].x = rotationPoint.x;
                points[0].y = rotationPoint.y - 1;

                points[1].x = rotationPoint.x;
                points[1].y = rotationPoint.y;

                points[3].x = rotationPoint.x;
                points[3].y = rotationPoint.y + 1;

                points[2].x = rotationPoint.x + 1;
                points[2].y = rotationPoint.y + 1;

                break;
            }

            case UP: {

                points[1].x = rotationPoint.x - 1;
                points[1].y = rotationPoint.y - 1;

                points[3].x = rotationPoint.x;
                points[3].y = rotationPoint.y + 1;

                points[2].x = rotationPoint.x;
                points[2].y = rotationPoint.y;

                points[0].x = rotationPoint.x;
                points[0].y = rotationPoint.y - 1;

                break;
            }

            case LEFT: {

                points[0].x = rotationPoint.x;
                points[0].y = rotationPoint.y;

                points[1].x = rotationPoint.x - 1;
                points[1].y = rotationPoint.y;

                points[2].x = rotationPoint.x + 1;
                points[2].y = rotationPoint.y;

                points[3].x = rotationPoint.x - 1;
                points[3].y = rotationPoint.y + 1;

                break;
            }

            case RIGHT: {

                points[1].x = rotationPoint.x - 1;
                points[1].y = rotationPoint.y;

                points[3].x = rotationPoint.x;
                points[3].y = rotationPoint.y;

                points[0].x = rotationPoint.x + 1;
                points[0].y = rotationPoint.y - 1;

                points[2].x = rotationPoint.x + 1;
                points[2].y = rotationPoint.y;

                break;
            }
        }
    }
}
