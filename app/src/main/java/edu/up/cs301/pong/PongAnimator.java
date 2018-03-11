package edu.up.cs301.pong;

import android.graphics.*;
import android.view.MotionEvent;

import edu.up.cs301.animation.Animator;

public class PongAnimator implements Animator {
    private Paint wallPaint = new Paint();

    private int velX = 25;
    private int velY = 10;

    private int width;
    private int height;

    private Ball ball;
    private Paddle paddle;

    public PongAnimator(Ball ball, Paddle paddle) {
        this.ball = ball;
        this.paddle = paddle;
    }

    /**
     * Interval between animation frames: .03 seconds (i.e., about 33 times
     * per second).
     *
     * @return the time interval between frames, in milliseconds.
     */
    public int interval() {
        return 30;
    }

    /**
     * The background color: a light blue.
     *
     * @return the background color onto which we will draw the image.
     */
    public int backgroundColor() {
        // create/return the background color
        return Color.rgb(255, 255, 255);
    }

    /**
     * The wall color: a black color.
     *
     * @return the wall color onto which we will draw the image.
     */
    private int wallColor() {
        return Color.rgb(100, 100, 100);
    }

    /**
     * Action to perform on clock tick
     *
     * @param c the graphics object on which to draw
     */
    public void tick(Canvas c) {
        width = c.getWidth();
        height = c.getHeight();

        wallPaint.setColor(wallColor());
        c.drawRect(0, 0, 100, height, wallPaint);
        c.drawRect(0, 0, width, 100, wallPaint);
        c.drawRect(width-100, 0, width, height, wallPaint);

        // Draw the ball in the correct position.
        Paint ballPaint = new Paint();
        ballPaint.setColor(Color.BLACK);

        ball.setPosX(ball.getPosX()+velX);
        ball.setPosY(ball.getPosY()+velY);

        switch (isHittingWall()) {
            case 1:
                velX *= -1;
                break;
            case 2:
                velY *= -1;
                break;
            case 3:
                velX *= -1;
                break;
        }

        if (collidingWithPaddle()) {
            velY *= -1;
        }

        ball.draw(c);
        paddle.draw(c);
    }

    /**
     * Tells that we never pause.
     *
     * @return indication of whether to pause
     */
    public boolean doPause() {
        return false;
    }

    /**
     * Tells that we never stop the animation.
     *
     * @return indication of whether to quit.
     */
    public boolean doQuit() {
        return false;
    }

    /**
     * reverse the ball's direction when the screen is tapped
     */
    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            velX *= -1;
            velY *= -1;
        }
    }

    private boolean collidingWithPaddle() {
        return ball.getPosY() >= height-100 && ball.getPosX() >= paddle.getPosX()
                && ball.getPosX() <= paddle.getPosX()+paddle.getPaddleLength();
    }

    private int isHittingWall() {
        if (ball.getPosX() <= 100) {
            return 1;
        }

        if (ball.getPosY() <= 100) {
            return 2;
        }

        if (ball.getPosX() >= width-100) {
            return 3;
        }

        return 0;
    }
}
