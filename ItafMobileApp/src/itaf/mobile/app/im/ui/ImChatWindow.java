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
package itaf.mobile.app.im.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.ui.adapter.ImChatRecordAdapter;
import itaf.mobile.app.im.xmpp.XmppChatManager;
import itaf.mobile.app.im.xmpp.XmppChatMsgListener;
import itaf.mobile.app.im.xmpp.XmppChatMsgListenerManager;
import itaf.mobile.app.im.xmpp.XmppRosterManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.app.util.AlertDialogHelper;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smackx.vcardtemp.packet.VCard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 好友聊天窗口
 * 
 * @author
 * 
 * @update 2013年10月17日
 */
public class ImChatWindow extends BaseImActivity implements XmppChatMsgListener {

	public static final String CHAT_JID = "chatjid";

	public final String SERVICE_FILE_LISTENER = "itaf.mobile.app.services.FileListenerService";

	private Button btn_icw_back;
	private Button btn_icw_detail;
	private Button btn_icw_select_type;
	private Button btn_icw_send_msg;

	private EditText et_icw_msg_send_text;

	private ListView lv_icw_chat_record;

	private TextView tv_icw_friend_username;

	private Chat currChat;

	private ImChatRecordAdapter chatRecordAdapter;

	private List<HistoryChatRecord> historyChatRecords = new ArrayList<HistoryChatRecord>();

	private String friendJid;

