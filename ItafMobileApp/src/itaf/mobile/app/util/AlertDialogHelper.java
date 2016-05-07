package itaf.mobile.app.util;

import itaf.mobile.app.im.xmpp.XmppRosterManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
import itaf.mobile.core.app.AppActivityManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * 弹出框帮助类
 *
 *
 * @author
 *
 * @UpdateDate 2014年8月21日
 */
public class AlertDialogHelper {

	public static void addFrindDialog(final Activity mActivity,
			final String jid, final boolean isFinish) {
		new AlertDialog.Builder(mActivity)
				.setTitle("通知")
				.setMessage("只有好友才能继续聊天，是否要添加为好友？")
				.setPositiveButton("添加", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						XmppRosterManager.getInstance()
								.addFriend(
										jid,
										XmppVCardManager.getInstance()
												.getNickname(jid));
						if (isFinish) {
							AppActivityManager.getInstance().finishActivity(
									mActivity);
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						if (isFinish) {
							AppActivityManager.getInstance().finishActivity(
									mActivity);
						}
					}
				}).show();
	}

	/**
	 * 确认要删除提示框
	 * 
	 * @param mActivity
	 *            Activity
	 * @param confirmListener
	 *            DialogInterface.OnClickListener
	 */
	public static void showDialog(final Activity mActivity, String title,
			String msg, String btnName,
			DialogInterface.OnClickListener confirmListener) {
		new AlertDialog.Builder(mActivity).setTitle(title).setMessage(msg)
				.setPositiveButton(btnName, confirmListener)
				.setNegativeButton("关闭", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						return;
					}
				}).show();
	}

}
