package itaf.mobile.app.ui.adapter;

import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.R;

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
public class UserDeliveryAddressAdapter extends AbstractBaseAdapter {

	private List<BzUserDeliveryAddressDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		TextView tv_udaa_contact_person;
		TextView tv_udaa_contact_no;
		TextView tv_udaa_address;
		TextView tv_udaa_postcode;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public UserDeliveryAddressAdapter(Context context,
			List<BzUserDeliveryAddressDto> data) {
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
			convertView = listContainer.inflate(
					R.layout.user_delivery_address_adapter, parent, false);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.tv_udaa_contact_person = (TextView) convertView
					.findViewById(R.id.tv_udaa_contact_person);
			listItemView.tv_udaa_contact_no = (TextView) convertView
					.findViewById(R.id.tv_udaa_contact_no);
			listItemView.tv_udaa_address = (TextView) convertView
					.findViewById(R.id.tv_udaa_address);
			listItemView.tv_udaa_postcode = (TextView) convertView
					.findViewById(R.id.tv_udaa_postcode);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		BzUserDeliveryAddressDto target = (BzUserDeliveryAddressDto) listItems
				.get(position);
		listItemView.tv_udaa_contact_person.setText(target.getContactPerson());
		listItemView.tv_udaa_contact_no.setText(target.getContactNo());
		listItemView.tv_udaa_address.setText(target.getAddress());
		listItemView.tv_udaa_postcode.setText(target.getPostcode());
		return convertView;

	}

}
