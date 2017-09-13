package org.phoneos.cydiahook;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import android.content.Context;


public class FileUtil  {
	public static void checkDir(File parent) {
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}
	private static final int BUFFER_SIZE = 1024; 
	/**
	 * 复制本地raw中文件到SD卡 EXTRA_PATH/desFileName中
	 * @param context
	 * @param desFileName
	 * @param sourceId
	 * @param MD5
	 */
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public static  String  readFileFromAsset(Context context,String fileName){ 
		StringBuilder sb=new StringBuilder();
		InputStream in = null;
		BufferedReader br = null;
		try{
			in = context.getAssets().open(fileName);
			br = new BufferedReader(new InputStreamReader(in));
			String line=null;
            while((line=br.readLine())!=null)
            {
         	   sb.append(line);
            }
            br.close();
            return sb.toString();
		}catch(Exception e){
			e.printStackTrace();
		}
		return sb.toString();
	}  
	/**
	 * 从 文件 逐行读取
	 * @param path
	 * @return
	 */
	public static List<String>  readFileStrFromAsset(Context context,String fileName){ 
		List<String> str = new ArrayList<String>();
		InputStream in = null;
		BufferedReader br = null;
		try{
			in = context.getAssets().open(fileName);
			br = new BufferedReader(new InputStreamReader(in));
			String line=null;
            while((line=br.readLine())!=null)
            {
            	str.add(line);
            }
            br.close();
            in.close();
            return str;
		}catch(Exception e){
			e.printStackTrace();
		}
		return str;
	}
}
