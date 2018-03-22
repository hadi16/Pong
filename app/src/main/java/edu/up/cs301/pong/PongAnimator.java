package edu.up.cs301.pong;

import android.content.Context;
import android.graphics.*;
import android.view.MotionEvent;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;

import edu.up.cs301.animation.Animator;

/**
 * Class: PongAnimator
 * This class is for the animator.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 21, 2018
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

    // To allow for pause of the game
    private boolean pauseMode = false;

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

        for (Ball ball : balls) ball.draw(c);

        if( pauseMode ) return ;

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

            // Increment the ball position by the velocity.
            ball.setPosX(ball.getPosX() + ball.getVelX());
            ball.setPosY(ball.getPosY() + ball.getVelY());

            // Radius is incremented or decremented with helper method.
            ball.changeRadius();

            // Remove the ball if it goes off the screen.
            if (ball.getPosY() >= height) {
                iterator.remove();
            }
        }

        // Color of all PongObjects are changed.
        for (Ball ball : balls) ball.setRandomColor();
        wall.setRandomColor();
        paddle.setRandomColor();
    }

    /**
     * Method: saveState
     * This is called when the application is closed.
     *
     * @param osw The file output stream.
     */
    public void saveState(OutputStreamWriter osw) {
        try {
            for (Ball b : balls) {
                osw.write(Integer.toString(b.posX) + "\n");
                osw.write(Integer.toString(b.posY) + "\n");
                osw.write(Integer.toString(b.paint.getColor()) + "\n");
                osw.write(Integer.toString(b.getVelX()) + "\n");
                osw.write(Integer.toString(b.getVelY()) + "\n");
                osw.write(Integer.toString(b.getRadius()) + "\n");
                osw.write(Integer.toString(b.getChangeSize()) + "\n");
            }
        }
        catch (IOException ioe) {
        }
    }

    /**
     * Method: readState
     * This is called when the application is reopened.
     */
    public void readState() {

    }

    /**
     * Method: doPause
     * Required by the animator implementation (overridden).
     * Always returns false.
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
     * Method: togglePause
     * Helper method to reverse the value of the pauseMode boolean.
     */
    public void togglePause() {
        pauseMode = !pauseMode;
    }

    // Getter for pauseMode.
    public boolean isPauseMode() {
        return pauseMode;
    }
}
