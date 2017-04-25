package wu.rang.hao.sensoracc;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private TextView textView;
	private TextView textViewFilterNoise;
	private float gravity[]=new float[3];
	private float acc[]=new float[3];
	private SensorManager sensorManager;
	private Sensor sensor;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView=(TextView)findViewById(R.id.textViewId);
		textViewFilterNoise=(TextView)findViewById(R.id.textViewFilterGravity);
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		sensor=sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(new SensorEventListener() {
			
			@Override
			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float value[]=event.values;
				textView.setText("X方向的加速度为："+value[0]+"\n"+
				"Y方向的加速度为："+value[1]+"\n"+
						"Z轴方向的加速度为："+value[2]);
				final float alpha=0.8f;
				gravity[0]=gravity[0]*alpha+value[0]*(1-alpha);
				gravity[1]=gravity[1]*alpha+value[1]*(1-alpha);
				gravity[2]=gravity[2]*alpha+value[2]*(1-alpha);
				
				acc[0]=value[0]-gravity[0];
				acc[1]=value[1]-gravity[1];
				acc[2]=value[2]-gravity[2];
				textViewFilterNoise.setText("去除重力加速度后的加速度如下："+"\n"+"X方向的加速度为："+acc[0]+"\n"+
						"Y方向的加速度为："+acc[1]+"\n"+
								"Z轴方向的加速度为："+acc[2]);
				
				
			}
			
			@Override
			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}
		}, sensor, SensorManager.SENSOR_DELAY_NORMAL);
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
