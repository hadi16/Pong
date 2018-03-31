package edu.up.cs301.pong;

import android.graphics.Canvas;

/**
 * Class: Block
 * This represents the blocks in the Pong game.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 30, 2018
 */
public class Block extends PongObject {
    private int length;
    private int width;

    private boolean isSmashed;

    /**
     * Constructor: PongObject
     * PongObject is initialized with its x, y, and color.
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

    @Override
    public void draw(Canvas c) {
        if (isSmashed) return;
        c.drawRect(posX, posY, posX+length, posY+width, paint);
    }

    /**
     * @param b ball to check whether it is colliding with
     * @return int that tells what side the ball is colliding with the block
     *   -1 - not hitting the block
     *   0 - hitting the block from the bottom
     *   1 - hitting the block from the top
     */
    public int isCollidingWith( Ball b ){

        if (b.getPosY()+b.getRadius() >= posY
                && b.getPosX()+b.getRadius() >= posX
                && b.getPosX()-b.getRadius() <= posX+length
                && b.getPosY()-b.getRadius() <= posY+width) {

            isSmashed = true;

            if (b.getVelY() <= 0) {
                return 0;
            } else {
                return 1;
            }
        } else {
            return -1;
        }
    }

    public boolean isSmashed(){
        return isSmashed;
    }
}
