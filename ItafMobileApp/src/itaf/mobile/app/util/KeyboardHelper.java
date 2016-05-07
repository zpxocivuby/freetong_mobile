package itaf.mobile.app.util;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * 软键盘工具类
 * 
 * @author
 * 
 * @update 2013年10月29日
 */
public class KeyboardHelper {

	/**
	 * 隐藏键盘
	 * 
	 * @param acitivity
	 *            当前Activity
	 */
	public static void hideSoftInput(Activity acitivity) {
		InputMethodManager imm = (InputMethodManager) acitivity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(acitivity.getWindow().getDecorView()
				.getApplicationWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
	}

	/**
	 * EditText输入框显示键盘
	 * 
	 * @param et
	 *            EditText输入框对象
	 */
	public static void showSoftInput(EditText et) {
		et.requestFocus();
		InputMethodManager imm = (InputMethodManager) et.getContext()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(et, InputMethodManager.RESULT_UNCHANGED_SHOWN);
	}

	/**
	 * EditText输入框显示键盘不会遮挡UI
	 * 
	 * @param et
	 *            EditText输入框对象
	 */
	public static void showSoftInputNoExtractUi(final EditText et) {
		et.setImeOptions(EditorInfo.IME_FLAG_NO_EXTRACT_UI);
		et.setFocusable(true);
		et.requestFocusFromTouch();
		et.requestFocus();

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				InputMethodManager m = (InputMethodManager) et.getContext()
						.getSystemService(Context.INPUT_METHOD_SERVICE);
				m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			}

		}, 300);
	}

	/**
	 * 判断EditText输入框是否显示
	 * 
	 * @param acitivity
	 *            当前Activity
	 * @param et
	 *            EditText输入框对象
	 * @return true 显示， false 隐藏
	 */
	public static boolean isActive(Activity acitivity, EditText et) {
		InputMethodManager imm = (InputMethodManager) acitivity
				.getSystemService(Activity.INPUT_METHOD_SERVICE);
		return imm.hideSoftInputFromWindow(et.getWindowToken(), 0);
	}

}
