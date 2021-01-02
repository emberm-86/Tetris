package com.games.tetris.shape;

import java.awt.Color;

/**
 * Parent of all tetrominos.
 * Rotation checks for different states.
 */

public abstract class AbstractShape {

    public static final int SIZE = 20;
    public static final int ELEMENT_NUM = 4;

    protected Point rotationPoint;
    protected Point[] points;
    protected Color color;

    public enum RotationState {

        DOWN, LEFT, UP, RIGHT;

        public RotationState next() {
            return values()[(ordinal() + 1) % values().length];
        }
    }

    public RotationState state;

    public AbstractShape(Point rotatePoint, RotationState state, Color color) {
        this.color = color;
        this.rotationPoint = rotatePoint;
        this.state = state;

        this.points = new Point[ELEMENT_NUM];

        for (int i = 0; i < ELEMENT_NUM; i++) {
            this.points[i] = new AbstractShape.Point(0, 0);
        }

        updateRotationState();
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