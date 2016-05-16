/**
 * Copyright 2015 Freetong
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package itaf.mobile.app.im.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.im.db.LastestChatObjcetDb;
import itaf.mobile.app.im.xmpp.XmppRosterManager;
import itaf.mobile.app.im.xmpp.XmppVCardManager;
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
public class ImAddFriendDialog extends BaseImActivity {

	private LinearLayout linear_iafd_layout;
	private Button btn_iafd_accept;
	private Button btn_iafd_refuse;
	private TextView tv_iafd_add_content;
	private LastestChatObjcetDb chatObjDb = new LastestChatObjcetDb(this);

	private String jid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_add_friend_dialog);
		linear_iafd_layout = (LinearLayout) findViewById(R.id.linear_iafd_layout);
		linear_iafd_layout.setLayoutParams(new LinearLayout.LayoutParams(
				(int) (getWidowWidth() * 0.8), (int) (getWidowHeight() * 0.5)));
		Bundle bundle = this.getIntent().getExtras();
		jid = bundle.getString(ImHelper.IM_IMUSERNAME);
		if (jid.startsWith(ImHelper.INVITATION)) {
			chatObjDb.deleteAndRecordByJid(jid, AppApplication.getInstance()
					.getSessionUser().getUsername());
			jid = jid.substring(ImHelper.INVITATION.length());
		}
		btn_iafd_accept = (Button) findViewById(R.id.btn_iafd_accept);
		btn_iafd_accept.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (StringHelper.isNotEmpty(jid)) {
					XmppRosterManager.getInstance().addFriend(jid,
							XmppVCardManager.getInstance().getNickname(jid));
				}
				AppActivityManager.getInstance().finishActivity(
						ImAddFriendDialog.this);
			}
		});
		btn_iafd_refuse = (Button) findViewById(R.id.btn_iafd_refuse);
		btn_iafd_refuse.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (StringHelper.isNotEmpty(jid)) {
					XmppRosterManager.getInstance().removeUser(jid);
				}
				AppActivityManager.getInstance().finishActivity(
						ImAddFriendDialog.this);
			}
		});

		tv_iafd_add_content = (TextView) findViewById(R.id.tv_iafd_add_content);
		tv_iafd_add_content.setText(ImHelper.processToUsername(jid) + "("
				+ XmppVCardManager.getInstance().getNickname(jid) + ")"
				+ "请求添加您为好友？");
	}

	/** 重写父类方法，返回弹出是否退出应用对话框 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (StringHelper.isNotEmpty(jid)) {
				XmppRosterManager.getInstance().removeUser(jid);
			}
		}
		return super.onKeyDown(keyCode, event);
	}

}
