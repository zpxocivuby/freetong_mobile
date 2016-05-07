package itaf.mobile.core.utils;

import java.util.Locale;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * 网络帮助
 * 
 * 
 * @author
 * 
 * @updateDate 2014年3月14日
 */
public class NetWorkHelper {

	public static final int NETTYPE_WIFI = 0x01;
	public static final int NETTYPE_CMWAP = 0x02;
	public static final int NETTYPE_CMNET = 0x03;

	/**
	 * 检测网络是否可用
	 * 
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();
		return ni != null && ni.isConnectedOrConnecting();
	}

	/**
	 * 获取当前网络类型
	 * 
	 * @return 0：没有网络 1：WIFI网络 2：WAP网络 3：NET网络
	 */
	public static int getNetWorkType(Context context) {
		int netType = 0;
		try {
			ConnectivityManager cm = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = cm.getActiveNetworkInfo();
			if (networkInfo == null) {
				return netType;
			}
			int nType = networkInfo.getType();
			if (nType == ConnectivityManager.TYPE_MOBILE) {
				String extraInfo = networkInfo.getExtraInfo();
				if (!isEmpty(extraInfo)) {
					if (extraInfo.toLowerCase(Locale.getDefault()).equals(
							"cmnet")) {
						netType = NETTYPE_CMNET;
					} else {
						netType = NETTYPE_CMWAP;
					}
				}
			} else if (nType == ConnectivityManager.TYPE_WIFI) {
				netType = NETTYPE_WIFI;
			}
		} catch (Exception e) {
			Log.e(NetWorkHelper.class.getName(), e.getMessage());
		}
		return netType;
	}

	/**
	 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串 若输入字符串为null或空字符串，返回true
	 * 
	 * @param input
	 * @return boolean
	 */
	public static boolean isEmpty(String input) {
		if (input == null || "".equals(input))
			return true;

		for (int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
				return false;
			}
		}
		return true;
	}

}
