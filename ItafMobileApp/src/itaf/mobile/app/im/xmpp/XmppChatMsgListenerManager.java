/**
 * Copyright 2015 Freetong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
