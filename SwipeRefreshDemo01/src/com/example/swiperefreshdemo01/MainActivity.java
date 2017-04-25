package com.example.swiperefreshdemo01;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private SwipeRefreshLayout  swipeRefreshLayout;
	private ListView listView;
	private TextView textView;
	private List<String> lists=new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe_refresh);
		listView=(ListView)findViewById(R.id.listViewId);
		textView=(TextView)findViewById(R.id.textViewId);
		
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getData()));
		 swipeRefreshLayout.setEnabled(false);
		swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipeRefreshLayout.setRefreshing(true);
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
					}
				}, 3000);
				
			}
		});
		  listView.setOnScrollListener(new AbsListView.OnScrollListener() {
	            @Override
	             public void onScrollStateChanged(AbsListView absListView, int i) {
	 
	        }
	 
	            @Override
	             public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
	                if (firstVisibleItem == 0)
	                    swipeRefreshLayout.setEnabled(true);
	                else
	                    swipeRefreshLayout.setEnabled(false);
	        }
	    });
		
		
		
	}
	
  public List<String> getData(){
	  for (int i = 0; i < 20; i++) {
		lists.add("wuranghao:"+i);
	}
	  return lists;
  }


}
