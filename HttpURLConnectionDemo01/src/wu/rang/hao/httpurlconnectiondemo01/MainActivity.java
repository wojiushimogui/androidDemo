package wu.rang.hao.httpurlconnectiondemo01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private Button sendRequest;
	private TextView textView;
	private Handler handler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			textView.setText(msg.obj.toString());
			
			
		}
		
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sendRequest=(Button)findViewById(R.id.sendRequest);
		textView=(TextView)findViewById(R.id.textView);
		sendRequest.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendRequestHttpURLConnection();
			}
		});
	}
	
	public void sendRequestHttpURLConnection(){
		//另外开启一个线程来执行耗时比较长的网络任务
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				HttpURLConnection connection=null;
				// TODO Auto-generated method stub
				try {
					URL url=new URL("http://www.baidu.com");
					try {
						 connection=(HttpURLConnection)url.openConnection();
						connection.setRequestMethod("GET");
						connection.setReadTimeout(5*1000);
						connection.setConnectTimeout(4*1000);
						InputStream in=connection.getInputStream();
						BufferedReader reader=new BufferedReader(new InputStreamReader(in));
						StringBuffer buffer=new StringBuffer();
						String line="";
						while((line=reader.readLine())!=null){
							buffer.append(line);
							
						}
						//由于在子线程中我们不能将内容在UI中显示，因此我们采用异步消息机制将消息发送至主线程
						Message mes=new Message();
						mes.obj=buffer;
						handler.sendMessage(mes);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					if(connection!=null){
						connection.disconnect();
					}
					
				}
			}
		}).start();;
		
		//HttpURLConnection
		
	}

	
}
