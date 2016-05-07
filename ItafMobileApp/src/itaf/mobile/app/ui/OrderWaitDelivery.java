package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderPagerTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshExpandableListView;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

/**
 * 待发货
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class OrderWaitDelivery extends BaseUIActivity implements UIRefresh {

	protected static final int RC_REFRESH = 0;

	// 返回
	private Button btn_owd_back;

	private PullToRefreshExpandableListView elv_owd_content;
	private OrderWaitDeliveryAdapter adapter;
	private List<BzOrderDto> adapterContent = new ArrayList<BzOrderDto>();
	private int currentIndex;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_wait_delivery);
		initPageAttribute();
		initListView();
		addOrderPagerTask();
	}

	private void initPageAttribute() {
		btn_owd_back = (Button) this.findViewById(R.id.btn_owd_back);
		btn_owd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderWaitDelivery.this));
	}

	private void initListView() {
		elv_owd_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_owd_content);
		elv_owd_content.setShowIndicator(false);
		adapter = new OrderWaitDeliveryAdapter(OrderWaitDelivery.this,
				adapterContent);
		elv_owd_content.getRefreshableView().setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_owd_content.getRefreshableView().setCacheColorHint(0);

		elv_owd_content
				.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						currentIndex = 0;
						addOrderPagerTask();
					}
				});

		elv_owd_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addOrderPagerTask();
						}
					}
				});
		elv_owd_content.getRefreshableView().setOnChildClickListener(
				new OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
								adapterContent.get(groupPosition)
										.getBzOrderItemDtos()
										.get(childPosition).getBzProductDto()
										.getId());
						intent.setClass(OrderWaitDelivery.this,
								ProductDetail.class);
						startActivity(intent);
						return false;
					}
				});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_REFRESH) {
				currentIndex = 0;
				addOrderPagerTask();
			}
		}
	}

	private void addOrderPagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderPagerTask.TP_ROLE_TYPE,
				OrderPagerTask.TYPE_BZ_MERCHANT_ID);
		params.put(OrderPagerTask.TP_ROLE_TYPE_VALUE, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(OrderPagerTask.TP_ORDER_STATUS,
				BzOrderDto.STATUS_WAIT_DELIVERY);
		params.put(OrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(OrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new OrderPagerTask(OrderWaitDelivery.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (OrderPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_owd_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderWaitDelivery.this, "加载失败");
				} else {
					ToastHelper.showToast(OrderWaitDelivery.this,
							result.getErrorMsg());
				}
				elv_owd_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			adapterContent.addAll(result.getContent());
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_owd_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_owd_content.getRefreshableView().setSelection(currentIndex);
			currentIndex += getPageSize();
			elv_owd_content.onRefreshComplete();
			return;
		}
	}

	class OrderWaitDeliveryAdapter extends BaseExpandableListAdapter {

		private Context mContext;
		private List<BzOrderDto> listItems;// 数据集合
		private LayoutInflater listContainer;

		public OrderWaitDeliveryAdapter(Context context, List<BzOrderDto> data) {
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
			convertView = listContainer.inflate(
					R.layout.order_wait_delivery_adapter_group, parent, false);
			TextView tv_owdag_serial_no = (TextView) convertView
					.findViewById(R.id.tv_owdag_serial_no);
			tv_owdag_serial_no.setText(StringHelper.trimToEmpty(target
					.getOrderSerialNo()));
			TextView tv_owdag_amount = (TextView) convertView
					.findViewById(R.id.tv_owdag_amount);
			tv_owdag_amount.setText(StringHelper.bigDecimalToRmb(target
					.getOrderAmount()));

			Button btn_owdag_invoice = (Button) convertView
					.findViewById(R.id.btn_owdag_invoice);
			btn_owdag_invoice.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable(OrderAlipay.AP_BZ_ORDER_DTO,
							new SerializableList<BzOrderDto>(target));
					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(mContext, MerchantInvoice.class);
					startActivityForResult(intent, RC_REFRESH);
				}
			});
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzOrderItemDto target = (BzOrderItemDto) getChild(
					groupPosition, childPosition);
			convertView = listContainer.inflate(
					R.layout.order_wait_delivery_adapter_group_item, parent,
					false);
			AsyncImageView aiv_owdagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_owdagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				aiv_owdagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_owdagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_owdagi_product_name = (TextView) convertView
					.findViewById(R.id.tv_owdagi_product_name);
			tv_owdagi_product_name.setText(StringHelper.trimToEmpty(target
					.getBzProductDto().getProductName()));

			TextView tv_owdagi_price = (TextView) convertView
					.findViewById(R.id.tv_owdagi_price);
			tv_owdagi_price.setText(StringHelper.bigDecimalToRmb(target
					.getItemUnitPrice()));
			TextView tv_owdagi_buy_num = (TextView) convertView
					.findViewById(R.id.tv_owdagi_buy_num);
			tv_owdagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			TextView tv_owdagi_price_total = (TextView) convertView
					.findViewById(R.id.tv_owdagi_price_total);
			tv_owdagi_price_total.setText(StringHelper.bigDecimalToRmb(target
					.getItemAmount()));
			return convertView;
		}

	}

}
