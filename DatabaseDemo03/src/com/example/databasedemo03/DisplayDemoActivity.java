package com.example.databasedemo03;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ZoomControls;

public class DisplayDemoActivity extends ActionBarActivity {

	    /** Called when the activity is first created. */
	   private Button btnStart,btnExit;
	   private SurfaceView sfv;
	   private String num;
	   private ZoomControls zctlX,zctlY;
	    
	   private  ClsOscilloscope clsOscilloscope=new ClsOscilloscope();
	    
	        
	   private     static final int xMax = 200;//X����С�������ֵ,X���������޴����ײ���ˢ����ʱ
	   private     static final int xMin = 0;//X����С������Сֵ
	   private     static final int yMax = 400;//Y��Ŵ�������ֵ
	   private     static final int yMin = 100;//Y��Ŵ������Сֵ
	        
	   private     int recBufSize;//¼����Сbuffer��С
	   private     Paint mPaint;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_display_demo);
	        //¼�����
	        Intent intent=getIntent();
			 num=intent.getStringExtra("num_data");
			Toast.makeText(DisplayDemoActivity.this, "�յ����ݱ��:"+num, Toast.LENGTH_SHORT).show();
	                
	                //����
	                btnStart = (Button) this.findViewById(R.id.btnStart);
	                btnStart.setOnClickListener(new ClickEvent());
	                btnExit = (Button) this.findViewById(R.id.btnExit);
	                btnExit.setOnClickListener(new ClickEvent());
	                //����ͻ���
	                sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01); 
	                System.out.println("----------------------height="+sfv.getHeight());
	                System.out.println("-----------------------width="+sfv.getWidth());
	                sfv.setOnTouchListener(new TouchEvent());
	                mPaint = new Paint();  
	                mPaint.setColor(Color.GREEN);// ����Ϊ��ɫ  
	                mPaint.setStrokeWidth(1);// ���û��ʴ�ϸ 
	        //ʾ�������
	        clsOscilloscope.initOscilloscope(xMax/2, yMax/2, sfv.getHeight()/2);
	        
	        //���ſؼ���X���������С�ı��ʸ�Щ
	                zctlX = (ZoomControls)this.findViewById(R.id.zctlX);
	                zctlX.setOnZoomInClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateX>xMin)
	                                        clsOscilloscope.rateX-=10;
	                                setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
	                                                +","+"Y��Ŵ�"+String.valueOf(clsOscilloscope.rateY)+"��");
	                        }
	                });
	                zctlX.setOnZoomOutClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateX<xMax)
	                                        clsOscilloscope.rateX+=10;        
	                                setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
	                                                +","+"Y��Ŵ�"+String.valueOf(clsOscilloscope.rateY)+"��");
	                        }
	                });
	                zctlY = (ZoomControls)this.findViewById(R.id.zctlY);
	                zctlY.setOnZoomInClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateY>yMin)
	                                        clsOscilloscope.rateY-=50;
	                                setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
	                                                +","+"Y��Ŵ�"+String.valueOf(clsOscilloscope.rateY)+"��");
	                        }
	                });
	                
	                zctlY.setOnZoomOutClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateY<yMax)
	                                        clsOscilloscope.rateY+=50;        
	                                setTitle("X����С"+String.valueOf(clsOscilloscope.rateX)+"��"
	                                                +","+"Y��Ŵ�"+String.valueOf(clsOscilloscope.rateY)+"��");
	                        }
	                });
	    }
	    public void onBackPressed() {
	    	
	    	Intent intent=new Intent(DisplayDemoActivity.this,MainActivity.class);
	    	startActivity(intent);
	    	
	    	finish();
	    	//onDestroy();
	    	
	    };
	        /*@Override
	        protected void onDestroy() {
	        	
	                super.onDestroy();
	             //   android.os.Process.killProcess(android.os.Process.myPid());
	        }*/
	   
	        
	        /**
	         * �����¼�����
	         * @author GV
	         *
	         */
	        class ClickEvent implements View.OnClickListener {
	                @Override
	                public void onClick(View v) {
	                        if (v == btnStart) {
	                        	Toast.makeText(getApplicationContext(), "��ʼ�������", Toast.LENGTH_SHORT).show();
	                                clsOscilloscope.baseLine=sfv.getHeight()/2;
	                                clsOscilloscope.Start(num,recBufSize,sfv,mPaint);
	                        } else if (v == btnExit) {
	                        	Toast.makeText(getApplicationContext(), "stop�������", Toast.LENGTH_SHORT).show();
	                                clsOscilloscope.Stop();
	                        }
	                }
	        }
	        /**
	         * ��������̬���ò���ͼ����
	         * @author GV
	         *
	         */
	        class TouchEvent implements OnTouchListener{
	                @Override
	                public boolean onTouch(View v, MotionEvent event) {
	                        clsOscilloscope.baseLine=(int)event.getY();
	                        return true;
	                }
	                
	        }
	        
	
}
