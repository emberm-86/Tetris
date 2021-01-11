package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.COL_NUM;
import static com.examples.game.tetris.common.Constants.ROW_NUM;

/**
 * Parent of all tetrominos.
 * Rotation checks for different states.
 */
public abstract class AbstractShape {

    public static final int RECT_SIZE = 20;
    public static final int RECT_NUM = 4;

    protected Point rotationPoint;
    protected Point[] points;
    protected Color color;
    public RotationState state;

    public enum RotationState {

        DOWN, LEFT, UP, RIGHT;

        public RotationState next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

    public AbstractShape(Point rotP, RotationState state, Color color) {
        this.rotationPoint = rotP;
        this.state = state;
        this.color = color;
        this.points = new Point[RECT_NUM];

        for (int i = 0; i < RECT_NUM; i++) {
            this.points[i] = new AbstractShape.Point(0, 0);
        }

        updateRotationState();
    }

    /**
     * Check the screen bounds.
     */
    protected boolean isNotInBounds(int i) {
        return rotationPoint.x <= 0 || rotationPoint.y <= 0
                || rotationPoint.x >= COL_NUM - i
                || rotationPoint.y >= ROW_NUM - i;
    }

    /**
     * This method called in updateRotationState.
     */
    protected void resetPoints() {
        for (AbstractShape.Point point : points) {
            point.x = rotationPoint.x;
            point.y = rotationPoint.y;
        }
    }

    public Color getColor() {
        return color;
    }

    public Point getRotationPoint() {
        return rotationPoint;
    }

    public RotationState getState() {
        return state;
    }

    public void setState(RotationState state) {
        this.state = state;
    }

    public Point[] getPoints() {
        return points;
    }

    public abstract boolean isRotatable(Color[][] fillingColors);

    public abstract void updateRotationState();

    public static class Point {
        public int x;
        public int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}