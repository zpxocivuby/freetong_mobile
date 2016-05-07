package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.bean.NotificationInfo;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.ui.ImChatFriends;
import itaf.mobile.app.im.ui.ImChatRoomWindow;
import itaf.mobile.app.im.ui.ImChatWindow;
import itaf.mobile.app.im.ui.MenuIm;
import itaf.mobile.app.im.xmpp.XmppChatMsgListener;
import itaf.mobile.app.im.xmpp.XmppChatMsgListenerManager;
import itaf.mobile.app.im.xmpp.XmppChatRoomManager;
import itaf.mobile.app.im.xmpp.XmppChatRoomMsgListener;
import itaf.mobile.app.im.xmpp.XmppChatRoomMsgListenerManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.app.notification.NoticeFacade;
import itaf.mobile.app.notification.NoticeId;
import itaf.mobile.app.services.NoticeService;
import itaf.mobile.app.util.AppHelper;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Message.Type;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

/**
 * 登陆后的主菜单Activity，管理消息推送
 * 
 * @author
 * 
 */
@SuppressWarnings("deprecation")
public class AppMain extends TabActivity implements XmppChatMsgListener,
		XmppChatRoomMsgListener {

	/** Called when the activity is first created. */
	private TabHost tabHost;
	private Map<Integer, TabRadio> tabRadios;

	private RadioGroup app_radio_group;

	private HistoryChatRecordDb historyDb = new HistoryChatRecordDb(this);
	private LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(this);
	private static final String INTENT_CLASS_CHAT_WINDOW = "chatWindow";
	private static final String INTENT_CLASS_CHATROOM_WINDOW = "chatRoomWindow";
	private static final String INTENT_CLASS_MENU_IM = "menuIm";
	private static final String READIO_SELECT = "readioSelect";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		setContentView(R.layout.app_main);
		XmppChatMsgListenerManager.getInstance().registerListener(this);
		XmppChatRoomMsgListenerManager.getInstance().registerListener(this);
		AppActivityManager.getInstance().addActivity(this);
		initTabRadios();

		tabHost = this.getTabHost();
		// TabHost.TabSpec spec;
		for (Map.Entry<Integer, TabRadio> entry : tabRadios.entrySet()) {
			TabRadio radioTag = entry.getValue();
			TabHost.TabSpec spec = tabHost.newTabSpec(radioTag.tab_name)
					.setIndicator(radioTag.tab_name)
					.setContent(new Intent().setClass(this, radioTag.cls));
			tabHost.addTab(spec);
		}

		tabHost.setCurrentTab(0);

		final RadioButton home = (RadioButton) this
				.findViewById(R.id.radio_menu_home);
		home.setChecked(true);
		home.setCompoundDrawablesWithIntrinsicBounds(0,
				R.drawable.menu_home_press, 0, 0);

		app_radio_group = (RadioGroup) this.findViewById(R.id.app_radio_group);
		app_radio_group
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						for (Map.Entry<Integer, TabRadio> entry : tabRadios
								.entrySet()) {
							TabRadio radioTag = entry.getValue();
							RadioButton view = (RadioButton) findViewById(radioTag.id);
							if (checkedId == radioTag.id) {
								tabHost.setCurrentTabByTag(radioTag.tab_name);
								view.setCompoundDrawablesWithIntrinsicBounds(0,
										radioTag.drawable_tag_hover, 0, 0);
							} else {
								view.setCompoundDrawablesWithIntrinsicBounds(0,
										radioTag.drawable_tag, 0, 0);
							}
						}
					}
				});

	}

	public void setRadioGroupChecked(int checkedId) {
		app_radio_group.check(checkedId);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onResume() {
		super.onResume();
		Bundle bundle = getIntent().getExtras();
		if (bundle != null) {
			Object selectObj = bundle.get(READIO_SELECT);
			if (selectObj != null && selectObj.toString().equals("MenuIm")) {
				setRadioGroupChecked(R.id.radio_menu_im);
			}
		}

	}

	private void initTabRadios() {
		if (tabRadios == null) {
			tabRadios = new LinkedHashMap<Integer, TabRadio>();
			tabRadios.put(R.id.radio_menu_home, new TabRadio(
					R.id.radio_menu_home, "首页", R.drawable.menu_home,
					R.drawable.menu_home_press, MenuHome.class));
			tabRadios.put(R.id.radio_menu_side, new TabRadio(
					R.id.radio_menu_side, "身边", R.drawable.menu_side,
					R.drawable.menu_side_press, MenuSide.class));
			tabRadios.put(R.id.radio_menu_im, new TabRadio(R.id.radio_menu_im,
					"IM", R.drawable.menu_im, R.drawable.menu_im_press,
					MenuIm.class));
			tabRadios.put(R.id.radio_menu_cart, new TabRadio(
					R.id.radio_menu_cart, "购物车", R.drawable.menu_spcart,
					R.drawable.menu_spcart_press, MenuCart.class));
			tabRadios.put(R.id.radio_menu_account, new TabRadio(
					R.id.radio_menu_account, "账号", R.drawable.menu_account,
					R.drawable.menu_account_press, MenuAccount.class));
		}
	}

	class TabRadio {
		int id;
		String tab_name;
		int drawable_tag;
		int drawable_tag_hover;
		Class<?> cls;

		public TabRadio(int id, String tab_name, int drawable_tag,
				int drawable_tag_hover, Class<?> cls) {
			this.id = id;
			this.tab_name = tab_name;
			this.drawable_tag = drawable_tag;
			this.drawable_tag_hover = drawable_tag_hover;
			this.cls = cls;
		}
	}

	public void refreshChatMsg(Chat newchat, Message message) {
		String subject = message.getSubject();

		// 如果为推送消息，发送推行消息并返回
		if (NoticeFacade.checkAndSendNotification(this, message.getFrom(),
				subject, message.getBody())) {
			return;
		}

		String jid = message.getFrom();
		if (StringHelper.isEmpty(jid)) {
			return;
		}

		// 处理JID
		if (jid.indexOf("/") != -1) {
			jid = jid.substring(0, jid.indexOf("/"));
		}

		Activity activity = AppActivityManager.getInstance().getCurrActivity();
		// 聊天窗口存在
		if (activity instanceof ImChatWindow
				&& AppHelper.isAppOnForeground(this)) {
			ImChatWindow chatWindow = (ImChatWindow) activity;
			if (chatWindow.isContains(jid)) {
				return;
			}
		}

		if (Type.chat.equals(message.getType())) {
			HistoryChatRecord record = new HistoryChatRecord();
			record.setChatType(HistoryChatRecord.CHAT_TYPE_OFFLINE);
			record.setJid(jid);
			record.setNickname(XmppVCardManager.getInstance().getNickname(jid));
			record.setHeadUrl(null);
			record.setChatTime(new Date());
			record.setChatContent(message.getBody());
			record.setUsername(AppApplication.getInstance().getSessionUser()
					.getUsername());
			historyDb.save(record);

			LastestChatObjcet chatObj = new LastestChatObjcet();
			chatObj.setUsername(AppApplication.getInstance().getSessionUser()
					.getUsername());
			chatObj.setHeadUrl(null);
			chatObj.setJid(jid);
			chatObj.setNickname(XmppVCardManager.getInstance().getNickname(jid));
			chatObj.setLastestChatDateTime(new Date());
			chatObj.setType(LastestChatObjcet.CHAT);
			chatObjDb.saveOrUpdate(chatObj);
			// 如果当前窗口为IM主窗口，返回
			if (activity instanceof MenuIm && AppHelper.isAppOnForeground(this)) {
				MenuIm menuIm = (MenuIm) activity;
				menuIm.refreshCallBack();
				return;
			}

			// 如果当前窗口为IM主窗口，返回
			if (activity instanceof ImChatFriends
					&& AppHelper.isAppOnForeground(this)) {
				ImChatFriends chatFriends = (ImChatFriends) activity;
				chatFriends.refreshCallBack();
				return;
			}

			String[] subjectAndBody = getSubjectAndBody(XmppVCardManager
					.getInstance().getNickname(jid), message.getBody(),
					INTENT_CLASS_CHAT_WINDOW);
			Map<String, String> params = new HashMap<String, String>();
			params.put(ImChatWindow.CHAT_JID, jid);
			params.put(READIO_SELECT, "MenuIm");
			NotificationInfo info = new NotificationInfo();
			info.setType(NotificationInfo.TYPE_SEND_MSG);
			info.setSubject(subjectAndBody[0]);
			info.setBody(subjectAndBody[1]);
			info.setClazz(INTENT_CLASS_CHAT_WINDOW.equals(subjectAndBody[2]) ? ImChatWindow.class
					: AppMain.class);
			info.setNotificationId(NoticeId.NID_IM_MSG);
			info.setParams(params);
			NoticeService.addNotification(info);
		}
	}

	private String[] getSubjectAndBody(String subject, String body,
			String intentClass) {
		Map<String, Integer> records = historyDb
				.findOfflineByGourp(AppApplication.getInstance()
						.getSessionUser().getUsername());
		if (records == null || records.size() <= 0) {
			return new String[] { subject, body, intentClass };
		}
		if (records.size() == 1) {
			return new String[] {
					subject + "有(" + records.values().iterator().next()
							+ ")条新消息", body, intentClass };
		}
		int recordCount = 0;
		for (Integer count : records.values()) {
			recordCount += count;
		}
		return new String[] { "即时消息",
				"有" + records.size() + "好友发送(" + recordCount + ")条消息",
				INTENT_CLASS_MENU_IM };
	}

	public void refreshChatRoomMsg(Message message) {
		String from = message.getFrom();
		if (StringHelper.isEmpty(from)) {
			return;
		}

		String roomJid = from;
		String nickname = "";
		if (from.indexOf("/") != -1) {
			roomJid = from.substring(0, from.indexOf("/"));
			nickname = from.substring(from.indexOf("/") + 1);
		}

		Activity activity = AppActivityManager.getInstance().getCurrActivity();

		// 聊天室窗口存在
		if (activity instanceof ImChatRoomWindow
				&& AppHelper.isAppOnForeground(this)) {
			ImChatRoomWindow chatRoomWindow = (ImChatRoomWindow) activity;
			if (chatRoomWindow.isContains(roomJid)) {
				return;
			}
		}

		if (Type.groupchat.equals(message.getType())) {
			// 保存历史聊天记录
			HistoryChatRecord record = new HistoryChatRecord();
			record.setChatType(HistoryChatRecord.CHAT_TYPE_OFFLINE);
			record.setNickname(nickname);
			record.setHeadUrl(null);
			record.setChatTime(new Date());
			record.setChatContent(message.getBody());
			record.setUsername(AppApplication.getInstance().getSessionUser()
					.getUsername());
			record.setJid(roomJid);
			historyDb.save(record);

			// 保存为最新聊天对象
			LastestChatObjcet chatObj = new LastestChatObjcet();
			chatObj.setUsername(AppApplication.getInstance().getSessionUser()
					.getUsername());
			// chatObj.setHeadUrl(vcard.getHeadUrl());
			chatObj.setJid(roomJid);
			chatObj.setNickname(XmppChatRoomManager.getInstance().getSubject(
					roomJid));
			chatObj.setLastestChatDateTime(new Date());
			chatObj.setType(LastestChatObjcet.CHAT_ROOM);
			chatObjDb.saveOrUpdate(chatObj);

			// 如果当前窗口为IM主窗口，返回
			if (activity instanceof MenuIm && AppHelper.isAppOnForeground(this)) {
				MenuIm menuIm = (MenuIm) activity;
				menuIm.refreshCallBack();
				return;
			}

			String[] subjectAndBody = getSubjectAndBody(
					ImHelper.processToRoomSubject(roomJid), message.getBody(),
					INTENT_CLASS_CHATROOM_WINDOW);
			NotificationInfo info = new NotificationInfo();
			Map<String, String> params = new HashMap<String, String>();
			params.put(ImChatRoomWindow.ROOM_JID, roomJid);
			params.put(READIO_SELECT, "MenuIm");
			info.setType(NotificationInfo.TYPE_SEND_MSG);
			info.setSubject(subjectAndBody[0]);
			info.setBody(subjectAndBody[1]);
			info.setClazz(INTENT_CLASS_CHATROOM_WINDOW
					.equals(subjectAndBody[2]) ? ImChatRoomWindow.class
					: AppMain.class);
			info.setNotificationId(NoticeId.NID_IM_MSG);
			info.setParams(params);
			NoticeService.addNotification(info);
		}

	}

}
