package itaf.mobile.app.im.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.xmpp.XmppChatRoomManager;
import itaf.mobile.app.util.ImHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 好友添加页面
 * 
 * 
 * @author
 * 
 * @update 2013年12月5日
 */
public class ImAddChatRoomDialog extends BaseImActivity {

	private LinearLayout linear_iafd_layout;
	private Button btn_iafd_accept;
	private Button btn_iafd_refuse;
	private TextView tv_iafd_add_content;
	private LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(this);

	private String roomjid;
	private String inviter;
	private String reason;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_add_friend_dialog);
		linear_iafd_layout = (LinearLayout) findViewById(R.id.linear_iafd_layout);
		linear_iafd_layout.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (getWidowWidth() * 0.8), (int) (getWidowHeight() * 0.5)));
		Bundle bundle = this.getIntent().getExtras();
		roomjid = bundle.getString(ImHelper.IM_ROOMJID);
		if (roomjid.startsWith(ImHelper.INVITATION)) {
			chatObjDb.deleteAndRecordByJid(roomjid, AppApplication
					.getInstance().getSessionUser().getUsername());
			roomjid = roomjid.substring(ImHelper.INVITATION.length());
		}
		inviter = bundle.getString(ImHelper.IM_INVITER);
		reason = bundle.getString(ImHelper.IM_REASON);
		btn_iafd_accept = (Button) findViewById(R.id.btn_iafd_accept);
		btn_iafd_accept.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (StringHelper.isNotEmpty(roomjid)) {
					XmppChatRoomManager.getInstance().grantInvitation(
							ImAddChatRoomDialog.this, roomjid);
				}
				AppActivityManager.getInstance().finishActivity(
						ImAddChatRoomDialog.this);
			}
		});
		btn_iafd_refuse = (Button) findViewById(R.id.btn_iafd_refuse);
		btn_iafd_refuse.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (StringHelper.isNotEmpty(roomjid)) {
					XmppChatRoomManager.getInstance().revokeInvitation(roomjid,
							inviter, "I'm very busy.");
				}
				AppActivityManager.getInstance().finishActivity(
						ImAddChatRoomDialog.this);
			}
		});

		tv_iafd_add_content = (TextView) findViewById(R.id.tv_iafd_add_content);
		tv_iafd_add_content.setText(inviter + reason);
	}

	/** 重写父类方法，返回弹出是否退出应用对话框 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (StringHelper.isNotEmpty(roomjid)) {
				XmppChatRoomManager.getInstance().revokeInvitation(roomjid,
						inviter, "I'm very busy.");
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
