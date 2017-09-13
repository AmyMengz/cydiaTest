package org.phoneos.cydiahook;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import android.net.wifi.ScanResult;
import android.text.TextUtils;
import android.util.Log;

public class XposedParamHelpUtil {


	
	public static String KEY_ARGS = "DATA";
	public static String KEY_METHOD = "METHOD";
	public static String KEY_RESULT = "RESULT";
	public static void saveSystemValue(String packageName,String method,Object result,Object... arg) {
		
		Log.i("hook result  saveSystemValue", packageName+" "+method+" "+result+" "+arg);
			ArrayList<Object> arrayList = new ArrayList<Object>();
			for (int i = 1; i < arg.length; i++) {
				arrayList.add(arg[i].toString());
			}
			HashMap<String, ArrayList<Object>> hashMap = new HashMap<String, ArrayList<Object>>();
			hashMap.put(KEY_ARGS, arrayList);
			try {
				JSONObject jsonObject = new JSONObject(hashMap);
//				jsonObject.put(KEY_ARGS, data);
				jsonObject.put(KEY_METHOD, method);
				jsonObject.put(KEY_RESULT, result);
				FileSysOptRecordToFile.saveSystemOpt(packageName,jsonObject.toString());
			} catch (JSONException e) {
				Log.i("hook result  saveSystemValue  e", e+"e-----------------");

				e.printStackTrace();
			}
	}
	
}
