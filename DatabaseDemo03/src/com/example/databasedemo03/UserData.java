package com.example.databasedemo03;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

public class UserData extends DataSupport {

	private int id;
	private String userName;
	private String userPwd;
	
	private List<EcgData> ecgDataList=new ArrayList<EcgData>();

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<EcgData> getEcgDataList() {
		return ecgDataList;
	}

	public void setEcgDataList(List<EcgData> ecgDataList) {
		this.ecgDataList = ecgDataList;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	

	public UserData(){
		//空的构造函数
	}

	public UserData(String userName, String userPwd) {
		super();
		this.userName = userName;
		this.userPwd = userPwd;
	}

}
