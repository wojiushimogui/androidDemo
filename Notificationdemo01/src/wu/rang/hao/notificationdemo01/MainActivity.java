package wu.rang.hao.notificationdemo01;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends ActionBarActivity {
	private NotificationManager notificationManager;
	private Notification notification;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notification=new Notification(R.drawable.ic_launcher,"this is a notification", System.currentTimeMillis());
		//notification.ledARGB=Color.RED;
		//notification.ledOnMS=10000;
		//notification.ledOffMS=1000;
		notification.defaults=Notification.DEFAULT_ALL;
	
		notification.setLatestEventInfo(this, "this is a title", "this is a content", null);
		notificationManager.notify(1, notification);
		}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
