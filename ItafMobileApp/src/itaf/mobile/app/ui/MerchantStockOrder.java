package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzStockOrderDto;
import itaf.framework.merchant.dto.BzStockOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.StockOrderFinishedTask;
import itaf.mobile.app.task.netreader.StockOrderListTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
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
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * 备货
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年12月4日
 */
public class MerchantStockOrder extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_mso_back;

	private PullToRefreshExpandableListView elv_mso_content;
	private StockOrderAdapter adapter;
	private List<BzStockOrderDto> adapterContent = new ArrayList<BzStockOrderDto>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_stock_order);
		initPageAttribute();
		initListView();
		addStockOrderListTask();
	}

	private void initPageAttribute() {
		btn_mso_back = (Button) this.findViewById(R.id.btn_mso_back);
		btn_mso_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantStockOrder.this));
	}

	private void initListView() {
		// 增加那个 更多按钮,必须放在Adapter之前
		elv_mso_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_mso_content);
		elv_mso_content.setShowIndicator(false);
		adapter = new StockOrderAdapter(MerchantStockOrder.this, adapterContent);
		elv_mso_content.getRefreshableView().setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_mso_content.getRefreshableView().setCacheColorHint(0);

		elv_mso_content
				.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						addStockOrderListTask();
					}
				});
	}

	private void addStockOrderListTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(StockOrderListTask.TP_BZ_MERCHANT_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new StockOrderListTask(MerchantStockOrder.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	private void addStockOrderFinishedTask(Long bzStockOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(StockOrderFinishedTask.TP_BZ_STOCK_ORDER_ID, bzStockOrderId);
		TaskService.addTask(new StockOrderFinishedTask(MerchantStockOrder.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (StockOrderListTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_mso_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzStockOrderDto> result = (WsPageResult<BzStockOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantStockOrder.this, "发货失败");
				} else {
					ToastHelper.showToast(MerchantStockOrder.this,
							result.getErrorMsg());
				}
				elv_mso_content.onRefreshComplete();
				return;
			}
			adapterContent.clear();
			adapterContent.addAll(result.getContent());
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_mso_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_mso_content.onRefreshComplete();
		} else if (StockOrderFinishedTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzStockOrderDto> result = (WsPageResult<BzStockOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantStockOrder.this, "设置完成失败");
				} else {
					ToastHelper.showToast(MerchantStockOrder.this,
							result.getErrorMsg());
				}
				elv_mso_content.onRefreshComplete();
				return;
			}
			ToastHelper.showToast(MerchantStockOrder.this, "设置完成备货成功！");
			addStockOrderListTask();
		}
	}

	class StockOrderAdapter extends BaseExpandableListAdapter {

		private List<BzStockOrderDto> listItems;// 数据集合
		private LayoutInflater listContainer;

		public StockOrderAdapter(Context context, List<BzStockOrderDto> data) {
			this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = data;
		}

		@Override
		public int getGroupCount() {
			return listItems.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return listItems.get(groupPosition).getBzStockOrderItemDtos()
					.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return listItems.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return listItems.get(groupPosition).getBzStockOrderItemDtos()
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
			final BzStockOrderDto target = (BzStockOrderDto) getGroup(groupPosition);
			convertView = listContainer.inflate(
					R.layout.merchant_stock_order_adapter_group, parent, false);
			TextView tv_msoag_serial_no = (TextView) convertView
					.findViewById(R.id.tv_msoag_serial_no);
			tv_msoag_serial_no.setText(StringHelper.trimToEmpty(target
					.getOrderSerialNo()));
			TextView tv_msoag_edc = (TextView) convertView
					.findViewById(R.id.tv_msoag_edc);
			tv_msoag_edc.setText(DateUtil.formatDate(target.getOrderEdc()));
			Button btn_msoag_finished = (Button) convertView
					.findViewById(R.id.btn_msoag_finished);
			btn_msoag_finished.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addStockOrderFinishedTask(target.getId());
				}
			});
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzStockOrderItemDto target = (BzStockOrderItemDto) getChild(
					groupPosition, childPosition);
			convertView = listContainer.inflate(
					R.layout.merchant_stock_order_adapter_group_item, parent,
					false);
			AsyncImageView aiv_msoagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_msoagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				aiv_msoagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_msoagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_msoagi_product_name = (TextView) convertView
					.findViewById(R.id.tv_msoagi_product_name);
			tv_msoagi_product_name.setText(StringHelper.trimToEmpty(target
					.getBzProductDto().getProductName()));
			TextView tv_msoagi_buy_num = (TextView) convertView
					.findViewById(R.id.tv_msoagi_buy_num);
			tv_msoagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			return convertView;
		}

	}
}