package org.phoneos.cydiahook;

import java.lang.reflect.Method;

import android.content.SharedPreferences;
import android.os.Environment;
import com.saurik.substrate.MS;

public class Main {
	static String getimei = "12345678912345";
	static void setResult(String value){
		getimei = value;
	}
	static SharedPreferences preferences ;
	static String getResult(){
		try{
		if( preferences == null){
			preferences = MyApplication.getContextObj().getSharedPreferences("test", 0);
		}
		System.out.println("hook packageName------imei getresult----->"+preferences);
		return preferences.getString("imie", getimei);
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e);
			return "869381021542042";
		}
	}
	static void initialize() {		
//		MS.	
		MS.hookClassLoad("android.os.Environment", new MS.ClassLoadHook() {

			@Override
			public void classLoaded(Class<?> arg0) {
				Method getExternalStorageState;
				try {
					String packageName = arg0.getPackage().getName();
					System.out.println("hook packageName----------->"+packageName);
					getExternalStorageState = arg0.getMethod(
							"getExternalStorageState", new Class<?>[0]);
				} catch (NoSuchMethodException e) {
					getExternalStorageState = null;
				}

				if (getExternalStorageState != null) {
					final MS.MethodPointer old = new MS.MethodPointer();

					MS.hookMethod(arg0, getExternalStorageState,
							new MS.MethodHook() {
								@Override
								public Object invoked(Object obj,
										Object... args) throws Throwable {
									return Environment.MEDIA_MOUNTED;
								}
							}, old);
				}
			}
		});
		MS.hookClassLoad("android.content.res.Resources",
				new MS.ClassLoadHook() {
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
                        	String packageName = arg0.getPackage().getName();
        					System.out.println("hook packageName----------->"+packageName);
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
                                    System.out.println("hook imei----------->");
                                    String imei = (String) old1.invoke(arg0,
                                            arg1);
                                    System.out.println("imei-------->" + imei);
                                    
                                    imei = getResult();//"999996015409998";
                                    return imei;
                                }
                            }, old1);
                        }
                    }
                });
    }
}