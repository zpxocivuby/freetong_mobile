package itaf.mobile.app.ui.base;

import itaf.mobile.app.util.UIHelper;
import android.view.KeyEvent;

/**
 * 菜单继承类
 * 
 * @author
 * 
 * @update 2013年10月9日
 */
public class BaseMenuActivity extends BaseUIActivity {

	/** 重写父类方法，返回弹出是否退出应用对话框 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			UIHelper.appExit(this);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

}
