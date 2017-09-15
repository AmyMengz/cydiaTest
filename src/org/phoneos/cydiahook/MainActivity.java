package org.phoneos.cydiahook;

import java.io.File;

import org.phoneos.cydiahook.config.C;
import org.phoneos.cydiahook.config.Logger;
import org.phoneos.cydiahook.hook.FileSysOptRecordToFile;
import org.phoneos.cydiahook.hook.PullXmlUtil;
import org.phoneos.cydiahook.hook.SystemValueOperation;
import org.phoneos.cydiahook.hook.XmlUtils;
import org.phoneos.cydiahook.util.FileUtil;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = "cydiahook.MainActivity";
	public static boolean isManualTrigger = false;
	SharedPreferences preferences ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
//		preferences = getSharedPreferences("cat", 0);
		preferences=getSharedPreferences("cat",Context.MODE_WORLD_READABLE);
//		Editor editor=preferences.edit();
		
//		formXml();
//		SharedPreferences settings = getSharedPreferences("INFO", 0);  
//		String username = settings.getString("USERNAME", ""); 
//		
		Logger.i("preference:::::::::"+preferences);
		Button btnMountSd = (Button)this.findViewById(R.id.button1);
		btnMountSd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				resetSystem();
			}
		});
		Button button2 = (Button)findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PullXmlUtil pullXmlUtil = new PullXmlUtil();
//				try {
//					Map<String,String> maps =  pullXmlUtil.getMaps(new FileInputStream(new File("sdcard/xx/cat.xml")));
					
//				} catch (FileNotFoundException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				XmlUtils xmlUtils = XmlUtils.getInstance();
				String imei = xmlUtils.getValue("imei");
				Logger.i("imei----------------------------->:"+imei);
			}
		});
		Button button3 = (Button)findViewById(R.id.button3);
		button3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			formXml();
			boolean res = FileUtil.copyFile(C.XMLSRC, C.XMLPTAH);
			if(res) showToast("ok");
			else {
				showToast("err");
			}
			}
		});
		
	}
	Toast toast;
	public void showToast(String tips){
		toast = (toast==null)?Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_SHORT):toast;
		toast.setText(tips);
		toast.show();
		
	}
	public void formXml(){
//		preferences = getSharedPreferences("cat", 0);
		Editor editor=preferences.edit();
		editor.putString("guid", "252133f3-a2d3-4563-b2c4-63db0986e4e8");
		editor.putString("tacid", "2305");
		editor.putString("tac", "863583");
		editor.putString("imei", "863583875569890");
		editor.putString("meid", "863583875569890");
		editor.putString("imsi", "460003702716348");
		editor.putString("number", "+8618218866766");
		editor.putString("simserial", "89860018311491128742");
		editor.putString("wifimac", "48:7b:6e:25:5b:64");
		editor.putString("bluemac", "18:60:88:7d:48:51");
		editor.putString("dummy0", "4c:48:1d:18:41:2e");
		editor.putString("p2p0", "40:59:3c:30:75:55");
		editor.putString("bluename", "MI 3S");
		editor.putString("androidid", "f337b68073cadde2");
		editor.putString("serial", "f7eb685597");
		editor.putString("model", "MI 3S");
		editor.putString("manufacturer", "Xiaomi");
		editor.putString("brand", "Xiaomi");
		editor.putString("board", "MSM8960");
		editor.putString("device", "MI 3S");
		
		editor.putString("hardware", "Xiaomi");
		editor.putString("device", "Xiaomi");
		editor.putString("product", "aries");
		editor.putString("ssid", "zhuozhicao");
		editor.putString("bssid", "48:18:33:63:18:5b");
		editor.putString("display", "KTU84P");
		
		editor.putString("description", "aries-user 4.4.4 KTU84P JLB50.0 release-keys");
		editor.putString("fingerprint", "Xiaomi/aries/aries:4.4.4 KTU84P/JLB50.0:user/release-keys");
		editor.putString("simcountryiso", "cn");
		editor.putString("simoperator", "46000");
		editor.putString("simoperatorname", "中国移动");
		editor.putString("networkcountryiso", "cn");
		editor.putString("networkoperator", "46000");
		editor.putString("networkoperatorname", "中国移动");
		editor.putString("networktype", "13");
		editor.putString("phonetype", "1");
		editor.putString("simstate", "cn");
		editor.putString("radioversion", "M9615A-CEFWMAZM-2.0.128017");
		editor.putString("id", "KTU84P");
		editor.putString("version", "4.4.4");
		editor.putString("osname", "none");
		editor.putString("apilevel", "19");
		editor.putString("cid", "78a70afec14b22d44fec60ee4623c5f6");
		editor.putString("csd", "6c04a496e430592fa6ac1adfe4d71add");
		editor.putString("versionstr", "Linux version 3.65.13  (gcc version 4.7 (GCC) ) #1 SMP PREEMPT 2017/4/8 17:56:46");
		editor.putString("ipv6", "fe8000000000000075c3a1adeefaeb68 11 40 20 80    wlan0");
		editor.putString("firstboot", "1505117502");
		editor.putString("buildtime", "1498791319");
		editor.putString("sno", "161524924149");
		editor.putString("radiomodem", "WCDMA");
		editor.putString("timediff", "17:55:56");
		editor.putString("seed", "622932979");
		editor.putString("density", "0");
		editor.putString("rw", "0");
		editor.putString("rh", "0");
		editor.putString("densityDpi", "0");
		editor.putString("swidth", "720");
		editor.putString("sheight", "1280");
		editor.putString("screen", "0");
		editor.putString("mem", "0");
		editor.putString("sensor", "");
		editor.putString("cpu", "MI 3s");
		editor.putString("rootdir", "");
		editor.putString("psx", "117.415553127351");
		editor.putString("psy", "30.3185189138813");
		editor.putString("psz", "128.414325141075");
		editor.putString("pacc", "1449");
		editor.putString("rl", "-1");

		editor.putString("glac", "99908");

		editor.putString("gcid", "3668264");

		editor.putString("disgps", "0");

		editor.putString("innerip", "192.168.50.43");

		editor.putString("dns", "192.168.50.1");
		editor.commit();
		
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void resetSystem(){
		SystemValueOperation appOperation = new SystemValueOperation(getApplicationContext());
		appOperation.recoverSysValue();
		File file = new File(FileSysOptRecordToFile.RECORD_SYSTEM_OPT_PATH);
		if(file.exists()){
			file.delete();
		}
//		new SystemValueOperation(getApplicationContext());
		
	}

}
