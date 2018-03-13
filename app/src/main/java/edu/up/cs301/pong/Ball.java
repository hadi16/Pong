package edu.up.cs301.pong;

import android.graphics.Canvas;

import java.util.Random;

public class Ball extends PongObject {
    private Random rand = new Random();

    private int velX = rand.nextInt(101)-50;
    private int velY = rand.nextInt(101)-50;

    private int radius = 60;

    public Ball(int c) {
        // Ball shouldn't be all the way at the bottom of screen upon creation.
        super(new Random().nextInt(1848)+100, new Random().nextInt(764)+100, c);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(posX, posY, radius, paint);
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

    public int getRadius() { return radius; }

    public int isHittingWall(int width, int wallWidth){
        if (posX-radius <= wallWidth && velX <= 0) {
            return 1;
        }

        if (posY-radius <= wallWidth && velY <= 0) {
            return 2;
        }

        if (posX+radius >= width-wallWidth && velX >= 0) {
            return 3;
        }

        return 0;
    }

    public boolean isCollidingWithPaddle(int height, Paddle paddle){
        return posY+radius >= height-paddle.getWidth()/2 && posX >= paddle.getPosX()
                && posX <= paddle.getPosX()+paddle.getLength();
    }

    public void reverseVelX() {
        velX = (velX*-1);
    }

    public void reverseVelY() {velY = (velY*-1); }

    public boolean isCollidingWithBall( Ball b ){

        double distance = Math.pow(Math.pow((posX - b.getPosX()),2) + Math.pow((posY - b.getPosY()),2),.5);

        return distance <= b.getRadius();

    }

}
