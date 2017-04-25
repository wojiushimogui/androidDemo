package wu.rang.hao.databasedemo01;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button button;
	private MySQLiteHelper dbHelper;
	private SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button=(Button)findViewById(R.id.create);
		dbHelper=new MySQLiteHelper(this, "news", null, 3);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db=dbHelper.getWritableDatabase();
				//�������
//				ContentValues values=new ContentValues();
//				values.put("title", "����һ�����ű���---1");
//				values.put("content", "����һ�����ŵ�����-----1");
//				values.put("publishdate", System.currentTimeMillis());
//				long id=db.insert("news", null, values);
//				Toast.makeText(MainActivity.this, "��ӵ�����id:"+id, Toast.LENGTH_SHORT).show();
				//��������
//				ContentValues values=new ContentValues();
//				values.put("title", "�����Ǹ�������");
//				//db.update("news", values, "_id=?", new String[]{"1"});
//				db.update("news", values, "title=?", new String[]{"����һ�����ű���---1"});
				
				//ɾ������
				db.delete("news", "title=?", new String[]{"�����Ǹ�������"});
				
			}
		});
		
	}


}
