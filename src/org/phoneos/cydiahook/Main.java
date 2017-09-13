package org.phoneos.cydiahook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;

import android.content.ContentResolver;
import android.text.TextUtils;

import com.saurik.substrate.MS;

public class Main {
	static String getimei = "12345678912345";

	static String getResult() {
		return "863531037180387";
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
		MS.hookClassLoad("android.telephony.TelephonyManager",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					public void classLoaded(Class<?> arg0) {
						Method hookimei;
						try {
							hookimei = arg0.getMethod("getDeviceId", null);
						} catch (NoSuchMethodException e) {
							e.printStackTrace();
							hookimei = null;
						}
						if (hookimei != null) {
							final MS.MethodPointer old1 = new MS.MethodPointer();
							MS.hookMethod(arg0, hookimei, new MS.MethodHook() {
								@Override
								public Object invoked(Object arg0,
										Object... arg1) throws Throwable {
									String imei = (String) old1.invoke(arg0,arg1);
									imei = getResult();// "999996015409998";
									return imei;
								}
							}, old1);
						}
					}
				});
		MS.hookClassLoad("android.provider.Settings$Secure",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					@Override
					public void classLoaded(Class<?> clz) {
						Method methodGetAndroidId;
						try {
							methodGetAndroidId = clz.getMethod("getString",ContentResolver.class, String.class);
						} catch (NoSuchMethodException e) {
							methodGetAndroidId = null;
						}
						if (methodGetAndroidId != null) {
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetAndroidId,
									new MS.MethodHook() {//String packageName,String method,String result,Object... arg
										@Override
										public Object invoked(Object obj,Object... args)throws Throwable {
											XposedParamHelpUtil.saveSystemValue(getpackageName(), "getString",old.invoke(obj, args),args);
											
											if ("android_id".equals(String.valueOf(args[1]))) {
												return "8a7821698b478523";
											}
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
						// hook getAndroidId
						Method methodGetAndroidId;
						try {
							methodGetAndroidId = clz.getMethod("getString",
									ContentResolver.class, String.class);
						} catch (NoSuchMethodException e) {
							methodGetAndroidId = null;
						}
						
						if (methodGetAndroidId != null) {
							final MS.MethodPointer old = new MS.MethodPointer();
							MS.hookMethod(clz, methodGetAndroidId,
									new MS.MethodHook() {
										@Override
										public Object invoked(Object obj,
												Object... args)
												throws Throwable {
											XposedParamHelpUtil.saveSystemValue(getpackageName(), "getString",old.invoke(obj, args),args);

//											System.out.println("hook result------name----->"+String
//													.valueOf(args[1])+" result: "+old.invoke(obj, args));
											if ("android_id".equals(String
													.valueOf(args[1]))) {
												return "8a7821698b478522";
											}
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
//							if (ishook())
								System.out.println("hook SystemProperties----------->"
												+ String.valueOf(args[0])
												+ "  " + getpackageName());
							if ("ro.product.brand".equals(String
									.valueOf(args[0]))) {

								return "HONNOR";
							}
							if ("ro.product.model".equals(String
									.valueOf(args[0]))) {

								return "CAZ-AL10";
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
//							if (ishook())
								System.out.println("hook MAC----------->"
										+ getpackageName());
							return "00:21:D3:55:54:56";
						}
					}, old);
				}
			}
		});
		MS.hookClassLoad("android.provider.Settings$System", new MS.ClassLoadHook() {
			
			@Override
			public void classLoaded(Class<?> clz) {
				Method method;
				try {
					method = clz.getMethod("putString",ContentResolver.class, String.class, String.class);
					System.out.println("hook result----method---========method===method=---->"+method);
				} catch (NoSuchMethodException e) {
					System.out.println("hook result----NoSuchMethodException---========method===method=---->"+e);
					method = null;
				}
				if (method != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, method, new MS.MethodHook() {
						@Override
						public Object invoked(Object arg0, Object... args)
								throws Throwable {
								System.out.println("hook result----name---============---->"+String.valueOf(args[1])+"---value---->"+String.valueOf(args[2])+" putString:");
							return old.invoke(arg0, args);
						}
					}, old);
				}
				
			}
		});		
		
//		SystemPutVauleHook("putString",ContentResolver.class, String.class, String.class);
////		SystemPutVauleHook("putLong",ContentResolver.class, String.class,long.class);
////		SystemPutVauleHook("putFloat",ContentResolver.class, String.class,float.class);
//		SystemPutVauleHook("putInt",ContentResolver.class, String.class,int.class);
//		
//		SystemgetVauleHook("getString",ContentResolver.class, String.class);
////		SystemgetVauleHook("getLong",ContentResolver.class, String.class);
////		SystemgetVauleHook("getFloat",ContentResolver.class, String.class);
//		SystemgetVauleHook("getInt",ContentResolver.class, String.class);
//		SystemgetVauleHook("getInt",ContentResolver.class, String.class,int.class);
	}
	private static void SystemgetVauleHook(final String methodName,final Class<?>... parameterTypes) {
		MS.hookClassLoad("android.provider.Settings$Secure", new MS.ClassLoadHook() {
			
			@Override
			public void classLoaded(Class<?> clz) {
				Method method;
				try {
					method = clz.getMethod(methodName,parameterTypes);
					System.out.println("hook result----method---========method===method=---->"+method);
				} catch (NoSuchMethodException e) {
					System.out.println("hook result----NoSuchMethodException---========method===method=---->"+e);
					method = null;
				}
				if (method != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, method, new MS.MethodHook() {
						@Override
						public Object invoked(Object arg0, Object... args)
								throws Throwable {
//							Object result =  old.invoke(arg0,args);
//							if (ishook())
//								System.out.println("hook result----------->"+result+"  methodName:"+methodName);
								System.out.println("hook result----name----======--->"+String.valueOf(args[1])+"---value---->"+String.valueOf(args[2])+" methodName:"+methodName);
							return old.invoke(arg0, args);
						}
					}, old);
				}
				
			}
		});
		MS.hookClassLoad("android.provider.Settings$System", new MS.ClassLoadHook() {
			
			@Override
			public void classLoaded(Class<?> clz) {
				Method method;
				try {
					method = clz.getMethod(methodName,parameterTypes);
					System.out.println("hook result----method---========method===method=---->"+method);
				} catch (NoSuchMethodException e) {
					System.out.println("hook result----NoSuchMethodException---========method===method=---->"+e);
					method = null;
				}
				if (method != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, method, new MS.MethodHook() {
						@Override
						public Object invoked(Object arg0, Object... args)
								throws Throwable {
//							Object result =  old.invoke(arg0,args);
//							if (ishook())
//								System.out.println("hook result----------->"+result+"  methodName:"+methodName);
								System.out.println("hook result----name----======--->"+String.valueOf(args[1])+"---value---->"+String.valueOf(args[2])+" methodName:"+methodName);
							return old.invoke(arg0, args);
						}
					}, old);
				}
				
			}
		});

	}
	public static void SystemPutVauleHook(final String methodName,final Class<?>... parameterTypes) {
		System.out.println("hook result----SystemPutVauleHook---========method===method=---->"+methodName+"  "+parameterTypes);
		
		MS.hookClassLoad("android.provider.Settings$Secure", new MS.ClassLoadHook() {
			
			@Override
			public void classLoaded(Class<?> clz) {
				Method method;
				try {
					method = clz.getMethod(methodName,parameterTypes);
					System.out.println("hook result----method---========method===method=---->"+method);

				} catch (NoSuchMethodException e) {
					System.out.println("hook result----NoSuchMethodException---========method===method=---->"+e);
					method = null;
				}
				if (method != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, method, new MS.MethodHook() {
						@Override
						public Object invoked(Object arg0, Object... args)
								throws Throwable {
//							boolean result = (Boolean) old.invoke(arg0,args);
////							if (ishook())
//								System.out.println("hook result----------->"+result+"  methodName:"+methodName);
								System.out.println("hook result----name---============---->"+String.valueOf(args[1])+"---value---->"+String.valueOf(args[2])+" methodName:"+methodName);
							return old.invoke(arg0, args);
						}
					}, old);
				}
				
			}
		});
		MS.hookClassLoad("android.provider.Settings$System", new MS.ClassLoadHook() {
			
			@Override
			public void classLoaded(Class<?> clz) {
				Method method;
				try {
					method = clz.getMethod(methodName,parameterTypes);
					System.out.println("hook result----method---========method===method=---->"+method);
				} catch (NoSuchMethodException e) {
					System.out.println("hook result----NoSuchMethodException---========method===method=---->"+e);
					method = null;
				}
				if (method != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(clz, method, new MS.MethodHook() {
						@Override
						public Object invoked(Object arg0, Object... args)
								throws Throwable {
//							boolean result = (Boolean) old.invoke(arg0,args);
////							if (ishook())
//								System.out.println("hook result----------->"+result+"  methodName:"+methodName);
								System.out.println("hook result----name---============---->"+String.valueOf(args[1])+"---value---->"+String.valueOf(args[2])+" methodName:"+methodName);
							return old.invoke(arg0, args);
						}
					}, old);
				}
				
			}
		});		
	}
	
