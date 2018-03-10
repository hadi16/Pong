package edu.up.cs301.pong;

import android.graphics.Canvas;

public class Paddle extends PongObject {
    private int paddleLength;

    public Paddle(int x, int y, int c) {
        super(x, y, c);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(posX, posY, posX+paddleLength, posY+canvas.getHeight()/5, paint);
    }

    @Override
    public boolean isCollidingWith(PongObject pongObject) {
        return false;
    }

    public int getPaddleLength() {
        return paddleLength;
    }

    public void setPaddleLength(int paddleLength) {
        this.paddleLength = paddleLength;
    }
}
