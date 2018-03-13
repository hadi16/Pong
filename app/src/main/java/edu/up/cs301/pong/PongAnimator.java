package edu.up.cs301.pong;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;

import java.util.ArrayList;

import edu.up.cs301.animation.Animator;

public class PongAnimator implements Animator {
    private Paint wallPaint = new Paint();

    private int width;
    private int height;

    private static final int wallWidth = 100;

    private ArrayList<Ball> balls;
    private Paddle paddle;

    private boolean collideMode = false;

    public PongAnimator(Ball ball, Paddle paddle) {
        balls = new ArrayList<>();
        this.balls.add(ball);
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
        return Color.rgb(100,100,100);
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
        c.drawRect(0, 0, wallWidth, height, wallPaint);
        c.drawRect(0, 0, width, wallWidth, wallPaint);
        c.drawRect(width-wallWidth, 0, width, height, wallPaint);

        // Draw the ball in the correct position.
        Paint ballPaint = new Paint();
        ballPaint.setColor(Color.BLACK);
        for( Ball ball : balls ) {
            ball.setPosX(ball.getPosX() + ball.getVelX());
            ball.setPosY(ball.getPosY() + ball.getVelY());

            switch (ball.isHittingWall(width, wallWidth)) {
                case 1:
                    ball.reverseVelX();
                    break;
                case 2:
                    ball.reverseVelY();
                    break;
                case 3:
                    ball.reverseVelX();
                    break;
            }

            if (ball.isCollidingWithPaddle(height,paddle)) {
                ball.reverseVelY();
            }

            if( collideMode ){
                //Log.i("ran","ran");

                for( int i = 0; i < balls.size(); i++ ){

                    for( int j = i; j < balls.size(); j++ ){

                        if( i != j ){

                            if( balls.get(i).isCollidingWithBall(balls.get(j))){
                                balls.get(i).reverseVelY();
                                balls.get(i).reverseVelX();
                                balls.get(j).reverseVelX();
                                balls.get(j).reverseVelY();
                            }

                        }



                    }

                }


            }

            ball.draw(c);
            paddle.draw(c);

            if (ball.getPosY() >= height) {
                balls.remove(ball);
                break;
            }
        }
        for(Ball b : balls ){
            b.draw(c);
        }

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
            for( Ball b : balls ){
                b.reverseVelX();
                b.reverseVelY();
            }
        }
    }




    public void addBall(Ball ball) {
        this.balls.add(ball);
    }

    public void toggleCollision(){
        collideMode = !collideMode;
    }

}
