package edu.up.cs301.pong;

import android.graphics.Canvas;

public class Paddle extends PongObject {
    private int length;
    private static final int width = 100;

    public Paddle(int c) {
        // Start with beginner mode.
        super(474, 1164, c);
        length = 1100;
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawRect(posX, posY, posX+length, posY+100, paint);
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }
}
