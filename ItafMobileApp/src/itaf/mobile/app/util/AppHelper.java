package itaf.mobile.app.util;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;

public class AppHelper {

	/**
	 * APP程序是否在前台运行
	 * 
	 * @return true 前台 false 后台
	 */
	public static boolean isAppOnForeground(Context mContext) {
		ActivityManager activityManager = (ActivityManager) mContext
				.getApplicationContext().getSystemService(
						Context.ACTIVITY_SERVICE);
		String packageName = mContext.getApplicationContext().getPackageName();
		List<RunningAppProcessInfo> appProcesses = activityManager
				.getRunningAppProcesses();
		if (appProcesses == null) {
			return false;
		}
		for (RunningAppProcessInfo appProcess : appProcesses) {
			if (appProcess.processName.equals(packageName)
					&& appProcess.importance == RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
				return true;
			}
		}
		return false;
	}

}
