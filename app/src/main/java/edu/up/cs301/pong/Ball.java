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
}
