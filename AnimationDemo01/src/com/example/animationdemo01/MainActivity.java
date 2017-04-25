package com.example.animationdemo01;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private TextView textView;
	private ValueAnimator anim;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView=(TextView)findViewById(R.id.textViewId);
		anim=ValueAnimator.ofFloat(1.0f,0.0f); 
		anim.setDuration(5000);
		
		
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				// TODO Auto-generated method stub
				Float value=(Float)animation.getAnimatedValue();
				System.out.println("  "+value);
				textView.setText(""+value);
				
			}
		});
		anim.start();
		
		
	}

}
