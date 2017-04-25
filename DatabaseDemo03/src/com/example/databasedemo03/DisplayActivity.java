// ���Activityû����
 

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
		Toast.makeText(DisplayActivity.this, "�յ����ݱ��:"+num, Toast.LENGTH_SHORT).show();
		
		sfvECG=(SurfaceView)findViewById(R.id.surfaceViewId);
		mPaint = new Paint();//�������ʲ����û��ʵ�һЩ����
		mPaint.setColor(Color.BLUE);
		mPaint.setStrokeWidth(3);
		mPaint.setAntiAlias(true);
		//mECG = new DrawECG(sfvECG,mPaint);//DrawECGWaveForm�����ʵ����������Ĳ���ΪSrufaceView��ʵ��
		//System.out.println("��û�����е�������-----------------1");
		
		 mReadThread.start();
	}
	
	class ReadThread extends Thread{
		public void run(){
			List<EcgData> ecgDataList=DataSupport.where("fileId=?",num).find(EcgData.class);
			ecgData=ecgDataList.get(0);
			   revTmpStr=ecgData.getContent();
			   //System.out.println("����Ϊ��"+revTmpStr);
			 
				
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
				//System.out.println("��û�����е������������ݻ�ͼ������---------------------2");
				//mECG.DrawtoView(ECGCacheData);
				//System.out.println("���ݣ�"+ECGCacheData);
			//	System.out.println("String����󳤶�Ϊ��");
				
			
		}
	}
	
	 class DrawThread extends Thread {
         private int oldX = 0;// �ϴλ��Ƶ�X����
         private int oldY = 0;// �ϴλ��Ƶ�Y����
         private SurfaceView sfv;// ����
         private int X_index = 0;// ��ǰ��ͼ������ĻX�������
         private Paint mPaint;// ����
         public DrawThread(SurfaceView sfv, Paint mPaint) {
                 this.sfv = sfv;
                 this.mPaint = mPaint;
         }
         public void run() {
        	 
                 
         }
         /**
          * ����ָ������
          * 
          * @param start
          *            X�Ὺʼ��λ��(ȫ��)
          * @param buffer
          *            ������
          * @param rate
          *            Y��������С�ı���
          * @param baseLine
          *            Y�����
          */
         void SimpleDraw(int start, Float[] buffer, int rate, int baseLine) {
                 if (start == 0)
                         oldX = 0;
                 Canvas canvas = sfv.getHolder().lockCanvas(
                                 new Rect(start, 0, start + buffer.length, sfv.getHeight()));// �ؼ�:��ȡ����
                 canvas.drawColor(Color.BLACK);// �������
                 int y;
                 for (int i = 0; i < buffer.length; i++) {// �ж��ٻ�����
                         int x = i + start;
                         y = (int) (buffer[i] / rate + baseLine);// ������С���������ڻ�׼��
                         canvas.drawLine(oldX, oldY, x, y, mPaint);
                         oldX = x;
                         oldY = y;
                 }
                 sfv.getHolder().unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��
         }
 }
}



