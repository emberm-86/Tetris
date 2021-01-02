package com.games.tetris.shape;

import java.awt.Color;

public final class OShape extends AbstractShape {

    private static final Color color = new Color(147, 112, 219);

    public OShape(Point rotatePoint) {
        super(rotatePoint, null, color);
    }

    @Override
    public boolean isRotatable(Color[][] fillingColors) {
        return false;
    }

    @Override
    public void updateRotationState() {

        points[0].x = rotationPoint.x - 1;
        points[0].y = rotationPoint.y;

        points[1].x = rotationPoint.x - 1;
        points[1].y = rotationPoint.y + 1;

        points[2].x = rotationPoint.x;
        points[2].y = rotationPoint.y;

        points[3].x = rotationPoint.x;
        points[3].y = rotationPoint.y + 1;
    }
}
