package edu.up.cs301.pong;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.OutputStreamWriter;

import edu.up.cs301.animation.AnimationSurface;

/**
 * Class: PongMainActivity
 * This class contains the code to interact with the XML.
 *
 * ADDED ENHANCEMENTS:
 * - Beginner and expert mode for the paddle
 * - Multiple balls on the screen at once (with button to add balls)
 * - Pause Button (Button that toggles the pausing of the game)
 * - Speed SeekBar (SeekBar that changes the speeds of all of the balls)
 * - Color for the all of the Game objects change with every tick
 * - Sizes of the ball oscillate from 10 to 100, increasing by 5 every tick
 * - File IO (game saved when app exited and restarted)
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 21, 2018
 */
public class PongMainActivity extends Activity {
    // Instance variables
    private PongAnimator pongAnimator;
    private Paddle paddle;
    private Button buttonTogglePause;
    private TextView speedText;

    /**
     * Method: onCreate
     * Creates an AnimationSurface containing a PongAnimator.
     *
     * @param savedInstanceState The Bundle object for the current instance.
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
        // Required method calls.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pong_main);

        // Instantiate the Ball, Paddle, PongAnimator, and Listener objects.
        Ball ball = new Ball(Color.rgb(0,0,0));
        paddle = new Paddle(Color.RED);
        pongAnimator = new PongAnimator(ball, paddle);
        Listener listeners = new Listener();

        // Connect the animation surface with the animator.
        AnimationSurface mySurface =
                (AnimationSurface)findViewById(R.id.animationSurface);
        mySurface.setAnimator(pongAnimator);

        // Set RadioGroup properties for beginner/expert mode enhancement.
        RadioGroup radioGroupDifficulty =
                (RadioGroup)findViewById(R.id.radioGroupDifficulty);
        radioGroupDifficulty.setOnCheckedChangeListener(listeners);
        radioGroupDifficulty.check(R.id.radioButtonBeginner);

        // Get the button for adding balls (enhancement) and set its listener.
        Button buttonAddBall =
                (Button)findViewById(R.id.buttonAddBall);
        buttonAddBall.setOnClickListener(listeners);

        // Get the button for toggling collisions and set its listener.
        buttonTogglePause =
                (Button)findViewById(R.id.buttonPause);
        buttonTogglePause.setOnClickListener(listeners);

        //Setup the SeekBar and the TextView for the speed Changing
        SeekBar speedSeekBar = (SeekBar)findViewById(R.id.seekBarSpeed);
        speedSeekBar.setOnSeekBarChangeListener(listeners);

        speedText = (TextView)findViewById(R.id.textViewSpeed);

        //start the speed at half speed
        speedSeekBar.setProgress(50);
        pongAnimator.setSpeed(50);

	}

    /**
     * Method: onResume
     * Called when the application is reopened.
     * Used to read the state of the app.
     */
	@Override
    public void onResume() {
        super.onResume();
        try {
            BufferedReader br = new BufferedReader(new FileReader("saveData.txt"));
            pongAnimator.readBallState(br);
        }
        catch (FileNotFoundException e) {
            Log.i("onResume", "The save file was not found.");
        }
    }

    /**
     * Method: onStop
     * Called when the application is terminated.
     * Used to write the state of the app to a file.
     */
    @Override
    public void onStop() {
        super.onStop();
        try {
            OutputStreamWriter osw = new OutputStreamWriter
                    (getApplicationContext().openFileOutput("saveData.txt",
                            Context.MODE_PRIVATE));
            pongAnimator.saveBallState(osw);
        }
        catch (FileNotFoundException e) {
            Log.i("onStop", "The save file was not found.");
        }
    }

    /**
     * Inner Class: Listener
     * Contains code for all listeners.
     */
	private class Listener implements RadioGroup.OnCheckedChangeListener,
            View.OnClickListener, SeekBar.OnSeekBarChangeListener {
        /**
         * Method: onCheckedChanged
         * Listener for the RadioGroup and RadioButtons.
         * Executed when user checks different RadioButton.
         *
         * @param group The RadioGroup object.
         * @param checkedId The ID of the checked RadioButton in the group.
         */
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            // Must be the RadioGroup for difficulty.
            if (group.getId() != R.id.radioGroupDifficulty) return;

            // Beginner Mode
            if (checkedId == R.id.radioButtonBeginner) {
                paddle.setExpertMode(false);
            }
            // Expert Mode
            else if (checkedId == R.id.radioButtonExpert) {
                paddle.setExpertMode(true);
            }
        }

        /**
         * Method: onClick
         * Executed when something is clicked.
         * @param v The view that was clicked.
         */
        @Override
        public void onClick(View v) {
            // Add a new ball to the game.
            if (v.getId() == R.id.buttonAddBall) {
                pongAnimator.addBall(new Ball(Color.rgb(
                        (int) (Math.random() * 256),
                        (int) (Math.random() * 256),
                        (int) (Math.random() * 256))));
            }
            // Toggle the collisions.
            else if (v.getId() == R.id.buttonPause) {
                pongAnimator.togglePause();
                if (pongAnimator.isPauseMode()) {
                    buttonTogglePause.setText("Pause: ON");
                }
                else {
                    buttonTogglePause.setText("Pause: OFF");
                }
            }
        }

        /**
         * Method: onProgressChanged
         * Executed when the progress of the SeekBar changes.
         * @param seekBar The SeekBar.
         * @param progress The current progress.
         * @param fromUser Whether it came from the user.
         */
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            // check if the seekBar is the speed SeekBar and then set the text to resemble
            // the SeekBar and then sets the new speed in the animator
            if( R.id.seekBarSpeed == seekBar.getId() ) {
                speedText.setText("Speed: " + progress);
                pongAnimator.setSpeed(progress);
            }
        }

        /**
         * Required for the SeekBar listener. Not used.
         */
        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        /**
         * Required for the SeekBar listener. Not used.
         */
        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }
}
