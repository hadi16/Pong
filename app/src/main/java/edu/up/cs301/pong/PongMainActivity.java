package edu.up.cs301.pong;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import edu.up.cs301.animation.AnimationSurface;

public class PongMainActivity extends Activity {
    private AnimationSurface mySurface;
    private PongAnimator pongAnimator;
    private Button buttonReadyAddBall;
    private Paddle paddle;

	/**
	 * creates an AnimationSurface containing a PongAnimator.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pong_main);

		Ball ball = new Ball(Color.rgb(0,0,0));
        paddle = new Paddle(Color.rgb(100,100,100));

        pongAnimator = new PongAnimator(ball, paddle);

		// Connect the animation surface with the animator
		mySurface = (AnimationSurface) findViewById(R.id.animationSurface);
		mySurface.setAnimator(pongAnimator);

        Listener listeners = new Listener();

        RadioGroup radioGroupDifficulty = (RadioGroup)findViewById(R.id.radioGroupDifficulty);
        radioGroupDifficulty.setOnCheckedChangeListener(listeners);
        radioGroupDifficulty.check(R.id.radioButtonBeginner);

        buttonReadyAddBall = (Button)findViewById(R.id.buttonReadyAddBall);
        buttonReadyAddBall.setOnClickListener(listeners);
	}

	private class Listener implements RadioGroup.OnCheckedChangeListener,
            View.OnClickListener {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            if (group.getId() != R.id.radioGroupDifficulty) return;

            if (checkedId == R.id.radioButtonBeginner) {
                // Make paddle large
                paddle.setPaddleLength(1100);
                paddle.setPosX(474);
                mySurface.invalidate();
            }
            else if (checkedId == R.id.radioButtonExpert) {
                // Make paddle small
                paddle.setPaddleLength(550);
                paddle.setPosX(749);
                mySurface.invalidate();
            }
        }

        @Override
        public void onClick(View v) {
            if (v.getId() != R.id.buttonReadyAddBall) return;

            pongAnimator.setBall(new Ball(Color.rgb(0,0,0)));
        }
    }
}
