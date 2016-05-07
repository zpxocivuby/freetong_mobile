package itaf.mobile.app.util;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.AppMain;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.core.app.AppActivityManager;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 复用OnClickListener事件
 * 
 * 
 * @author
 * 
 * @updateDate 2014年1月13日
 */
public class OnClickListenerHelper {

	/**
	 * 分享到邮件
	 * 
	 * @param mContext
	 *            android.app.Activity 上下文
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 * @param sendAddress
	 *            分享的地址String[]
	 * @return android.view.View.OnClickListener
	 */
	public static OnClickListener shareToEmail(final Activity mContext,
			final String title, final String content, final String[] sendAddress) {
		return new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(android.content.Intent.ACTION_SEND);
				intent.putExtra(android.content.Intent.EXTRA_EMAIL, sendAddress);
				intent.putExtra(android.content.Intent.EXTRA_SUBJECT, title);
				intent.putExtra(android.content.Intent.EXTRA_TEXT, content);
				intent.setType("plain/text");
				// 附件
				// intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
				// intent.setType("application/octet-stream");
				mContext.startActivity(Intent.createChooser(intent, "选择邮件客户端"));
				PopCommon.dismiss();
			}
		};
	}

	/**
	 * finish当前Activity
	 * 
	 * @param activity
	 *            当前Activity
	 * @return View.OnClickListener
	 */
	public static View.OnClickListener finishActivity(final Activity activity) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				try {
					AppActivityManager.getInstance().finishActivity(activity);
				} catch (Exception e) {
					Log.e("finishActivity", "finishActivity:" + e.getMessage());
				}
			}
		};
	}

	/**
	 * 跳转到主菜单的首页
	 * 
	 * @param context
	 *            当前Activity
	 * @return View.OnClickListener
	 */
	public static View.OnClickListener openHomePage(final Activity context) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				AppActivityManager.getInstance().finishActivity(
						(Activity) context);
				((AppMain) AppActivityManager.getInstance().getActivity(
						AppMain.class.getName()))
						.setRadioGroupChecked(R.id.radio_menu_home);
			}
		};
	}

	/**
	 * 跳转到主菜单的首页
	 * 
	 * @param context
	 *            当前Activity
	 * @return View.OnClickListener
	 */
	public static View.OnClickListener openMenuSide(final Activity context) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				AppActivityManager.getInstance().finishActivity(
						(Activity) context);
				((AppMain) AppActivityManager.getInstance().getActivity(
						AppMain.class.getName()))
						.setRadioGroupChecked(R.id.radio_menu_side);
			}
		};
	}

	/**
	 * 跳转到主菜单的首页
	 * 
	 * @param context
	 *            当前Activity
	 * @return View.OnClickListener
	 */
	public static View.OnClickListener openMenuAccount(final Activity context) {
		return new View.OnClickListener() {
			public void onClick(View v) {
				AppActivityManager.getInstance().finishActivity(
						(Activity) context);
				((AppMain) AppActivityManager.getInstance().getActivity(
						AppMain.class.getName()))
						.setRadioGroupChecked(R.id.radio_menu_account);
			}
		};
	}

}