package wu.rang.hao.servicedemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	
	private MyService.DownloaderBind bind;
	private ServiceConnection connection=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			bind=(MyService.DownloaderBind)service;
			bind.downloader();
			bind.getProgess();
			
		}
	};
	private Button startService;
	private Button stopService;
	private Button connectionService;
	private Button disconnectionService;
	
	private Button intentService;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		startService=(Button)findViewById(R.id.startService);
		stopService=(Button)findViewById(R.id.stopService);
		connectionService=(Button)findViewById(R.id.connectionservice);
		disconnectionService=(Button)findViewById(R.id.disconnectionService);
		intentService=(Button)findViewById(R.id.intentService);
		startService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,MyService.class);
				startService(intent);
				
			}
		});
		stopService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,MyService.class);
				stopService(intent);
				
			}
		});
		connectionService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(MainActivity.this,MyService.class);
				bindService(intent, connection, BIND_AUTO_CREATE);
				
				
			}
		});
		disconnectionService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				unbindService(connection);
				
			}
		});
		intentService.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("MyIntentService","Main Thread id is"+Thread.currentThread().getId());
				Intent intent=new Intent(MainActivity.this,MyIntentService.class);
				startService(intent);
			}
		});
	}

	
}
