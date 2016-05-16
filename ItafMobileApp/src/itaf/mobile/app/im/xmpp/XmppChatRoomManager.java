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
import itaf.mobile.app.im.ui.ImAddChatRoomDialog;
import itaf.mobile.app.im.ui.ImChatRoomWindow;
import itaf.mobile.app.im.ui.MenuIm;
import itaf.mobile.app.notification.NoticeId;
import itaf.mobile.app.services.NoticeService;
import itaf.mobile.app.util.AppHelper;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.utils.StringHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jivesoftware.smack.SmackException;
import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.InvitationListener;
import org.jivesoftware.smackx.muc.InvitationRejectionListener;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.muc.RoomInfo;
import org.jivesoftware.smackx.muc.SubjectUpdatedListener;
import org.jivesoftware.smackx.xdata.Form;
import org.jivesoftware.smackx.xdata.FormField;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * 聊天室管理
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class XmppChatRoomManager {

	private XmppChatRoomManager() {
	}

	private static class SingletonHolder {
		static final XmppChatRoomManager INSTANCE = new XmppChatRoomManager();
	}

	public static XmppChatRoomManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	/**
	 * 监听邀请加入聊天室请求
	 */
	public void addOnlineInvitationListener(final Context mContext) {
		MultiUserChat.addInvitationListener(XmppConnectionManager.getInstance()
				.getConnection(), new InvitationListener() {
			@Override
			public void invitationReceived(XMPPConnection conn, String room,
					String inviter, String reason, String password,
					Message message) {
				try {
					HistoryChatRecordDb historyDb = new HistoryChatRecordDb(
							mContext);
					HistoryChatRecord record = new HistoryChatRecord();
					record.setChatType(HistoryChatRecord.CHAT_TYPE_INVITATION);
					record.setJid(ImHelper.INVITATION + room);
					record.setNickname(inviter);
					record.setHeadUrl(null);
					record.setChatTime(new Date());
					record.setChatContent(inviter + reason);
					record.setUsername(AppApplication.getInstance()
							.getSessionUser().getUsername());
					historyDb.save(record);
					LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(
							mContext);
					LastestChatObjcet chatObj = new LastestChatObjcet();
					chatObj.setUsername(AppApplication.getInstance()
							.getSessionUser().getUsername());
					chatObj.setHeadUrl(null);
					chatObj.setJid(ImHelper.INVITATION + room);
					chatObj.setNickname(inviter);
					chatObj.setLastestChatDateTime(new Date());
					chatObj.setType(LastestChatObjcet.CHAT_ROOM_INVITATION);
					chatObjDb.saveOrUpdate(chatObj);

					Activity activity = AppActivityManager.getInstance()
							.getCurrActivity();
					// 如果当前窗口为IM主窗口，返回
					if (activity instanceof MenuIm
							&& AppHelper.isAppOnForeground(mContext)) {
						MenuIm menuIm = (MenuIm) activity;
						menuIm.refreshCallBack();
						return;
					}
					Map<String, String> params = new HashMap<String, String>();
					params.put(ImHelper.IM_ROOMJID, ImHelper.INVITATION + room);
					params.put(ImHelper.IM_INVITER, inviter);
					params.put(ImHelper.IM_REASON, reason);
					NotificationInfo info = new NotificationInfo();
					info.setSubject("聊天室邀请");
					info.setBody(reason);
					info.setType(NotificationInfo.TYPE_SEND_MSG);
					info.setClazz(ImAddChatRoomDialog.class);
					info.setNotificationId(NoticeId.NID_IM_MSG);
					info.setParams(params);
					NoticeService.addNotification(info);
				} catch (Exception e) {
					Log.e(this.getClass().getName(), "监听" + room + "聊天室邀请异常:"
							+ e.getMessage());
				}
			}

		});
	}

	/**
	 * 接收邀请
	 * 
	 * @param mContext
	 * @param roomJid
	 *            房间JID
	 */
	public void grantInvitation(Context mContext, String roomJid) {
		try {
			Intent intent = new Intent();
			intent.putExtra(ImChatRoomWindow.ROOM_JID, roomJid);
			intent.setClass(mContext, ImChatRoomWindow.class);
			mContext.startActivity(intent);
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"加入" + roomJid + "聊天室异常:" + e.getMessage());
		}
	}

	/**
	 * 拒绝邀请
	 * 
	 * @param roomJid
	 *            房间JID
	 * @param inviter
	 *            邀请人
	 * @param reason
	 *            拒绝的原因
	 */
	public void revokeInvitation(String roomJid, String inviter, String reason) {
		try {
			MultiUserChat.decline(XmppConnectionManager.getInstance()
					.getConnection(), roomJid, inviter, reason);
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"拒绝加入" + roomJid + "聊天室异常:" + e.getMessage());
		}
	}

	/**
	 * 离开room
	 * 
	 * @param roomJid
	 *            房间JID
	 */
	public void leaveRoom(String roomJid) {
		try {
			MultiUserChat muc = getMultiUserChat(roomJid);
			XmppChatRoomMsgListenerManager.getInstance().removeMessageListener(
					muc);
			muc.leave();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "离开room出错误:" + e.getMessage());
		}
	}

	public MultiUserChat getMultiUserChat(String roomJid) {
		MultiUserChat muc = null;
		if (!XmppConnectionManager.getInstance().isLogin()) {
			return muc;
		}
		if (StringHelper.isNotEmpty(roomJid)) {
			muc = new MultiUserChat(XmppConnectionManager.getInstance()
					.getConnection(), roomJid);
		}
		return muc;
	}

	public String getSubject(String roomJid) {
		String subject = "";
		if (!XmppConnectionManager.getInstance().isLogin()) {
			return subject;
		}
		try {
			RoomInfo roomInfo = MultiUserChat.getRoomInfo(XmppConnectionManager
					.getInstance().getConnection(), roomJid);
			subject = roomInfo == null ? "" : roomInfo.getSubject();
		} catch (XMPPException e) {
			Log.e(this.getClass().getName(), "roomInfo:" + e.getMessage());
		} catch (NoResponseException e) {

			e.printStackTrace();
		} catch (NotConnectedException e) {

			e.printStackTrace();
		}
		return subject;
	}

	public void addListenerForMuc(MultiUserChat muc) {
		try {
			if (!muc.isJoined()) {
				muc.join(ImHelper.getCurrRoomNickname());
			}
		} catch (XMPPException e) {
			e.printStackTrace();
		} catch (NoResponseException e) {

			e.printStackTrace();
		} catch (NotConnectedException e) {

			e.printStackTrace();
		}
		// 添加消息监听器
		XmppChatRoomMsgListenerManager.getInstance().addMessageListener(muc);
		// 改变主题监听器
		muc.addSubjectUpdatedListener(new SubjectUpdatedListener() {
			public void subjectUpdated(String subject, String from) {
				String roomjid = from;
				if (from.indexOf("/") != -1) {
					roomjid = from.substring(0, from.indexOf("/"));
				}
				Activity activity = AppActivityManager.getInstance()
						.getCurrActivity();
				if (activity instanceof ImChatRoomWindow) {
					ImChatRoomWindow chatRoomWindow = (ImChatRoomWindow) activity;
					if (chatRoomWindow.isContains(roomjid)) {
						chatRoomWindow.changeRoomSubject(subject);
					}
				}
			}
		});
		// 监听拒绝加入聊天室的用户
		muc.addInvitationRejectionListener(new InvitationRejectionListener() {
			public void invitationDeclined(String invitee, String reason) {
				Log.e(this.getClass().getName(), invitee + "拒绝加入：" + reason);
			}
		});

	}

	public MultiUserChat createMultiUserChat() {
		MultiUserChat muc = null;
		if (!XmppConnectionManager.getInstance().isLogin()) {
			return muc;
		}
		try {
			SessionUser sessionUser = AppApplication.getInstance()
					.getSessionUser();
			String roomJid = ImHelper.getRoomJid(sessionUser.getUsername());
			// 使用XMPPConnection创建一个MultiUserChat
			XMPPConnection connection = XmppConnectionManager.getInstance()
					.getConnection();
			muc = new MultiUserChat(connection, roomJid);
			// 创建聊天室
			muc.create(roomJid);
			// 获得聊天室的配置表单
			Form form = muc.getConfigurationForm();
			// 根据原始表单创建一个要提交的新表单。
			Form submitForm = form.createAnswerForm();
			// 向要提交的表单添加默认答复
			for (FormField field : form.getFields()) {
				if (!FormField.TYPE_HIDDEN.equals(field.getType())
						&& field.getVariable() != null) {
					// 设置默认值作为答复
					submitForm.setDefaultAnswer(field.getVariable());
				}
			}
			// 设置聊天室的新拥有者
			// List owners = new ArrayList();
			// owners.add("admin\\40cctv.com");
			// submitForm.setAnswer("muc#roomconfig_roomowners", owners);
			// 重新设置聊天室名称
			// submitForm.setAnswer("muc#roomconfig_roomname", subject);
			// 设置聊天室是持久聊天室，即将要被保存下来
			submitForm.setAnswer("muc#roomconfig_persistentroom", true);
			// 房间仅对成员开放
			submitForm.setAnswer("muc#roomconfig_membersonly", false);
			// 允许占有者邀请其他人
			submitForm.setAnswer("muc#roomconfig_allowinvites", true);
			// 能够发现占有者真实 JID 的角色
			// submitForm.setAnswer("muc#roomconfig_whois", "anyone");
			// 登录房间对话
			submitForm.setAnswer("muc#roomconfig_enablelogging", true);
			// 仅允许注册的昵称登录
			submitForm.setAnswer("x-muc#roomconfig_reservednick", true);
			// 允许使用者修改昵称
			submitForm.setAnswer("x-muc#roomconfig_canchangenick", false);
			// 允许用户注册房间
			submitForm.setAnswer("x-muc#roomconfig_registration", false);
			// 发送已完成的表单（有默认值）到服务器来配置聊天室
			muc.sendConfigurationForm(submitForm);
			muc.join(ImHelper.getCurrRoomNickname());

			addListenerForMuc(muc);

		} catch (XMPPException e) {
			Log.e(this.getClass().getName(), "聊天室创建异常：" + e.getMessage());
			muc = null;
		} catch (NoResponseException e) {

			e.printStackTrace();
		} catch (SmackException e) {

			e.printStackTrace();
		}
		return muc;
	}

}
