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
	 * 用户账号
	 * */
	private EditText mAccount;
	/*
	 * 用户密码
	 * */
	private EditText mPwd;
	/*
	 * 注册按钮
	 * */
	private Button mRegisterButton;
	/*
	 * 登陆按钮
	 * */
	private Button mLoginButton;
	/*
	 * 取消按钮
	 * */
	private Button mCancleButton;
	/*
	 * 登陆页面
	 * */
	private View loginView;
	/*
	 * 登陆成功View
	 * */
	private View loginSuccessView;
	/*
	 * 登陆成功显示TextView
	 * */
	private TextView loginSuccessShow;
	/*
	 * 保存在数据库中根据用户名和密码查找到的用户信息列表
	 * */
	private List<UserData> userDataList=new ArrayList<UserData>();
	/*
	 * 用户实例
	 * */
	private UserData userData=new UserData();

	/*
	 * 数据库对象
	 * */
	private SQLiteDatabase db;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.loginpage);
		this.setTitle("欢迎使用由何老师团队开发的心电图APP");
		inits();
		
		//为各个按钮设置监听器
		mRegisterButton.setOnClickListener(mListener);
		mLoginButton.setOnClickListener(mListener);
		mCancleButton.setOnClickListener(mListener);
		
	}
	public void inits(){
		//获取各个控件的对象
		mAccount = (EditText) findViewById(R.id.login_edit_account);
		mPwd = (EditText) findViewById(R.id.login_edit_pwd);
		mRegisterButton = (Button) findViewById(R.id.login_btn_register);
		mLoginButton = (Button) findViewById(R.id.login_btn_login);
		mCancleButton = (Button) findViewById(R.id.login_btn_cancle);
		loginView=findViewById(R.id.login_view);
		loginSuccessView=findViewById(R.id.login_success_view);
		loginSuccessShow=(TextView) findViewById(R.id.login_success_show);	
		//得到一个数据库对象
		db=Connector.getDatabase();
	}

	OnClickListener mListener = new OnClickListener() {
		public void onClick(View v) {//根据id来判断哪个按钮被点击了，并执行相应的操作
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
	 * 功能：登陆
	 * */
	public void login() {
		if (isUserNameAndPwdValid()) {
			String userName = mAccount.getText().toString().trim();//获取用户名（去除后面的空格）
			String userPwd = mPwd.getText().toString().trim();//获取密码（去除后面的空格）
			int result=findUserByNameAndPwd(userName, userPwd);//根据用户输入的用户名和密码在数据库中进行查找
			
			System.out.println("result="+result);
			if(result==1){
				//login success
				loginView.setVisibility(View.GONE);
				loginSuccessView.setVisibility(View.VISIBLE);
				loginSuccessShow.setText(getString(R.string.user_login_sucess, userName));
				
				
				Toast.makeText(this, getString(R.string.login_sucess),
						Toast.LENGTH_SHORT).show();
				/*
				 * 通过Intent从LoginActivity跳转到MainActivity中，并携带有用户名
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
		 * 在数据库中查找符合用户名和密码的用户信息，是以list返回的
		 * */
		userDataList=DataSupport.where("userName=? and userPwd=?",Name,pwd).find(UserData.class);
		System.out.println("-----------------------登入"+userDataList.size());
		return userDataList.size();
	}
	
	public int findUserByName(String Name){//
		//Log.i(TAG,"findUserByName , userName="+userName);
		int result=0;
		/*
		 * 在数据库中查找符合用户名用户信息，用来判断此用户名是否已经注册
		 * */
		userDataList=DataSupport.where("userName=?",Name).find(UserData.class);
		
		 result=userDataList.size();
		 System.out.println("-------------------------------注册的："+result);
		 return  result;
		
	}
//public boolean insertUserData(UserData userData) {//保存用户数据到数据库
//		
//		String userName=userData.getUserName();
//		String userPwd=userData.getUserPwd();
//		userData.setUserName(userName);
//		userData.setUserPwd(userPwd);
//		return userData.save();
//	}

	public void register() {//注册 
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
			
			//下面三行的功能时将用户数据保存到数据库，可以用一个函数写出
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
	 * 功能：取消
	 * */
	public void cancle() {
		/*
		 * 将输入用户数据的对话框清空，以备下次输入
		 * */
		mAccount.setText("");
		mPwd.setText("");
	}

	public boolean isUserNameAndPwdValid() {
		if (mAccount.getText().toString().trim().equals("")) {//若账号输入为空的情况，给出提示
			Toast.makeText(this, getString(R.string.account_empty),
					Toast.LENGTH_SHORT).show();
			return false;
		} else if (mPwd.getText().toString().trim().equals("")) {//若密码为空，给出相应的提示
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