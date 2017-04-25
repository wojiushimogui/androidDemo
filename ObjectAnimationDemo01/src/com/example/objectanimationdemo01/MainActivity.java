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
				//oa=ObjectAnimator.ofFloat(textView, "alpha", 1,0,1);//�ؼ�TextView�ӿɼ���Ϊ͸�����ٱ�Ϊ�ɼ�
				//oa=ObjectAnimator.ofFloat(textView, "rotation", 0,360,0);//��ת���ȴ�0��360��ת��˳ʱ�룩��Ȼ����360��0����ת����ʱ�룩
				//float currentX=textView.getTranslationX();
				//oa=ObjectAnimator.ofFloat(textView, "translationX", currentX,-500,currentX);//ˮƽ�ƶ�
				//����ĸ��ֶ���ֻ��Ҫ������Ĳ������иı�Ϳ����ˣ�������һ����
				
				//������ѧϰ����϶������������Ӷ��������һ�������ʾ
				AnimatorSet set=new AnimatorSet();
				ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(textView, "alpha", 1,0,1);//�ؼ�TextView�ӿɼ���Ϊ͸�����ٱ�Ϊ�ɼ�
				ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(textView, "rotation", 0,360,0);//��ת���ȴ�0��360��ת��˳ʱ�룩��Ȼ����360��0����ת����ʱ�룩
				float currentX=textView.getTranslationX();
				ObjectAnimator moveAnimator=ObjectAnimator.ofFloat(textView, "translationX", currentX,-500,currentX);//ˮƽ�ƶ�
				set.play(alphaAnimator).after(moveAnimator).with(rotationAnimator);
				
				set.setDuration(10*1000);
				set.start();
			}
		});
		
		
	}

	
}
