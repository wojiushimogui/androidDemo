package com.example.servicedemo01;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {

	private  final String TAG="MyService";
	private MyBinder mBinder=new MyBinder();
	
	public class MyBinder extends Binder{
		public void downLoad(){
			Log.d(TAG,"dowmLoad execute");
		}
	}
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return mBinder;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		System.out.println("onCreate execute");
		super.onCreate();
		Log.d("MyService", "onCreate execute");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d(TAG,"onStartCommand execute");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.d(TAG,"onDestroy execute");
	}

}
