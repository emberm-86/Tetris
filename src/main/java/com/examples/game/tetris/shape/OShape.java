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
        resetPoints();

        points[0].x -= 1;
        points[1].x -= 1;
        points[1].y += 1;
        points[3].y += 1;
    }
}
