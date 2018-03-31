package edu.up.cs301.pong;

import android.graphics.Canvas;

/**
 * Class: Block
 * This represents the blocks in the Pong game. Inherits from PongObject.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 30, 2018
 */
public class Block extends PongObject {
    // Length and width of each block.
    private int length;
    private int width;

    /**
     * Constructor: Block
     * Block is initialized with its x, y, and color.
     *
     * @param x The x position.
     * @param y The y position.
     * @param l The length of the block
     * @param w The width of the block
     * @param color The color.
     */
    public Block(int x, int y, int l, int w, int color) {
        super(x, y, color);
        length = l;
        width = w;
    }

    /**
     * Method: draw
     * Draws the blocks.
     *
     * @param c The Canvas object.
     */
    @Override
    public void draw(Canvas c) {
        c.drawRect(posX, posY, posX+length, posY+width, paint);
    }

    /**
     * @param b ball to check whether it is colliding with
     * @return int that tells what side the ball is colliding with the block
     *   -1: not hitting the block
     *    0: hitting the block from the bottom
     *    1: hitting the block from the top
     */
    public int isCollidingWith(Ball b) {
        if (b.getPosY()+b.getRadius() >= posY
                && b.getPosX()+b.getRadius() >= posX
                && b.getPosX()-b.getRadius() <= posX+length
                && b.getPosY()-b.getRadius() <= posY+width) {
            if (b.getVelY() <= 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }
}
