package itaf.mobile.app.ui.adapter;

import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.im.ui.ImChatWindow;
import itaf.mobile.app.ui.OrderDetail;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

public class OrderListAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private List<BzOrderDto> listItems;// 数据集合
	private LayoutInflater listContainer;

	public OrderListAdapter(Context context, List<BzOrderDto> data) {
		this.mContext = context;
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
		convertView = listContainer.inflate(R.layout.order_list_adapter_group,
				parent, false);
		TextView tv_olag_serial_no = (TextView) convertView
				.findViewById(R.id.tv_olag_serial_no);
		tv_olag_serial_no.setText(StringHelper.trimToEmpty(target
				.getOrderSerialNo()));
		tv_olag_serial_no.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(OrderDetail.AP_BZ_ORDER_ID, target.getId());
				intent.setClass(mContext, OrderDetail.class);
				mContext.startActivity(intent);
			}
		});
		TextView tv_olag_status = (TextView) convertView
				.findViewById(R.id.tv_olag_status);
		tv_olag_status.setText(StringHelper.trimToEmpty(target
				.getOrderStatusString()));
		Button btn_olag_chat = (Button) convertView
				.findViewById(R.id.btn_olag_chat);
		btn_olag_chat.setText(StringHelper.trimToEmpty(target
				.getBzConsumerDto().getUsername()));
		btn_olag_chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(ImChatWindow.CHAT_JID, target
						.getBzConsumerDto().getUsername());
				intent.setClass(mContext, ImChatWindow.class);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		final BzOrderItemDto target = (BzOrderItemDto) getChild(groupPosition,
				childPosition);
		convertView = listContainer.inflate(
				R.layout.order_list_adapter_group_item, parent, false);
		AsyncImageView aiv_olagi_product_ico = (AsyncImageView) convertView
				.findViewById(R.id.aiv_olagi_product_ico);
		BzProductDto bzProductDto = target.getBzProductDto();
		if (bzProductDto.getBzProductAttachmentIds() != null
				&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
			aiv_olagi_product_ico
					.setDefaultImageResource(R.drawable.async_loader);
			aiv_olagi_product_ico.setPath(DownLoadHelper
					.getDownloadProductPath(bzProductDto
							.getBzProductAttachmentIds().get(0),
							AppConstants.IMAGE_SIZE_96X96));
		}
		TextView tv_olagi_product_name = (TextView) convertView
				.findViewById(R.id.tv_olagi_product_name);
		tv_olagi_product_name.setText(StringHelper.trimToEmpty(target
				.getBzProductDto().getProductName()));

		TextView tv_olagi_price = (TextView) convertView
				.findViewById(R.id.tv_olagi_price);
		tv_olagi_price.setText(StringHelper.bigDecimalToRmb(target
				.getItemUnitPrice()));
		TextView tv_olagi_buy_num = (TextView) convertView
				.findViewById(R.id.tv_olagi_buy_num);
		tv_olagi_buy_num
				.setText(StringHelper.longToString(target.getItemNum()));
		TextView tv_olagi_price_total = (TextView) convertView
				.findViewById(R.id.tv_olagi_price_total);
		tv_olagi_price_total.setText(StringHelper.bigDecimalToRmb(target
				.getItemAmount()));
		return convertView;
	}

}