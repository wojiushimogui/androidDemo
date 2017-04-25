package com.example.android_heart;

import java.text.SimpleDateFormat;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


/**
 * 褰撳墠绯荤粺鑾峰彇涓�浜涙暟鎹被
 * 
 * @author Administrator
 * 
 */
public class GetMessage {
	public static String get_name = "abcdefg";// 鐢ㄤ簬璁板綍JSON瑙ｆ瀽涓殑鍚嶅瓧
	public static int flag1 = 0;// JSON瑙ｆ瀽鎿嶄綔鎺у埗
	// 鐥呬汉涓汉淇℃伅鏇存柊涓�鍧�
	public static EditText edit_phoneNumber;// 鐢ㄦ潵淇濆瓨鎺ユ敹鍒扮殑浣忛櫌鍙�
	public static boolean flag_con = true;// 浣滀负鏄惁璺冲嚭杈撳叆妗嗙殑鏍囪
	public static EditText edittext_password;
	public static String[] array3 = new String[200];
	public static int len = 0;// 鑱旂郴浜轰釜鏁�
	public static long last_time = 0;// 涓婁竴娆¤Е鎽镐簨浠剁殑鏃堕棿

	/**
	 * 鑾峰彇褰撳墠鏃堕棿
	 */
	public static String getCurrentTime() {

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = formatter.format(System.currentTimeMillis());
		return str;
	}



}
