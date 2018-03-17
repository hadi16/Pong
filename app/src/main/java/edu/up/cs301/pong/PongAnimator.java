package edu.up.cs301.pong;

import android.graphics.*;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;

import edu.up.cs301.animation.Animator;

/**
 * Class: PongAnimator
 * This class is for the animator.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 17, 2018
 */
public class PongAnimator implements Animator {
    // Static variables for width and height of the canvas.
    public static int width = 2048;
    public static int height = 1200;

    // Balls: ArrayList for multiple balls enhancement.
    private ArrayList<Ball> balls = new ArrayList<>();

    // Paddle and Wall objects.
    private Paddle paddle;
    private Wall wall = new Wall();

    // To allow for toggling of the collision.
    private boolean collideMode = false;

    /**
     * Constructor: PongAnimator
     * Initializes the animator with one ball and the paddle.
     *
     * @param ball The Ball object.
     * @param paddle The Paddle object.
     */
    public PongAnimator(Ball ball, Paddle paddle) {
        this.balls.add(ball);
        this.paddle = paddle;
    }

    /**
     * Method: interval
     * Required for Animator implementation (overridden).
     * Interval between animation frames: .03 seconds (about 33 times per sec)
     *
     * @return the time interval between frames in milliseconds.
     */
    @Override
    public int interval() {
        return 30;
    }

    /**
     * Method: backgroundColor
     * Required for Animator implementation (overridden).
     * The background color, which is set to white.
     *
     * @return The background color onto which the animation is drawn.
     */
    @Override
    public int backgroundColor() {
        return Color.rgb(255, 255, 255);
    }

    /**
     * Method: tick
     * Action to perform on clock tick.
     * Required for Animator implementation (overridden).
     *
     * @param c The Canvas object onto which animation is drawn.
     */
    @Override
    public void tick(Canvas c) {
        // Draw wall and paddle.
        wall.draw(c);
        paddle.draw(c);

        /*
         External Citation
         Date: 17 March 2018
         Problem: Could not remove an item from list while iterating over it.
         Resource:
         https://stackoverflow.com/questions/1196586/
         calling-remove-in-foreach-loop-in-java
         Solution: Used iterator directly instead of foreach loop.
         */
        // Increment values of the balls with each tick.
        Iterator<Ball> iterator = balls.iterator();
        while (iterator.hasNext()) {
            Ball ball = iterator.next();

            // Increment the ball position by the velocity.
            ball.setPosX(ball.getPosX() + ball.getVelX());
            ball.setPosY(ball.getPosY() + ball.getVelY());

            // Make sure ball bounces off the walls.
            switch (ball.isHittingWall()) {
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

            // Make ball bounce off paddle.
            if (ball.isCollidingWithPaddle(paddle)) ball.reverseVelY();

            // If collideMode is enabled, check for collisions.
            if (collideMode) {
                for (int i = 0; i < balls.size(); i++) {
                    for (int j = i; j < balls.size(); j++) {
                        if (i != j) {
                            if (balls.get(i).isCollidingWithBall(balls.get(j))) {
                                balls.get(i).reverseVelY();
                                balls.get(i).reverseVelX();
                                balls.get(j).reverseVelX();
                                balls.get(j).reverseVelY();
                            }
                        }
                    }
                }
            }

            // Remove the ball if it goes off the screen.
            if (ball.getPosY() >= height) {
                iterator.remove();
            }
        }

        // All the balls are drawn.
        for (Ball ball : balls) ball.draw(c);
    }

    /**
     * Method: doPause
     * Required by the animator implementation (overridden).
     * Always returns false, which means that it is never paused.
     *
     * @return Indication of whether to pause as a boolean.
     */
    @Override
    public boolean doPause() {
        return false;
    }

    /**
     * Method: doQuit
     * Required by the animator implementation (overridden).
     * Always returns false (never quit).
     *
     * @return Indication of whether to quit as a boolean.
     */
    @Override
    public boolean doQuit() {
        return false;
    }

    /**
     * Method: onTouch
     * Required by the animator implementation (overridden).
     * Reverse each ball's direction when the screen is tapped by the user.
     */
    @Override
    public void onTouch(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            for (Ball b : balls) {
                b.reverseVelX();
                b.reverseVelY();
            }
        }
    }

    /**
     * Method: addBall
     * Add a Ball object to the balls ArrayList.
     *
     * @param ball The Ball object.
     */
    public void addBall(Ball ball) {
        this.balls.add(ball);
    }

    /**
     * Method: toggleCollision
     * Helper method to reverse the value of the collideMode boolean.
     */
    public void toggleCollision(){
        collideMode = !collideMode;
    }
}
