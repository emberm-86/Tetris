package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;

public final class TShape extends AbstractShape {

  private static final Color COLOR = new Color(255, 0, 255);

  public TShape(Point rotationPoint, RotationState state) {
    super(rotationPoint, state, COLOR);
  }

  @Override
  public boolean isRotatable(Color[][] fillingColors) {
    if (isNotInBounds(1)) {
      return false;
    }

    int rotPx = rotationPoint.x;
    int rotPy = rotationPoint.y;

    if (state == RotationState.LEFT) {
      return fillingColors[rotPx + 1][rotPy] == BG_COLOR;
    }

    if (state == RotationState.RIGHT) {
      return fillingColors[rotPx - 1][rotPy] == BG_COLOR;
    }

    if (state == RotationState.UP) {
      return fillingColors[rotPx][rotPy + 1] == BG_COLOR;
    }

    return fillingColors[rotPx][rotPy - 1] == BG_COLOR;
  }

  @Override
  public synchronized void updateRotationState() {
    resetPoints();

    switch (state) {
      case DOWN:
        points[1].x -= 1;
        points[2].x += 1;
        points[3].y += 1;
        break;

      case UP:
        points[0].y -= 1;
        points[1].x -= 1;
        points[2].x += 1;
        break;

      case LEFT:
        points[1].x -= 1;
        points[2].y -= 1;
        points[3].y += 1;
        break;

      case RIGHT:
        points[0].y -= 1;
        points[2].x += 1;
        points[3].y += 1;
        break;
    }
  }
}
