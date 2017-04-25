package wu.rang.hao.flow;

import java.util.ArrayList;  
import java.util.List;  
import java.util.Random;  
  
import android.content.Context;  
import android.graphics.Canvas;  
import android.graphics.Color;  
import android.graphics.Paint;  
import android.os.Handler;  
import android.os.Message;  
import android.util.AttributeSet;  
import android.view.View;  
  
public class MyView extends View{  
	 private int XPoint = 60;  //ԭ���X����
	    private int YPoint = 260;  //ԭ���Y����
	    private int XScale = 8;  //X�Ŀ̶ȳ���  
	    private int YScale = 40;  //Y�Ŀ̶ȳ���
	    private int XLength = 380;  //X��ĳ���
	    private int YLength = 240;  //Y��ĳ���
      
    private int MaxDataSize = XLength / XScale;  
      
    private List<Integer> data = new ArrayList<Integer>();  
      
  
      
    private String[] YLabel = new String[YLength / YScale];  
      
    private Handler handler = new Handler(){  
        public void handleMessage(Message msg) {  
            if(msg.what == 0x1234){  
                MyView.this.invalidate();  
            }  
        };  
    };  
    public MyView(Context context, AttributeSet attrs) {  
        super(context, attrs);  
        for(int i=0; i<YLabel.length; i++){  
            YLabel[i] = (i + 1) + "M/s";  
        }  
          
        new Thread(new Runnable() {  
              
            @Override  
            public void run() {  
                while(true){  
                    try {  
                        Thread.sleep(1000);  
                    } catch (InterruptedException e) {  
                        e.printStackTrace();  
                    }  
                    if(data.size() >= MaxDataSize){  
                        data.remove(0);  
                    }  
                    data.add(new Random().nextInt(4) + 1);  
                    handler.sendEmptyMessage(0x1234);  
                }  
            }  
        }).start();  
    }  
      
    @Override  
    protected void onDraw(Canvas canvas) {  
        super.onDraw(canvas);  
        Paint paint = new Paint();  
        paint.setStyle(Paint.Style.STROKE);  
        paint.setAntiAlias(true); //ȥ���  
        paint.setColor(Color.BLUE);  
          
        //��Y��  
        canvas.drawLine(XPoint, YPoint - YLength, XPoint, YPoint, paint);  
          
        //Y���ͷ  
        canvas.drawLine(XPoint, YPoint - YLength, XPoint - 3, YPoint-YLength + 6, paint);  //��ͷ  
        canvas.drawLine(XPoint, YPoint - YLength, XPoint + 3, YPoint-YLength + 6 ,paint);  
          
        //��ӿ̶Ⱥ�����  
        for(int i=0; i * YScale < YLength; i++) {  
            canvas.drawLine(XPoint, YPoint - i * YScale, XPoint + 5, YPoint - i * YScale, paint);  //�̶�  
              
            canvas.drawText(YLabel[i], XPoint - 50, YPoint - i * YScale, paint);//����  
        }  
          
        //��X��  
        canvas.drawLine(XPoint, YPoint, XPoint + XLength, YPoint, paint);  
        System.out.println("Data.size = " + data.size());  
        if(data.size() > 1){  
            for(int i=1; i<data.size(); i++){  
                canvas.drawLine(XPoint + (i-1) * XScale, YPoint - data.get(i-1) * YScale,   
                        XPoint + i * XScale, YPoint - data.get(i) * YScale, paint);  
            }  
        }  
    }  
} 