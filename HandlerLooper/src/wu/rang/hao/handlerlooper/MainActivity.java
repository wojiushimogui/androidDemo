package wu.rang.hao.handlerlooper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private TextView textView;
	private Button button;
	private Handler handler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView=(TextView)findViewById(R.id.textViewId);
		button=(Button)findViewById(R.id.buttonId);
		button.setOnClickListener(new ButtonListener());
		handler=new FirstHandler();
		
	}
	//定义一个监听器
	class ButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			Thread t=new FirstThread();
			t.start();
		}
		
	}
	//定义一个Worker Thread
	//在这个线程来发送消息
	class FirstThread extends Thread{
		@Override
		public void run(){
			//textView.setText("我在学android");错误
			try {
				Thread.sleep(2*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String s="模拟从互联网中得到的数据";
			//用handler来发送消息
			Message msg=handler.obtainMessage();
			msg.obj=s;
			handler.sendMessage(msg);
			
		}
	}
	//定义一个Handler类
	class FirstHandler extends Handler{

		
		public void handleMessage(Message msg) {
			String s=(String)msg.obj;
			textView.setText(s);
		}
	
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
