/**
 * 
 */
package itaf.mobile.app.thread;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;

/**
 * 同步线程基础类
 * 
 * @author
 * 
 */
public abstract class BaseSyncThread extends Thread {

	private Context context;
	private Map<String, Object> params;
	private boolean isRun = false;
	private int pageSize = 20;
	private int tryRate = 3;

	public BaseSyncThread(Context context, Map<String, Object> params) {
		this.context = context;
		this.params = params;
	}

	@Override
	public void run() {
		try {
			doSyncRuning();
		} catch (Exception e) {
			Log.e(this.getClass().getName(), e.getMessage());
		}
	}

	public abstract void doSyncRuning();

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		if (params == null) {
			this.params = new HashMap<String, Object>();
		} else {
			this.params = params;
		}
	}

	public boolean isRun() {
		return isRun;
	}

	public void setRun(boolean isRun) {
		this.isRun = isRun;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTryRate() {
		return tryRate;
	}

}
