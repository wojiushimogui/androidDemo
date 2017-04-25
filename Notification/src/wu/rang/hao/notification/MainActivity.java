package wu.rang.hao.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {
	private Button buttonSendNotification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		buttonSendNotification=(Button)findViewById(R.id.buttonSendNotificationId);
		buttonSendNotification.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				switch(view.getId()){
				case R.id.buttonSendNotificationId:
					NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
					Notification notification=new Notification(R.drawable.ic_launcher, "this is a notification", System.currentTimeMillis());
					//notification.defaults=Notification.COLOR_DEFAULT;
					//notification.ledARGB=Color.RED;
					//notification.ledOnMS=1000;
					//notification.ledOffMS=1000;
					//notification.flags=Notification.FLAG_SHOW_LIGHTS;
					long[] vibrates={0,1000,1000,1000};
					notification.vibrate=vibrates;
					Intent intent=new Intent(MainActivity.this,NotificationActivity.class);
					PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
					notification.setLatestEventInfo(MainActivity.this, "this is a title", "this is a content", pendingIntent);
					notificationManager.notify(1, notification);
					break;
					default:break;
					
					
				
				}
				
			}
		});
	}

	
}
