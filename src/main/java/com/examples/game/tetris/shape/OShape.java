package com.examples.game.tetris.shape;

import java.awt.Color;

public final class OShape extends AbstractShape {

    private static final Color COLOR = new Color(147, 112, 219);

    public OShape(Point rotationPoint) {
        super(rotationPoint, null, COLOR);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {
        return false;
    }

    @Override
    public void updateRotationState() {
        int rotPx = rotationPoint.x;
        int rotPy = rotationPoint.y;

        points[0].x = rotPx - 1;
        points[0].y = rotPy;

        points[1].x = rotPx - 1;
        points[1].y = rotPy + 1;

        points[2].x = rotPx;
        points[2].y = rotPy;

        points[3].x = rotPx;
        points[3].y = rotPy + 1;
    }
}
