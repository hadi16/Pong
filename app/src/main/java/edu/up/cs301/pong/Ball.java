package edu.up.cs301.pong;

import android.graphics.Canvas;

public class Ball extends PongObject {
    public Ball(int x, int y, int c ){
        super(x,y,c);
    }

    @Override
    public void draw(Canvas g) {
        g.drawCircle(posX, posY, 60, paint);
    }

    @Override
    public boolean isCollidingWith(PongObject pongObject) {
        if (pongObject instanceof Paddle) {
            Paddle paddle = (Paddle) pongObject;
            if (this.posY <= paddle.posY && this.posX >= paddle.posX && this.posX <= paddle.posX+paddle.getPaddleLength()) {
                return true;
            }
        }
        return false;
    }
}
