package itaf.mobile.app.notification;

import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.app.im.ui.ImChatFriends;
import itaf.mobile.app.services.NoticeService;

public class MessageNotice implements Notice {

	public void sendNotification(String username, String body) {
		NotificationInfo info = new NotificationInfo();
		info.setSubject("消息信息");
		info.setBody(body);
		info.setClazz(ImChatFriends.class);
		info.setNotificationId(NoticeId.NID_PUSH_MESSAGE);
		NoticeService.addNotification(info);
	}
}
