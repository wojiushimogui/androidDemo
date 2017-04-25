package wu.rang.hao.sensor;

import java.util.List;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private Button sensorButtonAll;
	private Button sensorButton;
	private TextView textViewAll;
	private	TextView textView;
	private String str;
	private SensorManager sensorManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sensorButtonAll=(Button)findViewById(R.id.sensorButton1);
		sensorButton=(Button)findViewById(R.id.sensorButton2);
		sensorManager=(SensorManager)getSystemService(Context.SENSOR_SERVICE);
		textViewAll=(TextView)findViewById(R.id.textViewId1);
		textView=(TextView)findViewById(R.id.textViewId2);
		sensorButtonAll.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				List<Sensor> sensors=sensorManager.getSensorList(Sensor.TYPE_ALL);
				for(Sensor sensor:sensors)
				//System.out.println(sensor.getName());
				 {str=str+sensor.getName()+"\n";
				 textViewAll.setText(str);
				 }
						
				
			}
		});
		sensorButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		Sensor sensor=sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		//System.out.println(sensor.getName());
		textView.setText(sensor.getName());
				
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
