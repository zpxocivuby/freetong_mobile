package itaf.mobile.app.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * @author zhanghaitao
 * @date 2011-7-7
 * @version 1.0
 */
public class FileListenerService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		Log.d("info", "Service Bind Success");
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// XmppFileManager _xmppFileMgr = XmppFileManager.getInstance();
		// _xmppFileMgr.initialize(getBaseContext(), XmppConnectionManager
		// .getInstance().getConnection());
		Log.d(this.getClass().getName(), "-----fileservice start");
		return START_STICKY;
	}

}
