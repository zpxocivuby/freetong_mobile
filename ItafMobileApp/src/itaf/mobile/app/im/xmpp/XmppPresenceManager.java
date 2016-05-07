package itaf.mobile.app.im.xmpp;

import itaf.mobile.core.app.AppApplication;

import java.util.Collection;

import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.Presence.Mode;
import org.jivesoftware.smack.util.StringUtils;

import android.util.Log;

/**
 * IM人员状态管理
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class XmppPresenceManager {

	private XMPPConnection connection = null;

	public static final String TYPE_ON_LINE = "在线";
	public static final String TYPE_ON_LINE_CHAT = "空闲";
	public static final String TYPE_ON_LINE_DND = "忙碌";
	public static final String TYPE_ON_LINE_AWAY = "离开";

	public static final String TYPE_OFF_HIDDEN_LINE = "隐身";
	public static final String TYPE_OFF_LINE = "离线";

	private XmppPresenceManager() {
		connection = XmppConnectionManager.getInstance().getConnection();
	}

	private static class SingletonHolder {
		static final XmppPresenceManager INSTANCE = new XmppPresenceManager();
	}

	public static XmppPresenceManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public void setAvailable() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = new Presence(Presence.Type.available);
			AppApplication.getInstance().getSessionUser().setImStatus("在线");
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置在线");
		}
	}

	public void setAvailableChat() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = new Presence(Presence.Type.available);
			AppApplication.getInstance().getSessionUser().setImStatus("空闲");
			presence.setMode(Presence.Mode.chat);
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置Q我吧");
		}
	}

	public void setAvailableDnd() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = new Presence(Presence.Type.available);
			AppApplication.getInstance().getSessionUser().setImStatus("忙碌");
			presence.setMode(Presence.Mode.dnd);
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置忙碌");
		}
	}

	public void setAvailableAway() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = new Presence(Presence.Type.available);
			AppApplication.getInstance().getSessionUser().setImStatus("离开");
			presence.setMode(Presence.Mode.away);
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置离开");
		}
	}

	public void setUnavailableHidden() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = null;
			Roster roster = connection.getRoster();
			Collection<RosterEntry> entries = roster.getEntries();
			for (RosterEntry entry : entries) {
				presence = new Presence(Presence.Type.unavailable);
				presence.setPacketID(Packet.ID_NOT_AVAILABLE);
				presence.setFrom(connection.getUser());
				presence.setTo(entry.getUser());
				try {
					connection.sendPacket(presence);
				} catch (NotConnectedException e) {

					e.printStackTrace();
				}
			}
			// 向同一用户的其他客户端发送隐身状态
			presence = new Presence(Presence.Type.unavailable);
			AppApplication.getInstance().getSessionUser().setImStatus("隐身");
			presence.setPacketID(Packet.ID_NOT_AVAILABLE);
			presence.setFrom(connection.getUser());
			presence.setTo(StringUtils.parseBareAddress(connection.getUser()));
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置隐身");
		}
	}

	public void setUnavailable() {
		if (XmppConnectionManager.getInstance().isLogin()) {
			Presence presence = new Presence(Presence.Type.unavailable);
			AppApplication.getInstance().getSessionUser().setImStatus("离线");
			try {
				connection.sendPacket(presence);
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
			Log.v("state", "设置离线");
		}
	}

	private String processPresenceType(Presence presence) {
		String result = TYPE_OFF_LINE;
		if (presence == null) {
			return result;
		}
		if (Presence.Type.available.equals(presence.getType())) {
			Mode mode = presence.getMode();
			if (mode == null) {
				return TYPE_ON_LINE;
			}
			if (Presence.Mode.chat.equals(mode)) {
				result = TYPE_ON_LINE_CHAT;
			} else if (Presence.Mode.dnd.equals(mode)) {
				result = TYPE_ON_LINE_DND;
			} else if (Presence.Mode.away.equals(mode)) {
				result = TYPE_ON_LINE_AWAY;
			}
		}
		return result;
	}

	public String getPresenceType(String jid) {
		Presence presence = XmppRosterManager.getInstance().getPresence(jid);
		return processPresenceType(presence);
	}

}
