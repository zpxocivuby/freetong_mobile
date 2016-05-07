package itaf.mobile.app.im.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.ui.adapter.LastestChatObjcetAdapter;
import itaf.mobile.app.im.xmpp.XmppChatRoomManager;
import itaf.mobile.app.notification.NoticeId;
import itaf.mobile.app.services.NoticeService;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.base.BaseMenuActivity;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * IM
 * 
 * @author XININ
 * 
 * @updateDate 2013-6-26
 */
public class MenuIm extends BaseMenuActivity {

	private static final int HANDLER_LOAD_USER = 1;
	private static final int HANDLER_CALLBACK_REFRESH = 2;

	private ImageView iv_imh_user_head;
	private TextView tv_imh_username;
	private TextView tv_imh_status;
	private TextView tv_imh_my_friends;
	private TextView tv_imh_send_message;

	private PullToRefreshListView pull_imh_lastest_chat_container;
	// -1是初始状态，1是加载完成，2是正在刷新，3是加载更多
	private int curLvDataState = -1;

	private LastestChatObjcetAdapter lastestChatObjcetAdapter;

	private List<LastestChatObjcet> lastestChatObjcets = new ArrayList<LastestChatObjcet>();

	private LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(this);
	private HistoryChatRecordDb recordDb = new HistoryChatRecordDb(this);

