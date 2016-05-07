/**
 * 
 */
package itaf.mobile.core.base;

import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppServiceManager;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

/**
 * 
 * 基础Activity
 * 
 * @author
 * 
 */
public abstract class BaseActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		AppServiceManager.getInstance().singleStartService(this);
		// 添加Activity到堆栈
		AppActivityManager.getInstance().addActivity(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		AppServiceManager.getInstance().singleStartService(this);
		// 更新Activity到堆栈到栈顶
		AppActivityManager.getInstance().addActivity(this);
	}

	// 返回键要消除当前的Activity，如果不需要消除，需要重写该方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppActivityManager.getInstance().finishActivity(this);
		}
		return super.onKeyDown(keyCode, event);
	}

}
