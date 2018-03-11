package edu.up.cs301.pong;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Class: PongObject
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 10, 2018
 */
public abstract class PongObject {
    protected int posX;
    protected int posY;
    protected Paint paint;

    public PongObject(int x, int y, int c){
        this.posX = x;
        this.posY = y;
        paint = new Paint();
        paint.setColor(c);
    }

    public abstract void draw( Canvas g );

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
    }
}
