package com.example.openotherapplicationdemo01;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	Button open_app1;
	public static final String CALENDAR_PACKAGE_NAME = "com.android.calendar";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		open_app1=(Button)findViewById(R.id.open_app1);
		open_app1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				launchBoohee(MainActivity.this);
				
				
			}
		});
		
	}
	
	

	/**
	 * ����ϵͳ�Դ���������App
	 * @param context
	 */
	public static void launchBoohee(Context context) {
	    // �ж��Ƿ�װ��App������ȥ�г�����
	    if (isAppInstalled(context, CALENDAR_PACKAGE_NAME)) {
	        context.startActivity(context.getPackageManager().getLaunchIntentForPackage(CALENDAR_PACKAGE_NAME));
	    } else {
	        goToMarket(context, CALENDAR_PACKAGE_NAME);
	    }
	}

	/**
	 * ���ĳ��Ӧ���Ƿ�װ
	 * 
	 * @param context
	 * @param pkgName
	 * @return
	 */
	public static boolean isAppInstalled(Context context, String packageName) {
	    try {
	        context.getPackageManager().getPackageInfo(packageName, 0);
	        return true;
	    } catch (NameNotFoundException e) {
	        return false;
	    }
	}

	/**
	 * ȥ�г�����ҳ��
	 */
	public static void goToMarket(Context context, String packageName) {
	    Uri uri = Uri.parse("market://details?id=" + packageName);
	    Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
	    try {
	        context.startActivity(goToMarket);
	    } catch (ActivityNotFoundException e) {
	    }
	}

	
}
