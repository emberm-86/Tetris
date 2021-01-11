package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;

public final class LShape extends AbstractShape {

    private static final Color COLOR = Color.BLUE;

    public LShape(Point rotationPoint, RotationState state) {
        super(rotationPoint, state, COLOR);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {
        if (isNotInBounds(1)) {
            return false;
        }

        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

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
        resetPoints();

        switch (state) {

            case DOWN:
                points[0].y -= 1;
                points[2].x += 1;
                points[2].y += 1;
                points[3].y += 1;
                break;

            case UP:
                points[0].y -= 1;
                points[1].x -= 1;
                points[1].y -= 1;
                points[3].y += 1;
                break;

            case LEFT:
                points[1].x -= 1;
                points[2].x += 1;
                points[3].x -= 1;
                points[3].y += 1;
                break;

            case RIGHT:
                points[0].x += 1;
                points[0].y -= 1;
                points[1].x -= 1;
                points[2].x += 1;
                break;
        }
    }
}
