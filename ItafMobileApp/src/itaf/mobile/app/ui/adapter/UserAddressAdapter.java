package itaf.mobile.app.ui.adapter;

import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 搜索界面适配
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class UserAddressAdapter extends AbstractBaseAdapter {

	private List<BzUserAddressDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		TextView tv_uaa_contact_person;
		TextView tv_uaa_contact_no;
		TextView tv_uaa_address;
		TextView tv_uaa_postcode;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public UserAddressAdapter(Context context, List<BzUserAddressDto> data) {
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = data;
	}

	public int getCount() {
		return listItems.size();
	}

	public Object getItem(int position) {
		return this.listItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	/**
	 * ListView Item设置
	 */
	public View getView(int position, View convertView, ViewGroup parent) {
		// 自定义视图
		ListItemView listItemView = null;
		if (convertView == null) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(R.layout.user_address_adapter,
					parent, false);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.tv_uaa_contact_person = (TextView) convertView
					.findViewById(R.id.tv_uaa_contact_person);
			listItemView.tv_uaa_contact_no = (TextView) convertView
					.findViewById(R.id.tv_uaa_contact_no);
			listItemView.tv_uaa_address = (TextView) convertView
					.findViewById(R.id.tv_uaa_address);
			listItemView.tv_uaa_postcode = (TextView) convertView
					.findViewById(R.id.tv_uaa_postcode);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		BzUserAddressDto target = (BzUserAddressDto) listItems.get(position);
		listItemView.tv_uaa_contact_person.setText(StringHelper
				.trimToEmpty(target.getContactPerson()));
		listItemView.tv_uaa_contact_no.setText(StringHelper.trimToEmpty(target
				.getContactNo()));
		listItemView.tv_uaa_address.setText(StringHelper.trimToEmpty(target
				.getAddress()));
		listItemView.tv_uaa_postcode.setText(StringHelper.trimToEmpty(target
				.getPostcode()));
		return convertView;

	}

}
