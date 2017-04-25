package com.example.databasedemo03;
import java.util.ArrayList;  
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.litepal.crud.DataSupport;

import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.graphics.Rect;  
 
import android.view.SurfaceView;  
public class ClsOscilloscope {  
	private int MAX_VALUES=2;
	private int MAX_COUNT_OF_VALUES=5000; // 最多保存数据的个数.
	
	private String revTmpStr = new String();
	public List<Float> ECGDataList = new ArrayList<Float>();
	public boolean ECGDLIsAvailable = true;
	private float ECGData = 0;
	private EcgData ecgData;
	private String num;
	
    private ArrayList<Float> inBuf = new ArrayList<Float>();  
    private boolean isRecording = false;// 线程控制标记  
    private List<Float> buf=new ArrayList<Float>();
    /** 
     * X轴缩小的比例 
     */  
    public int rateX = 400;  
    /** 
     * Y轴缩小的比例 
     */  
    public int rateY = 400;  
    /** 
     * Y轴基线 
     */  
    public int baseLine = 0;  
    /** 
     * 初始化 
     */  
    public void initOscilloscope(int rateX, int rateY, int baseLine) {  
        this.rateX = rateX;  
        this.rateY = rateY;  
        this.baseLine = baseLine;  
    }  
    /** 
     * 开始 
     *  
     * @param recBufSize 
     *            AudioRecord的MinBufferSize 
     */  
    public void Start(String number, int recBufSize, SurfaceView sfv,  
            Paint mPaint) {  
        isRecording = true;
        num=number;
        ECGSplite();
        System.out.println(" Start--------------------");
        inBuf.addAll(ECGDataList); 
        new DrawThread(sfv, mPaint).start();// 开始绘制线程  
    }  
//    private int cnt=0;
//    private List<Float> data=new ArrayList<Float>();
//    
//    public synchronized void push(List<Float> buf){
//    	
//    	while(cnt==MAX_COUNT_OF_VALUES){
//    		try{
//    			this.wait();
//    		}
//    		catch(Exception e){
//    			
//    		}
//    		this.notify();
//    		for(int number=0;cnt<MAX_COUNT_OF_VALUES&&number<buf.size();number++){
//    			data.add(buf.get(number));
//        		++cnt;
//    		}
//    		
//    		
//    	}
//    	
//    }
//    public synchronized float pop () {
//    	float ch=0;
//    	while(cnt==0){
//    		try{
//    			this.wait();
//    		}
//    		catch(Exception e){
//    			
//    			
//    		}
//    		this.notify();
//    		ch=data[cnt-1];
//    		--cnt;
//    	}
//    	return ch;
//    }
    /** 
     * 停止 
     */  
    public void Stop() {  
        isRecording = false;  
        inBuf.clear();// 清除  
    }  
    /** 
     *  
     * 负责从文件中读取数据到inBuf
     *  
     * @author GV 
     *  
     */  public void ECGSplite(){
    	 List<EcgData> ecgDataList=new ArrayList<EcgData>();
    	 if(Integer.valueOf(num)>0){
    		 ecgDataList=DataSupport.where("fileId=?",num).find(EcgData.class);
    	 }
    	 else{
    		 ecgDataList=DataSupport.where("fileId=?","1").find(EcgData.class);
    	 }
    		 
    	
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
				//System.out.println("数据：-----------------"+ECGDataList);
     }
				
  /* class ReadThread extends Thread{//这个线程没用，实际项目中可能要用
    	
    	
		public void run(){
			if(isRecording){
				for(int i=0;i<1000;i++){
					buf.add(ECGDataList.get(0));
					ECGDataList.remove(0);
					
				}
				
				
				
			}
					List<Float> ECGCacheData = new ArrayList<Float>();
					//System.out.println("neirong:"+ECGCacheData);
					ECGCacheData.addAll(ECGDataList);
					//System.out.println("neirong:"+ECGCacheData);
					//System.out.println("有没有运行到即将传送数据画图这里来---------------------2");
					//mECG.DrawtoView(ECGCacheData);
					//System.out.println("数据："+ECGCacheData);
				//	System.out.println("String的最大长度为：");
					
				inBuf.addAll(ECGCacheData);
			}
    	}
	*/		
	 
    /** 
     * 负责绘制inBuf中的数据 
     *  
     * @author GV 
     *  
     */  
    class DrawThread extends Thread {  
    	
