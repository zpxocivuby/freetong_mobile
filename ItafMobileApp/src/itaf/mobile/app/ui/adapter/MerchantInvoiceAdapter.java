package itaf.mobile.app.ui.adapter;

import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
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
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class MerchantInvoiceAdapter extends BaseExpandableListAdapter {

	private List<BzOrderDto> listItems;// 数据集合
	private LayoutInflater listContainer;

	public MerchantInvoiceAdapter(Context context, List<BzOrderDto> data) {
		this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
		this.listItems = data;
	}

	@Override
	public int getGroupCount() {
		return listItems.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return listItems.get(groupPosition).getBzOrderItemDtos().size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listItems.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return listItems.get(groupPosition).getBzOrderItemDtos()
				.get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		final BzOrderDto target = (BzOrderDto) getGroup(groupPosition);
		convertView = listContainer.inflate(
				R.layout.merchant_invoice_adapter_group, parent, false);
		TextView tv_miag_serial_no = (TextView) convertView
				.findViewById(R.id.tv_miag_serial_no);
		tv_miag_serial_no.setText(StringHelper.trimToEmpty(target
				.getOrderSerialNo()));
		TextView tv_miag_amount = (TextView) convertView
				.findViewById(R.id.tv_miag_amount);
		tv_miag_amount.setText(StringHelper.bigDecimalToRmb(target
				.getOrderAmount()));

		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final BzOrderItemDto target = (BzOrderItemDto) getChild(groupPosition,
				childPosition);
		convertView = listContainer.inflate(
				R.layout.merchant_invoice_adapter_group_item, parent, false);
		AsyncImageView aiv_miagi_product_ico = (AsyncImageView) convertView
				.findViewById(R.id.aiv_miagi_product_ico);
		BzProductDto bzProductDto = target.getBzProductDto();
		if (bzProductDto.getBzProductAttachmentIds() != null
				&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
			aiv_miagi_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			aiv_miagi_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(bzProductDto
							.getBzProductAttachmentIds().get(0),
							AppConstants.IMAGE_SIZE_64X64));
		}
		TextView tv_miagi_product_name = (TextView) convertView
				.findViewById(R.id.tv_miagi_product_name);
		tv_miagi_product_name.setText(StringHelper.trimToEmpty(target
				.getBzProductDto().getProductName()));

		TextView tv_miagi_price = (TextView) convertView
				.findViewById(R.id.tv_miagi_price);
		tv_miagi_price.setText(StringHelper.bigDecimalToRmb(target
				.getItemUnitPrice()));
		TextView tv_miagi_buy_num = (TextView) convertView
				.findViewById(R.id.tv_miagi_buy_num);
		tv_miagi_buy_num
				.setText(StringHelper.longToString(target.getItemNum()));
		TextView tv_miagi_price_total = (TextView) convertView
				.findViewById(R.id.tv_miagi_price_total);
		tv_miagi_price_total.setText(StringHelper.bigDecimalToRmb(target
				.getItemAmount()));
		return convertView;
	}

}