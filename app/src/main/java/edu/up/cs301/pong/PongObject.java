package edu.up.cs301.pong;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * Class: PongObject
 * Superclass for all of the objects in the Pong game.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 30, 2018
 */
public abstract class PongObject {
    // Instance variables (all Pong objects have x, y, paint).
    protected int posX;
    protected int posY;
    protected Paint paint;

    /**
     * Constructor: PongObject
     * PongObject is initialized with its x, y, and color.
     *
     * @param x The x position.
     * @param y The y position.
     * @param c The color.
     */
    public PongObject(int x, int y, int c) {
        posX = x;
        posY = y;
        paint = new Paint();
        paint.setColor(c);
    }

    /**
     * Method: setRandomColor
     * Paint color is set to a random color.
     */
    public void setRandomColor() {
        Random random = new Random();
        paint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));
    }

    /**
     * Method: draw
     * Executed when a PongObject needs to be drawn. An abstract method.
     * @param c The Canvas object.
     */
    public abstract void draw(Canvas c);


    // Getters and setters for posX and posY.
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