	private int currentIndex;
	private int totalCount;

	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case HANDLER_LOAD_USER:
				loadUserInfo();
				break;
			case HANDLER_CALLBACK_REFRESH:
				pull_imh_lastest_chat_container.setRefreshing();
				break;
			}
		}
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_im);
		initPageAttribute();
	}

	protected void onResume() {
		super.onResume();
		handler.sendEmptyMessage(HANDLER_LOAD_USER);
		handler.sendEmptyMessage(HANDLER_CALLBACK_REFRESH);
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
		handler.sendEmptyMessage(HANDLER_CALLBACK_REFRESH);
	}

	/**
	 * 加载当前用户信息
	 */
	private void loadUserInfo() {
		final SessionUser sessionUser = AppApplication.getInstance()
				.getSessionUser();
		iv_imh_user_head.setImageResource(R.drawable.im_user_head_ico_small);
		tv_imh_username.setText(ImHelper.generationNickname(sessionUser) + "("
				+ sessionUser.getUsername() + ")");
		tv_imh_status.setText("[" + sessionUser.getImStatus() + "]");
	}

	/** 初始化页面属性 */
	private void initPageAttribute() {
		iv_imh_user_head = (ImageView) findViewById(R.id.iv_imh_user_head);
		tv_imh_username = (TextView) findViewById(R.id.tv_imh_username);
		tv_imh_status = (TextView) findViewById(R.id.tv_imh_status);

		tv_imh_my_friends = (TextView) findViewById(R.id.tv_imh_my_friends);
		// 打开当前好友列表
		tv_imh_my_friends.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(MenuIm.this, ImChatFriends.class);
				startActivity(intent);
			}
		});

		tv_imh_send_message = (TextView) findViewById(R.id.tv_imh_send_message);
		// 打开好友选择页面，如果选择1个，打开单人聊天窗口，如果大于1个，选择多人聊天窗口
		tv_imh_send_message.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Bundle bundle = new Bundle();
				// bundle.putSerializable(AppConstants.TRANSMIT_LIST, null);
				// Intent intent = new Intent();
				// intent.putExtras(bundle);
				// intent.setClass(MenuIm.this, ImChatFriends.class);
				// startActivityForResult(intent,
				// AppConstants.REQUEST_CODE_SELECT_USER);
			}
		});

		initPullToRefreshListView();
	}

	/** 选择好友回调 */
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
		// Intent intent = new Intent();
		// if (target.size() == 1) {
		// intent.putExtra(ImChatWindow.CHAT_JID, target.iterator()
		// .next().getJid());
		// intent.setClass(MenuIm.this, ImChatWindow.class);
		// startActivity(intent);
		// break;
		// }
		// String roomMemberJids = "";
		// for (ImUserInfo imUser : target) {
		// roomMemberJids += imUser.getJid() + ",";
		// }
		// intent.putExtra(ImChatRoomWindow.ROOM_MEMBER_JIDS,
		// roomMemberJids);
		// intent.setClass(MenuIm.this, ImChatRoomWindow.class);
		// startActivity(intent);
		// break;
		// }
		}
	}

	/** 初始化ListView */
	private void initPullToRefreshListView() {
		pull_imh_lastest_chat_container = (PullToRefreshListView) findViewById(R.id.pull_imh_lastest_chat_container);

		lastestChatObjcetAdapter = new LastestChatObjcetAdapter(this,
				lastestChatObjcets);
		pull_imh_lastest_chat_container.setAdapter(lastestChatObjcetAdapter);
		pull_imh_lastest_chat_container
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						LastestChatObjcet chatObj = lastestChatObjcets
								.get(position - 1);
						Intent intent = new Intent();
						final String jid = chatObj.getJid();
						if (chatObj.getType() == LastestChatObjcet.CHAT) {
							intent.putExtra(ImChatWindow.CHAT_JID, jid);
							intent.setClass(MenuIm.this, ImChatWindow.class);
						} else if (chatObj.getType() == LastestChatObjcet.CHAT_ROOM) {
							intent.putExtra(ImChatRoomWindow.ROOM_JID, jid);
							intent.setClass(MenuIm.this, ImChatRoomWindow.class);
						} else if (chatObj.getType() == LastestChatObjcet.CHAT_INVITATION) {
							lastestChatObjcets.remove(chatObj);
							intent.putExtra(ImHelper.IM_IMUSERNAME, jid);
							intent.setClass(MenuIm.this,
									ImAddFriendDialog.class);
						} else if (chatObj.getType() == LastestChatObjcet.CHAT_ROOM_INVITATION) {
							lastestChatObjcets.remove(chatObj);
							intent.putExtra(ImHelper.IM_ROOMJID, jid);
							intent.putExtra(ImHelper.IM_INVITER,
									chatObj.getNickname());
							intent.putExtra(ImHelper.IM_REASON,
									ImChatRoomWindow.REASON);
							intent.setClass(MenuIm.this,
									ImAddChatRoomDialog.class);

						}
						startActivity(intent);
					}
				});
		pull_imh_lastest_chat_container
				.setOnScrollListener(new AbsListView.OnScrollListener() {
					public void onScrollStateChanged(AbsListView view,
							int scrollState) {
						pull_imh_lastest_chat_container.onScrollStateChanged(
								view, scrollState);

						// 数据为空--不用继续下面代码了
						if (lastestChatObjcets.size() == 0) {
							return;
						}

						if (curLvDataState == 1 && currentIndex < totalCount) {
							curLvDataState = 3;
							loadMoreLastestChatFriends();
						}
					}

					public void onScroll(AbsListView view,
							int firstVisibleItem, int visibleItemCount,
							int totalItemCount) {
						pull_imh_lastest_chat_container.onScroll(view,
								firstVisibleItem, visibleItemCount,
								totalItemCount);
					}
				});
		pull_imh_lastest_chat_container
				.setOnLongClickListener(new OnLongClickListener() {
					@Override
					public boolean onLongClick(View v) {
						final LastestChatObjcet chatObj = lastestChatObjcets
								.get(0);
						int type = chatObj.getType();
						List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
						if (type == LastestChatObjcet.CHAT) {
							popItems.add(new PopupWindowItem("聊天",
									new OnClickListener() {
										public void onClick(View v) {
											PopCommon.dismiss();
											Intent intent = new Intent();
											intent.putExtra(
													ImChatWindow.CHAT_JID,
													chatObj.getJid());
											intent.setClass(MenuIm.this,
													ImChatWindow.class);
											startActivity(intent);
										}
									}));
							popItems.add(new PopupWindowItem("删除记录",
									new OnClickListener() {
										public void onClick(View v) {
											PopCommon.dismiss();
											lastestChatObjcets.remove(chatObj);
											chatObjDb.deleteByJid(
													chatObj.getJid(),
													chatObj.getUsername());
											handler.sendEmptyMessage(2);
										}
									}));
						} else if (chatObj.getType() == LastestChatObjcet.CHAT_ROOM) {
							popItems.add(new PopupWindowItem("进入聊天室",
									new OnClickListener() {
										public void onClick(View v) {
											PopCommon.dismiss();
											Intent intent = new Intent();
											intent.putExtra(
													ImChatRoomWindow.ROOM_JID,
													chatObj.getJid());
											intent.setClass(MenuIm.this,
													ImChatRoomWindow.class);
											startActivity(intent);
										}
									}));
							popItems.add(new PopupWindowItem("删除聊天室",
									new OnClickListener() {
										public void onClick(View v) {
											PopCommon.dismiss();
											lastestChatObjcets.remove(chatObj);
											chatObjDb.deleteAndRecordByJid(
													chatObj.getJid(),
													chatObj.getUsername());
											XmppChatRoomManager
													.getInstance()
													.leaveRoom(chatObj.getJid());
											handler.sendEmptyMessage(2);
										}
									}));
						} else {
							// do nothing
							return false;
						}
						PopCommon.show(MenuIm.this, popItems);
						return false;
					}
				});

		pull_imh_lastest_chat_container
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						if (curLvDataState == -1 || curLvDataState == 1) {
							curLvDataState = 2;
							currentIndex = 0;
							loadMoreLastestChatFriends();
						}

					}
				});
		pull_imh_lastest_chat_container.setRefreshing();
	}

	private void loadMoreLastestChatFriends() {
		WsPageResult<LastestChatObjcet> queryResult = chatObjDb.findPager(
				AppApplication.getInstance().getSessionUser().getUsername(),
				currentIndex, this.getPageSize());
		if (currentIndex == 0) {
			lastestChatObjcets.clear();
		}
		totalCount = queryResult.getTotalCount();
		currentIndex = queryResult.getCurrentIndex() + getPageSize();
		lastestChatObjcets.addAll(queryResult.getContent());
		lastestChatObjcetAdapter.notifyDataSetChanged();
		pull_imh_lastest_chat_container.onRefreshComplete();
		curLvDataState = 1;
	}

}
