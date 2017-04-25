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
	    
	        
	   private     static final int xMax = 200;//X轴缩小比例最大值,X轴数据量巨大，容易产生刷新延时
	   private     static final int xMin = 0;//X轴缩小比例最小值
	   private     static final int yMax = 400;//Y轴放大比例最大值
	   private     static final int yMin = 100;//Y轴放大比例最小值
	        
	   private     int recBufSize;//录音最小buffer大小
	   private     Paint mPaint;
	    @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_display_demo);
	        //录音组件
	        Intent intent=getIntent();
			 num=intent.getStringExtra("num_data");
			Toast.makeText(DisplayDemoActivity.this, "收到数据编号:"+num, Toast.LENGTH_SHORT).show();
	                
	                //按键
	                btnStart = (Button) this.findViewById(R.id.btnStart);
	                btnStart.setOnClickListener(new ClickEvent());
	                btnExit = (Button) this.findViewById(R.id.btnExit);
	                btnExit.setOnClickListener(new ClickEvent());
	                //画板和画笔
	                sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01); 
	                System.out.println("----------------------height="+sfv.getHeight());
	                System.out.println("-----------------------width="+sfv.getWidth());
	                sfv.setOnTouchListener(new TouchEvent());
	                mPaint = new Paint();  
	                mPaint.setColor(Color.GREEN);// 画笔为绿色  
	                mPaint.setStrokeWidth(1);// 设置画笔粗细 
	        //示波器类库
	        clsOscilloscope.initOscilloscope(xMax/2, yMax/2, sfv.getHeight()/2);
	        
	        //缩放控件，X轴的数据缩小的比率高些
	                zctlX = (ZoomControls)this.findViewById(R.id.zctlX);
	                zctlX.setOnZoomInClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateX>xMin)
	                                        clsOscilloscope.rateX-=10;
	                                setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
	                                                +","+"Y轴放大"+String.valueOf(clsOscilloscope.rateY)+"倍");
	                        }
	                });
	                zctlX.setOnZoomOutClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateX<xMax)
	                                        clsOscilloscope.rateX+=10;        
	                                setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
	                                                +","+"Y轴放大"+String.valueOf(clsOscilloscope.rateY)+"倍");
	                        }
	                });
	                zctlY = (ZoomControls)this.findViewById(R.id.zctlY);
	                zctlY.setOnZoomInClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateY>yMin)
	                                        clsOscilloscope.rateY-=50;
	                                setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
	                                                +","+"Y轴放大"+String.valueOf(clsOscilloscope.rateY)+"倍");
	                        }
	                });
	                
	                zctlY.setOnZoomOutClickListener(new View.OnClickListener() {
	                        @Override
	                        public void onClick(View v) {
	                                if(clsOscilloscope.rateY<yMax)
	                                        clsOscilloscope.rateY+=50;        
	                                setTitle("X轴缩小"+String.valueOf(clsOscilloscope.rateX)+"倍"
	                                                +","+"Y轴放大"+String.valueOf(clsOscilloscope.rateY)+"倍");
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
	         * 按键事件处理
	         * @author GV
	         *
	         */
	        class ClickEvent implements View.OnClickListener {
	                @Override
	                public void onClick(View v) {
	                        if (v == btnStart) {
	                        	Toast.makeText(getApplicationContext(), "开始被点击了", Toast.LENGTH_SHORT).show();
	                                clsOscilloscope.baseLine=sfv.getHeight()/2;
	                                clsOscilloscope.Start(num,recBufSize,sfv,mPaint);
	                        } else if (v == btnExit) {
	                        	Toast.makeText(getApplicationContext(), "stop被点击了", Toast.LENGTH_SHORT).show();
	                                clsOscilloscope.Stop();
	                        }
	                }
	        }
	        /**
	         * 触摸屏动态设置波形图基线
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
