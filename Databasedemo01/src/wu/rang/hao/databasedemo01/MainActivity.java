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
				//添加数据
//				ContentValues values=new ContentValues();
//				values.put("title", "这是一个新闻标题---1");
//				values.put("content", "这是一条新闻的内容-----1");
//				values.put("publishdate", System.currentTimeMillis());
//				long id=db.insert("news", null, values);
//				Toast.makeText(MainActivity.this, "添加的数据id:"+id, Toast.LENGTH_SHORT).show();
				//更新数据
//				ContentValues values=new ContentValues();
//				values.put("title", "今天是个好日子");
//				//db.update("news", values, "_id=?", new String[]{"1"});
//				db.update("news", values, "title=?", new String[]{"这是一个新闻标题---1"});
				
				//删除数据
				db.delete("news", "title=?", new String[]{"今天是个好日子"});
				
			}
		});
		
	}


}
