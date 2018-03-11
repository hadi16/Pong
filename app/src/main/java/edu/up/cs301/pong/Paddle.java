package edu.up.cs301.pong;

import android.graphics.Canvas;

public class Paddle extends PongObject {
    private int paddleLength;

    public Paddle(int c) {
        // Start with beginner mode.
        super(474, 1164, c);
        paddleLength = 1100;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(posX, posY, posX+paddleLength, posY+100, paint);
    }

    public int getPaddleLength() {
        return paddleLength;
    }

    public void setPaddleLength(int paddleLength) {
        this.paddleLength = paddleLength;
    }
}
