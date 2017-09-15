package org.phoneos.cydiahook.hook;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.phoneos.cydiahook.config.Logger;

import android.content.Context;
import android.provider.Settings.System;
import android.text.TextUtils;

public class SystemValueOperation {
	public static String PUT_STRING = "putString";
	public static String GET_STRING = "getString";
	public static String GET_LONG = "getLong";
	public static String PUT_LONG = "putLong";
	public static String GET_FLOAT = "getFloat";
	public static String PUT_FLOAT = "putFloat";
	public static String GET_INT = "getInt";
	public static String PUT_INT = "putInt";
	String listenApk;
	Context context;
	Set<String> changeSet;
	public SystemValueOperation(Context context) {
		this.context = context;
		changeSet = new HashSet<String>();
		getSysVaule();
	}

	public void getSysVaule() {
			int count = 0;
			Set<String> systemSet = FileSysOptRecordToFile.getSysStringset(listenApk);
			Logger.i("systemSet:"+systemSet.size());
//			Logger
			Iterator<String> iterator = systemSet.iterator();
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				try {
					JSONObject jsonObject = new JSONObject(string);
					Logger.i("jsonObject"+jsonObject);
					JSONArray args = jsonObject.optJSONArray(XposedParamHelpUtil.KEY_ARGS);
					Logger.i("args:"+args);
					String arg1 = (String) args.opt(0);
					String method = jsonObject.optString(XposedParamHelpUtil.KEY_METHOD);
					if (!TextUtils.isEmpty(arg1)&&method.equals(GET_STRING)
							|| method.equals(PUT_STRING)
							|| method.equals(GET_INT)
							|| method.equals(PUT_INT)) {
						if (arg1.equals("android_id")) {
							continue;
						}
						changeSet.add(arg1);
						count++;
					} else {
						continue;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
	}

	public int recoverSysValue() {
		int failedCount = 0;
		// changeSet
		if (changeSet.size() > 0) {
			Iterator<String> iterator = changeSet.iterator();
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				boolean result = System.putString(context.getContentResolver(),string, null);
				Logger.i("recoverSysValue  string=========="+string);
				if (!result) {
					failedCount++;
				}
			}
		}
		return failedCount;
	}
}
