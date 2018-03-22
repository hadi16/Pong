package edu.up.cs301.pong;

import android.graphics.Canvas;

/**
 * Class: Paddle
 * This class contains the code for the Paddle. Inherits from PongObject.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 21, 2018
 */
public class Paddle extends PongObject {
    // Instance variable for length of paddle.
    private int length;

    // Constants for the paddle.
    private static final int width = 50;
    private static final int BEGINNER_LENGTH = 1100;
    private static final int EXPERT_LENGTH = BEGINNER_LENGTH/2;

    /**
     * Constructor: Paddle
     * Initially set to beginner mode (enhancement).
     */
    public Paddle(int c) {
        super((PongAnimator.width-BEGINNER_LENGTH)/2,
                PongAnimator.height-width, c);
        length = BEGINNER_LENGTH;
    }

    /**
     * Method: draw
     * Draws the paddle.
     *
     * @param c The Canvas object.
     */
    @Override
    public void draw(Canvas c) {
        c.drawRect(posX, posY, posX+length, posY+width, paint);
    }

    /**
     * Method: setExpertMode
     * Sets to expert mode or beginner mode.
     *
     * @param expertMode Expert mode (true) or beginner mode (false).
     */
    public void setExpertMode(boolean expertMode) {
        length = expertMode ? EXPERT_LENGTH : BEGINNER_LENGTH;
        posX = (PongAnimator.width-length)/2;
    }

    // Getters for length and width of paddle.
    public int getLength() {
        return length;
    }
    public static int getWidth() {
        return width;
    }
}
