package itaf.mobile.app.ui.adapter;

import itaf.framework.order.dto.BzPaymentTypeDto;
import itaf.mobile.app.R;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

/**
 * 搜索界面适配
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class PayTypeAdapter extends AbstractBaseAdapter {

	private List<BzPaymentTypeDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		public RadioButton pay_type_name;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public PayTypeAdapter(Context context, List<BzPaymentTypeDto> data) {
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
					R.layout.order_create_pay_type_adapter, parent, false);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.pay_type_name = (RadioButton) convertView
					.findViewById(R.id.rb_ocpta_pay_type);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		BzPaymentTypeDto target = (BzPaymentTypeDto) listItems.get(position);
		listItemView.pay_type_name.setText(StringHelper.trimToEmpty(target
				.getTypeName()));
		return convertView;

	}

}
