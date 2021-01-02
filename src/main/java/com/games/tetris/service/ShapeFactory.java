package com.games.tetris.service;

import com.games.tetris.shape.AbstractShape;
import com.games.tetris.shape.IShape;
import com.games.tetris.shape.JShape;
import com.games.tetris.shape.LShape;
import com.games.tetris.shape.OShape;
import com.games.tetris.shape.SShape;
import com.games.tetris.shape.TShape;
import com.games.tetris.shape.ZShape;

import java.util.Random;

/**
 * Factory class for creating a new tetromino.
 */
public class ShapeFactory {

    public static final int SHAPE_TYPE_NUM = 7;

    private static final Random ran = new Random();

    public static AbstractShape createShape(int x, int y) {

        switch (ran.nextInt(SHAPE_TYPE_NUM)) {

            case 0:
                return new IShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.LEFT);

            case 1:
                return new OShape(new AbstractShape.Point(x, y));

            case 2:
                return new ZShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.LEFT);

            case 3:
                return new SShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.LEFT);

            case 4:
                return new TShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.DOWN);

            case 5:
                return new LShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.DOWN);

            case 6:
                return new JShape(new AbstractShape.Point(x, y),
                        AbstractShape.RotationState.DOWN);

            default:
                return null;
        }
    }
}
