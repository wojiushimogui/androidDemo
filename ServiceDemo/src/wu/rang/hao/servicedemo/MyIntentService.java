package wu.rang.hao.servicedemo;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

	public MyIntentService() {
		super("MyIntentService");
		// TODO Auto-generated constructor stub
		
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub
		Log.d("MyIntentService","Thread id id "+Thread.currentThread().getId());
		
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		Log.d("MyIntentService","onDestroy execute");
		super.onDestroy();
	}

}
