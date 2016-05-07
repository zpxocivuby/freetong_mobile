package itaf.mobile.app.notification;

import itaf.mobile.core.constant.AppConstants;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

public class NoticeFacade {

	public static Map<String, Notice> notices;

	public static Map<String, Boolean> idAndOnOffMap;

	public static void initNotices() {
		if (notices == null) {
			notices = new HashMap<String, Notice>();
			// notices.put(AppConstants.PUSH_MESSAGE, new MessageNotice());
		}

	}

	public static void refreshIdAndOnOffMap(Activity mActivity) {
		if (idAndOnOffMap == null) {
			idAndOnOffMap = new HashMap<String, Boolean>();
		} else {
			idAndOnOffMap.clear();
		}
	}

	public static boolean checkPushConfig(Activity mActivity, String subject) {
		refreshIdAndOnOffMap(mActivity);
		// if (idAndOnOffMap.containsKey(AppConstants.PUSH_ON_OFF)
		// && idAndOnOffMap.get(AppConstants.PUSH_ON_OFF)) {
		// return idAndOnOffMap.containsKey(subject)
		// && idAndOnOffMap.get(subject);
		// }
		return false;
	}

	public static boolean checkAndSendNotification(Activity mActivity,
			String username, String subject, String body) {
		initNotices();
		if (!notices.containsKey(subject)) {
			return false;
		}
		if (checkPushConfig(mActivity, subject)) {
			notices.get(subject).sendNotification(username, body);
		}
		return true;
	}
}
