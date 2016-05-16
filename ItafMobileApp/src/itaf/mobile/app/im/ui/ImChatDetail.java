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
import itaf.mobile.app.im.bean.ImUserInfo;
import itaf.mobile.app.im.ui.adapter.ImChatMemberAdapter;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 好友聊天详情
 * 
 * @author
 * 
 * @update 2013年10月17日
 */
public class ImChatDetail extends BaseImActivity {

	public static final String JID_OR_ROOMJID = "jidOrRoomjid";

	public static final String ROOM_MEMBERS = "roomMembers";

	private Button btn_icd_back;
	private RelativeLayout relative_icd_chat_history;
	private TextView tv_icd_add_chat_member;

	private ListView lv_icd_chat_peoples;
	private ImChatMemberAdapter chatPeoplesAdapter;
	private List<ImUserInfo> chatMember = new ArrayList<ImUserInfo>();
	private String memberJids;
	private String jidOrRoomjid;
	private static final int RELOAD_CHAT_MEMBERS = 1;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case RELOAD_CHAT_MEMBERS:
				loadChatPeoples();
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat_detail);

		Bundle bundle = this.getIntent().getExtras();
		jidOrRoomjid = bundle.getString(JID_OR_ROOMJID);
		memberJids = bundle.getString(ROOM_MEMBERS);
		if (StringHelper.isEmpty(jidOrRoomjid)) {
			AppActivityManager.getInstance().finishActivity(this);
		}
		if (StringHelper.isEmpty(memberJids)) {
			memberJids = jidOrRoomjid;
		}
		initPageAttribute();
		loadChatPeoples();
	}

	private void initPageAttribute() {
		btn_icd_back = (Button) findViewById(R.id.btn_icd_back);
		btn_icd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));
		relative_icd_chat_history = (RelativeLayout) findViewById(R.id.relative_icd_chat_history);
		relative_icd_chat_history.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(ImChatHistory.JID_OR_ROOMJID, jidOrRoomjid);
				intent.setClass(ImChatDetail.this, ImChatHistory.class);
				startActivity(intent);
			}
		});
		lv_icd_chat_peoples = (ListView) findViewById(R.id.lv_icd_chat_peoples);
		tv_icd_add_chat_member = (TextView) findViewById(R.id.tv_icd_add_chat_member);
		tv_icd_add_chat_member.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Bundle bundle = new Bundle();
				// bundle.putSerializable(AppConstants.TRANSMIT_LIST,
				// new SerializableList<ImUserInfo>(chatMember));
				// Intent intent = new Intent();
				// intent.putExtras(bundle);
				// intent.setClass(ImChatDetail.this, ImChatFriends.class);
				// startActivityForResult(intent,
				// AppConstants.REQUEST_CODE_SELECT_USER);
			}
		});

	}

	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// case AppConstants.REQUEST_CODE_SELECT_USER:
		// if (resultCode == Activity.RESULT_OK) {
		// Bundle bundle = data.getExtras();
		// SerializableList<ImUserInfo> sList = (SerializableList<ImUserInfo>)
		// bundle
		// .getSerializable(AppConstants.TRANSMIT_LIST);
		// if (sList == null || sList.getTarget() == null
		// || sList.getTarget().size() <= 0) {
		// break;
		// }
		// Collection<ImUserInfo> target = sList.getTarget();
		// if (ImHelper.isChatRoom(jidOrRoomjid)) {
		// MultiUserChat muc = XmppChatRoomManager.getInstance()
		// .getMultiUserChat(jidOrRoomjid);
		// for (ImUserInfo imUserInfo : target) {
		// try {
		// muc.invite(imUserInfo.getJid(), "邀请你加入聊天室！");
		// } catch (NotConnectedException e) {
		//
		// e.printStackTrace();
		// }
		// }
		// AppActivityManager.getInstance().finishActivity(this);
		// break;
		// }
		// Intent intent = new Intent();
		// String newMemberJids = jidOrRoomjid;
		// for (ImUserInfo imUserInfo : target) {
		// newMemberJids += "," + imUserInfo.getJid();
		// }
		// intent.putExtra(ImChatRoomWindow.ROOM_MEMBER_JIDS,
		// newMemberJids);
		// intent.setClass(ImChatDetail.this, ImChatRoomWindow.class);
		// startActivity(intent);
		// break;
		// }
		}
	}

	private void loadChatPeoples() {
		if (StringHelper.isNotEmpty(memberJids)) {
			String[] jidArr = memberJids.split(",");
			for (String jid : jidArr) {
				ImUserInfo temp = new ImUserInfo();
				temp.setJid(jid);
				temp.setUsername(ImHelper.processToUsername(jid));
				temp.setNickname(XmppVCardManager.getInstance()
						.getNickname(jid));
				chatMember.add(temp);
			}
			chatPeoplesAdapter = new ImChatMemberAdapter(this, chatMember);
			lv_icd_chat_peoples.setAdapter(chatPeoplesAdapter);
		}
	}

}
