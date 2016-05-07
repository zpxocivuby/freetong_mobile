package itaf.mobile.app.im.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.im.bean.ImUserInfo;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.xmpp.XmppPresenceManager;
import itaf.mobile.app.im.xmpp.XmppRosterManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.app.notification.NoticeId;
import itaf.mobile.app.services.NoticeService;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.custom.BadgeView;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 我的好友列表
 * 
 * @author
 * 
 */
public class ImChatFriends extends BaseImActivity {

	private Button btn_icf_back;
	private Button btn_icf_add;
	private Button btn_icf_select_friend;

	// 用户好友列表
	private ListView lv_icf_my_friend_list;

	private ImMyFriendsAdapter imMyFriendsAdapter;
	// 好友列表
	private List<ImUserInfo> myFriendList = new ArrayList<ImUserInfo>();
	// 选中的好友
	private Map<String, ImUserInfo> selectFriendDto = new HashMap<String, ImUserInfo>();

	private HistoryChatRecordDb recordDb = new HistoryChatRecordDb(this);

	private Serializable serializable;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				refreshMyFriendList();
				break;
			case 2:
				imMyFriendsAdapter.notifyDataSetChanged();
				lv_icf_my_friend_list.setSelection(0);
				break;
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat_friends);
		// serializable = getIntent().getSerializableExtra(
		// AppConstants.TRANSMIT_LIST);
		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_icf_back = (Button) findViewById(R.id.btn_icf_back);
		btn_icf_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));

		// ListView初始化
		lv_icf_my_friend_list = (ListView) findViewById(R.id.lv_icf_my_friend_list);
		imMyFriendsAdapter = new ImMyFriendsAdapter();
		lv_icf_my_friend_list.setAdapter(imMyFriendsAdapter);
		lv_icf_my_friend_list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ImUserInfo imUser = myFriendList.get(position);
				if (serializable != null) {
					CheckBox select_friend = (CheckBox) view
							.findViewById(R.id.cb_pim_select_friend);
					if (select_friend.isChecked()) {
						select_friend.setChecked(false);
						selectFriendDto.remove(imUser.getUsername());
					} else {
						select_friend.setChecked(true);
						selectFriendDto.put(imUser.getUsername(), imUser);
					}
				} else {
					// 获取用户，跳到单用户聊天室
					startImChatWindow(imUser.getJid());

				}
			}
		});

		lv_icf_my_friend_list
				.setOnItemLongClickListener(new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						final ImUserInfo imUser = myFriendList.get(position);
						List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
						popItems.add(new PopupWindowItem("好友聊天",
								new OnClickListener() {
									public void onClick(View v) {
										startImChatWindow(imUser.getJid());
										PopCommon.dismiss();
									}
								}));
						popItems.add(new PopupWindowItem("删除好友",
								new OnClickListener() {
									public void onClick(View v) {
										XmppRosterManager.getInstance()
												.removeUser(imUser.getJid());
										myFriendList.remove(imUser);
										handler.sendEmptyMessage(2);
										PopCommon.dismiss();
									}

								}));
						PopCommon.show(ImChatFriends.this, popItems);
						return false;
					}
				});

		btn_icf_add = (Button) findViewById(R.id.btn_icf_add);
		btn_icf_select_friend = (Button) findViewById(R.id.btn_icf_select_friend);
		if (serializable != null) {
			btn_icf_select_friend.setVisibility(View.VISIBLE);
			btn_icf_add.setVisibility(View.GONE);
			btn_icf_select_friend.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Bundle mbundle = new Bundle();
					List<ImUserInfo> selectFriends = new ArrayList<ImUserInfo>();
					selectFriends.addAll(selectFriendDto.values());
					// mbundle.putSerializable(AppConstants.TRANSMIT_LIST,
					// new SerializableList<ImUserInfo>(selectFriends));
					Intent returnIntent = new Intent();
					returnIntent.putExtras(mbundle);
					setResult(RESULT_OK, returnIntent);
					AppActivityManager.getInstance().finishActivity(
							ImChatFriends.this);
				}
			});
		} else {
			btn_icf_select_friend.setVisibility(View.GONE);
			btn_icf_add.setVisibility(View.VISIBLE);
			// 打开联系人界面，选择联系人，然后添加为我的好友
			// btn_icf_add.setOnClickListener(OnClickListenerHelper
			// .openMenuAddressBook(this, null));
		}

		refreshMyFriendList();
	}

	/**
	 * 添加好友
	 */
	@SuppressWarnings("unchecked")
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		// case AppConstants.REQUEST_CODE_SELECT_USER:
		// if (resultCode == Activity.RESULT_OK) {
		// Bundle bundle = data.getExtras();
		// Serializable serializable = bundle
		// .getSerializable(AppConstants.TRANSMIT_LIST);
		// if (serializable == null) {
		// break;
		// }
		// SerializableList<SysUserDto> userDtos =
		// (SerializableList<SysUserDto>) serializable;
		// if (userDtos == null || userDtos.getTarget() == null
		// || userDtos.getTarget().size() <= 0) {
		// break;
		// }
		// for (SysUserDto userDto : userDtos.getTarget()) {
		// String jid = ImHelper.processToJid(userDto.getUsername());
		// String nickname = XmppVCardManager.getInstance()
		// .getNickname(jid);
		// XmppRosterManager.getInstance().addFriend(jid, nickname);
		// }
		// }
		}
	}

	@Override
	protected void onResume() {
		super.onPause();
		handler.sendEmptyMessage(1);
		if (recordDb.findOfflineAndInvcationCount(AppApplication.getInstance()
				.getSessionUser().getUsername()) > 0) {
			NotificationInfo info = new NotificationInfo();
			info.setType(NotificationInfo.TYPE_CANCEL_BY_ID);
			info.setNotificationId(NoticeId.NID_IM_MSG);
			NoticeService.addNotification(info);
			// 消息状态为OFFLINE更新为NOREAD
			if (recordDb.findOfflineCount(AppApplication.getInstance()
					.getSessionUser().getUsername()) > 0) {
				recordDb.updateChatTypeToNoRead(AppApplication.getInstance()
						.getSessionUser().getUsername());
			}
		}
	}

	public void refreshCallBack() {
		handler.sendEmptyMessage(1);
	}

	// 只有双向好友才会显示
	@SuppressWarnings("unchecked")
	public void refreshMyFriendList() {
		myFriendList.clear();
		List<RosterEntry> entries = XmppRosterManager.getInstance()
				.getMyFriends();
		if (entries == null || entries.size() <= 0) {
			return;
		}
		for (RosterEntry entry : entries) {
			if (entry == null || !ItemType.both.equals(entry.getType())) {
				continue;
			}
			String jid = entry.getUser();
			String username = ImHelper.processToUsername(jid);
			ImUserInfo imUser = new ImUserInfo();
			imUser.setJid(jid);
			imUser.setUsername(username);
			imUser.setNickname(XmppVCardManager.getInstance().getNickname(jid));
			imUser.setStatus(XmppPresenceManager.getInstance().getPresenceType(
					jid));
			myFriendList.add(imUser);
		}
		if (serializable != null) {
			SerializableList<ImUserInfo> sList = (SerializableList<ImUserInfo>) serializable;
			if (sList.getTarget() != null && sList.getTarget().size() > 0) {
				myFriendList.removeAll(sList.getTarget());
			}
		}

		imMyFriendsAdapter.notifyDataSetChanged();
		lv_icf_my_friend_list.setSelection(0);
	}

	private void startImChatWindow(String jid) {
		Intent intent = new Intent();
		intent.putExtra(ImChatWindow.CHAT_JID, jid);
		intent.setClass(ImChatFriends.this, ImChatWindow.class);
		startActivity(intent);
	}

	class ImMyFriendsAdapter extends AbstractBaseAdapter {

		public int getCount() {
			return myFriendList.size();
		}

		public Object getItem(int position) {
			return myFriendList.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			// 获取list_item布局文件的视图
			convertView = LayoutInflater.from(ImChatFriends.this).inflate(
					R.layout.im_myfriends_adapter, parent, false);
			ImageView iv_pim_headurl = (ImageView) convertView
					.findViewById(R.id.iv_pim_headurl);
			TextView tv_pim_nickname = (TextView) convertView
					.findViewById(R.id.tv_pim_nickname);
			TextView tv_pim_status = (TextView) convertView
					.findViewById(R.id.tv_pim_status);
			TextView tv_pim_personalsign = (TextView) convertView
					.findViewById(R.id.tv_pim_personalsign);
			TextView tv_pim_chat_time = (TextView) convertView
					.findViewById(R.id.tv_pim_chat_time);
			CheckBox cb_pim_select_friend = (CheckBox) convertView
					.findViewById(R.id.cb_pim_select_friend);

			final ImUserInfo userDto = (ImUserInfo) myFriendList.get(position);
			iv_pim_headurl.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// Intent intent = new Intent();
					// intent.putExtra(AppConstants.TRANSMIT_USER_NAME,
					// userDto.getUsername());
					// intent.setClass(ImChatFriends.this,
					// SettingUserInfo.class);
					// startActivity(intent);
				}
			});
			// 是不是选择好友
			if (serializable != null) {
				tv_pim_chat_time.setVisibility(View.GONE);
				cb_pim_select_friend.setVisibility(View.VISIBLE);
				cb_pim_select_friend.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						if (selectFriendDto.containsKey(userDto.getUsername())) {
							selectFriendDto.remove(userDto.getUsername());
						} else {
							selectFriendDto.put(userDto.getUsername(), userDto);
						}
					}
				});
			} else {
				tv_pim_chat_time.setVisibility(View.VISIBLE);
				cb_pim_select_friend.setVisibility(View.GONE);
				tv_pim_chat_time.setText(processDateToHightLightTime(userDto
						.getLastLoginDateTime()));
				Integer offlineCount = recordDb.findOfflineCountByJid(
						userDto.getJid(), AppApplication.getInstance()
								.getSessionUser().getUsername());
				if (offlineCount > 0) {
					BadgeView badge = new BadgeView(ImChatFriends.this,
							iv_pim_headurl);
					badge.setText(String.valueOf(offlineCount));
					badge.show(true);
				}
			}

			if (StringHelper.isNotEmpty(userDto.getHeadUrl())) {
				iv_pim_headurl.setImageURI(Uri.parse(processString(userDto
						.getHeadUrl())));
			}
			tv_pim_nickname.setText(processString(userDto.getNickname()));
			tv_pim_status.setText("(" + processString(userDto.getStatus())
					+ ")");
			tv_pim_personalsign
					.setText(processString(userDto.getPersonalSign()));

			return convertView;
		}

	}

}