        private int oldX = 0;// 上次绘制的X坐标  
        private int oldY = 0;// 上次绘制的Y坐标  
        private SurfaceView sfv;// 画板  
        private int X_index = 0;// 当前画图所在屏幕X轴的坐标  
        private Paint mPaint;// 画笔  
        ArrayList<Float> buff = new ArrayList<Float>(); 
        public DrawThread(SurfaceView sfv, Paint mPaint) {  
            this.sfv = sfv;  
            this.mPaint = mPaint;  
            System.out.println("wuranghao");
            
        }  
        public void run() {  
            while (isRecording) {  
            	
                synchronized (inBuf) {  
                    if (inBuf.size() == 0)  
                        continue;  
                    if(buff.size()>MAX_COUNT_OF_VALUES){
                    	for(int i=0;i<360;i++){
                    		buff.remove(0);
                    	}
                    	
                    }
                    else{
                    	for(int i=0;i<800&&buff.size()<=MAX_COUNT_OF_VALUES&&inBuf.size()>0;i++){
                    		buff.add(inBuf.get(0));
                    			inBuf.remove(0);
                    		
                    		
                    		 
                    	}
                    	
                    }
//                    if(inBuf.size()==0&&buff.size()==0){
//                    	Stop();
//                    }
                    buf =(ArrayList<Float>) buff.clone();// 保存  
                    buff.clear();
                   
                   // inBuf.clear();// 清除  
                }  
//                for (int i = 0; i < buf.size(); i++) {  
//                    short[] tmpBuf = buf.get(i);  
                    SimpleDraw(X_index, buf, rateY, baseLine);// 把缓冲区数据画出来  
                    X_index = X_index + buf.size();  
                    if (X_index > sfv.getWidth()) {  
                        X_index = 0;  
                    }  
               // }  
            }  
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
        void SimpleDraw(int start, List<Float> buffer, int rate, int baseLine) {  
        	 
        	/*
        	 * 先寻找出buffer中这个多节拍中的最大值点，这个最大值点就是其中的一个心拍的R波峰值点
        	 * 
        	 * */
        	float max=Collections.max(buffer); 
        	 int index_max=buffer.indexOf(max);
        	 
        	 /*
        	  * 找出buffer中的所有心拍R波的峰值以及峰值在buffer中所对应的下标保存在Map中
        	  * */
        	 Map<Integer,Float>   maxR_buffer_map=maxBuffer(buffer,max);
        	    	 
        	 /*
        	  * 找出所有的Q点波的峰值以及峰值在buffer中所对应的下标保存在Map中
        	  * */
        	// Float minQ=Collections.min(buffer.subList(index_max-18, index_max));//找出一个Q点的值作为参考值
        	 Map<Integer,Float> minQ_buffer=minQBuffer(buffer,maxR_buffer_map);
        	 /*
        	  * 找出所有的S点波的峰值以及峰值在buffer中所对应的下标保存在Map中
        	  * */
        	 Map<Integer,Float> minS_buffer=minSBuffer(buffer,maxR_buffer_map);
        	 
        	 
        	
        	 int temp_max=(int)(-max*rate+baseLine);
        	//寻找R波峰值点前15个点的最小值点
        	
        	 
        	 
//        	 int temp_minQ=(int)(-minQ*rate+baseLine);
//        	//寻找R波峰值点后15个点的最小值点
//        	 List<Float> buffer_tempS=new ArrayList<Float>();
//        	 
//        	
//        	 for (int i = index_max+1; i < index_max+18; i++) {
// 				buffer_tempS.add(buffer.get(i));
// 			}
//        	 float minS=Collections.min(buffer_tempS);
//        	 int temp_minS=(int)(-minS*rate+baseLine);
//        	 
        	 System.out.println("此次读的数据的数量为："+buffer.size()+"     这个数据集中的最大值为："+max);
        	
        	 
            if (start == 0)  
                oldX = 0;  
            Canvas canvas = sfv.getHolder().lockCanvas(new Rect(start, 0, start + buffer.size(), sfv.getHeight()));// 关键:获取画布  
            canvas.drawColor(Color.BLACK);// 清除背景  
            int y;  
            
            for(int i=0;i<buffer.size();i++){
            	 int x = i + start;  
                 System.out.println(buffer.get(i)+";");
                float temp=buffer.get(i);
                y = (int) (-temp *rate + baseLine);
            	if(maxR_buffer_map.keySet().contains(i)){//利用R波峰在buffer中的位置来对其进行标记
            		 mPaint.setColor(Color.RED);
            		 mPaint.setStrokeWidth(10);
                  	canvas.drawLine(oldX, oldY, x, y, mPaint); 
            		
            	}
            	else if(minQ_buffer.keySet().contains(i)){//标记Q波
            		mPaint.setColor(Color.WHITE);
            		 mPaint.setStrokeWidth(10);
                	 canvas.drawLine(oldX, oldY, x, y,mPaint); 
            		
            	}
            	else if(minS_buffer.keySet().contains(i)){//标记S波峰
            		 mPaint.setColor(Color.BLUE);
            		 mPaint.setStrokeWidth(10);
            		 canvas.drawLine(oldX, oldY, x, y,mPaint); 
            		
            	}
            	else{
            		 mPaint.setColor(Color.GREEN);
            		 mPaint.setStrokeWidth(1);
                	 canvas.drawLine(oldX, oldY, x, y,mPaint);  
            	 }
            	 oldX = x;  
                 oldY = y; 
//                 try {
// 					Thread.sleep(1);
// 				} catch (InterruptedException e) {
// 					// TODO Auto-generated catch block
// 					e.printStackTrace();
// 				}
            	
            	
            }
            sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像  
           
        }      
   /*         for(int i=0;i<index_max;i++){
            	
            	 int x = i + start;  
                 System.out.println(buffer.get(i)+";");
                float temp=buffer.get(i);
                y = (int) (-temp *rate + baseLine);
                 if(y!=temp_max){//判断是否为最大值点
                 	 if(y==temp_minQ){
                 		 mPaint.setColor(Color.WHITE);
                 		
                     	 canvas.drawLine(oldX, oldY, x, y,mPaint); 
                 	 }
                 	 
                 	 else{
                 		 mPaint.setColor(Color.GREEN);
                     	 canvas.drawLine(oldX, oldY, x, y,mPaint);  
                 	 }
                 	 
                 }
                 else{
                 	// 调节缩小比例，调节基准线 
                 	 mPaint.setColor(Color.RED);	 
                 	canvas.drawLine(oldX, oldY, x, y, mPaint); 
                 }
                 oldX = x;  
                 oldY = y; 
                 try {
 					Thread.sleep(1);
 				} catch (InterruptedException e) {
 					// TODO Auto-generated catch block
 					e.printStackTrace();
 				}
            	
            }
            
            for (int i = index_max; i < buffer.size(); i++) {// 有多少画多少  
                int x = i + start;  
                System.out.println(buffer.get(i)+";");
               float temp=buffer.get(i);
               y = (int) (-temp *rate + baseLine);
                if(y!=temp_max){//判断是否为最大值点
                	 
                	 if(y==temp_minS){
                		 mPaint.setColor(Color.BLUE);
//                		 int y_temp3=y;
//                 		 for(int j=0;j<10;j++){
//                 			 canvas.drawLine(oldX,oldY,x,y_temp3,mPaint); 
//                 			 oldX=x;
//                 			 oldY=y_temp3;
//                 			 y_temp3++;
//                 		 }
                    	 canvas.drawLine(oldX, oldY, x, y,mPaint); 
                	 }
                	 else{
                		 mPaint.setColor(Color.GREEN);
                    	 canvas.drawLine(oldX, oldY, x, y,mPaint);  
                	 }
                	 
                }
                else{
                	// 调节缩小比例，调节基准线 
                	 mPaint.setColor(Color.RED);
                	
                	canvas.drawLine(oldX, oldY, x, y, mPaint); 
                }
               
                oldX = x;  
                oldY = y;  
                try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }  
            sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像  
        } */
        /*
         * 寻找buffer中的所用心拍R波的峰值点
         * */
        public Map<Integer,Float> maxBuffer(List<Float> buffer,Float max){
        	Float temp;
        	Map<Integer,Float> map=new HashMap<Integer,Float>();
        	for(int i=1;i<buffer.size()-1;i++){
        		temp=buffer.get(i);
        		if(temp>=buffer.get(i-1)&&temp>=buffer.get(i+1)&&buffer.get(i)>(max-0.3)){
        			map.put(i, temp);
        			i+=100;//找到一个峰值点后加上一个量比较大的偏移量，使其速度更快点查找
        			
        		}
        	}
        		
        	return map;
        }
        /*
         * 寻找所有心拍的Q点
         * */
        
        public Map<Integer,Float> minQBuffer(List<Float> buffer,Map<Integer,Float> maxBufferMap /*,Float minQ*/){
        	Map<Integer,Float> minQ_buffer=new HashMap<Integer,Float>();
        	List<Float> sub;
        	Set<Integer> set=new HashSet<Integer>();
        	set=maxBufferMap.keySet();
        	int  index;
        	Float min;
        	Iterator<Integer> iterator=set.iterator();
        	for(int i=0;i<maxBufferMap.size();i++){
        		index=iterator.next();
        		
        		sub=new ArrayList<Float>(buffer.subList(index-(index<=50?index:50), index));
        		min=Collections.min(sub);
        		//if(Math.abs(min-minQ)<0.1){
        			minQ_buffer.put(index-(sub.size()-sub.indexOf(min)), min);
        		//}
        		sub.clear();
        		
        	}
       	
        	
        	return minQ_buffer;
        }
        
        /*
         * 寻找所有心拍的S波的峰值点和其对应在buffer的下标
         * */
        
        public Map<Integer,Float> minSBuffer(List<Float> buffer,Map<Integer,Float> maxBufferMap /*,Float minQ*/){
        	Map<Integer,Float> minS_buffer=new HashMap<Integer,Float>();
        	List<Float> sub;
        	Set<Integer> set=new HashSet<Integer>();
        	set=maxBufferMap.keySet();
        	int  index;
        	Float min;
        	Iterator<Integer> iterator=set.iterator();
        	for(int i=0;i<maxBufferMap.size();i++){
        		index=iterator.next();
        		
        		sub=new ArrayList<Float>(buffer.subList(index, index+(((buffer.size()-index)>=50)?50:(buffer.size()-index))));
        		min=Collections.min(sub);
        		//if(Math.abs(min-minQ)<0.1){
        			minS_buffer.put(index+sub.indexOf(min), min);
        		//}
        		sub.clear();
        		
        	}
       	
        	
        	return minS_buffer;
        }
        
        
        
    }  
}
