package edu.up.cs301.pong;

import android.graphics.Canvas;

/**
 * Class: Paddle
 * This class contains the code for the Paddle. Inherits from PongObject.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 30, 2018
 */
public class Paddle extends PongObject {
    // Instance variables for the paddle.
    private int length;
    private boolean selected;
    private int selectedPos;
    private boolean expertMode;

    // Constants for the paddle (all private).
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
     * Method: setSelected
     * Sets whether or not paddle is selected. If selected, set posX.
     *
     * @param selected Whether or not the paddle is selected.
     * @param posX The posX to set.
     */
    public void setSelected(boolean selected, int posX) {
        this.selected = selected;
        if (!selected) return;
        selectedPos = posX - this.posX;
    }

    /**
     * Method: dragX
     * This method allows the paddle to be dragged in the x direction.
     *
     * @param posX The position of x.
     */
    public void dragX(int posX) {
        if(!selected) return;
        this.posX = posX - selectedPos;
        if (this.posX >= PongAnimator.width - Wall.getWidth() - length) {
            this.posX = PongAnimator.width-Wall.getWidth()-length;
        }
        if (this.posX <= Wall.getWidth()) {
            this.posX = Wall.getWidth();
        }
    }

    /**
     * Method: contains
     * Returns whether the paddle is contained in a given x & y position.
     * @param x The x position to check.
     * @param y The y position to check.
     * @return True if it contains the paddle, otherwise false.
     */
    public boolean contains(float x, float y) {
        int posX = (int)x;
        int posY = (int)y;

        return posY >= PongAnimator.height-Paddle.getWidth()
                && posX >= this.posX
                && posX <= this.posX+length;
    }

    /**
     * Method: setExpertMode
     * Sets to expert mode or beginner mode.
     *
     * @param expertMode Expert mode (true) or beginner mode (false).
     */
    public void setExpertMode(boolean expertMode) {
        this.expertMode = expertMode;
        length = expertMode ? EXPERT_LENGTH : BEGINNER_LENGTH;
        posX = (PongAnimator.width-length)/2;
    }

    // Getters for length, width, and expert mode of paddle.
    public int getLength() {
        return length;
    }
    public void setLength(int length) {
        this.length = length;
    }
    public boolean isExpertMode() {
        return expertMode;
    }

    // Getters for constants.
    public static int getBeginnerLength() {
        return BEGINNER_LENGTH;
    }
    public static int getWidth() {
        return width;
    }
}
