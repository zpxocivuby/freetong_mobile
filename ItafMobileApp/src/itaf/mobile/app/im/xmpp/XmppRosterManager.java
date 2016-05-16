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

import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.ui.ImAddFriendDialog;
import itaf.mobile.app.im.ui.ImChatFriends;
import itaf.mobile.app.im.ui.MenuIm;
import itaf.mobile.app.notification.NoticeId;
import itaf.mobile.app.services.NoticeService;
import itaf.mobile.app.util.AppHelper;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.RosterListener;
import org.jivesoftware.smack.packet.Presence;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

/**
 * 好友管理
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class XmppRosterManager {

	private XmppRosterManager() {
	}

	private static class SingletonHolder {
		static final XmppRosterManager INSTANCE = new XmppRosterManager();
	}

	public static XmppRosterManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public List<RosterEntry> getMyFriends() {
		if (XmppConnectionManager.getInstance().getRoster() == null) {
			return null;
		}
		Collection<RosterEntry> entries = XmppConnectionManager.getInstance()
				.getRoster().getEntries();
		if (entries == null || entries.size() <= 0) {
			return null;
		}
		List<RosterEntry> result = new ArrayList<RosterEntry>();
		for (RosterEntry rosterEntry : entries) {
			String jid = rosterEntry.getUser();
			RosterEntry temp = this.getRosterEntry(jid);
			result.add(temp);
		}
		return result;
	}

	public Presence getPresence(String jid) {
		Presence presence = null;
		if (XmppConnectionManager.getInstance().getRoster() == null) {
			return presence;
		}
		try {
			presence = XmppConnectionManager.getInstance().getRoster()
					.getPresence(jid);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "getPresence():" + e.getMessage());
		}
		return presence;
	}

	/**
	 * 添加好友 无分组
	 * 
	 * @param roster
	 * @param userName
	 * @param name
	 * @return
	 */
	public boolean addFriend(String jid, String nickName) {
		if (XmppConnectionManager.getInstance().getRoster() == null) {
			return false;
		}
		try {
			XmppConnectionManager.getInstance().getRoster()
					.createEntry(jid, nickName, null);
			return true;
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "addFriend():" + e.getMessage());
			return false;
		}
	}

	public boolean removeUser(String jid) {
		if (XmppConnectionManager.getInstance().getRoster() == null) {
			return false;
		}
		try {
			XmppConnectionManager.getInstance().getRoster()
					.removeEntry(getRosterEntry(jid));
			return true;
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "removeUser():" + e.getMessage());
			return false;
		}
	}

	public boolean isFriend(String jid) {
		RosterEntry entry = getRosterEntry(jid);
		return entry != null && ItemType.both.equals(entry.getType());
	}

	public RosterEntry getRosterEntry(String jid) {
		RosterEntry entry = null;
		if (XmppConnectionManager.getInstance().getRoster() == null) {
			return entry;
		}
		try {
			entry = XmppConnectionManager.getInstance().getRoster()
					.getEntry(jid);
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"getRosterEntry():" + e.getMessage());
		}
		return entry;
	}

	private void sendAddFriendNotification(Context mContext, String from) {
		String chatContent = ImHelper.processToUsername(from) + "("
				+ XmppVCardManager.getInstance().getNickname(from) + ")"
				+ "请求添加您为好友？";
		HistoryChatRecordDb historyDb = new HistoryChatRecordDb(mContext);
		HistoryChatRecord record = new HistoryChatRecord();
		record.setChatType(HistoryChatRecord.CHAT_TYPE_INVITATION);
		record.setJid(ImHelper.INVITATION + from);
		record.setNickname(XmppVCardManager.getInstance().getNickname(from));
		record.setHeadUrl(null);
		record.setChatTime(new Date());
		record.setChatContent(chatContent);
		record.setUsername(AppApplication.getInstance().getSessionUser()
				.getUsername());
		historyDb.save(record);
		LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(mContext);
		LastestChatObjcet chatObj = new LastestChatObjcet();
		chatObj.setUsername(AppApplication.getInstance().getSessionUser()
				.getUsername());
		chatObj.setHeadUrl(null);
		chatObj.setJid(ImHelper.INVITATION + from);
		chatObj.setNickname(XmppVCardManager.getInstance().getNickname(from));
		chatObj.setLastestChatDateTime(new Date());
		chatObj.setType(LastestChatObjcet.CHAT_INVITATION);
		chatObjDb.saveOrUpdate(chatObj);

		Activity activity = AppActivityManager.getInstance().getCurrActivity();
		// 如果当前窗口为IM主窗口，返回
		if (activity instanceof MenuIm && AppHelper.isAppOnForeground(mContext)) {
			MenuIm menuIm = (MenuIm) activity;
			menuIm.refreshCallBack();
			return;
		}

		Map<String, String> params = new HashMap<String, String>();
		params.put(ImHelper.IM_IMUSERNAME, ImHelper.INVITATION + from);
		NotificationInfo info = new NotificationInfo();
		info.setType(NotificationInfo.TYPE_SEND_MSG);
		info.setSubject("好友验证消息");
		info.setBody(chatContent);
		info.setClazz(ImAddFriendDialog.class);
		info.setNotificationId(NoticeId.NID_IM_MSG);
		info.setParams(params);
		NoticeService.addNotification(info);
	}

	public void addOnlineRosterListener(final Context mContext) {
		// 监听好友申请消息
		XmppConnectionManager.getInstance().getRoster()
				.addRosterListener(new RosterListener() {
					public void entriesAdded(Collection<String> addresses) {
						for (String from : addresses) {
							RosterEntry rosterEntry = getRosterEntry(from);
							if (rosterEntry != null
									&& ItemType.from.equals(rosterEntry
											.getType())) {
								sendAddFriendNotification(mContext, from);
							}

						}
					}

					// 监听好友同意添加消息
					public void entriesUpdated(Collection<String> addresses) {
						for (String from : addresses) {
							RosterEntry rosterEntry = getRosterEntry(from);
							if (rosterEntry != null
									&& ItemType.both.equals(rosterEntry
											.getType())) {
								// 如果有好友页面，进行刷新
								Activity activity = AppActivityManager
										.getInstance().getCurrActivity();
								if (activity instanceof ImChatFriends) {
									ImChatFriends chatFriends = (ImChatFriends) activity;
									chatFriends.refreshCallBack();
								}
							}
						}

					}

					// 监听好友删除消息,需要删除历史聊天记录和历史聊天对象
					public void entriesDeleted(Collection<String> addresses) {
						try {
							Activity activity = AppActivityManager
									.getInstance().getCurrActivity();
							if (activity instanceof MenuIm) {
								MenuIm menuIm = (MenuIm) activity;
								menuIm.refreshCallBack();
							}
						} catch (Exception e) {
							Log.e(this.getClass().getName(), e.getMessage());
						}
					}

					// 监听好友状态改变消息
					public void presenceChanged(Presence presence) {
						Activity activity = AppActivityManager.getInstance()
								.getCurrActivity();
						if (activity instanceof ImChatFriends) {
							ImChatFriends chatFriends = (ImChatFriends) activity;
							chatFriends.refreshCallBack();
						}
					}
				});
	}

}
