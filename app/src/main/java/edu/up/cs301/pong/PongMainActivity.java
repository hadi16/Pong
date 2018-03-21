package edu.up.cs301.pong;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import edu.up.cs301.animation.AnimationSurface;

/**
 * Class: PongMainActivity
 * This class contains the code to interact with the XML.
 *
 * @author Alex Hadi
 * @author Jason Twigg
 * @version March 17, 2018
 */
public class PongMainActivity extends Activity {
    // Instance variables
    private PongAnimator pongAnimator;
    private Paddle paddle;

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
        Button buttonTogglePause =
                (Button)findViewById(R.id.buttonPause);
        buttonTogglePause.setOnClickListener(listeners);
	}

    /**
     * Inner Class: Listener
     * Contains code for all listeners.
     */
	private class Listener implements RadioGroup.OnCheckedChangeListener,
            View.OnClickListener {
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
            }
        }
    }
}
