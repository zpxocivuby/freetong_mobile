package itaf.mobile.core.app;

import itaf.mobile.core.bean.SessionUser;

import java.util.HashMap;
import java.util.Map;

import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.SDKInitializer;

/**
 * 系统Application
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月16日
 */
public class AppApplication extends Application {

	// 密码加密的常量
	public static final String MAK = "innoview";
	// 当前线程中的用户信息
	private SessionUser sessionUser = new SessionUser();
	// 分页的条数
	private int pageSize = 20;
	// WS头文件
	private Map<String, String> httpHeader = new HashMap<String, String>();
	// 百度地图定位
	public LocationClient mLocationClient;

	public void onCreate() {
		super.onCreate();
		mLocationClient = new LocationClient(this.getApplicationContext());
		// AppExceptionHandler.getInstance().init(getApplicationContext());
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		// 百度地图
		SDKInitializer.initialize(this);
	}

	private static class SingletonHolder {
		static final AppApplication instance = new AppApplication();
	}

	public static AppApplication getInstance() {
		return SingletonHolder.instance;
	}

	// 清理Application
	public void clearApplication() {
		sessionUser = new SessionUser();
		httpHeader = new HashMap<String, String>();
	}

	// 用户是否登录
	public boolean isLogin() {
		return sessionUser != null && sessionUser.getUsername() != null
				&& sessionUser.getUsername().length() > 0;
	}

	/**
	 * 获取手机号码
	 * 
	 * @param context
	 * @return 手机号码
	 */
	public String getMobileNo(Context context) {
		// 创建电话管理
		TelephonyManager tm = (TelephonyManager)
		// 与手机建立连接
		context.getSystemService(Context.TELEPHONY_SERVICE);
		// 获取手机号码
		return tm.getLine1Number();
	}

	public SessionUser getSessionUser() {
		return sessionUser;
	}

	public void setSessionUser(SessionUser sessionUser) {
		this.sessionUser = sessionUser;
	}

	public Map<String, String> getHttpHeader() {
		return httpHeader;
	}

	public void setHttpHeader(Map<String, String> httpHeader) {
		this.httpHeader = httpHeader;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
