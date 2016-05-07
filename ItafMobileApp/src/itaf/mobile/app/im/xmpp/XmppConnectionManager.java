package itaf.mobile.app.im.xmpp;

import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.exception.AppException;
import itaf.mobile.core.utils.DummySSLSocketFactory;

import java.util.Calendar;

import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.tcp.XMPPTCPConnection;

import android.content.Context;
import android.util.Log;

/**
 * @author Sam.Io
 * @time 2011/11/18
 * @project AdXmpp
 */
public class XmppConnectionManager {

	private XMPPConnection connection = null;
	private boolean isConnected = true;
	private long reconnectTime = 5000L;
	private boolean isLogin = false;
	private Context mContent;

	private XmppConnectionManager() {
	}

	private static class SingletonHolder {
		static final XmppConnectionManager INSTANCE = new XmppConnectionManager();
	}

	public static XmppConnectionManager getInstance() {
		return SingletonHolder.INSTANCE;
	}

	public Roster getRoster() {
		Roster roster = null;
		if (isLogin) {
			try {
				roster = getConnection().getRoster();
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "Roster获取异常：" + e.getMessage());
			}
		}
		return roster;
	}

	public ChatManager getChatManager() {
		ChatManager chatManager = null;
		if (isLogin) {
			try {
				chatManager = ChatManager.getInstanceFor(getConnection());
			} catch (Exception e) {
				Log.e(this.getClass().getName(),
						"ChatManager获取异常：" + e.getMessage());
			}
		}
		return chatManager;
	}

	private boolean tryLogin(String userName, String passwd) {
		try {
			if (connected() && !getConnection().isAuthenticated()) {
				getConnection().login(userName, passwd);
			}
			isLogin = true;
			reconnectTime = 5000L;
		} catch (Exception e) {
			isLogin = false;
			Log.e(this.getClass().getName(), "tryLogin登陆异常：" + e.getMessage());
		}
		if (isLogin) {
			try {
				// 初始化消息监听
				XmppChatMsgListenerManager.getInstance().initialize();
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "初始化消息监听异常：" + e.getMessage());
			}

			try {
				// 添加好友监听器
				XmppRosterManager.getInstance().addOnlineRosterListener(
						mContent);
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "添加好友监听器异常：" + e.getMessage());
			}

			try {
				// 监听邀请加入聊天室请求
				XmppChatRoomManager.getInstance().addOnlineInvitationListener(
						mContent);
			} catch (Exception e) {
				Log.e(this.getClass().getName(),
						"监听邀请加入聊天室请求异常：" + e.getMessage());
			}

			try {
				// 监听聊天室消息
				XmppChatRoomMsgListenerManager.getInstance().initialize();
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "监听聊天室消息异常：" + e.getMessage());
			}

			try {
				// 设置状态
				XmppPresenceManager.getInstance().setAvailable();
			} catch (Exception e) {
				Log.e(this.getClass().getName(), "设置状态异常：" + e.getMessage());
			}

		}
		return isLogin;
	}

	/** 系统退出，关闭连接 */
	public void closeConnection() {
		try {
			if (getConnection() != null && getConnection().isConnected()) {
				getConnection().disconnect();
				connection = null;
			}
			isConnected = false;
		} catch (Exception e) {
			Log.e(this.getClass().getName(), "断开连接异常：" + e.getMessage());
		}
	}

	/** 获取连接 */
	public XMPPConnection getConnection() {
		if (connection == null) {
			initConnection();
		}
		return connection;
	}

	public boolean isLogin() {
		return isLogin;
	}

	/** 登陆 */
	public boolean doLogon(Context context, String userName, String passwd) {
		this.mContent = context;
		AppApplication.getInstance().getSessionUser()
				.setImStatus(XmppPresenceManager.TYPE_OFF_LINE);
		if (!tryLogin(userName, passwd)) {
			// 登陆异常，启动线程开始尝试重新连接
			startTryLoginThread();
			return false;
		}
		return true;
	}

	private boolean connected() {
		try {
			if (!getConnection().isConnected()) {
				getConnection().connect();
				// 连接过程中有可以连接突然中断，连接出错等等问题，要进行监听
				getConnection().addConnectionListener(connectionListener);
			}
			isConnected = true;
		} catch (Exception e) {
			isConnected = false;
			throw new AppException("connected连接异常");
		}
		return isConnected;
	}

	private void initConnection() {
		// 声明连接
		ConnectionConfiguration config = new ConnectionConfiguration(
				AppConfig.imServerIp, AppConfig.imServerPort);
		// 尝试重新连接
		config.setReconnectionAllowed(true);
		config.setSecurityMode(ConnectionConfiguration.SecurityMode.enabled);
		// 使用SASL验证
		// config.setSASLAuthenticationEnabled(true);
		config.setSocketFactory(new DummySSLSocketFactory());
		connection = new XMPPTCPConnection(config);
	}

	private void startTryLoginThread() {
		new Thread() {
			@Override
			public void run() {
				while (!isConnected) {
					try {
						if (!tryLogin(AppApplication.getInstance()
								.getSessionUser().getUsername(), AppApplication
								.getInstance().getSessionUser().getPassword())) {
							Thread.sleep(reconnectTime);
							Log.e(this.getClass().getName(), reconnectTime
									/ 1000 + "秒后重新连接！");
							reconnectTime = reconnectTime + 5000L;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}.start();
	}

	private ConnectionListener connectionListener = new ConnectionListener() {
		public void connectionClosed() {
			// 来自连接监听,connection正常关闭
			Log.d("IM的connection连接事件", "IM的connection正常关闭");
			isConnected = false;
			isLogin = false;
			XmppPresenceManager.getInstance().setUnavailable();
		}

		public void connectionClosedOnError(Exception e) {
			// 这里就是网络不正常或者被挤掉断线激发的事件
			Log.d("IM的connection连接事件", "IM的connection网络不正常或者被挤掉断线"
					+ Calendar.getInstance().getTimeInMillis());
			AppApplication.getInstance().getSessionUser()
					.setImStatus(XmppPresenceManager.TYPE_OFF_LINE);
			isConnected = false;
			isLogin = false;
			// 登陆成功后，IM连接出现异常，启动线程开始尝试重新连接
			startTryLoginThread();
		}

		public void reconnectingIn(int seconds) {
			// 重新连接的动作正在进行的动作，里面的参数arg0是一个倒计时的数字，如果连接异常的次数增多，数字会越来越大，开始的时候是9
			Log.d("IM的connection连接事件", "IM的connection重新连接中"
					+ Calendar.getInstance().getTimeInMillis());
		}

		public void reconnectionFailed(Exception e) {
			// 重新连接异常
			Log.d("IM的connection连接事件", "IM的connection重新连接异常"
					+ Calendar.getInstance().getTimeInMillis());
		}

		public void reconnectionSuccessful() {
			// 当网络断线了，重新连接上服务器触发的事件
			Log.d("IM的connection连接事件", "IM的connection重新连接成功");
			XmppPresenceManager.getInstance().setAvailable();
		}

		@Override
		public void authenticated(XMPPConnection arg0) {
			//

		}

		@Override
		public void connected(XMPPConnection arg0) {
			//

		}

	};

}
