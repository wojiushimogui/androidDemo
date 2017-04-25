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
     * ȷ���Լ�λ�õĹ�����
     */
    private LocationManager locationManager;
    /*
     * Location�����а����˾��ȡ�γ�ȡ����ε�һϵ�е�λ����Ϣ
     * */
   // private Location location;
    /*
     * λ���ṩ��
     * */
    String provider;
    
    private BMapManager mBMapManager;  
    /** 
     * MapView �ǵ�ͼ���ؼ� 
     */  
    private MapView mMapView = null;  
    /** 
     * MapControllerΪ��ͼ���ܿ�����
     * ��MapController��ɵ�ͼ���� 
     */  
    private MapController mMapController = null;  
    /** 
     * ���澫�Ⱥ�γ�ȵ���, 
     */  
    GeoPoint point;
    /** 
     * MKMapViewListener ���ڴ����ͼ�¼��ص� 
     */  
    MKMapViewListener mMapListener = null; 
  
  
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);  
       
        
        /** 
         * ʹ�õ�ͼsdkǰ���ȳ�ʼ��BMapManager�����������setContentView()�ȳ�ʼ�� 
         **/  
        mBMapManager = new BMapManager(this);  
          
        //��һ��������API key,  
        //�ڶ��������ǳ����¼���������������ͨ�������������Ȩ��֤����ȣ���Ҳ���Բ��������ص��ӿ�  
        mBMapManager.init("N0FX0g18X0D04AQ8ckTptfgx", new MKGeneralListener() {  
              
            //��Ȩ�����ʱ����õĻص�����  
            @Override  
            public void onGetPermissionState(int iError) {  
                if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {  
                    showToast("API KEY����, ���飡");  
                }  
            }  
              
            //һЩ����״̬�Ĵ�����ص�����  
            @Override  
            public void onGetNetworkState(int iError) {  
                if (iError == MKEvent.ERROR_NETWORK_CONNECT) {  
                    Toast.makeText(getApplication(), "���������������", Toast.LENGTH_LONG).show();  
                }  
            }  
        });  
          
          
        setContentView(R.layout.activity_main);  //���ز��� 
        /*
         * ��ȡLocationManager��ʵ��
         * */
        locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
      //�ж�GPS�Ƿ���������  
        if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){  
            Toast.makeText(this, "�뿪��GPS����...", Toast.LENGTH_SHORT).show();  
          
            //���ؿ���GPS�������ý���  
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);     
            startActivityForResult(intent,0);   
            return;  
        }
        
       
        /*
         * 
         * ��ȡ���п��õ�λ���ṩ��,
         */
        List<String> providerList=locationManager.getProviders(true);
        if(providerList.contains(LocationManager.GPS_PROVIDER)){
        	provider=LocationManager.GPS_PROVIDER;
        	
        }
        else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
        	provider=LocationManager.NETWORK_PROVIDER;
        	
        }
        else{
        	showToast("û�п��õ�λ���ṩ��");
        	return;
        }
        
       
        /*
         * getLastKnownLocation�����������Ի�ȡ���豸��ǰ��λ����Ϣ
         * */
       Location location=locationManager.getLastKnownLocation(provider);
        if(location!=null){
        	navigateTo(location);
        	
        }
        /*
         * ���豸�ƶ�ʱ����ȡ�����µ�λ����Ϣ
         * ��һ������Ϊ��λ���ṩ�������
         * �ڶ�������Ϊ������λ�ñ仯��ʱ�����������5000��ָ5000�������һ�Σ����¼����������onLocationChanged����
         * ����������Ϊ������λ�ñ仯�ľ�����������Ϊ��λ�������5ָ���ǵ��ƶ����볬��5�׵�ʱ�����һ��
         * ���ĸ�����Ϊ��LocationListener�ļ�����
         * */
        locationManager.requestLocationUpdates(provider, 5000, 5, locationListener);
       
        
       
        mMapView = (MapView) findViewById(R.id.bmapView);  
      
         
          
        /** 
         * ��ʾ�������ſؼ� 
         */  
        mMapView.setBuiltInZoomControls(true);  //����true��ʾ�������õ����ſ��ƹ���
          
        
         
        mMapView.regMapViewListener(mBMapManager, new MKMapViewListener() {  
              
            /** 
             * ��ͼ�ƶ����ʱ��ص��˽ӿ� ���� 
             */  
            @Override  
            public void onMapMoveFinish() {  
                showToast("��ͼ�ƶ���ϣ�");  
            }  
              
            /** 
             * ��ͼ������ϻص��˽ӿڷ��� 
             */  
            @Override  
            public void onMapLoadFinish() {  
                showToast("��ͼ������ϣ�");  
            }  
              
            /** 
             *  ��ͼ��ɴ������Ĳ�������: animationTo()���󣬴˻ص������� 
             */  
            @Override  
            public void onMapAnimationFinish() {  
                  
            }  
              
            /** 
             *  �����ù� mMapView.getCurrentMap()�󣬴˻ص��ᱻ���� 
             *  ���ڴ˱����ͼ���洢�豸 
             */  
            @Override  
            public void onGetCurrentMap(Bitmap arg0) {  
                  
            }  
              
            /** 
             * �����ͼ�ϱ���ǵĵ�ص��˷��� 
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
         * ��ȡ��ͼ������ ��ʵ��
         **/  
      mMapController = mMapView.getController();  
      /** 
       *  ���õ�ͼ�Ƿ���Ӧ����¼�  . 
       */  
      mMapController.enableClick(true);  
      /** 
       * ���õ�ͼ���ż��� 
       */  
      mMapController.setZoom(16);//��ȡֵΪ3��19������Խ�ߣ���ͼ��ʾ����Ϣ��Խ��ϸ  
      point = new GeoPoint((int)(location.getLatitude() * 1E6), (int)(location.getLongitude() * 1E6));  
      //����p�ط�Ϊ���ĵ�  
      mMapController.setCenter(point); 
      //����Ĵ���Ϊ�ڰٶȵ�ͼ��ʹ�ø�����
      MyLocationOverlay myLocationOverlay=new MyLocationOverlay(mMapView);
      LocationData locationData=new LocationData();
      //ָ���ҵ�λ��
      locationData.latitude=location.getLatitude();
      locationData.longitude=location.getLongitude();
      myLocationOverlay.setData(locationData);
      mMapView.getOverlays().add(myLocationOverlay);
      mMapView.refresh();//ˢ��ʹ������������Ч
      
      
		
	}
  
	@Override  
    protected void onResume() {  
        //MapView������������Activityͬ������activity����ʱ�����MapView.onPause()  
        mMapView.onResume();  
        if(mBMapManager!=null){
        	mBMapManager.start();
        	
        }
        super.onResume();  
    }  
  
  
  
    @Override  
    protected void onPause() {  
        //MapView������������Activityͬ������activity����ʱ�����MapView.onPause()  
        mMapView.onPause();  
        if(mBMapManager!=null){
        	mBMapManager.stop();
        	
        }
        super.onPause();  
    }  
  
    @Override  
    protected void onDestroy() {  
        //MapView������������Activityͬ������activity����ʱ�����MapView.destroy()  
        mMapView.destroy();  
          
        //�˳�Ӧ�õ���BMapManager��destroy()����  
        if(mBMapManager != null){  
            mBMapManager.destroy();  
            mBMapManager = null;  
        }  
        if(locationManager!=null){//�رճ���ʱ���������Ƴ�
        	locationManager.removeUpdates(locationListener);
        	
        }
      
          
        super.onDestroy();  
    }  
  
      
      
     /**  
     * ��ʾToast��Ϣ  
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
