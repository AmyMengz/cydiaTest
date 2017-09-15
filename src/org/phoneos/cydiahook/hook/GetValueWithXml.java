package org.phoneos.cydiahook.hook;

import org.phoneos.cydiahook.config.Logger;

import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;

public class GetValueWithXml {
	public static String getImei(XmlUtils xmlUtils) {
		String imei = xmlUtils.getValue("imei");
		Logger.i("imei----------"+imei);
		return imei;
	}
	public static String getModel(XmlUtils xmlUtils) {
		String model = xmlUtils.getValue("model");
		return model;
	}
	public static String getWifimac(XmlUtils xmlUtils) {
		String wifimac = xmlUtils.getValue("wifimac");
		return wifimac;
	}
	public static String getAndroidId(XmlUtils xmlUtils) {
		String androidid = xmlUtils.getValue("androidid");
		return androidid;
	}
	public static String getCid(XmlUtils xmlUtils) {
		String tacid = xmlUtils.getValue("tacid");
		return tacid;
	}
	public static String getLac(XmlUtils xmlUtils) {
		String glac = xmlUtils.getValue("glac");
		return glac;
	}
	
	public static CellLocation getCellLocation(XmlUtils xmlUtils) {
		String cellID = getCid(xmlUtils);
		String cellLac = getLac(xmlUtils);
		int cell_id = Integer.parseInt(cellID);
		int cell_lac = Integer.parseInt(cellLac);
		if (cell_id > 0 && cell_lac > 0) {
			GsmCellLocation gsmCellLocation = new GsmCellLocation();
			gsmCellLocation.setLacAndCid(cell_lac, cell_id);
			CellLocation cellLocation = gsmCellLocation;
			return cellLocation;
		}
		return null;
	}
	
	public static String getNetworkoperator(XmlUtils xmlUtils){
		return xmlUtils.getValue("networkoperator");
	}
	public static String getICCID(XmlUtils xmlUtils){
		return xmlUtils.getValue("simserial");
	}

}
