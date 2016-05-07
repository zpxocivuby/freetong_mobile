package itaf.mobile.app.ui.adapter;

import itaf.framework.product.dto.BzProductDto;
import itaf.framework.product.dto.BzProductFavoriteDto;
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
public class ProductFavoriteAdapter extends AbstractBaseAdapter {

	private List<BzProductFavoriteDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView {
		AsyncImageView aiv_pfa_product_ico;
		TextView tv_pfa_product_name;
		TextView tv_pfa_merchant_name;
		TextView tv_pfa_shelf_num;
		TextView tv_pfa_dist_range;
		TextView tv_pfa_price;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public ProductFavoriteAdapter(BaseActivity context,
			List<BzProductFavoriteDto> data) {
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
					R.layout.product_favorite_adapter, parent, false);
			listItemView = new ListItemView();
			listItemView.aiv_pfa_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_pfa_product_ico);
			listItemView.tv_pfa_product_name = (TextView) convertView
					.findViewById(R.id.tv_pfa_product_name);
			listItemView.tv_pfa_merchant_name = (TextView) convertView
					.findViewById(R.id.tv_pfa_merchant_name);
			listItemView.tv_pfa_shelf_num = (TextView) convertView
					.findViewById(R.id.tv_pfa_shelf_num);
			listItemView.tv_pfa_dist_range = (TextView) convertView
					.findViewById(R.id.tv_pfa_dist_range);
			listItemView.tv_pfa_price = (TextView) convertView
					.findViewById(R.id.tv_pfa_price);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		final BzProductFavoriteDto target = (BzProductFavoriteDto) listItems
				.get(position);
		final BzProductDto productDto = target.getBzProductDto();
		BzProductDto bzProductDto = target.getBzProductDto();
		if (bzProductDto.getBzProductAttachmentIds() != null
				&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
			listItemView.aiv_pfa_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			listItemView.aiv_pfa_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(bzProductDto
							.getBzProductAttachmentIds().get(0),
							AppConstants.IMAGE_SIZE_96X96));
		}
		listItemView.tv_pfa_product_name.setText(StringHelper
				.trimToEmpty(productDto.getProductName()));
		listItemView.tv_pfa_merchant_name.setText(StringHelper
				.trimToEmpty(productDto.getBzMerchantDto().getCompanyName()));
		// TODO 没有数据
		listItemView.tv_pfa_shelf_num.setText(StringHelper
				.longToString(productDto.getShelfNum()));
		listItemView.tv_pfa_price.setText(StringHelper
				.bigDecimalToRmb(productDto.getProductPrice()));
		listItemView.tv_pfa_dist_range.setText(StringHelper.longToKm(productDto
				.getBzMerchantDto().getServiceCoverage()));
		return convertView;

	}

}
