package wu.rang.hao.databasedemo01;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper{
	
	private static final String CREATE_NEWS="create table news(_id integer primary key autoincrement,"
			+ "title text,"
			+ "content text,"
			+ "publishdate integer,"
			+ "commentcount integer)";
	private static final String CREATE_COMMENT="create table comment("
			+ "id integer primary key autoincrement,"
			+ "content text"
			+ "publishdata integer)";
	private Context mContext;

	public MySQLiteHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
		mContext=context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_NEWS);
		db.execSQL(CREATE_COMMENT);
		Toast.makeText(mContext, "建表成功", Toast.LENGTH_SHORT).show();;
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		switch (oldVersion) {
		case 1:
			db.execSQL(CREATE_COMMENT);
			
			break;
		case 2:
			db.execSQL("alter table comment add column publishdata integer");
			break;

		default:
			break;
		}
		
	}

}
