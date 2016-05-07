package itaf.mobile.app.bean;

import itaf.mobile.core.bean.SessionUser;

import java.util.List;
import java.util.Map;

import android.content.Intent;

/**
 * 通知信息
 * 
 * 
 * @author
 * 
 * @update 2013年12月13日
 */
public class NotificationInfo {

	// -1为取消全部通知
	public static final int TYPE_CANCEL_ALL = -1;
	// 0根据id取消通知
	public static final int TYPE_CANCEL_BY_ID = 0;
	// 1为发送通知,默认值
	public static final int TYPE_SEND_MSG = 1;

	private String subject;
	private String body;
	private Class<?> clazz;
	private int notificationId;
	private Map<String, String> params;
	private Intent intent;
	private List<SessionUser> target;
	private NotificationConfig notificationConfig;
	// -1为取消全部通知，0根据id取消通知，1为发送通知
	private int type = TYPE_SEND_MSG;

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}

	public int getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(int notificationId) {
		this.notificationId = notificationId;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public List<SessionUser> getTarget() {
		return target;
	}

	public void setTarget(List<SessionUser> target) {
		this.target = target;
	}

	public NotificationConfig getNotificationConfig() {
		return notificationConfig;
	}

	public void setNotificationConfig(NotificationConfig notificationConfig) {
		this.notificationConfig = notificationConfig;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
