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
package itaf.mobile.app.im.ui.adapter;

import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * 我的好友列表Adapter
 * 
 * @author
 * 
 * @update 2013年10月17日
 */
@Deprecated
public class ImMyFriendsAdapter extends AbstractBaseAdapter {

	public int getCount() {
		//
		return 0;
	}

	public Object getItem(int position) {
		//
		return null;
	}

	public long getItemId(int position) {
		//
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		//
		return null;
	}
	// private Context context;
	// private List<SessionUser> listItems;// 数据集合
	// private LayoutInflater listContainer;// 视图容器
	// private boolean isSelect;// 视图容器
	//
	// static class ListItemView { // 自定义控件集合
	// ImageView iv_pim_headurl;
	// TextView tv_pim_nickname;
	// TextView tv_pim_status;
	// TextView tv_pim_personalsign;
	// TextView tv_pim_chat_time;
	// CheckBox cb_pim_select_friend;
	// }
	//
	// public ImMyFriendsAdapter(Context context, List<SessionUser> data,
	// boolean isSelect) {
	// // 创建视图容器并设置上下文
	// this.context = context;
	// this.listContainer = LayoutInflater.from(context);
	// this.listItems = data;
	// this.isSelect = isSelect;
	// }
	//
	// public int getCount() {
	// return listItems.size();
	// }
	//
	// public Object getItem(int arg0) {
	// return null;
	// }
	//
	// public long getItemId(int arg0) {
	// return 0;
	// }
	//
	// /**
	// * ListView Item设置
	// */
	// public View getView(int position, View convertView, ViewGroup parent) {
	// ListItemView listItemView = null;
	// if (convertView == null) {
	// // 获取list_item布局文件的视图
	// convertView = listContainer.inflate(
	// R.layout.pager_item_im_myfriends, parent, false);
	// listItemView = new ListItemView();
	// listItemView.iv_pim_headurl = (ImageView) convertView
	// .findViewById(R.id.iv_pim_headurl);
	// listItemView.tv_pim_nickname = (TextView) convertView
	// .findViewById(R.id.tv_pim_nickname);
	// listItemView.tv_pim_status = (TextView) convertView
	// .findViewById(R.id.tv_pim_status);
	// listItemView.tv_pim_personalsign = (TextView) convertView
	// .findViewById(R.id.tv_pim_personalsign);
	// listItemView.tv_pim_chat_time = (TextView) convertView
	// .findViewById(R.id.tv_pim_chat_time);
	// listItemView.cb_pim_select_friend = (CheckBox) convertView
	// .findViewById(R.id.cb_pim_select_friend);
	// // 设置控件集到convertView
	// convertView.setTag(listItemView);
	// } else {
	// listItemView = (ListItemView) convertView.getTag();
	// }
	//
	// final SessionUser userDto = (SessionUser) listItems.get(position);
	// // 是不是选择好友
	// if (isSelect) {
	// listItemView.tv_pim_chat_time.setVisibility(View.GONE);
	// listItemView.cb_pim_select_friend.setVisibility(View.VISIBLE);
	// listItemView.cb_pim_select_friend.setChecked(processBoolean(userDto
	// .getChecked()));
	// convertView.setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	//
	// }
	// });
	// } else {
	// listItemView.tv_pim_chat_time.setVisibility(View.VISIBLE);
	// listItemView.cb_pim_select_friend.setVisibility(View.GONE);
	// listItemView.tv_pim_chat_time
	// .setText(processDateToHightLightTime(userDto
	// .getLastLoginDateTime()));
	// convertView.setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	// // 获取用户，跳到单用户聊天室
	// Intent intent = new Intent();
	// intent.putExtra(ImChatWindow.CHAT_JID, userDto.getImUsername());
	// intent.setClass(context, ImChatWindow.class);
	// context.startActivity(intent);
	// }
	// });
	// }
	//
	// listItemView.iv_pim_headurl.setOnClickListener(new OnClickListener() {
	// public void onClick(View v) {
	// Intent intent = new Intent();
	// intent.putExtra(AppConstants.TRANSMIT_USER_NAME,
	// userDto.getUsername());
	// intent.setClass(context, SettingUserInfo.class);
	// context.startActivity(intent);
	// }
	// });
	//
	// if (StringHelper.isNotEmpty(userDto.getHeadUrl())) {
	// listItemView.iv_pim_headurl.setImageURI(Uri.parse(StringHelper
	// .trimToEmpty(userDto.getHeadUrl())));
	// }
	// listItemView.tv_pim_nickname.setText(StringHelper.trimToEmpty(userDto
	// .getNickname()));
	// listItemView.tv_pim_status.setText(StringHelper.trimToEmpty(userDto
	// .getStatus()));
	// listItemView.tv_pim_personalsign.setText(StringHelper
	// .trimToEmpty(userDto.getPersonalSign()));
	//
	// return convertView;
	// }

}
