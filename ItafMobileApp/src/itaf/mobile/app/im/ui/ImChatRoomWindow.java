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
/**
 * 
 */
package itaf.mobile.app.im.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.ui.adapter.ImChatRecordAdapter;
import itaf.mobile.app.im.xmpp.XmppChatRoomManager;
import itaf.mobile.app.im.xmpp.XmppChatRoomMsgListener;
import itaf.mobile.app.im.xmpp.XmppChatRoomMsgListenerManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.app.ui.AppMain;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.SmackException.NoResponseException;
import org.jivesoftware.smack.SmackException.NotConnectedException;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 聊天室窗口
 * 
 * @author
 * 
 */
public class ImChatRoomWindow extends BaseImActivity implements
		XmppChatRoomMsgListener {

	public static final String ROOM_JID = "roomjid";

	public static final String ROOM_MEMBER_JIDS = "roomMemberJids";

	public static final String REASON = "邀请你加入聊天室！";

	private Button btn_icrw_back;
	private Button btn_icrw_detail;
	private Button btn_icrw_select_type;
	private Button btn_icrw_send_msg;

	private EditText et_icrw_msg_send_text;
	private TextView tv_icrw_friend_username;

	private ListView lv_icrw_chat_record;

	private List<HistoryChatRecord> historyChatRecords = new ArrayList<HistoryChatRecord>();
	private String newMemberJids;

	private String roomJid;
	private String roomSubject = "";
	private MultiUserChat muc;
	private ImChatRecordAdapter chatRecordAdapter;

	private HistoryChatRecordDb historyDb = new HistoryChatRecordDb(this);
	private LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(this);

	public boolean isContains(String from) {
		return from.contains(roomJid);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat_room_window);
		XmppChatRoomMsgListenerManager.getInstance().registerListener(this);

		Bundle bundle = this.getIntent().getExtras();
		roomJid = bundle.getString(ROOM_JID);
		newMemberJids = bundle.getString(ROOM_MEMBER_JIDS);
		if (StringHelper.isEmpty(roomJid)) {
			muc = XmppChatRoomManager.getInstance().createMultiUserChat();
			if (muc == null) {
				ToastHelper.showToast(this, "创建聊天室窗口失败，查看是否IM为登陆状态！");
				AppActivityManager.getInstance().finishActivity(this);
				return;
			}
			String[] roomArr = newMemberJids.split(",");
			for (String jid : roomArr) {
				if (StringHelper.isNotEmpty(jid)) {
					try {
						muc.invite(jid, REASON);
					} catch (NotConnectedException e) {

						e.printStackTrace();
					}
				}
			}
			roomSubject += createSubject(roomArr);
			// 改变主题
			try {
				muc.changeSubject(roomSubject);
			} catch (XMPPException e) {
				e.printStackTrace();
			} catch (NoResponseException e) {

				e.printStackTrace();
			} catch (NotConnectedException e) {

				e.printStackTrace();
			}
		} else {
			muc = XmppChatRoomManager.getInstance().getMultiUserChat(roomJid);
			if (muc == null) {
				ToastHelper.showToast(this, "创建聊天室窗口失败，查看是否IM为登陆状态！");
				AppActivityManager.getInstance().finishActivity(this);
				return;
			}
			XmppChatRoomManager.getInstance().addListenerForMuc(muc);
			roomSubject = XmppChatRoomManager.getInstance().getSubject(roomJid);
		}
		roomJid = muc.getRoom();
		saveLastestChatObjcet(roomJid, roomSubject);
		initPageAttribute();
		showOfflineChatRecord();
	}

	private String createSubject(String[] roomArr) {
		String subject = "";
		if (roomArr == null || roomArr.length <= 0) {
			return subject;
		}
		for (int i = 0; i < roomArr.length; i++) {
			if (i == 2) {
				subject += "...";
				break;
			}
			if (i == 1) {
				subject += "、";
			}
			subject += XmppVCardManager.getInstance().getNickname(roomArr[i]);

		}
		return subject;
	}

	public void changeRoomSubject(String subject) {
		roomSubject = subject;
		saveLastestChatObjcet(roomJid, roomSubject);
		handler.sendEmptyMessage(2);
	}

	private void showOfflineChatRecord() {
		HistoryChatRecordDb db = new HistoryChatRecordDb(this);
		List<HistoryChatRecord> records = db.findOfflineByJid(roomJid,
				AppApplication.getInstance().getSessionUser().getUsername());
		if (records != null && records.size() > 0) {
			db.updateChatTypeByJid(roomJid, AppApplication.getInstance()
					.getSessionUser().getUsername());
			historyChatRecords.addAll(records);
			chatRecordAdapter.notifyDataSetChanged();
			lv_icrw_chat_record.setSelection(historyChatRecords.size());
		}
	}

	public void refreshChatRoomMsg(Message message) {
		String from = message.getFrom();
		if (from != null && from.indexOf("/") != -1) {
			from = from.substring(from.indexOf("/") + 1);
			if (!from.contains("("
					+ AppApplication.getInstance().getSessionUser()
							.getUsername() + ")")) {
				String[] fromAndBody = { from, message.getBody() };
				android.os.Message msg = handler.obtainMessage();
				msg.what = 1;
				msg.obj = fromAndBody;
				msg.sendToTarget();
			}
		}
	}

	@SuppressLint("HandlerLeak")
	public Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String[] fromAndBody = (String[]) msg.obj;
				HistoryChatRecord record = new HistoryChatRecord();
				record.setChatType(HistoryChatRecord.CHAT_TYPE_TO);
				record.setNickname(fromAndBody[0]);
				record.setChatContent(fromAndBody[1]);
				record.setChatTime(new Date());
				record.setJid(roomJid);
				record.setUsername(AppApplication.getInstance()
						.getSessionUser().getUsername());
				historyChatRecords.add(record);
				chatRecordAdapter.notifyDataSetChanged();
				lv_icrw_chat_record.setSelection(historyChatRecords.size());
				historyDb.save(record);
				break;
			case 2:
				tv_icrw_friend_username.setText(roomSubject);
				break;
			}
		}
	};

	private void initPageAttribute() {
		// 返回按钮
		btn_icrw_back = (Button) findViewById(R.id.btn_icrw_back);
		btn_icrw_back.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				backToMenuIm();
			}
		});

		// 设置标题
		tv_icrw_friend_username = (TextView) findViewById(R.id.tv_icrw_friend_username);
		tv_icrw_friend_username.setText(roomSubject);

		// 消息发送文本
		et_icrw_msg_send_text = (EditText) findViewById(R.id.et_icrw_msg_send_text);

		// 聊天记录
		lv_icrw_chat_record = (ListView) findViewById(R.id.lv_icrw_chat_record);
		chatRecordAdapter = new ImChatRecordAdapter(this, historyChatRecords);
		lv_icrw_chat_record.setAdapter(chatRecordAdapter);

		// 查看好友详情
		btn_icrw_detail = (Button) findViewById(R.id.btn_icrw_detail);
		btn_icrw_detail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				List<String> occupants = muc.getOccupants();
				String roomMembers = "";
				if (occupants != null) {
					for (String occupant : occupants) {
						if (occupant.contains("(")) {
							roomMembers += ImHelper.processToJid(occupant
									.substring(occupant.lastIndexOf("(") + 1,
											occupant.lastIndexOf(")")))
									+ ",";
						}
					}
				}
				intent.putExtra(ImChatDetail.JID_OR_ROOMJID, roomJid);
				intent.putExtra(ImChatDetail.ROOM_MEMBERS, roomMembers);
				intent.setClass(ImChatRoomWindow.this, ImChatDetail.class);
				startActivity(intent);
			}
		});

		// 选择类型
		btn_icrw_select_type = (Button) findViewById(R.id.btn_icrw_select_type);
		btn_icrw_select_type.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO选择发送类型 图片，文件，表情
			}
		});

		// 发送消息
		btn_icrw_send_msg = (Button) findViewById(R.id.btn_icrw_send_msg);
		btn_icrw_send_msg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String chatContent = getTextViewToString(et_icrw_msg_send_text);
				if (chatContent.length() > 0) {
					SessionUser sessionUser = AppApplication.getInstance()
							.getSessionUser();
					HistoryChatRecord record = new HistoryChatRecord();
					record.setChatType(HistoryChatRecord.CHAT_TYPE_FROM);
					record.setNickname(ImHelper.generationNickname(sessionUser));
					// record.setHeadUrl(sessionUser.getHeadUrl());
					record.setChatTime(new Date());
					record.setChatContent(chatContent);
					record.setJid(roomJid);
					record.setUsername(sessionUser.getUsername());
					historyChatRecords.add(record);
					chatRecordAdapter.notifyDataSetChanged();
					lv_icrw_chat_record.setSelection(historyChatRecords.size());
					try {
						muc.sendMessage(createMessage(chatContent));
						historyDb.save(record);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				et_icrw_msg_send_text.setText("");
			}
		});

	}

	private Message createMessage(String body) {
		Message message = new Message(roomJid, Message.Type.groupchat);
		message.setBody(body);
		return message;
	}

	private void saveLastestChatObjcet(String roomJid, String subject) {
		LastestChatObjcet chatObj = new LastestChatObjcet();
		String currUserName = AppApplication.getInstance().getSessionUser()
				.getUsername();
		chatObj.setUsername(currUserName);
		chatObj.setHeadUrl(null);
		chatObj.setNickname(subject);
		chatObj.setJid(roomJid);
		chatObj.setLastestChatDateTime(new Date());
		chatObj.setStatus(LastestChatObjcet.CHAT_ROOM_STATUS);
		chatObj.setType(LastestChatObjcet.CHAT_ROOM);
		chatObjDb.saveOrUpdate(chatObj);
	}

	@Override
	protected void onDestroy() {
		// muc.leave();
		super.onDestroy();
	}

	private void backToMenuIm() {
		startActivity(new Intent(this, AppMain.class));
		AppActivityManager.getInstance().finishActivity(this);
		((AppMain) AppActivityManager.getInstance().getActivity(
				AppMain.class.getName()))
				.setRadioGroupChecked(R.id.radio_menu_im);
	}

	/** 重写父类方法，返回弹出是否退出应用对话框 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			backToMenuIm();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
