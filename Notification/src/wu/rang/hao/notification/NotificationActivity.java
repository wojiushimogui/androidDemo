package wu.rang.hao.notification;

import android.app.Activity;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

public class NotificationActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		NotificationManager notification=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		notification.cancel(1);
	}

}
