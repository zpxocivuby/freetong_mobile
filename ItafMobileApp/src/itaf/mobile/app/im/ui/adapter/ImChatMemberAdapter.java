package itaf.mobile.app.im.ui.adapter;

import itaf.mobile.app.R;
import itaf.mobile.app.im.bean.ImUserInfo;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 聊天的好友列表
 * 
 * @author
 * 
 * @update 2013年10月25日
 */
public class ImChatMemberAdapter extends AbstractBaseAdapter {

	// private Context mContext;
	private List<ImUserInfo> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		ImageView iv_piicm_headurl;
		TextView tv_piicm_chat_member;
	}

	public ImChatMemberAdapter(Context context, List<ImUserInfo> data) {
		// this.mContext = context;
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
			convertView = listContainer.inflate(
					R.layout.im_chat_member_adapter, parent, false);
			listItemView = new ListItemView();
			listItemView.iv_piicm_headurl = (ImageView) convertView
					.findViewById(R.id.iv_piicm_headurl);
			listItemView.tv_piicm_chat_member = (TextView) convertView
					.findViewById(R.id.tv_piicm_chat_member);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		final ImUserInfo userDto = (ImUserInfo) listItems.get(position);
		convertView.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Intent intent = new Intent();
				// intent.putExtra(AppConstants.TRANSMIT_USER_NAME,
				// userDto.getUsername());
				// intent.setClass(mContext, SettingUserInfo.class);
				// mContext.startActivity(intent);
			}
		});

		listItemView.tv_piicm_chat_member.setText(userDto.getNickname() + "("
				+ userDto.getUsername() + ")");
		if (StringHelper.isNotEmpty(userDto.getHeadUrl())) {
			// TODO 获取头像
			listItemView.iv_piicm_headurl.setImageURI(Uri.parse(StringHelper
					.trimToEmpty(userDto.getHeadUrl())));
		}
		return convertView;
	}
}
