package com.example.databasedemo03;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	/*
	 * �û��˺�
	 * */
	private EditText mAccount;
	/*
	 * �û�����
	 * */
	private EditText mPwd;
	/*
	 * ע�ᰴť
	 * */
	private Button mRegisterButton;
	/*
	 * ��½��ť
	 * */
	private Button mLoginButton;
	/*
	 * ȡ����ť
	 * */
	private Button mCancleButton;
	/*
	 * ��½ҳ��
	 * */
	private View loginView;
	/*
	 * ��½�ɹ�View
	 * */
	private View loginSuccessView;
	/*
	 * ��½�ɹ���ʾTextView
	 * */
	private TextView loginSuccessShow;
	/*
	 * ���������ݿ��и����û�����������ҵ����û���Ϣ�б�
	 * */
	private List<UserData> userDataList=new ArrayList<UserData>();
	/*
	 * �û�ʵ��
	 * */
	private UserData userData=new UserData();

	/*
	 * ���ݿ����
	 * */
	private SQLiteDatabase db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		this.setTitle("��ӭʹ���ɺ���ʦ�Ŷӿ������ĵ�ͼAPP");
		inits();
		
		//Ϊ������ť���ü�����
		mRegisterButton.setOnClickListener(mListener);
		mLoginButton.setOnClickListener(mListener);
		mCancleButton.setOnClickListener(mListener);
		
	}
	public void inits(){
		//��ȡ�����ؼ��Ķ���
		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mRegisterButton = (Button) findViewById(R.id.login_btn_register);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
		loginView=findViewById(R.id.login_view);
		loginSuccessView=findViewById(R.id.login_success_view);
		loginSuccessShow=(TextView) findViewById(R.id.login_success_show);	
		//�õ�һ�����ݿ����
		db=Connector.getDatabase();
	}

	OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {//����id���ж��ĸ���ť������ˣ���ִ����Ӧ�Ĳ���
			switch (v.getId()) {
			case R.id.login_btn_register:
				register();
				break;
			case R.id.login_btn_login:
				login();
				break;
			case R.id.login_btn_cancle:
				cancle();
				break;
			}
		}
	};
	/*
	 * ���ܣ���½
	 * */
	public void login() {
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();//��ȡ�û�����ȥ������Ŀո�
			String userPwd = mPwd.getText().toString().trim();//��ȡ���루ȥ������Ŀո�
			int result=findUserByNameAndPwd(userName, userPwd);//�����û�������û��������������ݿ��н��в���
			
			System.out.println("result="+result);
			if(result==1){
				//login success
				loginView.setVisibility(View.GONE);
				loginSuccessView.setVisibility(View.VISIBLE);
				loginSuccessShow.setText(getString(R.string.user_login_sucess, userName));
				
				
				Toast.makeText(this, getString(R.string.login_sucess),
						Toast.LENGTH_SHORT).show();
				/*
				 * ͨ��Intent��LoginActivity��ת��MainActivity�У���Я�����û���
				 * */
				Intent intent=new Intent(LoginActivity.this,MainActivity.class);
				intent.putExtra("user_name", userName);
				startActivity(intent);
				finish();
			}else if(result==0){
				//login failed,user does't exist
				Toast.makeText(this, getString(R.string.login_fail),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	
	public int findUserByNameAndPwd(String Name,String pwd){
		/*
		 * �����ݿ��в��ҷ����û�����������û���Ϣ������list���ص�
		 * */
		userDataList=DataSupport.where("userName=? and userPwd=?",Name,pwd).find(UserData.class);
		System.out.println("-----------------------����"+userDataList.size());
		return userDataList.size();
	}
	
	public int findUserByName(String Name){//
		//Log.i(TAG,"findUserByName , userName="+userName);
		int result=0;
		/*
		 * �����ݿ��в��ҷ����û����û���Ϣ�������жϴ��û����Ƿ��Ѿ�ע��
		 * */
		userDataList=DataSupport.where("userName=?",Name).find(UserData.class);
		
		 result=userDataList.size();
		 System.out.println("-------------------------------ע��ģ�"+result);
		 return  result;
		
	}
//public boolean insertUserData(UserData userData) {//�����û����ݵ����ݿ�
//		
//		String userName=userData.getUserName();
//		String userPwd=userData.getUserPwd();
//		userData.setUserName(userName);
//		userData.setUserPwd(userPwd);
//		return userData.save();
//	}

	public void register() {//ע�� 
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();
			String userPwd = mPwd.getText().toString().trim();
			//check if user name is already exist
			int count=findUserByName(userName);
			
			if(count>0){
				Toast.makeText(this, getString(R.string.name_already_exist, userName),
						Toast.LENGTH_SHORT).show();
				return ;
			}
			
			//�������еĹ���ʱ���û����ݱ��浽���ݿ⣬������һ������д��
			userData.setUserName(userName);
			userData.setUserPwd(userPwd);
			
			boolean flag = userData.save();
			if (flag == false) {
				Toast.makeText(this, getString(R.string.register_fail),
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(this, getString(R.string.register_sucess),
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	/*
	 * ���ܣ�ȡ��
	 * */
	public void cancle() {
		/*
		 * �������û����ݵĶԻ�����գ��Ա��´�����
		 * */
		mAccount.setText("");
		mPwd.setText("");
	}

	public boolean isUserNameAndPwdValid() {
		if (mAccount.getText().toString().trim().equals("")) {//���˺�����Ϊ�յ������������ʾ
			Toast.makeText(this, getString(R.string.account_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {//������Ϊ�գ�������Ӧ����ʾ
			Toast.makeText(this, getString(R.string.pwd_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		}
		return true;
	}


	@Override
	protected void onDestroy() {
		super.onDestroy();
	}


	 public void onBackPressed(){
	    	finish();
	    }
}