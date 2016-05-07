package itaf.mobile.app.ui.custom;

import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 普通PopupWindow类
 * 
 * 
 * @author
 * 
 * @updateDate 2014年1月10日
 */
public class PopCommon {

	private static ScrollView popLayout;
	private static PopupWindow popup_window;

	public static void dismiss() {
		if (popup_window != null && popup_window.isShowing()) {
			popup_window.dismiss();
		}
	}

	public static void show(Activity mActivity, List<PopupWindowItem> popItems) {
		if (popup_window != null && popup_window.isShowing()) {
			popup_window.dismiss();
		}
		LinearLayout parentView = new LinearLayout(mActivity);
		popLayout = (ScrollView) LayoutInflater.from(mActivity).inflate(
				R.layout.pop_common, parentView, false);
		// 默认值
		LinearLayout subPopLayout = (LinearLayout) popLayout
				.findViewById(R.id.linear_cpw_popup_window);
		if (popItems != null) {
			int size = popItems.size();
			for (int i = 0; i < size; i++) {
				PopupWindowItem pop = popItems.get(i);
				TextView tvPopItem = new TextView(mActivity);
				tvPopItem.setLayoutParams(new LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				if (size > 1) {
					if (i == (size - 1)) {
						tvPopItem
								.setBackgroundResource(R.drawable.popup_black_bottom);
					} else {
						tvPopItem
								.setBackgroundResource(R.drawable.popup_black_top);
					}
				} else {
					tvPopItem
							.setBackgroundResource(R.drawable.popup_black_bottom);
				}
				tvPopItem.setPadding(15, 15, 15, 15);
				tvPopItem.setGravity(Gravity.LEFT);
				tvPopItem.setText(pop.getText());
				tvPopItem.setTextSize(16);
				tvPopItem.setTextColor(mActivity.getResources().getColor(
						R.color.white));
				tvPopItem.setOnClickListener(pop.getOnClickListener());
				subPopLayout.addView(tvPopItem);
			}
		}

		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		popup_window = new PopupWindow(popLayout, dm.widthPixels / 2,
				dm.heightPixels / 3, true);
		Resources res = null;
		popup_window.setBackgroundDrawable(new BitmapDrawable(res));
		popup_window.setOutsideTouchable(true);
		popup_window.setFocusable(true);
		popup_window.setContentView(popLayout);
		int gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
		int x = dm.widthPixels / 4;
		int y = subPopLayout.getHeight() / 4;
		// 需要指定Gravity，默认情况是center.
		popup_window.showAtLocation(subPopLayout, gravity, x, y);
	}
}
