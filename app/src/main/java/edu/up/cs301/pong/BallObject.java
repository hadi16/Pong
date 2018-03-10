package edu.up.cs301.pong;

import android.graphics.Canvas;

/**
 * Created by Jason on 3/10/2018.
 */

public class BallObject extends PongObject {

    public BallObject(int x, int y, int c ){

        super(x,y,c);

    }

    @Override
    public void draw(Canvas g) {

    }

    @Override
    public boolean isCollidingWith(PongObject pongObject) {
        return false;
    }
}
