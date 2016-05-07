package itaf.mobile.core.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * App的Activity管理类
 * 
 * 
 * @author
 * 
 * @update 2013年11月13日
 */
public class AppActivityManager {

	private static Map<String, Activity> activityMap;

	private static Stack<Activity> activityStack;

	private AppActivityManager() {
	}

	private static class SingletonHolder {
		static final AppActivityManager instance = new AppActivityManager();
	}

	public static AppActivityManager getInstance() {
		return SingletonHolder.instance;
	}

	/**
	 * 添加Activity到堆栈
	 */
	public void addActivity(Activity activity) {
		if (activityMap == null) {
			activityMap = new HashMap<String, Activity>();
		}
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		if (activityMap.containsKey(activity.getClass().getName())) {
			Activity oldActivity = activityMap.get(activity.getClass()
					.getName());
			if (oldActivity == activity) {
				activityStack.remove(activity);
			} else {
				// 结束掉以前的
				finishActivity(oldActivity);
			}
		}
		activityMap.put(activity.getClass().getName(), activity);
		activityStack.add(activity);
	}

	public Activity getCurrActivity() {
		return activityStack.lastElement();
	}

	public Activity getActivity(String key) {
		return activityMap.get(key);
	}

	public boolean containsActivity(String key) {
		return activityMap.get(key) != null;
	}

	/**
	 * 结束当前Activity（堆栈中最后一个压入的）
	 */
	public void finishActivity(Activity activity) {
		activityStack.remove(activity);
		activityMap.remove(activity.getClass().getName());
		activity.finish();
		activity = null;
	}

	/**
	 * 结束所有Activity
	 */
	public void finishAllActivity() {
		for (String key : activityMap.keySet()) {
			if (null != activityMap.get(key)) {
				activityMap.get(key).finish();
			}
		}
		activityMap.clear();
		activityStack.clear();
	}

	/**
	 * 退出应用程序
	 */
	public void appExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			int nPid = android.os.Process.myPid();
			android.os.Process.killProcess(nPid);
			System.exit(0);
		} catch (Exception e) {
		}
	}

}
