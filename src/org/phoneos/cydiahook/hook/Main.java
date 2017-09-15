package org.phoneos.cydiahook.hook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import org.phoneos.cydiahook.config.Logger;

import android.content.ContentResolver;
import android.telephony.CellLocation;
import android.telephony.PhoneStateListener;
import android.text.TextUtils;

import com.saurik.substrate.MS;

public class Main {
	static String getimei = "12345678912345";
//	static XmlUtils xmlUtils = new XmlUtils();//XmlUtils.getInstance();
	static String getImei() {
		XmlUtils xmlUtils = new XmlUtils();
		String imei = xmlUtils.getValue("imei");
		Logger.i("imei----------"+imei);
		return imei;
	}
	static void initialize() {
		MS.hookClassLoad("android.content.res.Resources",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					public void classLoaded(Class<?> resources) {
						Method getColor;
						try {
							getColor = resources.getMethod("getColor",Integer.TYPE);
						} catch (NoSuchMethodException e) {
							getColor = null;
						}
						if (getColor != null) {
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(resources, getColor,
									new MS.MethodHook() {
										public Object invoked(Object resources,Object... args)throws Throwable {
											int color = (Integer) old.invoke(resources, args);
											return color & ~0x0000ff00| 0x00ff0000;
										}
									}, old);
						}
					}
				});
		/**
		 * TelephonyManager
		 */
		MS.hookClassLoad("android.telephony.TelephonyManager",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					public void classLoaded(Class<?> arg0) {
							hookTelephonyManager(arg0);
					}
				});
		MS.hookClassLoad("android.provider.Settings$Secure",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					@Override
					public void classLoaded(Class<?> clz) {
						Method methodGetString;
						try {
							methodGetString = clz.getMethod("getString",ContentResolver.class, String.class);
						} catch (NoSuchMethodException e) {
							methodGetString = null;
						}
						if (methodGetString != null) {
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetString,
									new MS.MethodHook() {//String packageName,String method,String result,Object... arg
										@Override
										public Object invoked(Object obj,Object... args)throws Throwable {
											if(ishook()){
												XposedParamHelpUtil.saveSystemValue(getpackageName(), "getString",old.invoke(obj, args),args);
											}
											if ("android_id".equals(String.valueOf(args[1]))) {
												return GetValue.getAndroidId();
											}
											return old.invoke(obj, args);
										}
									}, old);

						}
						Method methodPutString;
						try{
							methodPutString = clz.getMethod("putString", ContentResolver.class,String.class,String.class);
						}catch(NoSuchMethodException e){
							methodPutString = null;
						}
						if(methodPutString != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodPutString, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									String pac = getpackageName();
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "putString",old.invoke(obj, args),args);
									}
									// TODO Auto-generated method stub
									return old.invoke(obj, args);
								}
							}, old);
						}
						
						Method methodPutInt;
						try{
							methodPutInt = clz.getMethod("putInt", ContentResolver.class,String.class,int.class);
						}catch(NoSuchMethodException e){
							methodPutInt = null;
						}
						if(methodPutInt != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodPutInt, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "putInt",old.invoke(obj, args),args);
									}
									// TODO Auto-generated method stub
									return old.invoke(obj, args);
								}
							}, old);
						}
						Method methodGetInt;
						try{
							methodGetInt = clz.getMethod("getInt", ContentResolver.class,String.class);
						}catch(NoSuchMethodException e){
							methodGetInt = null;
						}
						if(methodGetInt != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetInt, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "getInt",old.invoke(obj, args),args);
									}
									// TODO Auto-generated method stub
									return old.invoke(obj, args);
								}
							}, old);
						}
					}
				});
		MS.hookClassLoad("android.provider.Settings$System",
				new MS.ClassLoadHook() {

					@SuppressWarnings("unchecked")
					@Override
					public void classLoaded(Class<?> clz) {
						Method methodGetString;
						try {
							methodGetString = clz.getMethod("getString",ContentResolver.class, String.class);
						} catch (NoSuchMethodException e) {
							methodGetString = null;
						}
						
						if (methodGetString != null) {
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetString,
									new MS.MethodHook() {
										@Override
										public Object invoked(Object obj,Object... args)throws Throwable {
//											Logger.i("NOLIMIT---------System-------:"+pac+" getpackageName:"+getpackageName()+"  args:"+args[1]+"  "+ishook(pac));

											if(ishook()){
												Logger.i("system:"+" getpackageName:"+getpackageName());
												XposedParamHelpUtil.saveSystemValue(getpackageName(), "getString",old.invoke(obj, args),args);
											}						
											if ("android_id".equals(String.valueOf(args[1]))) {
												return GetValue.getAndroidId();
											}
											return old.invoke(obj, args);
										}
									}, old);

						}
						Method methodPutString;
						try{
							methodPutString = clz.getMethod("putString", ContentResolver.class,String.class,String.class);
						}catch(NoSuchMethodException e){
							methodPutString = null;
						}
						if(methodPutString != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodPutString, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "putString",old.invoke(obj, args),args);
									}
									return old.invoke(obj, args);
								}
							}, old);
						}
						Method methodPutInt;
						try{
							methodPutInt = clz.getMethod("putInt", ContentResolver.class,String.class,int.class);
						}catch(NoSuchMethodException e){
							methodPutInt = null;
						}
						if(methodPutInt != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodPutInt, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "putInt",old.invoke(obj, args),args);
									}
									// TODO Auto-generated method stub
									return old.invoke(obj, args);
								}
							}, old);
						}
						Method methodGetInt;
						try{
							methodGetInt = clz.getMethod("getInt", ContentResolver.class,String.class);
						}catch(NoSuchMethodException e){
							methodGetInt = null;
						}
						if(methodGetInt != null){
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetInt, new MS.MethodHook() {

								@Override
								public Object invoked(Object obj,Object... args) throws Throwable {
									if(ishook()){
										XposedParamHelpUtil.saveSystemValue(getpackageName(), "getInt",old.invoke(obj, args),args);
									}
									// TODO Auto-generated method stub
									return old.invoke(obj, args);
								}
							}, old);
						}
					}
				});
		MS.hookClassLoad("android.os.SystemProperties", new MS.ClassLoadHook() {

			@SuppressWarnings("unchecked")
			@Override
			public void classLoaded(Class<?> clz) {
				// hook getString(String pro)
				Method methodGetString;
				try {
					methodGetString = clz.getMethod("get", String.class,
							String.class);
				} catch (NoSuchMethodException e) {
					methodGetString = null;
				}
				if (methodGetString != null) {

					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, methodGetString, new MS.MethodHook() {
						@Override
						public Object invoked(Object obj, Object... args)
								throws Throwable {
							if (!ishook()){
								return old.invoke(obj, args);
							}
							if ("ro.product.brand".equals(String
									.valueOf(args[0]))) {

								return GetValue.getBrand();
							}
							if ("ro.product.model".equals(String
									.valueOf(args[0]))) {

								return GetValue.getModel();
							}
							return old.invoke(obj, args);
						}
					}, old);

				}
			}
		});
		MS.hookClassLoad("android.net.wifi.WifiInfo", new MS.ClassLoadHook() {

			@SuppressWarnings("unchecked")
			@Override
			public void classLoaded(Class<?> clz) {
				// hook getMacAddress
				Method methodGetMac;
				try {
					methodGetMac = clz.getMethod("getMacAddress",
							new Class<?>[0]);
				} catch (NoSuchMethodException e) {
					methodGetMac = null;
				}
				if (methodGetMac != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, methodGetMac, new MS.MethodHook() {
						@Override
						public Object invoked(Object obj, Object... args)
								throws Throwable {
							if (!ishook()){
								return old.invoke(obj, args);
							}
							return GetValue.getMac();
						}
					}, old);
				}
			}
		});
	}
	/**
	 * TelephonyManager
	if(realsdk>21){HookMethod(TelephonyManager.class, "getLine1NumberForSubscriber", packageName, MethodInt.GET_LINE_NUMBER1_2, Integer.TYPE);}
	if(realsdk==21){ HookMethod(TelephonyManager.class, "getLine1NumberForSubscriber", packageName, MethodInt.GET_LINE_NUMBER1_3, Long.TYPE);		}
	 */
	protected static void hookTelephonyManager(Class<?> arg0) {
		
		Method getDeviceId1;
		try {
			getDeviceId1 = arg0.getMethod("getDeviceId", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			getDeviceId1 = null;
		}
		if (getDeviceId1 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, getDeviceId1, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getImei();
				}
			}, old1);
		}
		Method getDeviceId2;
		try {
			getDeviceId2 = arg0.getMethod("getDeviceId", Integer.TYPE);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			getDeviceId2 = null;
		}
		if (getDeviceId2 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, getDeviceId2, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getImei();
				}
			}, old1);
		}
		Method getImei;
		try {
			getImei = arg0.getMethod("getDeviceId", Integer.TYPE);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			getImei = null;
		}
		if (getImei != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, getImei, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getImei();
				}
			}, old1);
		}
		Method mGetCellLocation;
		try {
			mGetCellLocation = arg0.getMethod("getCellLocation", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetCellLocation = null;
		}
		if (mGetCellLocation != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetCellLocation, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					CellLocation cellLocation = GetValue.getCellLocation();
					if(cellLocation==null){
						return old1.invoke(arg0,arg1);
					}else {
						return cellLocation;
					}
				}
			}, old1);
		}
		Method mListen;
		try {
			mListen = arg0.getMethod("listen", PhoneStateListener.class,int.class);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mListen = null;
		}
		if (mListen != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mListen, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... args) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,args);
					}
					if(args.length > 1){
						if(args[0] instanceof PhoneStateListener){
							PhoneStateListener listener = (PhoneStateListener) args[0];
							listener = new XPhoneStateListener();
							args[0] = listener;
						}
					}
					return old1.invoke(arg0, args);
				}
			}, old1);
		}
		Method mHasIccCard;
		try {
			mHasIccCard = arg0.getMethod("hasIccCard", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mHasIccCard = null;
		}
		if (mHasIccCard != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mHasIccCard, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					return true;
				}
			}, old1);
		}
		Method mHasIccCard2;
		try {
			mHasIccCard2 = arg0.getMethod("hasIccCard", Integer.TYPE);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mHasIccCard2 = null;
		}
		if (mHasIccCard2 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mHasIccCard2, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return true;
				}
			}, old1);
		}
		Method mGetSimState;
		try {
			mGetSimState = arg0.getMethod("getSimState", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSimState = null;
		}
		if (mGetSimState != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimState, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					return 5;
				}
			}, old1);
		}
		
		Method mGetNetworkOperator;
		try {
			mGetNetworkOperator = arg0.getMethod("getNetworkoperator", null);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetNetworkOperator = null;
		}
		if (mGetNetworkOperator != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetNetworkOperator, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					return GetValue.getNetworkoperator();
				}
			}, old1);
		}
		Method mGetSimSerialNumber;
		try {
			mGetSimSerialNumber = arg0.getMethod("getSimSerialNumber", null);  //getSimSerialNumber iccid
		} catch (NoSuchMethodException e) {
			mGetSimSerialNumber = null;
		}
		if (mGetSimSerialNumber != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimSerialNumber, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getICCID();
				}
			}, old1);
		}
		Method mGetSimSerialNumber2;
		try {
			mGetSimSerialNumber2 = arg0.getMethod("getSimSerialNumber", Integer.TYPE);  //getSimSerialNumber iccid
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSimSerialNumber2 = null;
		}
		if (mGetSimSerialNumber2 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimSerialNumber2, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getICCID();
				}
			}, old1);
		}
		Method mGetSimSerialNumber3;
		try {
			mGetSimSerialNumber3 = arg0.getMethod("getSimSerialNumber", Long.TYPE);  //getSimSerialNumber iccid
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSimSerialNumber3 = null;
		}
		if (mGetSimSerialNumber3 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimSerialNumber3, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getICCID();
				}
			}, old1);
		}
		Method mGetLine1Number;
		try {
			mGetLine1Number = arg0.getMethod("getLine1Number",null);  //
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetLine1Number = null;
		}
		if (mGetLine1Number != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetLine1Number, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getLineNumber();
				}
			}, old1);
		}
		Method mGetPhoneType;
		try {
			mGetPhoneType = arg0.getMethod("getPhoneType",null);  //
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetPhoneType = null;
		}
		if (mGetPhoneType != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetPhoneType, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return Integer.valueOf(GetValue.getPhoneType());
				}
			}, old1);
		}
		Method mGetSimCountryIso;
		try {
			mGetSimCountryIso = arg0.getMethod("getSimCountryIso",null);  //
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSimCountryIso = null;
		}
		if (mGetSimCountryIso != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimCountryIso, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getSimCountryIso();
				}
			}, old1);
		}
		Method mGetSubscriberId;
		try {
			mGetSubscriberId = arg0.getMethod("getSubscriberId",null);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSubscriberId = null;
		}
		if (mGetSubscriberId != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSubscriberId, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getSubscriberId();
				}
			}, old1);
		}
		
		//-------------------
		Method mGetSubscriberId2;
		try {
			mGetSubscriberId2 = arg0.getMethod("getSubscriberId",Integer.TYPE);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSubscriberId2 = null;
		}
		if (mGetSubscriberId2 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSubscriberId2, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getSubscriberId();
				}
			}, old1);
		}
		Method mGetSubscriberId3;
		try {
			mGetSubscriberId3 = arg0.getMethod("getSubscriberId",Long.TYPE);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSubscriberId3 = null;
		}
		if (mGetSubscriberId3 != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSubscriberId3, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getSubscriberId();
				}
			}, old1);
		}
		Method mGetNetworkOperatorName;
		try {
			mGetNetworkOperatorName = arg0.getMethod("getNetworkOperatorName",null);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetNetworkOperatorName = null;
		}
		if (mGetNetworkOperatorName != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetNetworkOperatorName, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getNetworkOperatorName();
				}
			}, old1);
		}
		Method mGetSimOperatorName;
		try {
			mGetSimOperatorName = arg0.getMethod("getSimOperatorName",null);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetSimOperatorName = null;
		}
		if (mGetSimOperatorName != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetSimOperatorName, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getSimOperatorName();
				}
			}, old1);
		}
		Method mGetNetworkClass;
		try {
			mGetNetworkClass = arg0.getMethod("getNetworkClass",Integer.TYPE);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetNetworkClass = null;
		}
		if (mGetNetworkClass != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetNetworkClass, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getNetworkClass();
				}
			}, old1);
		}
		Method mGetNetworkType;
		try {
			mGetNetworkType = arg0.getMethod("getNetworkType",null);  //imsi
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
			mGetNetworkType = null;
		}
		if (mGetNetworkType != null) {
			final MS.MethodPointer old1 = new MS.MethodPointer();
			MS.hookMethod(arg0, mGetNetworkType, new MS.MethodHook() {
				@Override
				public Object invoked(Object arg0,Object... arg1) throws Throwable {
					if(!ishook()){
						return old1.invoke(arg0,arg1);
					}
					return GetValue.getNetworkType();
				}
			}, old1);
		}

	}
	public static boolean ishook() {
		int uid = android.os.Process.myUid();
		if(uid<=1000){
			return false;
		}
		String pac = getpackageName();
		if(TextUtils.isEmpty(pac)){
			return false;
		}
		if(pac.contains("org.phoneos.cydiahook")||pac.contains("com.saurik.substrate")){
			return false;
		}else {
			return true;
		}
//		return (!TextUtils.isEmpty(pac))
//				&& (pac.contains("com.example.hellojni") || pac
//						.contains("com.donson.leplay.store2")||pac.contains("com.example.getparamtest"));
	}

	static BufferedReader reader;

	public static String getpackageName() {
		int pid = android.os.Process.myPid();
		String cmdline = "";
		try {
			// reader = new BufferedReader(new FileReader("/proc/" +
			// pid+"/cmdline"));
			cmdline = ProcFile.readFile(String.format("/proc/%d/cmdline", pid))
					.trim();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return cmdline;

	}

}