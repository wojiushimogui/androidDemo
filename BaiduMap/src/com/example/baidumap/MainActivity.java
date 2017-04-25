package com.example.baidumap;


import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;



public class MainActivity extends Activity {  
    private Toast mToast;  
    /*
     * 确定自己位置的管理类
     */
    private LocationManager locationManager;
    /*
     * Location对象中包含了经度、纬度、海拔等一系列的位置信息
     * */
   // private Location location;
    /*
     * 位置提供器
     * */
    String provider;
    
    private BMapManager mBMapManager;  
    /** 
     * MapView 是地图主控件 
     */  
    private MapView mMapView = null;  
    /** 
     * MapController为地图的总控制器
     * 用MapController完成地图控制 
     */  
    private MapController mMapController = null;  
    /** 
     * 保存精度和纬度的类, 
     */  
    GeoPoint point;
    /** 
     * MKMapViewListener 用于处理地图事件回调 
     */  
    MKMapViewListener mMapListener = null; 
  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
       
        
        /** 
         * 使用地图sdk前需先初始化BMapManager，这个必须在setContentView()先初始化 
         **/  
        mBMapManager = new BMapManager(this);  
          
        //第一个参数是API key,  
        //第二个参数是常用事件监听，用来处理通常的网络错误，授权验证错误等，你也可以不添加这个回调接口  
        mBMapManager.init("N0FX0g18X0D04AQ8ckTptfgx", new MKGeneralListener() {  
              
            //授权错误的时候调用的回调函数  
            @Override  
            public void onGetPermissionState(int iError) {  
                if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {  
                    showToast("API KEY错误, 请检查！");  
                }  
            }  
              
            //一些网络状态的错误处理回调函数  
            @Override  
            public void onGetNetworkState(int iError) {  
                if (iError == MKEvent.ERROR_NETWORK_CONNECT) {  
                    Toast.makeText(getApplication(), "您的网络出错啦！", Toast.LENGTH_LONG).show();  
                }  
            }  
        });  
          
          
        setContentView(R.layout.activity_main);  //加载布局 
        /*
         * 获取LocationManager的实例
         * */
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
      //判断GPS是否正常启动  
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
            Toast.makeText(this, "请开启GPS导航...", Toast.LENGTH_SHORT).show();  
          
            //返回开启GPS导航设置界面  
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
            startActivityForResult(intent,0);   
            return;  
        }
        
       
        /*
         * 
         * 获取所有可用的位置提供器,
         */
        List<String> providerList=locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
        	provider=LocationManager.GPS_PROVIDER;
        	
        }
        else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
        	provider=LocationManager.NETWORK_PROVIDER;
        	
        }
        else{
        	showToast("没有可用的位置提供器");
        	return;
        }
        
       
        /*
         * getLastKnownLocation（）方法可以获取到设备当前的位置信息
         * */
       Location location=locationManager.getLastKnownLocation(provider);
        if(location!=null){
        	navigateTo(location);
        	
        }
        /*
         * 当设备移动时，获取到最新的位置信息
         * 第一个参数为：位置提供其的类型
         * 第二个参数为：监听位置变化的时间间隔，这里的5000即指5000毫秒更新一次，更新即调用下面的onLocationChanged方法
         * 第三个参数为：监听位置变化的距离间隔，以米为单位，这里的5指的是当移动距离超过5米的时候更新一次
         * 第四个参数为：LocationListener的监听器
         * */
        locationManager.requestLocationUpdates(provider, 5000, 5, locationListener);
       
        
       
        mMapView = (MapView) findViewById(R.id.bmapView);  
      
         
          
        /** 
         * 显示内置缩放控件 
         */  
        mMapView.setBuiltInZoomControls(true);  //传入true表示启用内置的缩放控制功能
          
        
         
        mMapView.regMapViewListener(mBMapManager, new MKMapViewListener() {  
              
            /** 
             * 地图移动完成时会回调此接口 方法 
             */  
            @Override  
            public void onMapMoveFinish() {  
                showToast("地图移动完毕！");  
            }  
              
            /** 
             * 地图加载完毕回调此接口方法 
             */  
            @Override  
            public void onMapLoadFinish() {  
                showToast("地图载入完毕！");  
            }  
              
            /** 
             *  地图完成带动画的操作（如: animationTo()）后，此回调被触发 
             */  
            @Override  
            public void onMapAnimationFinish() {  
                  
            }  
              
            /** 
             *  当调用过 mMapView.getCurrentMap()后，此回调会被触发 
             *  可在此保存截图至存储设备 
             */  
            @Override  
            public void onGetCurrentMap(Bitmap arg0) {  
                  
            }  
              
            /** 
             * 点击地图上被标记的点回调此方法 
             *  
             */  
            @Override  
            public void onClickMapPoi(MapPoi arg0) {  
                if (arg0 != null){  
                    showToast(arg0.strText);  
                }  
            }  
        });
        
        
    }  
    
    
    
    LocationListener locationListener=new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			navigateTo(location);
			
		}

		

		@Override
		public void onStatusChanged(String provider, int status,
				Bundle extras) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
    	
    };
  
  
      
   private void navigateTo(Location location) {
		// TODO Auto-generated method stub
    	 
        /** 
         * 
         * 获取地图控制器 的实例
         **/  
      mMapController = mMapView.getController();  
      /** 
       *  设置地图是否响应点击事件  . 
       */  
      mMapController.enableClick(true);  
      /** 
       * 设置地图缩放级别 
       */  
      mMapController.setZoom(16);//其取值为3到19，级别越高，地图显示的信息就越精细  
      point = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));  
      //设置p地方为中心点  
      mMapController.setCenter(point); 
      //下面的代码为在百度地图中使用覆盖物
      MyLocationOverlay myLocationOverlay=new MyLocationOverlay(mMapView);
      LocationData locationData=new LocationData();
      //指定我的位置
      locationData.latitude=location.getLatitude();
      locationData.longitude=location.getLongitude();
      myLocationOverlay.setData(locationData);
      mMapView.getOverlays().add(myLocationOverlay);
      mMapView.refresh();//刷新使新增覆盖物生效
      
      
		
	}
  
	@Override  
    protected void onResume() {  
        //MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()  
        mMapView.onResume();  
        if(mBMapManager!=null){
        	mBMapManager.start();
        	
        }
        super.onResume();  
    }  
  
  
  
    @Override  
    protected void onPause() {  
        //MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()  
        mMapView.onPause();  
        if(mBMapManager!=null){
        	mBMapManager.stop();
        	
        }
        super.onPause();  
    }  
  
    @Override  
    protected void onDestroy() {  
        //MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()  
        mMapView.destroy();  
          
        //退出应用调用BMapManager的destroy()方法  
        if(mBMapManager != null){  
            mBMapManager.destroy();  
            mBMapManager = null;  
        }  
        if(locationManager!=null){//关闭程序时将监听器移除
        	locationManager.removeUpdates(locationListener);
        	
        }
      
          
        super.onDestroy();  
    }  
  
      
      
     /**  
     * 显示Toast消息  
     * @param msg  
     */    
    private void showToast(String msg){    
        if(mToast == null){    
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);    
        }else{    
            mToast.setText(msg);    
            mToast.setDuration(Toast.LENGTH_SHORT);  
        }    
        mToast.show();    
    }   
      
      
} 
