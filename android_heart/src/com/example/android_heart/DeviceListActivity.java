package com.example.android_heart;




import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 鏄剧ず鎵弿钃濈墮璁惧鐨勭獥浣撶晫闈�
 * @author Administrator
 *
 */
public class DeviceListActivity extends Activity {
    // 璋冭瘯鐢�
    private static final String TAG = "DeviceListActivity";
    private static final boolean D = true;

    // 杩斿洖鏃舵暟鎹爣绛�
    public static String EXTRA_DEVICE_ADDRESS = "璁惧鍦板潃";

    // 鎴愬憳鍩�
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    private ArrayAdapter<String> mNewDevicesArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 鍒涘缓骞舵樉绀虹獥鍙�
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);  //璁剧疆绐楀彛鏄剧ず妯″紡涓虹獥鍙ｆ柟寮�
        setContentView(R.layout.device_list);

        // 璁惧畾榛樿杩斿洖鍊间负鍙栨秷
        setResult(Activity.RESULT_CANCELED);

        // 璁惧畾鎵弿鎸夐敭鍝嶅簲
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
            	Toast.makeText(DeviceListActivity.this, "shang", Toast.LENGTH_LONG)
				.show();
                doDiscovery();
                Toast.makeText(DeviceListActivity.this, "xia", Toast.LENGTH_LONG)
				.show();
                v.setVisibility(View.GONE);
            }
        });

        // 鍒濅娇鍖栬澶囧瓨鍌ㄦ暟缁�
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);
        mNewDevicesArrayAdapter = new ArrayAdapter<String>(this, R.layout.device_name);

        // 璁剧疆宸查厤闃熻澶囧垪琛�
        
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        pairedListView.setOnItemClickListener(mDeviceClickListener);

        // 璁剧疆鏂版煡鎵捐澶囧垪琛�
        ListView newDevicesListView = (ListView) findViewById(R.id.new_devices);
        newDevicesListView.setAdapter(mNewDevicesArrayAdapter);
        newDevicesListView.setOnItemClickListener(mDeviceClickListener);

        // 娉ㄥ唽鎺ユ敹鏌ユ壘鍒拌澶嘺ction鎺ユ敹鍣�
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // 娉ㄥ唽鏌ユ壘缁撴潫action鎺ユ敹鍣�
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // 寰楀埌鏈湴钃濈墮鍙ユ焺
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

//        // 寰楀埌宸查厤瀵硅摑鐗欒澶囧垪琛�
//        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();
//
//        // 娣诲姞宸查厤瀵硅澶囧埌鍒楄〃骞舵樉绀� 
//        if (pairedDevices.size() > 0) {
//            findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
//            for (BluetoothDevice device : pairedDevices) {
//                mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
//            }
//        } else {
//            String noDevices = "No devices have been paired";
//            mPairedDevicesArrayAdapter.add(noDevices);
//        }
    }

   
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 鍏抽棴鏈嶅姟鏌ユ壘
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // 娉ㄩ攢action鎺ユ敹鍣�
        this.unregisterReceiver(mReceiver);
    }
    
    public void OnCancel(View v){
    	finish();
    }
    /**
     * 寮�濮嬫湇鍔″拰璁惧鏌ユ壘
     */
    private void doDiscovery() {
        // 鍦ㄧ獥鍙ｆ樉绀烘煡鎵句腑淇℃伅
        setProgressBarIndeterminateVisibility(true);
        setTitle("鏌ユ壘璁惧涓�...");

        // 鏄剧ず鍏跺畠璁惧锛堟湭閰嶅璁惧锛夊垪琛�
        findViewById(R.id.title_new_devices).setVisibility(View.VISIBLE);

        // 鍏抽棴鍐嶈繘琛岀殑鏈嶅姟鏌ユ壘
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }
        //骞堕噸鏂板紑濮�
        mBtAdapter.startDiscovery();
    }

    // 閫夋嫨璁惧鍝嶅簲鍑芥暟 
    private OnItemClickListener mDeviceClickListener = new OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // 鍑嗗杩炴帴璁惧锛屽叧闂湇鍔℃煡鎵�
            mBtAdapter.cancelDiscovery();

            // 寰楀埌mac鍦板潃
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // 璁剧疆杩斿洖鏁版嵁
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // 璁剧疆杩斿洖鍊煎苟缁撴潫绋嬪簭
            setResult(Activity.RESULT_OK, intent);
            finish();
        }
    };

    // 鏌ユ壘鍒拌澶囧拰鎼滅储瀹屾垚action鐩戝惉鍣�
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // 鏌ユ壘鍒拌澶嘺ction
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // 寰楀埌钃濈墮璁惧
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // 濡傛灉鏄凡閰嶅鐨勫垯鐣ヨ繃锛屽凡寰楀埌鏄剧ず锛屽叾浣欑殑鍦ㄦ坊鍔犲埌鍒楄〃涓繘琛屾樉绀�
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }else{  //娣诲姞鍒板凡閰嶅璁惧鍒楄〃
                	mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            // 鎼滅储瀹屾垚action
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
                setTitle("閫夋嫨瑕佽繛鎺ョ殑璁惧");
                if (mNewDevicesArrayAdapter.getCount() == 0) {
                    String noDevices = "娌℃湁鎵惧埌鏂拌澶�";
                    mNewDevicesArrayAdapter.add(noDevices);
                }
             //   if(mPairedDevicesArrayAdapter.getCount() > 0)
              //  	findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
            }
        }
    };

    //---------------------------
    /**
	 * 骞挎挱鎺ユ敹閫�鍑虹▼搴忕殑淇℃伅
	 */
	private BroadcastReceiver br = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			finish();
		};
	};

	@Override
	protected void onResume() {
		super.onResume();
		IntentFilter filter = new IntentFilter();
		filter.addAction("ExitApp");
		this.registerReceiver(br, filter);
	};
    //-----------------------------------------

}
