package edu.up.cs301.pong;

import android.graphics.*;
import android.util.Log;
import android.view.MotionEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

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

    Random random;
    private Paint scorePaint;

    private int scoreCount;

    // Balls: ArrayList for multiple balls enhancement.
    private ArrayList<Ball> balls;

    // Paddle and Wall objects.
    private Paddle paddle;
    private Wall wall = new Wall();

    // To allow for pause of the game
    private boolean pauseMode = false;

    //speed for slowing down and speeding up the balls
    private double speed = 0.5;

    /**
     * Constructor: PongAnimator
     * Initializes the animator with one ball and the paddle.
     *
     * @param ball The Ball object.
     * @param paddle The Paddle object.
     */
    public PongAnimator(Ball ball, Paddle paddle) {
        if (balls == null) {
            balls = new ArrayList<>();
            balls.add(ball);
        }
        this.paddle = paddle;

        this.scoreCount = 0;
        scorePaint = new Paint();
        scorePaint.setTextSize(150f);

        random = new Random();
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
        return 60;
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
        scorePaint.setColor(Color.rgb(random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));
        c.drawText("Score: " + scoreCount, c.getWidth()/2 - 300,c.getHeight()/2,scorePaint);

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

            int incrementVelX = (10-(int)(Math.random()*21));
            int incrementVelY = (10-(int)(Math.random()*21));

            // Make sure ball bounces off the walls.
            switch (ball.isHittingWall()) {
                case 1:
                    ball.reverseVelX();
                    ball.setVelX(ball.getVelX()+incrementVelX);
                    ball.setVelY(ball.getVelY()+incrementVelY);
                    ball.setHitCount(ball.getHitCount()+1);
                    scoreCount+=ball.getHitCount();
                    break;
                case 2:
                    ball.reverseVelY();
                    ball.setVelY(ball.getVelY()+incrementVelX);
                    ball.setVelY(ball.getVelY()+incrementVelY);
                    ball.setHitCount(ball.getHitCount()+1);
                    scoreCount+=ball.getHitCount();
                    break;
                case 3:
                    ball.reverseVelX();
                    ball.setVelX(ball.getVelX()+incrementVelX);
                    ball.setVelY(ball.getVelY()+incrementVelY);
                    ball.setHitCount(ball.getHitCount()+1);
                    scoreCount+=ball.getHitCount();
                    break;
            }

            // Make ball bounce off paddle.
            if (ball.isCollidingWithPaddle(paddle)){
                ball.reverseVelY();
                ball.setVelX(ball.getVelX()+incrementVelX);
                ball.setVelY(ball.getVelY()+incrementVelY);
                ball.setHitCount(ball.getHitCount()+1);
                scoreCount+=ball.getHitCount();
            }

            // Increment the ball position by the velocity also
            // multiplies the velocity by the speed variable.
            ball.setPosX((int)(ball.getPosX() + ball.getVelX()*speed));
            ball.setPosY((int)(ball.getPosY() + ball.getVelY()*speed));

            // Radius is incremented or decremented with helper method.
            ball.changeRadius();

            // Remove the ball if it goes off the screen.
            if (ball.getPosY() >= height+100) {
                scoreCount-=ball.getHitCount()*5;
                iterator.remove();
            }
        }

        // Color of all PongObjects are changed.
        for (Ball ball : balls) ball.setRandomColor();
        wall.setRandomColor();
        paddle.setRandomColor();
    }

    /**
     * Method: saveBallState
     * This is called when the application is closed.
     *
     * @param osw The file output stream.
     */
    public void saveBallState(OutputStreamWriter osw) {
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
            osw.close();
        }
        catch (IOException ioe) {
            Log.i("saveBallState", "There was an IO exception.");
        }
    }

    /**
     * Method: readBallState
     * This is called when the application is reopened.
     */
    public void readBallState(BufferedReader br) {
        balls = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                int x = Integer.parseInt(line);
                int y = Integer.parseInt(line);
                int color = Integer.parseInt(line);
                Ball b = new Ball(x, y, color);

                int velX = Integer.parseInt(line);
                int velY = Integer.parseInt(line);
                int radius = Integer.parseInt(line);
                int changeSize = Integer.parseInt(line);

                b.setVelX(velX);
                b.setVelY(velY);
                b.setRadius(radius);
                b.setChangeSize(changeSize);

                addBall(b);
            }
            br.close();
        }
        catch (IOException ioe) {
            Log.i("readBallState", "There was an IO exception.");
        }
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

            if( paddle.contains(event.getX(),event.getY())) {
                paddle.setSelected(true,(int)event.getX());
             ;
            } else {
                if( event.getY() > 100) {
                    for (Ball b : balls) {
                        b.reverseVelX();
                        b.reverseVelY();
                    }
                }

            }
            //Log.i(paddle.contains((int)event.getX(),(int)event.getY())+" ","");
        } else if ( event.getAction() == MotionEvent.ACTION_UP){
            paddle.setSelected(false,0);

        } else if ( event.getAction() == MotionEvent.ACTION_MOVE){

            paddle.dragX((int)event.getX());
        }
    }

    /**
     * Method: addBall
     * Add a Ball object to the balls ArrayList.
     *
     * @param ball The Ball object.
     */
    public void addBall(Ball ball) {
        balls.add(ball);
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

    //Setter for the speed variable, it also sets it to a percent to make
    //the calculation easy
    public void setSpeed(int speed){
        this.speed = (double)(speed)/100.0;
    }


}