//XposedHelpers.findAndHookMethod(System.class, methodName, new_objects);
//	XposedHelpers.findAndHookMethod(Secure.class, methodName, new_objects);
	
	
//	SystemVauleHook(MethodInt.PUT_STRING, packageName, MethodInt.SYSTEM_VALUE_PUT_STRING, ContentResolver.class,String.class, String.class);
//	SystemVauleHook(MethodInt.GET_STRING, packageName, MethodInt.SYSTEM_VALUE_GET_STRING, ContentResolver.class,String.class);
//	
//	SystemVauleHook(MethodInt.PUT_INT, packageName, MethodInt.SYSTEM_VALUE_PUT_INT, ContentResolver.class,String.class, int.class);
//	SystemVauleHook(MethodInt.GET_INT, packageName, MethodInt.SYSTEM_VALUE_GET_INT_2, ContentResolver.class,String.class);
//	SystemVauleHook(MethodInt.GET_INT, packageName, MethodInt.SYSTEM_VALUE_GET_INT_3, ContentResolver.class,String.class, int.class);

	public static boolean ishook() {
		String pac = getpackageName();
		return (!TextUtils.isEmpty(pac))
				&& (pac.equals("com.example.hellojni") || pac
						.equals("com.donson.leplay.store2"));
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
			System.out
					.println("hook pac------================================================----->"
							+ cmdline);
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