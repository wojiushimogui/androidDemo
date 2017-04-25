package com.example.servicedemo01;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	private Button startButton,stopButton,binderButton,unbinderButton;
	private MyService service;
	
	ServiceConnection conn=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			MyService.MyBinder binder=(MyService.MyBinder)service;
			binder.downLoad();
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startButton=(Button)findViewById(R.id.startId);
		stopButton=(Button)findViewById(R.id.stopId);
		
		binderButton=(Button)findViewById(R.id.bind);
		unbinderButton=(Button)findViewById(R.id.unbinder);
		
		startButton.setOnClickListener(this);
		stopButton.setOnClickListener(this);
		
		binderButton.setOnClickListener(this);
		unbinderButton.setOnClickListener(this);
		
		
		
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.startId:{
			Intent intent=new Intent(this,MyService.class);
			startService(intent);
			break;
		}
		case R.id.stopId:{
			Intent intent=new Intent(this,MyService.class);
			stopService(intent);
			break;
		}
		case R.id.bind:{
			Intent intent=new Intent(this,MyService.class);
			bindService(intent, conn, BIND_AUTO_CREATE);
			break;
		}
		case R.id.unbinder:{
			unbindService(conn);
			break;
		}
		
		}
		
	}

	
}
