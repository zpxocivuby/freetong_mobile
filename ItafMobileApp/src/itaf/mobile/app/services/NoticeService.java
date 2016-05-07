package itaf.mobile.app.services;

import itaf.mobile.app.bean.NotificationConfig;
import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.ds.domain.SerializableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

/**
 * 通知服务
 * 
 * 
 * @author
 * 
 * @update 2013年12月13日
 */
public class NoticeService extends Service implements Runnable {

	private Context mContext;

	public boolean isrun = true;

	private static List<NotificationInfo> notificationInfos = new ArrayList<NotificationInfo>();

	private NotificationManager manager;

	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this.getApplicationContext();
		manager = (NotificationManager) mContext
				.getSystemService(Context.NOTIFICATION_SERVICE);
		new Thread(this).start();
	}

	public static void addNotification(NotificationInfo info) {
		notificationInfos.add(info);
	}

	public static void addTopNotification(NotificationInfo info) {
		notificationInfos.add(0, info);
	}

	@SuppressWarnings("deprecation")
	public void doSendNotification(NotificationInfo tagetInfo) {
		try {
			// 配置通知
			Notification notification = configNotification(tagetInfo
					.getNotificationConfig());
			Intent intent = new Intent();
			if (tagetInfo.getClazz() != null) {
				intent.setClass(mContext, tagetInfo.getClazz());
				intent.setFlags(Intent.FILL_IN_DATA);
				Map<String, String> params = tagetInfo.getParams();
				if (params != null && params.size() > 0) {
					for (String key : params.keySet()) {
						intent.putExtra(key, params.get(key));
					}
				}
				if (tagetInfo.getTarget() != null
						&& tagetInfo.getTarget().size() > 0) {
					// Bundle bundle = new Bundle();
					// bundle.putSerializable(
					// AppConstants.TRANSMIT_LIST,
					// new SerializableList<SessionUser>(tagetInfo
					// .getTarget()));
					// intent.putExtras(bundle);
				}
			}
			PendingIntent pi = PendingIntent.getActivity(mContext, 0, intent,
					PendingIntent.FLAG_UPDATE_CURRENT);
			notification.setLatestEventInfo(mContext, tagetInfo.getSubject(),
					tagetInfo.getBody(), pi);
			// manager.cancel(tagetInfo.getNotificationId());
			// 发送通知
			manager.notify(tagetInfo.getNotificationId(), notification);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "发送通知失败");
		}
	}

	public void doCancelById(int nid) {
		try {
			NotificationManager nm = (NotificationManager) mContext
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancel(nid);
			nm.notify();
		} catch (Exception e) {
			Log.i(this.getClass().getName(), "取消通知NID=" + nid + "失败");
		}
	}

	private void doCancelAll() {
		try {
			NotificationManager nm = (NotificationManager) mContext
					.getSystemService(Context.NOTIFICATION_SERVICE);
			nm.cancelAll();
			nm.notify();
		} catch (Exception e) {
			Log.i(this.getClass().getName(), "取消全部通知失败");
		}
	}

	private Notification configNotification(NotificationConfig config) {
		if (config == null) {
			config = new NotificationConfig();
		}
		Notification notification = new Notification();
		notification.icon = config.getIcon();
		notification.tickerText = config.getTickerText();
		notification.when = config.getWhen();

		int defaults = 0;
		if (config.isUseDefaultSound()) {
			defaults = defaults | Notification.DEFAULT_SOUND;
		} else {
			if (config.getSound() != null) {
				notification.sound = config.getSound();
			}
		}

		if (config.isUseDefaultVibrate()) {
			defaults = defaults | Notification.DEFAULT_VIBRATE;
		} else {
			if (config.getVibrate() != null) {
				notification.vibrate = config.getVibrate();
			}
		}

		if (config.isUseDefaultLights()) {
			defaults = defaults | Notification.DEFAULT_LIGHTS;
		} else {
			if (config.getLedARGB() != 0 && config.getLedOnMS() != 0
					&& config.getLedOffMS() != 0) {
				notification.ledARGB = config.getLedARGB();
				notification.ledOnMS = config.getLedOnMS();
				notification.ledOffMS = config.getLedOffMS();
			}
		}

		notification.defaults = defaults;

		int flags = 0;

		if (config.isUseFlagAutoCancel()) {
			flags = flags |= Notification.FLAG_AUTO_CANCEL;
		}

		if (config.isUseFlagOngoingEvent()) {
			flags = flags |= Notification.FLAG_ONGOING_EVENT;
		}

		if (config.isUseFlagNoClear()) {
			flags = flags |= Notification.FLAG_NO_CLEAR;
		}

		if (config.isUseFlagForegroundService()) {
			flags = flags |= Notification.FLAG_FOREGROUND_SERVICE;
		}

		if (config.isUseFlagInsistent()) {
			flags = flags |= Notification.FLAG_INSISTENT;
		}

		if (config.isUseFlagOnlyAlertOnce()) {
			flags = flags |= Notification.FLAG_ONLY_ALERT_ONCE;
		}

		if (config.isUseFlagShowLights()) {
			flags = flags |= Notification.FLAG_SHOW_LIGHTS;
			if (config.getLedARGB() != 0 && config.getLedOnMS() != 0
					&& config.getLedOffMS() != 0) {
				notification.ledARGB = config.getLedARGB();
				notification.ledOnMS = config.getLedOnMS();
				notification.ledOffMS = config.getLedOffMS();
			}
		}
		notification.flags = flags;
		return notification;
	}

	// 侦听任务
	public void run() {
		while (isrun) {
			if (notificationInfos.size() > 0) {
				NotificationInfo runNotification = notificationInfos.get(0);
				notificationInfos.remove(runNotification);
				if (runNotification != null) {
					switch (runNotification.getType()) {
					case NotificationInfo.TYPE_SEND_MSG:
						doSendNotification(runNotification);
						break;
					case NotificationInfo.TYPE_CANCEL_BY_ID:
						doCancelById(runNotification.getNotificationId());
						break;
					case NotificationInfo.TYPE_CANCEL_ALL:
						doCancelAll();
						break;
					}
				}
			}
			try {
				Thread.sleep(1000);
			} catch (Exception e) {
			}
		}
	}

	@Override
	public void onDestroy() {
		doCancelAll();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

}
