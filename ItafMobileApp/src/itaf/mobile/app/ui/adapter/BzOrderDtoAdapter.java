package itaf.mobile.app.ui.adapter;

import itaf.framework.order.dto.BzOrderDto;
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
public class BzOrderDtoAdapter extends AbstractBaseAdapter {

	private List<BzOrderDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		public TextView title;
		public TextView source;
		public TextView crawlTime;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public BzOrderDtoAdapter(Context context, List<BzOrderDto> data) {
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
			convertView = listContainer.inflate(R.layout.search_adapter,
					parent, false);
			listItemView = new ListItemView();
			// 获取控件对象
			listItemView.title = (TextView) convertView
					.findViewById(R.id.id_pis_title);
			listItemView.source = (TextView) convertView
					.findViewById(R.id.id_pis_source);
			listItemView.crawlTime = (TextView) convertView
					.findViewById(R.id.id_pis_crawl_time);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		BzOrderDto target = (BzOrderDto) listItems.get(position);
		listItemView.title.setText(StringHelper.trimToEmpty(target
				.getOrderSerialNo()));
		listItemView.source.setText(StringHelper.trimToEmpty(target
				.getOrderSerialNo()));
		listItemView.crawlTime.setText(processDateToHightLightTime(target
				.getCreatedDate()));
		return convertView;

	}

}
