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
	 * �ؼ���һЩ����ʵ��
	 * */
	private Button insertButton,deleteButton;
	private TextView textView;
	private EditText editText;
	private ListView listView;
	//private SQLiteDatabase db;
	private static volatile UserData  userData=new UserData();
	//private EcgData ecgData=new EcgData();
	/*
	 * ���������б����ļ�������
	 * */
	private List<String> listFileName=new ArrayList<String>();
	/*
	 * ������ʱ�����ļ�������
	 * */
	private List<String> temp_listFileName=new ArrayList<String>();
	/*
	 * ��SD���е��ļ����ݴ洢�����ݿ������õ���һЩ����
	 * */
	private BufferedReader bufferReader;
	private StringBuffer strBuffer;
	private String str=null;
	private File file;
	/*
	 * �û���
	 * */
	private String userName;
	/*
	 * ���������û�����
	 * */
	private List<UserData> userDataList=new ArrayList<UserData>();
	/*
	 * ��������һ���ĵ�ͼ���в����������
	 * */
	private List<EcgData> ecgDataList=new ArrayList<EcgData>();
	/*
	 * ListView����Ҫ��������
	 * */
	private ArrayAdapter<String> adapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * inits�����Ĺ��ܣ������Ը����ؼ����г�ʼ��
		 * */
		inits();
		
		/*
		 * ��ȡ�ĵ�ͼ����
		 * */
		read_database_record();
		
		/*
		 * ����List�б�
		 * */
		updataList();
		/*
		 * Ϊ���밴ť��ɾ����ť���õ���¼�
		 * */
		insertButton.setOnClickListener(this);
		deleteButton.setOnClickListener(this);
		
		/*
		 * ΪListView�����������õ���¼�
		 * */
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String str=listFileName.get(position);
				String[] str1=str.split(",");
				Intent intent=new Intent(MainActivity.this,DisplayDemoActivity.class);
				intent.putExtra("num_data", str1[0]);//Ϊ��һ���������fileId����str1[0]������ͼ
				startActivity(intent);
				//finish();
				
				
			}
		});
		
	}
	public void read_database_record(){////�����û�����ʷ��¼��ʼ��ListView
		/*
		 * ��ȡ����һ��Activity���͹�����Intent����
		 * */
		Intent  intent=getIntent();
		
		userName=intent.getStringExtra("user_name");//�õ������û����û����������ݿ��б������ʷ��¼��ʾ����
		MainActivity.this.setTitle("��ӭ�û���"+userName+"��ʹ��,    ������������");
		/*
		 *�����û��������ݿ��в��Ҵ��û� 
		 * */
		userDataList=DataSupport.where("userName=?",userName).find(UserData.class);
		userData=userDataList.get(0);//��Ϊ�û���������ͬ����˵�һ����Ϊ���ǵõ��û�����
		int userId=userData.getId();//�õ����û���id�ţ�������EcgData�����ҳ�����û�����������м�¼���� ����������ɵĴ˹��ܣ�
		ecgDataList=DataSupport.where("userdata_id=?",userId+"").find(EcgData.class);
		//System.out.println("userData:----------"+userData.getUserName());
		//ecgDataList=userData.getEcgDataList();
		System.out.println(" ecgDataList�еĴ�СΪ��"+ecgDataList.size());
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
				Toast.makeText(this, "���벻�Ϸ�������������", Toast.LENGTH_SHORT).show();
				break;
			}
			long time=System.currentTimeMillis();
			Date date=new Date(time); 
			listFileName.add(input_number+",			"+date.toString());//����ż���
			insertEcgData(ReadFileToDatabase(input_number),date,input_number);//���ļ��е�����д�뵽���ݿ��н��д洢
			updataList();//����ListView�б�������Ҫ�Ľ�һ��
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
//				System.out.println("ɾ��id��-----------"+s[0]);
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
				Toast.makeText(MainActivity.this, "���ݱ��浽���ݿ�ɹ�", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(MainActivity.this, "���ݱ��浽���ݿ�ʧ��", Toast.LENGTH_SHORT).show();
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
	    	//Log.d("MainActivity", "bufferReader���󹹽����---------֮ǰ");
			 bufferReader=new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			//Log.d("MainActivity", "bufferReader���󹹽����----------֮��");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			Log.d("MainActivity", "bufferReader���󹹽����-----------���쳣��");
		}
		 strBuffer=new StringBuffer();
		 
	
		try {
			while((str=bufferReader.readLine())!=null){
				Log.d("MainActivity", "bufferReader��ȡ�������-----------֮ǰ");
				strBuffer.append(str);
				
			}
			//bufferReader.close();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			Log.d("MainActivity", "bufferReader��ȡ�������-----------���쳣����");
		}
		return strBuffer.toString();
		
	} 
}
