package wu.rang.hao.servicedemo;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service{

	//������������ͷ������ϵ������onBind����
	//��һ�������ǽ���һ�������̳�Binder
	//�ڶ������½�һ��ʵ������onBind���������з���
	
	class DownloaderBind extends Binder{
		public void downloader(){
			Log.d("MyService","downloader execute");
			System.out.println("downloader execute");
		}
		public void getProgess(){
			Log.d("MyService","getProgess execute");
			System.out.println("getProgess execute");
		}
	}
	private DownloaderBind downloaderBind=new DownloaderBind();
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return downloaderBind;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		Log.d("MyService", "onCreate execute");
		//�������Ϊһ��ǰ̨����
		Notification notification=new Notification(R.drawable.ic_launcher,
				"this is a service", System.currentTimeMillis());
		Intent intent=new Intent(this,MainActivity.class);
		PendingIntent pendingIntent=PendingIntent.getActivity(this, 0, intent, 0);
		notification.setLatestEventInfo(this, "this is a title", "wuranghao", pendingIntent);
		startForeground(1, notification);
		
		//System.out.println("onCreate execute");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		Log.d("MyService","onStartCommand execute");
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("MyService","onDestroy execute");
		super.onDestroy();
	}

}
