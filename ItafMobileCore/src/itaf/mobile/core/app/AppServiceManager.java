/**
 * 
 */
package itaf.mobile.core.app;

import itaf.mobile.core.base.BaseActivity;
import android.content.Context;
import android.content.Intent;

/**
 * App的Service管理类
 * 
 * @author
 * 
 */
public class AppServiceManager {

	// 任务管理服务
	public static final String SERVICE_TASK_MANAGER = "itaf.mobile.app.services.TaskService";
	// 自动同步启动
	public static final String SERVICE_AUTO_SYNCHRONIZE = "itaf.mobile.app.services.AutoSyncService";
	// 通知的Service
	public static final String SERVICE_NOTIFICATIONS = "itaf.mobile.app.services.NoticeService";

	private static ThreadLocal<AppServiceManager> threadLocal = new ThreadLocal<AppServiceManager>();

	private AppServiceManager() {
	}

	public static AppServiceManager getInstance() {
		if (threadLocal.get() == null) {
			threadLocal.set(new AppServiceManager());
		}
		return threadLocal.get();
	}

	public void singleStartService(BaseActivity baseActivity) {
		baseActivity.startService(new Intent(SERVICE_TASK_MANAGER));
		baseActivity.startService(new Intent(SERVICE_NOTIFICATIONS));
	}

	public void startOneService(Context context, String serviceName) {
		context.startService(new Intent(serviceName));
	}

	public void stopOneService(Context context, String serviceName) {
		context.stopService(new Intent(serviceName));
	}

	public void stopAllService(Context context) {
		context.stopService(new Intent(SERVICE_TASK_MANAGER));
		context.stopService(new Intent(SERVICE_AUTO_SYNCHRONIZE));
		context.stopService(new Intent(SERVICE_NOTIFICATIONS));
	}
}
