/**
 * 
 */
package itaf.mobile.core.base;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * @author
 *         <p>
 *         框架的主要的基础service类,作为service的基类,
 *         <p>
 *         所有业务相关的service需要继承该service，并且实现该service的相关方法
 */
public class BaseService extends Service implements Runnable {

	public boolean isrun = true;

	public void run() {
		//

	}

	@Override
	public IBinder onBind(Intent intent) {
		//
		return null;
	}

}
