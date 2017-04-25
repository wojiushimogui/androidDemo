package com.example.databasedemo03;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	/*
	 * 控件的一些对象实例
	 * */
	private Button insertButton,deleteButton;
	private TextView textView;
	private EditText editText;
	private ListView listView;
	//private SQLiteDatabase db;
	private static volatile UserData  userData=new UserData();
	//private EcgData ecgData=new EcgData();
	/*
	 * 用来保存列表中文件的名字
	 * */
	private List<String> listFileName=new ArrayList<String>();
	/*
	 * 用来暂时保存文件的名字
	 * */
	private List<String> temp_listFileName=new ArrayList<String>();
	/*
	 * 将SD卡中的文件内容存储到数据库中所用到的一些变量
	 * */
	private BufferedReader bufferReader;
	private StringBuffer strBuffer;
	private String str=null;
	private File file;
	/*
	 * 用户名
	 * */
	private String userName;
	/*
	 * 用来保存用户数据
	 * */
	private List<UserData> userDataList=new ArrayList<UserData>();
	/*
	 * 用来保存一个心电图所有采样点的数据
	 * */
	private List<EcgData> ecgDataList=new ArrayList<EcgData>();
	/*
	 * ListView所需要的适配器
	 * */
	private ArrayAdapter<String> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * inits函数的功能：用来对各个控件进行初始化
		 * */
		inits();
		
		/*
		 * 读取心电图数据
		 * */
		read_database_record();
		
		/*
		 * 更新List列表
		 * */
		updataList();
		/*
		 * 为插入按钮和删除按钮设置点击事件
		 * */
		insertButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		
		/*
		 * 为ListView各个子项设置点击事件
		 * */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str=listFileName.get(position);
				String[] str1=str.split(",");
				Intent intent=new Intent(MainActivity.this,DisplayDemoActivity.class);
				intent.putExtra("num_data", str1[0]);//为下一个活动话画出fileId等于str1[0]的数据图
				startActivity(intent);
				//finish();
				
				
			}
		});
		
	}
	public void read_database_record(){////将此用户的历史记录初始化ListView
		/*
		 * 获取从上一个Activity传送过来的Intent对象
		 * */
		Intent  intent=getIntent();
		
		userName=intent.getStringExtra("user_name");//得到登入用户的用户名，将数据库中保存的历史记录显示出来
		MainActivity.this.setTitle("欢迎用户："+userName+"的使用,    请您继续操作");
		/*
		 *根据用户名在数据库中查找此用户 
		 * */
		userDataList=DataSupport.where("userName=?",userName).find(UserData.class);
		userData=userDataList.get(0);//因为用户名都不相同，因此第一个即为我们得到用户对象
		int userId=userData.getId();//得到此用户的id号，用于在EcgData表中找出与此用户想关联的所有记录（下 条语句就是完成的此功能）
		ecgDataList=DataSupport.where("userdata_id=?",userId+"").find(EcgData.class);
		//System.out.println("userData:----------"+userData.getUserName());
		//ecgDataList=userData.getEcgDataList();
		System.out.println(" ecgDataList中的大小为："+ecgDataList.size());
		for(EcgData ecgData:ecgDataList){
			String id=ecgData.getFileId();
			System.out.println("id=-------------"+id);
			Date date=ecgData.getDate();
			listFileName.add(id+",			"+date);
		}
		updataList();
		//listFileName=temp_listFileName;
	}
	
	public void inits(){
		textView=(TextView)findViewById(R.id.textViewId);
		editText=(EditText)findViewById(R.id.editTextId);
		insertButton=(Button)findViewById(R.id.insertId);
		deleteButton=(Button)findViewById(R.id.deleteId);
		listView=(ListView)findViewById(R.id.listViewId);
		
//		db=Connector.getDatabase();
//		insertUserData();
		
		
	}
	
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.insertId:{
			String input_number=editText.getText().toString();
			if(!input_number.matches("\\d+")){
				Toast.makeText(this, "输入不合法，请输入数字", Toast.LENGTH_SHORT).show();
				break;
			}
			long time=System.currentTimeMillis();
			Date date=new Date(time); 
			listFileName.add(input_number+",			"+date.toString());//将编号加入
			insertEcgData(ReadFileToDatabase(input_number),date,input_number);//将文件中的数据写入到数据库中进行存储
			updataList();//更新ListView列表，，可能要改进一下
			break;
		}
		case R.id.deleteId:{
			String number=editText.getText().toString();
			ecgDataList=DataSupport.where("fileId=?",number+"").find(EcgData.class);
			
			for(EcgData ecgData:ecgDataList){
				Date date=ecgData.getDate();
				listFileName.remove(number+",			"+date);
				
			}
			DataSupport.deleteAll(EcgData.class,"fileId=?",number);
			
//			for(String str:listFileName){
//				String[] s=str.split(",");
//				System.out.println("删除id：-----------"+s[0]);
//				if(s[0]==number){
//					listFileName.remove(location);
//					DataSupport.deleteAll(EcgData.class,"fileId=?",number);
//					
//				}
//			}
			
			updataList();
			break;
		}
		
		
		}
		
		
	}
//	public void insertUserData(){
//		userData.setUserName("zhang_san");
//		//userData.setUserAge(17);
//		
//		
//	}
	public void insertEcgData(String str,Date date,String id){
			
		EcgData ecgData=new EcgData();
		ecgData.setContent(str);
			ecgData.setDate(date);
			ecgData.setFileId(id);
			ecgData.save();
			userData.getEcgDataList().add(ecgData);
			userData.save();
			if(ecgData.save()){
				Toast.makeText(MainActivity.this, "数据保存到数据库成功", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(MainActivity.this, "数据保存到数据库失败", Toast.LENGTH_SHORT).show();
			}
			
		}
	public void updataList(){
		adapter=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1,listFileName);
		listView.setAdapter(adapter);
	}
	
	public String ReadFileToDatabase(String num){
		 file=new File(Environment.getExternalStorageDirectory(),num+".txt");
	    //file=new File(getDefaultFilePath());
		
	    try {
	    	//Log.d("MainActivity", "bufferReader对象构建语句---------之前");
			 bufferReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			//Log.d("MainActivity", "bufferReader对象构建语句----------之后");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Log.d("MainActivity", "bufferReader对象构建语句-----------出异常啦");
		}
		 strBuffer=new StringBuffer();
		 
	
		try {
			while((str=bufferReader.readLine())!=null){
				Log.d("MainActivity", "bufferReader读取数据语句-----------之前");
				strBuffer.append(str);
				
			}
			//bufferReader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d("MainActivity", "bufferReader读取数据语句-----------出异常了啦");
		}
		return strBuffer.toString();
		
	} 
}
