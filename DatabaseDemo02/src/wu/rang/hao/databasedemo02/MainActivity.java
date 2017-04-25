package wu.rang.hao.databasedemo02;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	private Button button;
	private SQLiteDatabase db;
	private News news;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		button=(Button)findViewById(R.id.buttonId);
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				db=Connector.getDatabase();
				//Ϊ�����������
				
				news=new News();
				
				news.setTitle("����һ�����ű���---->2");
				news.setContent("����һ�����ŵ�����---->2");
				news.setPublicData(System.currentTimeMillis());
				News news1=new News();
				news1.setTitle("����һ�����ű���---->3");
				news1.setContent("����һ�����ŵ�����---->3");
				news1.setPublicData(System.currentTimeMillis());
				News news2=new News();
				news2.setTitle("����һ�����ű���---->4");
				news2.setContent("����һ�����ŵ�����---->4");
				news2.setPublicData(System.currentTimeMillis());
				Log.d("TAG","id:------------>"+news.getId());
				//news.save();//�ص�
				Log.d("TAG","id:------------>"+news.getId());
				List<News> newsList=new ArrayList<News>();
				newsList.add(news);
				newsList.add(news1);
				newsList.add(news2);
				DataSupport.saveAll(newsList);
				
				
				
				
			}
		});
	}

	
}
