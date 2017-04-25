package com.example.layoutinflaterdemo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

public class MainActivity extends ActionBarActivity {

	private RelativeLayout relativeLayout;
	private LayoutInflater layoutInflater;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		relativeLayout=(RelativeLayout)findViewById(R.id.main_layout_Id);
		layoutInflater=LayoutInflater.from(this);
		View view=layoutInflater.inflate(R.layout.button_layout, null);
		relativeLayout.addView(view);
		
		
	}

	
}