	public boolean isContains(String from) {
		return from.contains(friendJid);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat_window);
		XmppChatMsgListenerManager.getInstance().registerListener(this);
		Bundle bundle = getIntent().getExtras();
		friendJid = bundle.getString(CHAT_JID);
		currChat = XmppChatManager.getInstance().createChat(friendJid);
		if (currChat == null) {
			ToastHelper.showToast(this, "创建聊天窗口失败，查看是否IM为登陆状态！");
			AppActivityManager.getInstance().finishActivity(this);
		}
		initPageAttribute();
		showOfflineChatRecord();
	}

	private void showOfflineChatRecord() {
		HistoryChatRecordDb db = new HistoryChatRecordDb(this);
		List<HistoryChatRecord> records = db.findOfflineByJid(friendJid,
				AppApplication.getInstance().getSessionUser().getUsername());
		if (records != null && records.size() > 0) {
			db.updateChatTypeByJid(friendJid, AppApplication.getInstance()
					.getSessionUser().getUsername());
			historyChatRecords.addAll(records);
			chatRecordAdapter.notifyDataSetChanged();
			lv_icw_chat_record.setSelection(historyChatRecords.size());
		}
	}

	private void initPageAttribute() {
		// 返回按钮
		btn_icw_back = (Button) findViewById(R.id.btn_icw_back);
		btn_icw_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));

		// 设置标题
		tv_icw_friend_username = (TextView) findViewById(R.id.tv_icw_friend_username);

		tv_icw_friend_username.setText(XmppVCardManager.getInstance()
				.getNickname(friendJid));

		// 查看好友详情
		btn_icw_detail = (Button) findViewById(R.id.btn_icw_detail);
		btn_icw_detail.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(ImChatDetail.JID_OR_ROOMJID, friendJid);
				intent.setClass(ImChatWindow.this, ImChatDetail.class);
				startActivity(intent);
			}
		});

		// 聊天记录
		lv_icw_chat_record = (ListView) findViewById(R.id.lv_icw_chat_record);
		chatRecordAdapter = new ImChatRecordAdapter(this, historyChatRecords);
		lv_icw_chat_record.setAdapter(chatRecordAdapter);

		// 选择类型
		btn_icw_select_type = (Button) findViewById(R.id.btn_icw_select_type);
		btn_icw_select_type.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO选择发送类型 图片，文件，表情
			}
		});

		// 消息发送文本
		et_icw_msg_send_text = (EditText) findViewById(R.id.et_icw_msg_send_text);

		// 发送按钮
		btn_icw_send_msg = (Button) findViewById(R.id.btn_icw_send_msg);
		btn_icw_send_msg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!XmppRosterManager.getInstance().isFriend(friendJid)) {
					AlertDialogHelper.addFrindDialog(ImChatWindow.this,
							friendJid, true);
					return;
				}
				String chatContent = getTextViewToString(et_icw_msg_send_text);
				if (chatContent.length() > 0) {
					SessionUser sessionUser = AppApplication.getInstance()
							.getSessionUser();
					HistoryChatRecord record = new HistoryChatRecord();
					record.setChatType(HistoryChatRecord.CHAT_TYPE_FROM);
					record.setNickname(ImHelper.generationNickname(sessionUser));
					// record.setHeadUrl(sessionUser.getHeadUrl());
					record.setChatTime(new Date());
					record.setChatContent(chatContent);
					record.setUsername(sessionUser.getUsername());
					record.setJid(friendJid);
					chatRecordAdapter.notifyDataSetChanged();
					lv_icw_chat_record.setSelection(historyChatRecords.size());
					sendMessage(chatContent);

					historyChatRecords.add(record);
					saveHistoryChatRecord(record);
				}
				et_icw_msg_send_text.setText("");
			}
		});
	}

	private void sendMessage(String chatContent) {
		try {
			currChat.sendMessage(chatContent);
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "发送消息失败：" + e.getMessage());
		}
	}

	private void saveHistoryChatRecord(HistoryChatRecord record) {
		HistoryChatRecordDb db = new HistoryChatRecordDb(this);
		db.save(record);
	}

	// 通过推送获取好友发送消息
	public void refreshChatMsg(Chat newchat, Message message) {
		if (message.getFrom().contains(friendJid)) {
			sendMsg(1, message.getBody());
		}

	}

	private void sendMsg(int what, String body) {
		android.os.Message msg = handler.obtainMessage();
		msg.what = what;
		msg.obj = body;
		msg.sendToTarget();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				String chatContent = (String) msg.obj;
				HistoryChatRecord record = new HistoryChatRecord();
				record.setChatType(HistoryChatRecord.CHAT_TYPE_TO);
				record.setChatContent(chatContent);
				record.setChatTime(new Date());
				record.setNickname(ImHelper.processToUsername(friendJid));
				VCard vcard = XmppVCardManager.getInstance().getVCardByJid(
						friendJid);
				record.setNickname(vcard.getNickName());
				// record.setHeadUrl(vcard.ge);
				record.setJid(friendJid);
				record.setUsername(AppApplication.getInstance()
						.getSessionUser().getUsername());
				chatRecordAdapter.notifyDataSetChanged();
				lv_icw_chat_record.setSelection(historyChatRecords.size());

				historyChatRecords.add(record);
				saveHistoryChatRecord(record);
				break;
			}
		}
	};

	// private void initService() {
	// // 绑定文件监听服务
	// Intent fileIntent = new Intent();
	// fileIntent.setAction(SERVICE_FILE_LISTENER);
	// startService(fileIntent);
	// }
	@Override
	protected void onDestroy() {
		// Intent fileIntent = new Intent();
		// fileIntent.setAction(SERVICE_FILE_LISTENER);
		// stopService(fileIntent);
		if (historyChatRecords.size() > 0) {
			saveLastestChatObjcet();
		}
		super.onDestroy();
	}

	private void saveLastestChatObjcet() {
		LastestChatObjcetDb db = new LastestChatObjcetDb(this);
		LastestChatObjcet chatObj = new LastestChatObjcet();
		chatObj.setUsername(AppApplication.getInstance().getSessionUser()
				.getUsername());
		VCard vcard = XmppVCardManager.getInstance().getVCardByJid(friendJid);
		// chatObj.setHeadUrl(vcard.getHeadUrl());
		chatObj.setJid(friendJid);
		chatObj.setNickname(vcard.getNickName());
		chatObj.setLastestChatDateTime(new Date());
		chatObj.setType(LastestChatObjcet.CHAT);
		db.saveOrUpdate(chatObj);
	}

}
