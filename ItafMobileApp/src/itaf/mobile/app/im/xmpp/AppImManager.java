package itaf.mobile.app.im.xmpp;

/**
 * IM管理类
 * 
 * 
 * @author
 * 
 * @update 2013年11月13日
 */
public class AppImManager {

	private static class SingletonHolder {
		static final AppImManager instance = new AppImManager();
	}

	public static AppImManager getInstance() {
		return SingletonHolder.instance;
	}

	public void exit() {
		// 关闭连接
		XmppConnectionManager.getInstance().closeConnection();
		// TODO 清理用户的在线状态
	}

}
