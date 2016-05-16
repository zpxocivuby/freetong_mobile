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

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 * 
 * @author xinxin
 * 
 */
public class XmppChatRoomMsgListenerManager implements PacketListener {

	private Map<String, XmppChatRoomMsgListener> listeners;

	private XmppChatRoomMsgListenerManager() {
		listeners = new HashMap<String, XmppChatRoomMsgListener>();
	}

	private static class SingletonHolder {
		static final XmppChatRoomMsgListenerManager INSTANCE = new XmppChatRoomMsgListenerManager();
	}

	public static XmppChatRoomMsgListenerManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void initialize() {
		// List<RoomInfo> joinedRooms = XmppChatRoomManager.getInstance()
		// .getJoinedRooms();
		// if (joinedRooms != null && joinedRooms.size() > 0) {
		// for (RoomInfo roomInfo : joinedRooms) {
		// MultiUserChat room = new MultiUserChat(XmppConnectionManager
		// .getInstance().getConnection(), roomInfo.getRoom());
		// room.addMessageListener(this);
		// }
		// }
	}

	public void addMessageListener(MultiUserChat room) {
		if (room != null) {
			room.addMessageListener(this);
		}
	}

	public void removeMessageListener(MultiUserChat room) {
		if (room != null) {
			room.removeMessageListener(this);
		}
	}

	public void registerListener(XmppChatRoomMsgListener listener) {
		listeners.put(listener.getClass().getName(), listener);
	}

	public void processPacket(Packet packet) {
		Message message = (Message) packet;
		if (listeners != null && listeners.size() > 0) {
			for (XmppChatRoomMsgListener listener : listeners.values()) {
				listener.refreshChatRoomMsg(message);
			}
		}

	}

}
