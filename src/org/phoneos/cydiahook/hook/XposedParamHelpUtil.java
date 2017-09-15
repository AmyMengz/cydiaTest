package org.phoneos.cydiahook.hook;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.phoneos.cydiahook.config.Logger;

import android.util.Log;

public class XposedParamHelpUtil {

	public static String KEY_ARGS = "DATA";
	public static String KEY_METHOD = "METHOD";
	public static String KEY_RESULT = "RESULT";

	public static void saveSystemValue(String packageName, String method,
			Object result, Object... arg) {

		Logger.i("saveSystemValue:" + packageName + " method:" + method
				+ " result:" + result + "  arg1:" + arg[1]
				+ ((arg.length >= 3) ? arg[2] : " "));
		JSONArray jsonArray = new JSONArray();
		ArrayList<Object> arrayList = new ArrayList<Object>();
		for (int i = 1; i < arg.length; i++) {
			if (arg[i] != null) {
				arrayList.add(arg[i].toString());
				jsonArray.put(arg[i].toString());
			}
		}
		try {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put(KEY_ARGS, jsonArray);
			jsonObject.put(KEY_METHOD, method);
			jsonObject.put(KEY_RESULT, result);

			FileSysOptRecordToFile.saveSystemOpt(packageName,
					jsonObject.toString());
		} catch (JSONException e) {
			Log.i("hook result  saveSystemValue  e", e + "e-----------------");
			e.printStackTrace();
		}
	}

}
