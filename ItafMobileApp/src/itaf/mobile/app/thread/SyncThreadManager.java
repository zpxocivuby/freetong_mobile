package itaf.mobile.app.thread;

import java.util.HashMap;

import android.content.Context;

/**
 * 同步线程管理类
 * 
 * 
 * @author
 * 
 * @updateDate 2014年4月2日
 */
public class SyncThreadManager {

	private BaseSyncThread syncProductCategoryThread;

	private BaseSyncThread syncServiceProviderTypeThread;

	private static class SingletonHolder {
		static final SyncThreadManager instance = new SyncThreadManager();
	}

	public static SyncThreadManager getInstance() {
		return SingletonHolder.instance;
	}

	// 同步商品品类
	public void startSyncProductCategory(Context context) {
		if (syncProductCategoryThread != null
				&& syncProductCategoryThread.isRun()) {
			return;
		}
		syncProductCategoryThread = new SyncProductCategoryThread(context,
				new HashMap<String, Object>());
		syncProductCategoryThread.setRun(true);
		syncProductCategoryThread.start();
	}

	// 取消同步商品品类
	public void stopSyncProductCategory() {
		// 结束同步用户
		if (syncProductCategoryThread != null) {
			syncProductCategoryThread.setRun(false);
			syncProductCategoryThread = null;
		}
	}

	// 同步商家分类
	public void startSyncServiceProviderType(Context context) {
		if (syncServiceProviderTypeThread != null
				&& syncServiceProviderTypeThread.isRun()) {
			return;
		}
		syncServiceProviderTypeThread = new SyncServiceProviderTypeThread(
				context, new HashMap<String, Object>());
		syncServiceProviderTypeThread.setRun(true);
		syncServiceProviderTypeThread.start();
	}

	// 取消同步商家分类
	public void stopSyncServiceProviderType() {
		if (syncServiceProviderTypeThread != null) {
			syncServiceProviderTypeThread.setRun(false);
			syncServiceProviderTypeThread = null;
		}
	}

}
