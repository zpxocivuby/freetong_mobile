package itaf.mobile.app.util;

import itaf.mobile.app.R;
import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * 
 * 
 * @author
 * 
 * @updateDate 2014年1月14日
 */
public class ToastHelper {

	public static void showToast(Activity mActivity, String showText) {
		showToast(mActivity, showText, Toast.LENGTH_SHORT);
	}

	public static void showToast(Activity mActivity, String showText,
			Integer duration) {
		Toast toast = new Toast(mActivity);
		View layout = mActivity.getLayoutInflater().inflate(R.layout.app_toast,
				(ViewGroup) mActivity.findViewById(R.id.linear_toast_layout));
		TextView tvShowText = (TextView) layout
				.findViewById(R.id.tv_toast_showtext);
		toast.setView(layout);
		tvShowText.setText(showText);
		tvShowText.setPadding(15, 5, 15, 5);
		toast.setDuration(duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

}
