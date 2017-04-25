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
	private int MAX_COUNT_OF_VALUES=5000; // ��ౣ�����ݵĸ���.
	
	private String revTmpStr = new String();
	public List<Float> ECGDataList = new ArrayList<Float>();
	public boolean ECGDLIsAvailable = true;
	private float ECGData = 0;
	private EcgData ecgData;
	private String num;
	
    private ArrayList<Float> inBuf = new ArrayList<Float>();  
    private boolean isRecording = false;// �߳̿��Ʊ��  
    private List<Float> buf=new ArrayList<Float>();
    /** 
     * X����С�ı��� 
     */  
    public int rateX = 400;  
    /** 
     * Y����С�ı��� 
     */  
    public int rateY = 400;  
    /** 
     * Y����� 
     */  
    public int baseLine = 0;  
    /** 
     * ��ʼ�� 
     */  
    public void initOscilloscope(int rateX, int rateY, int baseLine) {  
        this.rateX = rateX;  
        this.rateY = rateY;  
        this.baseLine = baseLine;  
    }  
    /** 
     * ��ʼ 
     *  
     * @param recBufSize 
     *            AudioRecord��MinBufferSize 
     */  
    public void Start(String number, int recBufSize, SurfaceView sfv,  
            Paint mPaint) {  
        isRecording = true;
        num=number;
        ECGSplite();
        System.out.println(" Start--------------------");
        inBuf.addAll(ECGDataList); 
        new DrawThread(sfv, mPaint).start();// ��ʼ�����߳�  
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
     * ֹͣ 
     */  
    public void Stop() {  
        isRecording = false;  
        inBuf.clear();// ���  
    }  
    /** 
     *  
     * ������ļ��ж�ȡ���ݵ�inBuf
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
				//System.out.println("���ݣ�-----------------"+ECGDataList);
     }
				
  /* class ReadThread extends Thread{//����߳�û�ã�ʵ����Ŀ�п���Ҫ��
    	
    	
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
					//System.out.println("��û�����е������������ݻ�ͼ������---------------------2");
					//mECG.DrawtoView(ECGCacheData);
					//System.out.println("���ݣ�"+ECGCacheData);
				//	System.out.println("String����󳤶�Ϊ��");
					
				inBuf.addAll(ECGCacheData);
			}
    	}
	*/		
	 
    /** 
     * �������inBuf�е����� 
     *  
     * @author GV 
     *  
     */  
    class DrawThread extends Thread {  
    	
        private int oldX = 0;// �ϴλ��Ƶ�X����  
        private int oldY = 0;// �ϴλ��Ƶ�Y����  
        private SurfaceView sfv;// ����  
        private int X_index = 0;// ��ǰ��ͼ������ĻX�������  
        private Paint mPaint;// ����  
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
                    buf =(ArrayList<Float>) buff.clone();// ����  
                    buff.clear();
                   
                   // inBuf.clear();// ���  
                }  
//                for (int i = 0; i < buf.size(); i++) {  
//                    short[] tmpBuf = buf.get(i);  
                    SimpleDraw(X_index, buf, rateY, baseLine);// �ѻ��������ݻ�����  
                    X_index = X_index + buf.size();  
                    if (X_index > sfv.getWidth()) {  
                        X_index = 0;  
                    }  
               // }  
            }  
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
        void SimpleDraw(int start, List<Float> buffer, int rate, int baseLine) {  
        	 
        	/*
        	 * ��Ѱ�ҳ�buffer�����������е����ֵ�㣬������ֵ��������е�һ�����ĵ�R����ֵ��
        	 * 
        	 * */
        	float max=Collections.max(buffer); 
        	 int index_max=buffer.indexOf(max);
        	 
        	 /*
        	  * �ҳ�buffer�е���������R���ķ�ֵ�Լ���ֵ��buffer������Ӧ���±걣����Map��
        	  * */
        	 Map<Integer,Float>   maxR_buffer_map=maxBuffer(buffer,max);
        	    	 
        	 /*
        	  * �ҳ����е�Q�㲨�ķ�ֵ�Լ���ֵ��buffer������Ӧ���±걣����Map��
        	  * */
        	// Float minQ=Collections.min(buffer.subList(index_max-18, index_max));//�ҳ�һ��Q���ֵ��Ϊ�ο�ֵ
        	 Map<Integer,Float> minQ_buffer=minQBuffer(buffer,maxR_buffer_map);
        	 /*
        	  * �ҳ����е�S�㲨�ķ�ֵ�Լ���ֵ��buffer������Ӧ���±걣����Map��
        	  * */
        	 Map<Integer,Float> minS_buffer=minSBuffer(buffer,maxR_buffer_map);
        	 
        	 
        	
        	 int temp_max=(int)(-max*rate+baseLine);
        	//Ѱ��R����ֵ��ǰ15�������Сֵ��
        	
        	 
        	 
//        	 int temp_minQ=(int)(-minQ*rate+baseLine);
//        	//Ѱ��R����ֵ���15�������Сֵ��
//        	 List<Float> buffer_tempS=new ArrayList<Float>();
//        	 
//        	
//        	 for (int i = index_max+1; i < index_max+18; i++) {
// 				buffer_tempS.add(buffer.get(i));
// 			}
//        	 float minS=Collections.min(buffer_tempS);
//        	 int temp_minS=(int)(-minS*rate+baseLine);
//        	 
        	 System.out.println("�˴ζ������ݵ�����Ϊ��"+buffer.size()+"     ������ݼ��е����ֵΪ��"+max);
        	
        	 
            if (start == 0)  
                oldX = 0;  
            Canvas canvas = sfv.getHolder().lockCanvas(new Rect(start, 0, start + buffer.size(), sfv.getHeight()));// �ؼ�:��ȡ����  
            canvas.drawColor(Color.BLACK);// �������  
            int y;  
            
            for(int i=0;i<buffer.size();i++){
            	 int x = i + start;  
                 System.out.println(buffer.get(i)+";");
                float temp=buffer.get(i);
                y = (int) (-temp *rate + baseLine);
            	if(maxR_buffer_map.keySet().contains(i)){//����R������buffer�е�λ����������б��
            		 mPaint.setColor(Color.RED);
            		 mPaint.setStrokeWidth(10);
                  	canvas.drawLine(oldX, oldY, x, y, mPaint); 
            		
            	}
            	else if(minQ_buffer.keySet().contains(i)){//���Q��
            		mPaint.setColor(Color.WHITE);
            		 mPaint.setStrokeWidth(10);
                	 canvas.drawLine(oldX, oldY, x, y,mPaint); 
            		
            	}
            	else if(minS_buffer.keySet().contains(i)){//���S����
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
            sfv.getHolder().unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��  
           
        }      
   /*         for(int i=0;i<index_max;i++){
            	
            	 int x = i + start;  
                 System.out.println(buffer.get(i)+";");
                float temp=buffer.get(i);
                y = (int) (-temp *rate + baseLine);
                 if(y!=temp_max){//�ж��Ƿ�Ϊ���ֵ��
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
                 	// ������С���������ڻ�׼�� 
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
            
            for (int i = index_max; i < buffer.size(); i++) {// �ж��ٻ�����  
                int x = i + start;  
                System.out.println(buffer.get(i)+";");
               float temp=buffer.get(i);
               y = (int) (-temp *rate + baseLine);
                if(y!=temp_max){//�ж��Ƿ�Ϊ���ֵ��
                	 
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
                	// ������С���������ڻ�׼�� 
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
            sfv.getHolder().unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��  
        } */
        /*
         * Ѱ��buffer�е���������R���ķ�ֵ��
         * */
        public Map<Integer,Float> maxBuffer(List<Float> buffer,Float max){
        	Float temp;
        	Map<Integer,Float> map=new HashMap<Integer,Float>();
        	for(int i=1;i<buffer.size()-1;i++){
        		temp=buffer.get(i);
        		if(temp>=buffer.get(i-1)&&temp>=buffer.get(i+1)&&buffer.get(i)>(max-0.3)){
        			map.put(i, temp);
        			i+=100;//�ҵ�һ����ֵ������һ�����Ƚϴ��ƫ������ʹ���ٶȸ�������
        			
        		}
        	}
        		
        	return map;
        }
        /*
         * Ѱ���������ĵ�Q��
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
         * Ѱ���������ĵ�S���ķ�ֵ������Ӧ��buffer���±�
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
