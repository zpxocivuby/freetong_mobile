package itaf.mobile.app.im.ui.adapter;

import itaf.mobile.app.R;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.im.bean.LastestChatObjcet;
import itaf.mobile.app.im.db.HistoryChatRecordDb;
import itaf.mobile.app.im.xmpp.XmppPresenceManager;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.custom.BadgeView;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 用户最近聊天好友适配器
 * 
 * @author
 * 
 * @update 2013年10月15日
 */
public class LastestChatObjcetAdapter extends AbstractBaseAdapter {

	private Context mContext;
	private LayoutInflater listContainer;
	private List<LastestChatObjcet> lastestChatRecords;
	private HistoryChatRecordDb historyDb;

	public LastestChatObjcetAdapter(Context context,
			List<LastestChatObjcet> data) {
		this.mContext = context;
		listContainer = LayoutInflater.from(context);
		lastestChatRecords = data;
		historyDb = new HistoryChatRecordDb(mContext);
	}

	public int getCount() {
		return lastestChatRecords.size();
	}

	public Object getItem(int position) {
		return lastestChatRecords.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = listContainer.inflate(
				R.layout.im_lastest_chat_record_adapter, parent, false);
		ImageView iv_pilcr_user_head = (ImageView) convertView
				.findViewById(R.id.iv_pilcr_user_head);
		TextView tv_pilcr_nickname = (TextView) convertView
				.findViewById(R.id.tv_pilcr_nickname);
		TextView tv_pilcr_status = (TextView) convertView
				.findViewById(R.id.tv_pilcr_status);
		TextView tv_pilcr_chat_history_content = (TextView) convertView
				.findViewById(R.id.tv_pilcr_chat_history_content);
		TextView tv_pilcr_chat_date = (TextView) convertView
				.findViewById(R.id.tv_pilcr_chat_date);
		LastestChatObjcet chatObj = (LastestChatObjcet) lastestChatRecords
				.get(position);
		String status = "";
		// TODO 头像问题需要处理
		if (LastestChatObjcet.CHAT == chatObj.getType()) {
			if (StringHelper.isNotEmpty(chatObj.getHeadUrl())) {
				iv_pilcr_user_head.setImageURI(Uri.parse(StringHelper
						.trimToEmpty(chatObj.getHeadUrl())));
			}
			status = "("
					+ XmppPresenceManager.getInstance().getPresenceType(
							chatObj.getJid()) + ")";
		} else if (LastestChatObjcet.CHAT_ROOM == chatObj.getType()) {
			status = "(" + LastestChatObjcet.CHAT_ROOM_STATUS + ")";
		}

		Integer offlineCount = historyDb.findOfflineCountByJid(
				chatObj.getJid(), chatObj.getUsername());
		if (offlineCount > 0) {
			BadgeView badge = new BadgeView(mContext, iv_pilcr_user_head);
			badge.setText(String.valueOf(offlineCount));
			badge.show(true);
		}
		String nickname = StringHelper.trimToEmpty(chatObj.getNickname());
		if (LastestChatObjcet.CHAT_INVITATION == chatObj.getType()) {
			nickname = "添加好友邀请消息";
		} else if (LastestChatObjcet.CHAT_ROOM_INVITATION == chatObj.getType()) {
			nickname = "聊天室邀请消息";
		}
		tv_pilcr_nickname.setText(nickname);
		tv_pilcr_status.setText(status);
		HistoryChatRecord chatRecord = historyDb.findLastestChatContent(
				chatObj.getJid(), chatObj.getUsername());
		if (chatRecord != null) {
			tv_pilcr_chat_history_content.setText(StringHelper
					.trimToEmpty(chatRecord.getChatContent()));
			tv_pilcr_chat_date.setText(processDateToHightLightTime(chatRecord
					.getChatTime()));
		}
		return convertView;
	}

}