package org.phoneos.cydiahook;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.saurik.substrate.MS;

public class Main {
	static String getimei = "12345678912345";

	static void setResult(String value) {
		getimei = value;
	}

	static SharedPreferences preferences;

	static String getResult() {
		try {
			if (preferences == null) {
				preferences = MyApplication.getContextObj()
						.getSharedPreferences("test", 0);
			}
			System.out.println("hook packageName------imei getresult----->"
					+ preferences);
			return preferences.getString("imie", getimei);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
			return "863531037180387";
		}
	}

	static void initialize() {
		// MS.
		// MS.hookClassLoad("android.os.Environment", new MS.ClassLoadHook() {
		//
		// @Override
		// public void classLoaded(Class<?> arg0) {
		// Method getExternalStorageState;
		// try {
		// String packageName = arg0.getPackage().getName();
		// System.out.println("hook packageName----------->"
		// + packageName);
		// getExternalStorageState = arg0.getMethod(
		// "getExternalStorageState", new Class<?>[0]);
		// } catch (NoSuchMethodException e) {
		// getExternalStorageState = null;
		// }
		//
		// if (getExternalStorageState != null) {
		// final MS.MethodPointer old = new MS.MethodPointer();
		//
		// MS.hookMethod(arg0, getExternalStorageState,
		// new MS.MethodHook() {
		// @Override
		// public Object invoked(Object obj,
		// Object... args) throws Throwable {
		// return Environment.MEDIA_MOUNTED;
		// }
		// }, old);
		// }
		// }
		// });
		MS.hookClassLoad("android.content.res.Resources",
				new MS.ClassLoadHook() {
					@SuppressWarnings("unchecked")
					public void classLoaded(Class<?> resources) {
						Method getColor;
						try {
							getColor = resources.getMethod("getColor",
									Integer.TYPE);
						} catch (NoSuchMethodException e) {
							getColor = null;
						}

						if (getColor != null) {
							final MS.MethodPointer old = new MS.MethodPointer();

							MS.hookMethod(resources, getColor,
									new MS.MethodHook() {
										public Object invoked(Object resources,
												Object... args)
												throws Throwable {
											int color = (Integer) old.invoke(
													resources, args);
											return color & ~0x0000ff00
													| 0x00ff0000;
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
							// TODO Auto-generated catch block
							e.printStackTrace();
							hookimei = null;
						}
						if (hookimei != null) {
							final MS.MethodPointer old1 = new MS.MethodPointer();
							MS.hookMethod(arg0, hookimei, new MS.MethodHook() {
								@Override
								public Object invoked(Object arg0,
										Object... arg1) throws Throwable {
									// TODO Auto-generated method stub
//									if (ishook())
										System.out
												.println("hook imei----------->"
														+ getpackageName());
									String imei = (String) old1.invoke(arg0,
											arg1);
									System.out.println("imei-------->" + imei);

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
								System.out
										.println("hook SystemProperties----------->"
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
							return "00:21:D3:55:54:55";
						}
					}, old);
				}
			}
		});
	}

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