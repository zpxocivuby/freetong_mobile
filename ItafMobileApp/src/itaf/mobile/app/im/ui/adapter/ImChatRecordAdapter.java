package itaf.mobile.app.im.ui.adapter;

import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.im.bean.HistoryChatRecord;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * IM聊天内容
 * 
 * @author
 * 
 * @update 2013年10月23日
 */
public class ImChatRecordAdapter extends AbstractBaseAdapter {

	private List<HistoryChatRecord> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	private Activity mContext;

	static class ListItemView { // 自定义控件集合
		TextView tv_piic_chat_time;
		ImageView iv_piic_friend_headurl;
		TextView tv_piic_friend_nickname;
		TextView tv_piic_friend_chat_context;
		ImageView iv_piic_headurl;
		TextView tv_piic_nickname;
		TextView tv_piic_chat_context;
		LinearLayout linear_piic_chat_time;
		RelativeLayout relative_piic_chat_me;
		RelativeLayout relative_piic_chat_friend;
	}

	public ImChatRecordAdapter(Activity context, List<HistoryChatRecord> data) {
		this.mContext = context;
		// 创建视图容器并设置上下文
		this.listContainer = LayoutInflater.from(context);
		this.listItems = data;
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		ListItemView listItemView = null;
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.im_chat_adapter,
					parent, false);
			listItemView = new ListItemView();
			listItemView.linear_piic_chat_time = (LinearLayout) convertView
					.findViewById(R.id.linear_piic_chat_time);
			listItemView.iv_piic_friend_headurl = (ImageView) convertView
					.findViewById(R.id.iv_piic_friend_headurl);
			listItemView.tv_piic_friend_nickname = (TextView) convertView
					.findViewById(R.id.tv_piic_friend_nickname);
			listItemView.tv_piic_friend_chat_context = (TextView) convertView
					.findViewById(R.id.tv_piic_friend_chat_context);
			listItemView.relative_piic_chat_me = (RelativeLayout) convertView
					.findViewById(R.id.relative_piic_chat_me);
			listItemView.relative_piic_chat_friend = (RelativeLayout) convertView
					.findViewById(R.id.relative_piic_chat_friend);
			listItemView.iv_piic_headurl = (ImageView) convertView
					.findViewById(R.id.iv_piic_headurl);
			listItemView.tv_piic_nickname = (TextView) convertView
					.findViewById(R.id.tv_piic_nickname);
			listItemView.tv_piic_chat_context = (TextView) convertView
					.findViewById(R.id.tv_piic_chat_context);
			listItemView.tv_piic_chat_time = (TextView) convertView
					.findViewById(R.id.tv_piic_chat_time);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}

		HistoryChatRecord dto = (HistoryChatRecord) listItems.get(position);
		if (position == 0 && dto.getChatTime() != null) {
			listItemView.tv_piic_chat_time
					.setText(processDateToHightLightTime(dto.getChatTime()));
			listItemView.linear_piic_chat_time.setVisibility(View.VISIBLE);
		} else {
			listItemView.linear_piic_chat_time.setVisibility(View.GONE);
		}

		String chatType = dto.getChatType();
		String headUrl = dto.getHeadUrl();
		String nickname = dto.getNickname();
		String chatContent = dto.getChatContent();

		// final String copyContent = chatContent;
		listItemView.tv_piic_friend_chat_context
				.setOnLongClickListener(new OnLongClickListener() {
					public boolean onLongClick(View v) {
						List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
						popItems.add(new PopupWindowItem("复制",
								new OnClickListener() {
									public void onClick(View v) {
										PopCommon.dismiss();
										// ClipboardManager cm =
										// (ClipboardManager) mContext
										// .getSystemService(Context.CLIPBOARD_SERVICE);
										// cm.setPrimaryClip(copyContent);
									}
								}));
						PopCommon.show(mContext, popItems);
						return false;
					}
				});

		listItemView.tv_piic_chat_context
				.setOnLongClickListener(new OnLongClickListener() {
					public boolean onLongClick(View v) {
						List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
						popItems.add(new PopupWindowItem("复制",
								new OnClickListener() {
									public void onClick(View v) {
										PopCommon.dismiss();
										// ClipboardManager cm =
										// (ClipboardManager) mContext
										// .getSystemService(Context.CLIPBOARD_SERVICE);
										// cm.setPrimaryClip(copyContent);
									}
								}));
						PopCommon.show(mContext, popItems);
						return false;
					}
				});

		if (HistoryChatRecord.CHAT_TYPE_FROM.equals(chatType)) {
			if (StringHelper.isNotEmpty(headUrl)) {
				listItemView.iv_piic_headurl.setImageURI(Uri.parse(StringHelper
						.trimToEmpty(dto.getHeadUrl())));
			}
			listItemView.tv_piic_nickname.setText(nickname + ":");
			listItemView.tv_piic_chat_context.setText(chatContent);
			listItemView.relative_piic_chat_me.setVisibility(View.VISIBLE);
			listItemView.relative_piic_chat_friend.setVisibility(View.GONE);
		} else {
			if (StringHelper.isNotEmpty(headUrl)) {
				listItemView.iv_piic_friend_headurl.setImageURI(Uri
						.parse(StringHelper.trimToEmpty(headUrl)));
			}
			listItemView.tv_piic_friend_nickname.setText(":" + nickname);
			listItemView.tv_piic_friend_chat_context.setText(chatContent);
			listItemView.relative_piic_chat_friend.setVisibility(View.VISIBLE);
			listItemView.relative_piic_chat_me.setVisibility(View.GONE);
		}
		return convertView;
	}
}
