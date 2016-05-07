package itaf.mobile.app.ui.adapter;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;

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
public class ProductItemMechantAdapter extends AbstractBaseAdapter {

	private List<BzProductDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	private ProductCategoryDb db;

	static class ListItemView {
		public AsyncImageView iv_pima_product_ico;
		public TextView tv_pima_product_name;
		public TextView tv_pima_category;
		public TextView tv_pima_price;
		public TextView tv_pima_stock_num;
		public TextView tv_pima_shelf_num;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ProductItemMechantAdapter(BaseActivity context,
			List<BzProductDto> data) {
		db = new ProductCategoryDb(context);
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
					R.layout.product_item_merchant_adapter, parent, false);
			listItemView = new ListItemView();
			listItemView.iv_pima_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.iv_pima_product_ico);
			listItemView.tv_pima_product_name = (TextView) convertView
					.findViewById(R.id.tv_pima_product_name);
			listItemView.tv_pima_category = (TextView) convertView
					.findViewById(R.id.tv_pima_category);
			listItemView.tv_pima_price = (TextView) convertView
					.findViewById(R.id.tv_pima_price);
			listItemView.tv_pima_stock_num = (TextView) convertView
					.findViewById(R.id.tv_pima_stock_num);
			listItemView.tv_pima_shelf_num = (TextView) convertView
					.findViewById(R.id.tv_pima_shelf_num);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		final BzProductDto target = (BzProductDto) listItems.get(position);
		if (target.getBzProductAttachmentIds() != null
				&& target.getBzProductAttachmentIds().size() > 0) {
			listItemView.iv_pima_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			listItemView.iv_pima_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(target.getBzProductAttachmentIds()
							.get(0), AppConstants.IMAGE_SIZE_96X96));
		}
		listItemView.tv_pima_product_name.setText(StringHelper
				.trimToEmpty(target.getProductName()));
		String categoryName = "";
		for (Long id : target.getBzProductCategoryIds()) {
			BzProductCategoryDto dto = db.findById(id);
			categoryName += dto.getCategoryName() + ";";
		}
		listItemView.tv_pima_category.setText(categoryName);
		listItemView.tv_pima_price.setText(StringHelper.bigDecimalToRmb(target
				.getProductPrice()));
		listItemView.tv_pima_stock_num.setText(StringHelper.longToString(target
				.getStockNum()));
		listItemView.tv_pima_shelf_num.setText(StringHelper.longToString(target
				.getShelfNum()));
		return convertView;

	}

}
