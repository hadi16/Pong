package edu.up.cs301.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Alex Hadi on 3/10/2018.
 */

public class Paddle extends PongObject {
    private int paddleLength;

    public Paddle(int x, int y, int c) {
        super(x, y, c);
    }

    @Override
    public void draw(Canvas canvas) {
    }

    @Override
    public boolean isCollidingWith(PongObject pongObject) {
        return false;
    }

    public void setPaddleLength(int paddleLength) {
        this.paddleLength = paddleLength;
    }
}
