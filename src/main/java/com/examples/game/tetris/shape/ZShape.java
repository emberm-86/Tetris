package com.examples.game.tetris.shape;

import java.awt.Color;

import static com.examples.game.tetris.common.Constants.BG_COLOR;

public final class ZShape extends AbstractShape {

  private static final Color COLOR = new Color(255, 0, 0);

  public ZShape(Point rotationPoint, RotationState state) {
    super(rotationPoint, state, COLOR);
  }

  @Override
  public boolean isRotatable(Color[][] fillingColors) {
    if (isNotInBounds(1)) {
      return false;
    }

    int rotPx = rotationPoint.x;
    int rotPy = rotationPoint.y;

    if (state == RotationState.DOWN || state == RotationState.UP) {
      return fillingColors[rotPx + 1][rotPy + 1] == BG_COLOR
          && fillingColors[rotPx - 1][rotPy] == BG_COLOR;
    }

    return fillingColors[rotPx + 1][rotPy] == BG_COLOR
        && fillingColors[rotPx + 1][rotPy - 1] == BG_COLOR;
  }

  @Override
  public synchronized void updateRotationState() {
    resetPoints();

    if (state == RotationState.UP || state == RotationState.DOWN) {
      points[0].x += 1;
      points[0].y -= 1;
    } else {
      points[1].x -= 1;
      points[2].y += 1;
    }
    points[2].x += 1;
    points[3].y += 1;
  }
}