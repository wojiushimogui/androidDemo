package com.example.vollydemo;

import org.json.JSONObject;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends ActionBarActivity {

	private TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView =(TextView)findViewById(R.id.textViewId);
		RequestQueue mQueue=Volley.newRequestQueue(this);
//	StringRequest stringRequest=new StringRequest("http://www.baidu.com", new Response.Listener<String>() {
//
//		@Override
//		public void onResponse(String response) {
//			// TODO Auto-generated method stub
//			//Log.d("TAG",response);
//			textView.setText(response);
//			
//		}
//	}, new Response.ErrorListener() {
//
//		@Override
//		public void onErrorResponse(VolleyError error) {
//			// TODO Auto-generated method stub
//			textView.setText(error.toString());
//			
//		}
//	});
	//mQueue.add(stringRequest);
		JsonObjectRequest jsonObjectRequest=new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null
				, new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						// TODO Auto-generated method stub
						textView.setText(response.toString());
						
						Log.d("TAG", response.toString());
						
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						textView.setText(error.toString());
						
					}
				});
		mQueue.add(jsonObjectRequest);		
	}
}
