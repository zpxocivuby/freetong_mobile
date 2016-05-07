package itaf.mobile.app.im.xmpp;

import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.utils.StringHelper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

/**
 * 聊天管理
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class XmppChatManager {

	private XmppChatManager() {
	}

	private static class SingletonHolder {
		static final XmppChatManager INSTANCE = new XmppChatManager();
	}

	public static XmppChatManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public boolean sendMsg(String from, String to, String subject, String body) {
		boolean result = false;
		if (!to.contains("@")) {
			to = ImHelper.processToJid(to);
		}
		try {
			Chat currChat = this.createChat(to);
			if (currChat != null) {
				Message msg = new Message();
				msg.setFrom(from);
				msg.setTo(to);
				msg.setSubject(subject);
				msg.setBody(body);
				currChat.sendMessage(msg);
				result = true;
			}
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"发送给" + ImHelper.processToUsername(to) + "消息失败！");
		}
		return result;
	}

	public Chat createChat(String jid) {
		Chat newChat = null;
		if (StringHelper.isEmpty(jid)) {
			return newChat;
		}
		try {
			ChatManager chatManager = XmppConnectionManager.getInstance()
					.getChatManager();
			if (chatManager == null) {
				return newChat;
			}
			chatManager.addChatListener(new ChatManagerListener() {
				public void chatCreated(Chat chat, boolean arg1) {
					chat.addMessageListener(null);
				}
			});
			newChat = chatManager.createChat(jid, null);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "创建聊天窗口失败！");
		}
		return newChat;
	}
}
