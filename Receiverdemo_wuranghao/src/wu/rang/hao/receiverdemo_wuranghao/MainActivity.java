package wu.rang.hao.receiverdemo_wuranghao;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	private TextView sender;
	private TextView content;
	private EditText to;
	private EditText con;
	private Button send;
	//接受消息所用到的过滤器和广播接收器
	private IntentFilter intentFilter;
	private MessageReceiver messageReceiver;
	//发送消息后检查是否成功的过滤器和广播接收器
	private IntentFilter sendFilter;
	private SendReceiver sendReceiver;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sender=(TextView)findViewById(R.id.sender);
		content=(TextView)findViewById(R.id.content);
		to=(EditText)findViewById(R.id.to);
		con=(EditText)findViewById(R.id.con);
		send=(Button)findViewById(R.id.sendId);
		//添加接受短信的广播并注册
		intentFilter=new IntentFilter();
		intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
		messageReceiver=new MessageReceiver();
		registerReceiver(messageReceiver, intentFilter);
		//发送短信是否成功的事件并注册
		sendFilter=new IntentFilter();
		sendFilter.addAction("SENT_SMS_ACTION");
		sendReceiver=new SendReceiver();
		registerReceiver(sendReceiver, sendFilter);
		//发送短信的点击事件中需要处理的事情
				send.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent=new Intent("SENT_SMS_ACTION");
						PendingIntent pi=PendingIntent.getBroadcast(MainActivity.this, 0, intent, 0);
						SmsManager smsManager=SmsManager.getDefault();
						smsManager.sendTextMessage(to.getText().toString(), null, con.getText().toString(), pi, null);
						to.setText("");
						con.setText("");
						
					}
				});
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(messageReceiver);
		unregisterReceiver(sendReceiver);
		
	}

	//设置一个接受短信是否发送成功的广播接收器
	class SendReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(getResultCode()==RESULT_OK){
				Toast.makeText(MainActivity.this, "send success", Toast.LENGTH_SHORT).show();
			}
			else Toast.makeText(MainActivity.this, "send failed", Toast.LENGTH_SHORT).show();
			
		}
		
	}
	//设置一个广播接收器
	class MessageReceiver extends BroadcastReceiver{
		private static final String ACTION="android.provider.Telephony.SMS_RECEIVED";
		
		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			if(ACTION.equals(intent.getAction())){
				Bundle bundle=intent.getExtras();
				Object[] pdus=(Object[])bundle.get("pdus");
				SmsMessage [] smsMessage=new SmsMessage[pdus.length];
				for(int i=0;i<pdus.length;i++){
					smsMessage[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
				}
				String address=smsMessage[0].getOriginatingAddress();
				//String address="";
				String fullMessage="";
				for(SmsMessage message:smsMessage){
					//address=message.getOriginatingAddress();
					if(message!=null){
						fullMessage+=message.getMessageBody();
					}
					
				}
				sender.setText(address);
				content.setText(fullMessage);
			}
			
			
		}
		
	}
	
	
}
