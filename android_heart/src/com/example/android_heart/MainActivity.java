package com.example.android_heart;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;



import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.ContextMenu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity  {
	// 蓝牙设备
	private InputStream is; // 输入流，用来接收蓝牙数据
	Timer timer = new Timer();//计时器
	private static int flag_lockcanvas=0;//锁画布次数，当画布不是全屏的时候，清屏两次s
	private TimerTask task = null;//计时任务类
	private String smsg = ""; // 显示用数据缓存
	private String lmsg = "";
	private String fmsg = ""; // 保存用数据缓存
	private final static int REQUEST_CONNECT_DEVICE = 1; // 宏定义查询设备句柄
	private BluetoothAdapter _bluetooth1 = BluetoothAdapter.getDefaultAdapter(); // 获取本地蓝牙适配器，即蓝牙设备
	private BluetoothDevice _device1 = null; // 蓝牙设备
	private BluetoothSocket _socket1 = null; // 蓝牙通信socket
	private final static String MY_UUID = "00001101-0000-1000-8000-00805F9B34FB"; // SPP服务UUID号
	private BluetoothDevice _device = null; // 蓝牙设备
	private BluetoothSocket _socket = null; // 蓝牙通信socket
	boolean _discoveryFinished = false;
	boolean bRun = true;
	boolean bThread = false;
	private TextView lol;
	// 心电波形数据
	public String hearts_I = "";
	public String hearts_II = "";
	public String hearts_III = "";
	public String hearts_aVR = "";
	public String hearts_aVL = "";
	public String hearts_aVF = "";
	public static int hearts_V ;
	public static int draw_xd;
	// 心电参数
	public byte heartzt;// 心电状态
	public int xl;// 心率
	public int hxl;// 呼吸率
	public String STs = "";// ST点位
	public String xlyczt = "";// 心率异常状态
	private static float heart_begin_x;// 心电图起始x坐标
	private static float heart_begin_y;// 心电图起始y坐标
	private static float heart_end_x = 0;// 心电图终点x坐标
	private static float heart_end_y = 150;// 心电图终点y坐标
	private float get_x1[];
	private float get_x2[];
	private float get_y1[];
	private float get_y2[];
	private float temp;
	// 按钮
	private Button pausebtn;// 开启心电图/血氧图按钮
	private TextView test;
	private static int getHeartData = 128;
	private Button btn_back;
	private ImageButton show_hide;
	private boolean flag_show_hide = false;
	private static int flagnum;
	private static boolean flag_on_off = false;//开启关闭心电图用
	private static int flag_on_off_num = 0;
	// 画布XY轴的起始点
	static int dx = -2;//单位屏幕长度
	static int px = -2;//总长度
	static int k = 0;
	static int j = 0;
	// 画笔
	private Paint paint;
	// 画布
	private Canvas canvas;// 心电画布1
	private Canvas canvas1;// 心电画布2
	private Canvas canvas2;// 心电画布3
	private static boolean flag_canvas;//判断是哪块画布
	// SurfaceHolder 管理SurfaceView中的画布的锁定和释放
	SurfaceHolder holder_heart;// 心电管理
	SurfaceHolder holder_bloodHO2;// 血氧管理
	SurfaceView surface;
	SurfaceView surface_xy;
	private static int surface_size = 0;
	//标志线的横向坐标
	private static int flagx = 0;
	private boolean flag;
	// 路径
	private Path path;//心电图路径
	private Path path1;//心电图路径
	//时间显示
	private GetMessage getTime;
	private TextView showTime;
	private static int i = 0;//存数据变量
	//private static int j = 1;//取数据变量
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViews();// 查找组件
		initObject();
		registerListener();// 注册组件
	}
	/**
	 * 创建对象
	 */
	private void initObject(){
		holder_heart = surface.getHolder();
		paint = new Paint();
		path = new Path();
		path1 = new Path();
		get_x1 = new float[2048];
		get_x2 = new float[2048];
		get_y1 = new float[2048];
		get_y2 = new float[2048];
		getTime = new GetMessage();
		hearts_I = Integer.toString(new Random(47).nextInt());// ***
										hearts_II = Integer.toString(new Random().nextInt());// ***
										hearts_III = Integer.toString(new Random().nextInt());// ***
										hearts_aVR = Integer.toString(new Random().nextInt());// ***
										hearts_aVL = Integer.toString(new Random().nextInt());// ***
										hearts_aVF = Integer.toString(new Random().nextInt());// ***
	}
	
	/**
	 * 查找组件
	 */
	private void findViews() {
		pausebtn = (Button) findViewById(R.id.pausebtn);
		//test = (TextView) findViewById(R.id.test);
		surface = (SurfaceView) findViewById(R.id.heart_img);
		showTime = (TextView) findViewById(R.id.current_time);
		btn_back = (Button) findViewById(R.id.btn_back);
		show_hide = (ImageButton)findViewById(R.id.show_hide);
		lol = (TextView) findViewById(R.id.lol);
	}

	/**
	 * 注册组件事件
	 */
	private void registerListener() {
		
		holder_heart.addCallback(new SurfaceHolder.Callback() {
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			public void surfaceCreated(SurfaceHolder holder) {
				//countmanager = new CountManager();
				flag = true;
			}

			public void surfaceDestroyed(SurfaceHolder holder) {
				timer.cancel();
			}
		});
	}

	
	

	/**
	 * 自定义绘制方法
	 */

	private void myDraw() {
		boolean flag1 = false;
		surface.postInvalidate();
		if (holder_heart == null) {
			return;
		}
		canvas = holder_heart.lockCanvas(new Rect(0, 50+lol.getHeight(), surface_size, surface.getHeight()+50+lol.getHeight()));
		if (canvas == null) {
			return;
		}
		// 画布背景色
		canvas.drawColor(Color.BLACK);
		// 画笔属性
		paint.setStrokeWidth(2);
		paint.setColor(Color.RED);
		paint.setAntiAlias(true);//表示抗锯齿
		paint.setStyle(Paint.Style.STROKE);
		// 截取的画布大小
		//canvas.clipRect(0, 50+lol.getHeight(), surface_size, surface.getHeight()+50+lol.getHeight());
		canvas.save();// 保存画布
		dx += 2;
		px += 2;
		surface_size += 2;
		canvas.translate(dx, 0);
		if(dx >= surface.getWidth()){
			dx = -2;
			heart_end_x = 0;
			k = 0;
			surface_size = 0;
			flag1 = true;
		}
		// 画直线用到的起始点和终点
		heart_begin_x = heart_end_x;
		heart_begin_y = heart_end_y;
		heart_end_x = heart_begin_x - 2;
		heart_end_y = (float) (124+(getHeartData-128)/1.5);
		// 画直线
		path.moveTo(heart_begin_x, heart_begin_y);
		path.lineTo(heart_end_x, heart_end_y);
		canvas.drawPath(path, paint);
		if(flag1){
			path.reset();
		}
//		View view = getWindow().getDecorView().findViewById(R.id.heart_img);
//		Bitmap bit = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
//		Canvas canvas1 = new Canvas(bit);
//		doDraw(canvas1);
		
		// 恢复画布
		canvas.restore();
		holder_heart.unlockCanvasAndPost(canvas);
		if(dx+2 >= surface.getWidth()){
			canvas1 =holder_heart.lockCanvas();
			canvas1.drawColor(Color.BLACK);
			canvas1.clipRect(0, 50+lol.getHeight(), surface_size, surface.getHeight()+50+lol.getHeight());
			canvas1.save();// 保存画布
			View view = getWindow().getDecorView().findViewById(R.id.heart_img);
			Bitmap bit = Bitmap.createBitmap(view.getWidth(),view.getHeight(), Bitmap.Config.ARGB_8888);
			Rect src = new Rect(0, 0, bit.getWidth(), bit.getHeight());
			RectF dst = new RectF(0, 50, surface.getWidth(), surface.getHeight() - 50);
			canvas1.drawBitmap(bit,src,dst,paint);
			holder_heart.unlockCanvasAndPost(canvas1);
		}
	}
	
	private void doDraw(Canvas canvas1){
		View view = getWindow().getDecorView().findViewById(R.id.heart_img);
		view.setDrawingCacheEnabled(true);
		view.buildDrawingCache();
		Bitmap bit= view.getDrawingCache();   
		Rect src = new Rect(0, 0, bit.getWidth(), bit.getHeight());
		RectF dst = new RectF(0, 50, surface.getWidth(), surface.getHeight() - 50);
//		canvas1.drawBitmap(bit,src,dst,paint); 
		Bitmap bitmap2 = Bitmap.createBitmap(bit, 0, 50, surface.getWidth(), surface.getHeight()+50);
		canvas1.drawBitmap(bitmap2,src,dst,paint);
	}
	
	private void drawAll(){
		if (task != null) {
			task.cancel();
		}
		task = new TimerTask() {
			public void run() {
				while (flag) {
					
					myDraw();// 自定义绘制方法
					try {
						Thread.sleep(4);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				holder_heart.unlockCanvasAndPost(canvas);
			}
		};
		timer.scheduleAtFixedRate(task, 4, 4);

	}
	
	
}