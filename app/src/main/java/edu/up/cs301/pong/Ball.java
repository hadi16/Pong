package edu.up.cs301.pong;

import android.graphics.Canvas;

import java.util.Random;

public class Ball extends PongObject {
    private Random rand = new Random();

    private int velX = rand.nextInt(101)-50;
    private int velY = rand.nextInt(101)-50;

    public Ball(int c) {
        // Ball shouldn't be all the way at the bottom of screen upon creation.
        super(new Random().nextInt(1848)+100, new Random().nextInt(764)+100, c);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(posX, posY, 60, paint);
    }

    public int getVelX() {
        return velX;
    }

    public void setVelX(int velX) {
        this.velX = velX;
    }

    public int getVelY() {
        return velY;
    }

    public void setVelY(int velY) {
        this.velY = velY;
    }
}
