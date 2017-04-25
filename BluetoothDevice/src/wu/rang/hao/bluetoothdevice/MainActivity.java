package wu.rang.hao.bluetoothdevice;

import java.util.Iterator;
import java.util.Set;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button button;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button=(Button)findViewById(R.id.buttonId);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//
				BluetoothAdapter adapter=BluetoothAdapter.getDefaultAdapter();
				if(adapter!=null){
					System.out.println("本机有蓝牙设备");
					if(!(adapter.enable())){//判断蓝牙设备是否已经开启，若没有开启，则提醒用户打开
						System.out.println("1");
						Intent intent=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
						startActivity(intent);	
					}
					System.out.println("2");
					//获取本机的蓝牙设备
					Set <BluetoothDevice> devices=adapter.getBondedDevices();
					if(devices.size()>0){
						for(Iterator iterator=devices.iterator();iterator.hasNext();){
							BluetoothDevice bluetoothDevice=(BluetoothDevice)iterator.next();
							System.out.println(bluetoothDevice.getAddress());
							
						}	
					}
					
					
				}
				else System.out.println("本机没有蓝牙设备");
			}
		});
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
