package edu.up.cs301.pong;

import android.graphics.Canvas;
import android.graphics.Color;

/**
 * Class: Wall
 * This class contains the code to draw the walls. Inherits from PongObject.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 30, 2018
 */
public class Wall extends PongObject {
    // The wall width.
    private static final int width = 50;

    /**
     * Constructor: Wall
     * Wall is initialized with a x & y of -1 (not applicable to the wall).
     */
    public Wall() {
        super(-1, -1, Color.GRAY);
    }

    /**
     * Method: draw
     * Draws the wall.
     *
     * @param c The Canvas object.
     */
    @Override
    public void draw(Canvas c) {
        int canvasHeight = PongAnimator.height;
        int canvasWidth = PongAnimator.width;

        // The three walls are drawn.
        c.drawRect(0, 0, width, canvasHeight, paint);
        c.drawRect(0, 0, canvasWidth, width, paint);
        c.drawRect(canvasWidth-width, 0, canvasWidth, canvasHeight, paint);
    }

    // Getter for static & private width variable.
    public static int getWidth() {
        return width;
    }
}
