package itaf.mobile.app.util;

import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppServiceManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

/**
 * UI公共方法
 * 
 * @author XININ
 * 
 * @updateDate 2013-6-28
 */
public class UIHelper {

	/** 退出程序 */
	public static void appExit(final Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("确定要退出吗？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				clearApp(context);
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	public static void clearApp(final Context context) {
		try {
			AppServiceManager.getInstance().stopAllService(context);
		} catch (Exception e) {
			Log.e(context.getClass().getName(), e.getMessage());
		} finally {
			try {
				AppApplication.getInstance().clearApplication();
			} catch (Exception e) {
				Log.e(context.getClass().getName(), e.getMessage());
			} finally {
				AppActivityManager.getInstance().appExit(context);

			}

		}

	}

}
