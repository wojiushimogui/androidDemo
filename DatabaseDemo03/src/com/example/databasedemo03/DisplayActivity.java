// 这个Activity没有用
 

package com.example.databasedemo03;

import java.util.ArrayList;
import java.util.List;

import org.litepal.crud.DataSupport;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

public class DisplayActivity extends Activity {

	private Paint mPaint;
	private SurfaceView sfvECG;
	
	private String revTmpStr = new String();
	private ReadThread mReadThread;
	public List<Float> ECGDataList = new ArrayList<Float>();
	public boolean ECGDLIsAvailable = true;
	private float ECGData = 0;
	private EcgData ecgData;
	private String num;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.display);
		mReadThread=new ReadThread();
		Intent intent=getIntent();
		 num=intent.getStringExtra("num_data");
		Toast.makeText(DisplayActivity.this, "收到数据编号:"+num, Toast.LENGTH_SHORT).show();
		
		sfvECG=(SurfaceView)findViewById(R.id.surfaceViewId);
		mPaint = new Paint();//创建画笔并设置画笔的一些属性
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(3);
		mPaint.setAntiAlias(true);
		//mECG = new DrawECG(sfvECG,mPaint);//DrawECGWaveForm对象的实例化，传入的参数为SrufaceView的实例
		//System.out.println("有没有运行到这里来-----------------1");
		
		 mReadThread.start();
	}
	
	class ReadThread extends Thread{
		public void run(){
			List<EcgData> ecgDataList=DataSupport.where("fileId=?",num).find(EcgData.class);
			ecgData=ecgDataList.get(0);
			   revTmpStr=ecgData.getContent();
			   //System.out.println("数据为："+revTmpStr);
			 
				
				if(revTmpStr.indexOf(';')!=-1){ 
					try{
						String ECGDataStrs[] = revTmpStr.split(";");
						for (int i = 0; i < ECGDataStrs.length -1; i++){
							try{
								ECGData = Float.parseFloat(ECGDataStrs[i].replace(';',' '));
								ECGDataList.add(ECGData);											
							}catch(Exception e){
								e.printStackTrace();
								continue;
							}

						}
						if (ECGDataStrs[ECGDataStrs.length -1].length()==6 || ECGDataStrs[ECGDataStrs.length -1].length()==7&&ECGDataStrs[ECGDataStrs.length -1].indexOf('-')==0){
							try{
								ECGData = Float.parseFloat(ECGDataStrs[ECGDataStrs.length -1].replace(';',' '));
								ECGDataList.add(ECGData);
							}catch(Exception e){
								e.printStackTrace();
							}
							revTmpStr = "";
						}
						else{
							revTmpStr = ECGDataStrs[ECGDataStrs.length -1];
						}									
						
					}
					catch(Exception e){
						e.printStackTrace();
					}		
			
		}
				//List<Float> ECGCacheData = new ArrayList<Float>();
				//System.out.println("neirong:"+ECGCacheData);
				//ECGCacheData.addAll(ECGDataList);
				//System.out.println("neirong:"+ECGCacheData);
				//System.out.println("有没有运行到即将传送数据画图这里来---------------------2");
				//mECG.DrawtoView(ECGCacheData);
				//System.out.println("数据："+ECGCacheData);
			//	System.out.println("String的最大长度为：");
				
			
		}
	}
	
	 class DrawThread extends Thread {
         private int oldX = 0;// 上次绘制的X坐标
         private int oldY = 0;// 上次绘制的Y坐标
         private SurfaceView sfv;// 画板
         private int X_index = 0;// 当前画图所在屏幕X轴的坐标
         private Paint mPaint;// 画笔
         public DrawThread(SurfaceView sfv, Paint mPaint) {
                 this.sfv = sfv;
                 this.mPaint = mPaint;
         }
         public void run() {
        	 
                 
         }
         /**
          * 绘制指定区域
          * 
          * @param start
          *            X轴开始的位置(全屏)
          * @param buffer
          *            缓冲区
          * @param rate
          *            Y轴数据缩小的比例
          * @param baseLine
          *            Y轴基线
          */
         void SimpleDraw(int start, Float[] buffer, int rate, int baseLine) {
                 if (start == 0)
                         oldX = 0;
                 Canvas canvas = sfv.getHolder().lockCanvas(
                                 new Rect(start, 0, start + buffer.length, sfv.getHeight()));// 关键:获取画布
                 canvas.drawColor(Color.BLACK);// 清除背景
                 int y;
                 for (int i = 0; i < buffer.length; i++) {// 有多少画多少
                         int x = i + start;
                         y = (int) (buffer[i] / rate + baseLine);// 调节缩小比例，调节基准线
                         canvas.drawLine(oldX, oldY, x, y, mPaint);
                         oldX = x;
                         oldY = y;
                 }
                 sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
         }
 }
}



