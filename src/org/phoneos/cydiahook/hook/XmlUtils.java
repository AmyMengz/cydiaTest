package org.phoneos.cydiahook.hook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.phoneos.cydiahook.config.C;
//import org.dom4j.DocumentException;
//import org.dom4j.DocumentHelper;
//import org.dom4j.Element;

public class XmlUtils {

	Map<String, String> map;
	InputStream inputStream;
	public XmlUtils(InputStream inputStream) {
		this.inputStream = inputStream;
		map = PullXmlUtil.getMaps(inputStream);
	}

	private static XmlUtils instance;
	public static XmlUtils getInstance(){
		if(instance!=null){
			return instance;
		}
		synchronized (XmlUtils.class) {
			if(instance == null){
				instance = new XmlUtils();
			}
		}
		return instance;
	}
	public XmlUtils() {
		String path = C.XMLPTAH;
		try {
			inputStream = new FileInputStream(new File(path));
			map = PullXmlUtil.getMaps(inputStream);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	public String getValue(String name) {
		if (map == null){
			if(inputStream!=null){
				map = PullXmlUtil.getMaps(inputStream);
				if (map.containsKey(name)) {
					return map.get(name);
				}
			}else {
				return "";
			}
		}
		if (map.containsKey(name)) {
			return map.get(name);
		} else {
			return "";
		}
	}
	
	public boolean putString(String name,String value){
		Map<String, String> mMap = new HashMap<String, String>();
		mMap = map;
		if (mMap == null){
			if(inputStream!=null){
				mMap = PullXmlUtil.getMaps(inputStream);
				if(mMap!=null){
					mMap.put(name, value);
					return true;
				}else {
					return false;
				}
			}else {
				return false;
			}
		}else {
			mMap.put(name, value);
			return true;
		}
	}
	public void map2Xml(Map<String, String> map){
		
	}

}
