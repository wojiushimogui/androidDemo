package com.example.objectanimationdemo01;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private TextView textView;
	private Button button_start;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView=(TextView)findViewById(R.id.textViewId);
		button_start=(Button)findViewById(R.id.buttonId);
		button_start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//oa=ObjectAnimator.ofFloat(textView, "alpha", 1,0,1);//控件TextView从可见变为透明，再变为可见
				//oa=ObjectAnimator.ofFloat(textView, "rotation", 0,360,0);//旋转：先从0到360旋转（顺时针），然后是360到0的旋转（逆时针）
				//float currentX=textView.getTranslationX();
				//oa=ObjectAnimator.ofFloat(textView, "translationX", currentX,-500,currentX);//水平移动
				//上面的各种动画只需要对里面的参数进行改变就可以了，道理是一样的
				
				//下面来学习下组合动画，即将多钟动画混合在一起进行显示
				AnimatorSet set=new AnimatorSet();
				ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(textView, "alpha", 1,0,1);//控件TextView从可见变为透明，再变为可见
				ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(textView, "rotation", 0,360,0);//旋转：先从0到360旋转（顺时针），然后是360到0的旋转（逆时针）
				float currentX=textView.getTranslationX();
				ObjectAnimator moveAnimator=ObjectAnimator.ofFloat(textView, "translationX", currentX,-500,currentX);//水平移动
				set.play(alphaAnimator).after(moveAnimator).with(rotationAnimator);
				
				set.setDuration(10*1000);
				set.start();
			}
		});
		
		
	}

	
}
