package itaf.mobile.app.services;

import itaf.mobile.app.thread.SyncThreadManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 自动同步数据的Service
 * 
 * 
 * @author
 * 
 * @updateDate 2014年3月13日
 */
public class AutoSyncService extends Service implements Runnable {

	public boolean isrun = true;

	private static final int AUTO_SYNC_TIME = 300 * 60000;

	public void run() {
		while (isrun) {
			try {
				doSync();
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(this.getClass().getName(), "自动同步出错:" + e.getMessage());
			}
			try {
				Thread.sleep(AUTO_SYNC_TIME);
				Log.i(this.getClass().getName(), "下次同步时间在[" + AUTO_SYNC_TIME
						/ 60000 + "]分钟后");
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(this.getClass().getName(), e.getMessage());
			}

		}
	}

	private void doSync() {
		SyncThreadManager.getInstance().startSyncProductCategory(
				getApplicationContext());
		SyncThreadManager.getInstance().startSyncServiceProviderType(
				getApplicationContext());
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(this).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		isrun = false;
		SyncThreadManager.getInstance().stopSyncProductCategory();
		SyncThreadManager.getInstance().stopSyncServiceProviderType();

	}

}
