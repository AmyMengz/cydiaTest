package org.phoneos.cydiahook.hook;

import org.phoneos.cydiahook.config.Logger;

import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;

public class GetValue {
	public static String getImei() {
		XmlUtils xmlUtils = new XmlUtils();
		String imei = xmlUtils.getValue("imei");
		Logger.i("imei----------"+imei);
		return imei;
	}
	public static String getModel() {
		XmlUtils xmlUtils = new XmlUtils();
		String imei = xmlUtils.getValue("model");
		return imei;
	}
	public static String getWifimac() {
		XmlUtils xmlUtils = new XmlUtils();
		String imei = xmlUtils.getValue("wifimac");
		return imei;
	}
	public static String getAndroidId() {
		XmlUtils xmlUtils = new XmlUtils();
		String imei = xmlUtils.getValue("androidid");
		return imei;
	}
	public static String getCid(XmlUtils xmlUtils) {
		String tacid = xmlUtils.getValue("tacid");
		return tacid;
	}
	public static String getLac(XmlUtils xmlUtils) {
		String glac = xmlUtils.getValue("glac");
		return glac;
	}
	public static CellLocation getCellLocation() {
		XmlUtils xmlUtils = new XmlUtils();
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
	public static String getNetworkoperator(){
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("networkoperator");
	}
	public static String getICCID(){
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("simserial");
	}
	public static String getLineNumber(){
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("number");
	}
	public static String getPhoneType() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("phonetype");
	}
	public static String getSimCountryIso() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("simcountryiso");
	}
	public static String getSubscriberId() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("imsi");
	}
	public static String getNetworkOperatorName() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("networkoperatorname");
	}
	public static String getSimOperatorName() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("simoperatorname");
	}
	public static int getNetworkType() {
		XmlUtils xmlUtils = new XmlUtils();
		return Integer.valueOf(xmlUtils.getValue("networktype"));
	}
	public static Object getNetworkClass() {
		// TODO Auto-generated method stub
		return 3;
	}
	public static String getBrand() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("brand");
	}
	public static String getMac() {
		XmlUtils xmlUtils = new XmlUtils();
		return xmlUtils.getValue("wifimac");
	}


}
