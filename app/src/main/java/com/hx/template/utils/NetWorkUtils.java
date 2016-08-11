package com.hx.template.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

public class NetWorkUtils {
	/**
	 * 判断网络是否连接并可pin通
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetWorkConnect(Context context) {
		boolean isConnect = false;
		try {
			// boolean netSataus = false;
			ConnectivityManager cwjManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cwjManager.getActiveNetworkInfo();
			if (networkInfo != null) {
				isConnect = networkInfo.isAvailable()
						&& networkInfo.isConnected();
			}
			LogUtils.d("NetWorkUtils", "****net work connection = " + isConnect);
			return isConnect;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isConnect;
	}

	/**
	 * 获取本机号码
	 * 
	 * @param context
	 * @return
	 */
	public static String getLocalPhoneNumber(Context context) {
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			String nativePhoneNumber = "";
			nativePhoneNumber = telephonyManager.getLine1Number();
			return nativePhoneNumber;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
}
