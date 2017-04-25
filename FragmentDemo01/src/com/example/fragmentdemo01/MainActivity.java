package com.example.fragmentdemo01;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	
	/*
	 * ��Fragment���й����ManagerFragment��ʵ��
	 * */
	private FragmentManager fragmentManager;

	/*
	 * ��ʾ��Ϣ��Fragment
	 * */
	private MessageFragment messageFragment;
	/*
	 * ��ʾ��ϵ�˵�Fragment
	 * */
	private ContactsFragment contactsFragment;
	/*
	 * ��ʾ��̬��Fragment
	 * */
	private  NewsFragment newsFragment;
	/*
	 * ��ʾ���õ�Fragment
	 * */
	private SettingFragment settingFragment;
	
	/*
	 * 
	 * ��Ϣ����ϵ�ˡ���̬�����õĽ��沼��
	 * */
	View messageLayout,contactsLayout,newsLayout,settingLayout;
	/*
	 * ��ʾ��Ϣ����ϵ�ˡ���̬�����õ�ImageView�ؼ�ʵ��
	 * */
	ImageView messageImageView,contactsImageView,newsImageView,settingImageView;
	/*
	 * ��ʾ��Ϣ����ϵ�ˡ���̬�����õ�TextView�Ŀؼ�ʵ��
	 * */
	TextView  messageTextView,contactsTextView,newsTextView,settingTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		/*
		 * ��ʼ��
		 * */
		inits();
		fragmentManager=getFragmentManager();
		/*
		 * ��һ������ʱ������0λ�õ�Tap
		 * */
		setTapSelection(0);
	}
	
	public void inits(){
		/*
		 * ����Ϣ����ϵ�ˡ���̬�����õĽ��沼�ֽ���ʵ����
		 * */
		messageLayout=(RelativeLayout)findViewById(R.id.messageId);
		contactsLayout=(RelativeLayout)findViewById(R.id.contactsId);
		newsLayout=(RelativeLayout)findViewById(R.id.newsId);
		settingLayout=(RelativeLayout)findViewById(R.id.settingId);
		/*
		 * ����Ϣ����ϵ�ˡ���̬�������еĲ����еĿؼ�����ʵ����
		 * */
		messageImageView=(ImageView)findViewById(R.id.message_imageviewId);
		contactsImageView=(ImageView)findViewById(R.id.contacts_imageviewId);
		newsImageView=(ImageView)findViewById(R.id.news_imageviewId);
		settingImageView=(ImageView)findViewById(R.id.setting_imageviewId);
		messageTextView=(TextView)findViewById(R.id.message_textId);
		contactsTextView=(TextView)findViewById(R.id.contacts_textId);
		newsTextView=(TextView)findViewById(R.id.news_textId);
		settingTextView=(TextView)findViewById(R.id.setting_textId);
		/*
		 * Ϊ��Ϣ����ϵ�ˡ���̬���������ü�����
		 * */
		messageLayout.setOnClickListener(this);
		contactsLayout.setOnClickListener(this);
		newsLayout.setOnClickListener(this);
		settingLayout.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.messageId:{
			setTapSelection(0);
			break;
		}
		case R.id.contactsId:{
			setTapSelection(1);
			break;
		}
		case R.id.newsId:{
			setTapSelection(2);
			break;
		}
		case R.id.settingId:{
			setTapSelection(3);
			break;
		}
		
		
		}
		
	}
	public void setTapSelection(int position){
		/*
		 * ÿ��ѡ��֮ǰ������ϴε�ѡ��״̬
		 * */
		clearSelection();
		/*
		 * ����һ������
		 * */
		FragmentTransaction transaction=fragmentManager.beginTransaction();
		hideFragment(transaction);
		switch(position){
		case 0:{
			messageImageView.setImageResource(R.drawable.message_select);
			messageTextView.setTextColor(Color.WHITE);
			
			if(messageFragment==null){
				messageFragment=new MessageFragment();
				transaction.add(R.id.content, messageFragment);
			}
			else{
				transaction.show(messageFragment);
			}
			break;
		}
		case 1:{
			contactsImageView.setImageResource(R.drawable.contacts_select);
			contactsTextView.setTextColor(Color.WHITE);
			if(contactsFragment==null){
				contactsFragment=new ContactsFragment();
				transaction.add(R.id.content, contactsFragment);
				
			}
			else{
				transaction.show(contactsFragment);
			}
			break;
		}
		case 2:{
			newsImageView.setImageResource(R.drawable.news_select);
			newsTextView.setTextColor(Color.WHITE);
			if(newsFragment==null){
				newsFragment=new NewsFragment();
				transaction.add(R.id.content, newsFragment);
				
			}
			else{
				transaction.show(newsFragment);
			}
			break;
		}
		case 3:{
			settingImageView.setImageResource(R.drawable.setting_select);
			settingTextView.setTextColor(Color.WHITE);
			if(settingFragment==null){
				settingFragment=new SettingFragment();
				transaction.add(R.id.content, settingFragment);
				
			}
			else{
				transaction.show(settingFragment);
			}
			break;
		}
		default:break;
		}
		transaction.commit();
		
	}
	public void clearSelection(){
		
		messageImageView.setImageResource(R.drawable.message_unselect);
		messageTextView.setTextColor(Color.parseColor("#82858b"));
		contactsImageView.setImageResource(R.drawable.contacts_unselect);
		contactsTextView.setTextColor(Color.parseColor("#82858b"));
		newsImageView.setImageResource(R.drawable.news_unselect);
		newsTextView.setTextColor(Color.parseColor("#82858b"));
		settingImageView.setImageResource(R.drawable.setting_unselect);
		settingTextView.setTextColor(Color.parseColor("#82858b"));
		
	}
	public void hideFragment(FragmentTransaction transaction){
		if(messageFragment!=null){
			transaction.hide(messageFragment);
		}
		if(contactsFragment!=null){
			transaction.hide(contactsFragment);
		}
		if(newsFragment!=null){
			transaction.hide(newsFragment);
		}
		if(settingFragment!=null){
			transaction.hide(settingFragment);
		}
		
		
		
	}

}
