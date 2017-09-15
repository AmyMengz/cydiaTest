package org.phoneos.cydiahook.hook;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

public class PullXmlUtil {
	public static Map<String,String> getMaps(InputStream inputStream){
		Map<String, String> map = new HashMap<String, String>();
		XmlPullParser parser = Xml.newPullParser();
		Log.i("MAPPPP","inputStream:"+inputStream);
		try {
			parser.setInput(inputStream, "utf-8");
			int eventType = parser.getEventType();
			String name ="";
			String value = "";
			while(eventType!=XmlPullParser.END_DOCUMENT){
				String tagNameString = parser.getName();
				Log.i("MAPPPP","tagNameString:"+tagNameString+"  eventType:"+eventType);
				switch (eventType) {
				case XmlPullParser.START_TAG:
					if("string".equals(tagNameString)){
						name = parser.getAttributeValue(null, "name");
						value = parser.nextText();
						Log.i("MAPPPP","name:"+name+" value:"+value);
						map.put(name, value);
					}
					break;
				case XmlPullParser.END_TAG:
						
					break;
				default:
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i("MAPPPP","eee:"+e);
		}
		return map;
	}
	
	
//	public static final HashMap<String, ?> readMapXml(InputStream in)
//	  throws XmlPullParserException, java.io.IOException
//	   {
//	     XmlPullParser   parser = Xml.newPullParser();
//	      parser.setInput(in, "utf-8");
//	       return (HashMap<String, ?>) readValueXml(parser, new String[1]);
//	   }
//	public static final Object readValueXml(XmlPullParser parser, String[] name)
//	    throws XmlPullParserException, java.io.IOException
//	    {
//	        int eventType = parser.getEventType();
//	        do {
//	            if (eventType == parser.START_TAG) {
//	                return readThisValueXml(parser, name, false);
//	            } else if (eventType == parser.END_TAG) {
//	                throw new XmlPullParserException(
//	                    "Unexpected end tag at: " + parser.getName());
//	            } else if (eventType == parser.TEXT) {
//	                throw new XmlPullParserException(
//	                    "Unexpected text: " + parser.getText());
//	            }
//	            eventType = parser.next();
//	        } while (eventType != parser.END_DOCUMENT);
//	
//	        throw new XmlPullParserException(
//	            "Unexpected end of document");
//	    }
	
	
}
