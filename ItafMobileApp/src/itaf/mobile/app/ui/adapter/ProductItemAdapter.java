package itaf.mobile.app.ui.adapter;

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
 * 身边界面适配
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class ProductItemAdapter extends AbstractBaseAdapter {

	private List<BzProductDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView {
		public AsyncImageView iv_msa_product_ico;
		public TextView tv_msa_product_name;
		public TextView tv_msa_merchant_name;
		public TextView tv_msa_shelf_num;
		public TextView tv_msa_dist_range;
		public TextView tv_msa_price;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ProductItemAdapter(BaseActivity context, List<BzProductDto> data) {
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
			convertView = listContainer.inflate(R.layout.product_item_adapter,
					parent, false);
			listItemView = new ListItemView();
			listItemView.iv_msa_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.iv_msa_product_ico);
			listItemView.tv_msa_product_name = (TextView) convertView
					.findViewById(R.id.tv_msa_product_name);
			listItemView.tv_msa_merchant_name = (TextView) convertView
					.findViewById(R.id.tv_msa_merchant_name);
			listItemView.tv_msa_shelf_num = (TextView) convertView
					.findViewById(R.id.tv_msa_shelf_num);
			listItemView.tv_msa_dist_range = (TextView) convertView
					.findViewById(R.id.tv_msa_dist_range);
			listItemView.tv_msa_price = (TextView) convertView
					.findViewById(R.id.tv_msa_price);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		final BzProductDto target = (BzProductDto) listItems.get(position);
		if (target.getBzProductAttachmentIds() != null
				&& target.getBzProductAttachmentIds().size() > 0) {
			listItemView.iv_msa_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			listItemView.iv_msa_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(target.getBzProductAttachmentIds()
							.get(0), AppConstants.IMAGE_SIZE_64X64));
		}
		listItemView.tv_msa_product_name.setText(StringHelper
				.trimToEmpty(target.getProductName()));
		listItemView.tv_msa_merchant_name.setText(StringHelper
				.trimToEmpty(target.getBzMerchantDto().getCompanyName()));
		listItemView.tv_msa_price.setText(StringHelper.bigDecimalToRmb(target
				.getProductPrice()));
		listItemView.tv_msa_shelf_num.setText(StringHelper.longToString(target
				.getShelfNum()));
		listItemView.tv_msa_dist_range.setText(StringHelper.longToKm(target
				.getBzMerchantDto().getServiceCoverage()));
		return convertView;

	}

}
