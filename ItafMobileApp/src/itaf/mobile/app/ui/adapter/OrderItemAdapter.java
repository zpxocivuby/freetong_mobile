package itaf.mobile.app.ui.adapter;

import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 商品条目商家适配
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class OrderItemAdapter extends AbstractBaseAdapter {

	private List<BzOrderItemDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView {
		public AsyncImageView aiv_odpa_product_ico;
		public TextView tv_odpa_product_name;
		public TextView tv_odpa_price;
		public TextView tv_odpa_buy_num;
		public TextView tv_odpa_amount;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public OrderItemAdapter(BaseActivity context, List<BzOrderItemDto> data) {
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
					R.layout.order_detail_product_adapter, parent, false);
			listItemView = new ListItemView();
			listItemView.aiv_odpa_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_odpa_product_ico);
			listItemView.tv_odpa_product_name = (TextView) convertView
					.findViewById(R.id.tv_odpa_product_name);
			listItemView.tv_odpa_price = (TextView) convertView
					.findViewById(R.id.tv_odpa_price);
			listItemView.tv_odpa_buy_num = (TextView) convertView
					.findViewById(R.id.tv_odpa_buy_num);
			listItemView.tv_odpa_amount = (TextView) convertView
					.findViewById(R.id.tv_odpa_amount);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		final BzOrderItemDto target = (BzOrderItemDto) listItems.get(position);
		BzProductDto bzProductDto = target.getBzProductDto();
		if (bzProductDto.getBzProductAttachmentIds() != null
				&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
			listItemView.aiv_odpa_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			listItemView.aiv_odpa_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(bzProductDto
							.getBzProductAttachmentIds().get(0),
							AppConstants.IMAGE_SIZE_64X64));
		}
		listItemView.tv_odpa_product_name.setText(StringHelper
				.trimToEmpty(target.getBzProductDto().getProductName()));
		listItemView.tv_odpa_price.setText(StringHelper.bigDecimalToRmb(target
				.getItemUnitPrice()));
		listItemView.tv_odpa_buy_num.setText(StringHelper.longToString(target
				.getItemNum()));
		listItemView.tv_odpa_amount.setText(StringHelper.bigDecimalToRmb(target
				.getItemAmount()));
		return convertView;
	}

}
