package org.phoneos.cydiahook.util;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;


public class FileUtil  {
	public static String ROOTPATH = Environment.getExternalStorageDirectory().getAbsolutePath();
	public static boolean isExternalStorageFile(String path) {
		String externalPath = ROOTPATH;
		int length = externalPath.length();
		if (!TextUtils.isEmpty(path) && path.length() >= length
				&& externalPath.equals(path.substring(0, length))) {
			return true;
		} else {
			return false;
		}
	}
	public static void checkDir(File parent) {
		if (!parent.exists()) {
			parent.mkdirs();
		}
	}
	
	/**
	 * @param path
	 * @param listenPacName
	 * @return
	 */
	public static boolean isDataStorageFile(String path, String listenPacName) {
		if (TextUtils.isEmpty(listenPacName) || TextUtils.isEmpty(path)) {
			return false;
		}
		if (path.contains("data/data/" + listenPacName)) {
			return false;
		}
		if (path.contains(listenPacName)) {
			return true;
		}

		String dataPath = Environment.getDataDirectory().getAbsolutePath()
				+ File.separator + "data/" + listenPacName;
		int length = dataPath.length();
		if (!TextUtils.isEmpty(path) && path.length() >= length
				&& dataPath.equals(path.substring(0, length))) {
			return true;
		} else {
			return false;
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
            return str;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			closeCloseable(br,in);
		}
		return str;
	}
	/**
	 * @param source
	 * @param des
	 */
	public static boolean copyFile(String source,String des){
		File srcFile = new File(source);
		if(!srcFile.exists()){
			return false;
		}
		File desFile = new File(des);
		checkDir(desFile.getParentFile());
		FileInputStream fis = null;
		FileOutputStream fos = null;
		try {
			if(!desFile.exists()){
				desFile.createNewFile();
			}
			fis = new FileInputStream(srcFile);
			fos = new FileOutputStream(desFile);
			byte[] buffer = new byte[1024];
			int tmp = -1;
			while((tmp=fis.read(buffer))!=-1){
				fos.write(buffer, 0, tmp);
			}
			fos.flush();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			closeCloseable(fis,fos);
		}
	}
	/**
	 * 
	 * @param closers
	 */
	public static void closeCloseable(Closeable... closers){
		for(Closeable closer:closers){
			try {
				if(closer!=null){
					closer.close();
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
