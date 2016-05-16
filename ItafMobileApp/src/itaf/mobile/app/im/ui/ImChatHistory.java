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
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.ui.adapter.ImChatRecordAdapter;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 好友聊天历史
 * 
 * @author
 * 
 * @update 2013年10月17日
 */
public class ImChatHistory extends BaseImActivity {

	public static final String JID_OR_ROOMJID = "jidOrRoomjid";

	// 返回
	private Button btn_ich_back;
	// 清除聊天记录
	private Button btn_ich_clear_chat_history;

	private ListView lv_ich_chat_history;
	private ImChatRecordAdapter chatRecordAdapter;
	private List<HistoryChatRecord> chatRecordHistorys = new ArrayList<HistoryChatRecord>();
	private String jidOrRoomJid;

	private HistoryChatRecordDb db = new HistoryChatRecordDb(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.im_chat_history);

		Bundle bundle = this.getIntent().getExtras();
		jidOrRoomJid = bundle.getString(JID_OR_ROOMJID);
		if (StringHelper.isEmpty(jidOrRoomJid)) {
			AppActivityManager.getInstance().finishActivity(this);
		}

		initPageAttribute();
		loadHistoryChatRecord();
	}

	private void initPageAttribute() {
		btn_ich_back = (Button) findViewById(R.id.btn_ich_back);
		btn_ich_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));
		btn_ich_clear_chat_history = (Button) findViewById(R.id.btn_ich_clear_chat_history);
		btn_ich_clear_chat_history.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				db.deleteByJid(jidOrRoomJid, AppApplication.getInstance()
						.getSessionUser().getUsername());
				chatRecordHistorys.clear();
				chatRecordAdapter.notifyDataSetChanged();
			}
		});

		lv_ich_chat_history = (ListView) findViewById(R.id.lv_ich_chat_history);
		chatRecordAdapter = new ImChatRecordAdapter(this, chatRecordHistorys);
		lv_ich_chat_history.setAdapter(chatRecordAdapter);
	}

	private void loadHistoryChatRecord() {
		// 获取好友聊天历史记录
		List<HistoryChatRecord> records = db.findByJid(jidOrRoomJid,
				AppApplication.getInstance().getSessionUser().getUsername());
		if (records != null && records.size() > 0) {
			db.updateChatTypeByJid(jidOrRoomJid, AppApplication.getInstance()
					.getSessionUser().getUsername());
			chatRecordHistorys.addAll(records);
			chatRecordAdapter.notifyDataSetChanged();
		}

	}

}
