package itaf.mobile.app.im.xmpp;

import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

/**
 * 消息管理
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class XmppChatMsgListenerManager implements ChatManagerListener {

	private ChatManager manager = null;

	private Map<String, XmppChatMsgListener> listeners;

	private XmppChatMsgListenerManager() {
		listeners = new HashMap<String, XmppChatMsgListener>();
	}

	private static class SingletonHolder {
		static final XmppChatMsgListenerManager INSTANCE = new XmppChatMsgListenerManager();
	}

	public static XmppChatMsgListenerManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void initialize() {
		manager = XmppConnectionManager.getInstance().getChatManager();
		if (manager != null) {
			manager.addChatListener(this);
		}
	}

	public void registerListener(XmppChatMsgListener listener) {
		listeners.put(listener.getClass().getName(), listener);

	}

	public void chatCreated(Chat chat, boolean arg1) {
		chat.addMessageListener(new MessageListener() {
			public void processMessage(Chat newchat, Message message) {
				if (listeners != null && listeners.size() > 0) {
					// 在activity中 可以作为view中的回调接口
					for (XmppChatMsgListener listener : listeners.values()) {
						listener.refreshChatMsg(newchat, message);
					}
				}
			}

		});
	}

}
