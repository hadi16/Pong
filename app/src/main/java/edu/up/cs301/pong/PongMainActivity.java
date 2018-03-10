package edu.up.cs301.pong;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import edu.up.cs301.animation.AnimationSurface;

/**
 * PongMainActivity
 * 
 * This is the activity for the Pong game. It attaches a PongAnimator to
 * an AnimationSurface.
 * 
 * @author Andrew Nuxoll
 * @author Steven R. Vegdahl
 * @version July 2013
 * 
 */
public class PongMainActivity extends Activity {
    private RadioGroup radioGroupDifficulty;
    private AnimationSurface mySurface;


	private BallObject ballObject;
	private int initX;
	private int initY;
	private int ballColor;


	/**
	 * creates an AnimationSurface containing a TestAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pong_main);

		initX = 100;
		initY = 100;
		ballColor = Color.rgb(255,255,255);

		ballObject = new BallObject(initX,initY,ballColor);

		// Connect the animation surface with the animator
		mySurface = (AnimationSurface) this.findViewById(R.id.animationSurface);
		mySurface.setAnimator(new TestAnimator(ballObject));

        Listener listeners = new Listener();
        radioGroupDifficulty = (RadioGroup)findViewById(R.id.radioGroupDifficulty);
        radioGroupDifficulty.setOnCheckedChangeListener(listeners);
	}

	private class Listener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() != R.id.radioGroupDifficulty) return;

            if (checkedId == R.id.radioButtonBeginner) {
                // Make paddle large
                System.out.println("Beginner");
            }
            else if (checkedId == R.id.radioButtonExpert) {
                // Make paddle small
                System.out.println("Expert");
            }
        }
    }
}
