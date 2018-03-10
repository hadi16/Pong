package edu.up.cs301.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Jason on 3/10/2018.
 */

public abstract class PongObject {

    private int x;
    private int y;

    private Paint paint;

    public PongObject( int x, int y, int c ){

        this.x = x;
        this.y = y;
        paint = new Paint();
        paint.setColor(c);


    }

    public abstract void draw( Canvas g );


}
