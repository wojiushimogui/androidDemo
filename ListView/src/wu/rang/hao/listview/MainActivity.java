package wu.rang.hao.listview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
	private ListView listView=null;
	private String fruit[]={"apple","Banana","Oraenge","Peat","AAAA",
			"BBBBB","CCCCCC","DDDDDD","FFFFFFF","EEEEEEE",
			"GGGGGGG","HHHHHHH","IIIIIIII","JJJJJJJJ","KKKKKKKK",
			"LLLLLLLL","MMMMMMMM","NNNNNNNNN"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView=(ListView)findViewById(R.id.listViewId);
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(MainActivity.this, 
				android.R.layout.simple_list_item_1, fruit);
		listView.setAdapter(adapter);
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
