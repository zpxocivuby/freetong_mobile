package itaf.mobile.app.ui.adapter;

import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.R;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 商家列表适配
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class MerchantListAdapter extends AbstractBaseAdapter {

	private List<BzMerchantDto> listItems;// 数据集合
	private LayoutInflater listContainer;// 视图容器

	static class ListItemView { // 自定义控件集合
		public AsyncImageView aiv_mla_merchant_ico;
		public TextView tv_mla_company_name;
		public TextView tv_mla_merchant_category;
		public TextView tv_mla_service_coverage;
		public RatingBar rb_mla_evaluation;
		public TextView tv_mla_company_address;
	}

	/**
	 * 实例化Adapter
	 * 
	 * @param context
	 * @param data
	 * @param resource
	 */
	public MerchantListAdapter(Context context, List<BzMerchantDto> data) {
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
			convertView = listContainer.inflate(R.layout.merchant_list_adapter,
					parent, false);
			listItemView = new ListItemView();
			listItemView.aiv_mla_merchant_ico = (AsyncImageView) convertView
					.findViewById(R.id.iv_mla_merchant_ico);
			listItemView.tv_mla_company_name = (TextView) convertView
					.findViewById(R.id.tv_mla_company_name);
			listItemView.tv_mla_merchant_category = (TextView) convertView
					.findViewById(R.id.tv_mla_merchant_category);
			listItemView.tv_mla_service_coverage = (TextView) convertView
					.findViewById(R.id.tv_mla_service_coverage);
			listItemView.rb_mla_evaluation = (RatingBar) convertView
					.findViewById(R.id.rb_mla_evaluation);
			listItemView.tv_mla_company_address = (TextView) convertView
					.findViewById(R.id.tv_mla_company_address);
			// 设置控件集到convertView
			convertView.setTag(listItemView);
		} else {
			listItemView = (ListItemView) convertView.getTag();
		}
		// 设置文字和图片
		final BzMerchantDto target = (BzMerchantDto) listItems.get(position);
		if (StringHelper.isNotEmpty(target.getHeadIco())) {
			listItemView.aiv_mla_merchant_ico
					.setDefaultImageResource(R.drawable.async_loader);
			listItemView.aiv_mla_merchant_ico.setPath(DownLoadHelper
					.getHeadIcoPath(target.getHeadIco(),
							AppConstants.IMAGE_SIZE_96X96));
		}

		listItemView.tv_mla_company_name.setText(StringHelper
				.trimToEmpty(target.getCompanyName()));
		listItemView.tv_mla_merchant_category.setText(StringHelper
				.longToString(target.getMerchantCategory()));
		listItemView.tv_mla_service_coverage.setText(StringHelper
				.longToKm(target.getServiceCoverage()));
		listItemView.rb_mla_evaluation
				.setRating(target.getRatingScore() == null ? 0.0f : target
						.getRatingScore());
		listItemView.tv_mla_company_address.setText(StringHelper
				.trimToEmpty(target.getCompanyAddress()));
		return convertView;

	}

}
