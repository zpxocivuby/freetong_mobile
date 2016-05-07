package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.framework.merchant.dto.BzDistOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistOrderPagerTask;
import itaf.mobile.app.task.netreader.DistStartTask;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 配送订单列表
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class DistOrderList extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_dol_back;
	// 距离排序
	private Button btn_dol_distance;
	// 时间排序
	private Button btn_dol_credit;
	// 地图
	private ImageView iv_dol_select_map;
	// 列表
	private ImageView iv_dol_select_list;

	private PullToRefreshExpandableListView elv_dol_content;
	private DistOrderListAdapter adapter;
	private List<BzDistOrderDto> adapterContent = new ArrayList<BzDistOrderDto>();
	private int currentIndex = 0;
	private int totalCount;

	private int orderType = BzDistOrderDto.ORDER_TYPE_DISTANCE;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_order_list);
		initPageAttribute();
		initListView();
		addDistOrderPagerTask();
	}

	private void initPageAttribute() {
		btn_dol_back = (Button) this.findViewById(R.id.btn_dol_back);
		btn_dol_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistOrderList.this));

		btn_dol_distance = (Button) this.findViewById(R.id.btn_dol_distance);
		btn_dol_distance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_dol_distance.getBackground() == getResources()
						.getDrawable(R.drawable.tab_left_red_on)) {
					return;
				}
				btn_dol_distance
						.setBackgroundResource(R.drawable.tab_left_red_on);
				btn_dol_credit.setBackgroundResource(R.drawable.tab_right_red);
				orderType = BzDistOrderDto.ORDER_TYPE_DISTANCE;
				currentIndex = 0;
				addDistOrderPagerTask();
			}
		});

		btn_dol_credit = (Button) this.findViewById(R.id.btn_dol_credit);
		btn_dol_credit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_dol_credit.getBackground() == getResources()
						.getDrawable(R.drawable.tab_right_red_on)) {
					return;
				}
				btn_dol_distance.setBackgroundResource(R.drawable.tab_left_red);
				btn_dol_credit
						.setBackgroundResource(R.drawable.tab_right_red_on);
				orderType = BzDistOrderDto.ORDER_TYPE_CREDIT;
				currentIndex = 0;
				addDistOrderPagerTask();
			}
		});

		iv_dol_select_map = (ImageView) this
				.findViewById(R.id.iv_dol_select_map);
		iv_dol_select_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DistOrderList.this, MerchantMap.class);
				startActivity(intent);
			}
		});

		iv_dol_select_list = (ImageView) this
				.findViewById(R.id.iv_dol_select_list);
		iv_dol_select_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing
			}
		});

	}

	private void initListView() {
		// 分页相关
		elv_dol_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_dol_content);
		elv_dol_content.setShowIndicator(false);// 取消箭头
		adapter = new DistOrderListAdapter(DistOrderList.this, adapterContent);
		elv_dol_content.getRefreshableView().setAdapter(adapter);
		elv_dol_content
				.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						currentIndex = 0;
						addDistOrderPagerTask();
					}
				});

		elv_dol_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addDistOrderPagerTask();
						}
					}
				});
	}

	private void addDistOrderPagerTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(DistOrderPagerTask.TP_KEY_BZ_DIST_COMPANY_ID,
				AppApplication.getInstance().getSessionUser().getId());
		queryMap.put(DistOrderPagerTask.TP_KEY_ORDER_STATUS,
				BzDistOrderDto.STATUS_WAIT_DIST);
		queryMap.put(DistOrderPagerTask.TP_KEY_ORDER_TYPE, orderType);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistOrderPagerTask.TP_QUERY_MAP, queryMap);
		params.put(DistOrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(DistOrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new DistOrderPagerTask(DistOrderList.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addDistStartTask(Long bzDistOrderId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistStartTask.TP_BZ_DIST_ORDER_ID, bzDistOrderId);
		TaskService.addTask(new DistStartTask(DistOrderList.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (DistOrderPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_dol_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistOrderDto> result = (WsPageResult<BzDistOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				elv_dol_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_dol_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_dol_content.onRefreshComplete();
		} else if (DistStartTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzDistOrderDto> result = (WsPageResult<BzDistOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "开始配送失败！");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			currentIndex = 0;
			addDistOrderPagerTask();
		}
	}

	class DistOrderListAdapter extends BaseExpandableListAdapter {

		private List<BzDistOrderDto> listItems;// 数据集合
		private LayoutInflater listContainer;

		public DistOrderListAdapter(Context context, List<BzDistOrderDto> data) {
			this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = data;
		}

		@Override
		public int getGroupCount() {
			return listItems.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return listItems.get(groupPosition).getBzDistOrderItemDtos().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return listItems.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return listItems.get(groupPosition).getBzDistOrderItemDtos()
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
			final BzDistOrderDto target = (BzDistOrderDto) getGroup(groupPosition);
			convertView = listContainer.inflate(
					R.layout.dist_order_list_adapter_group, parent, false);

			Button btn_dolag_chat = (Button) convertView
					.findViewById(R.id.btn_dolag_chat);
			btn_dolag_chat.setText(StringHelper.trimToEmpty(target
					.getBzMerchantDto().getUsername()));
			btn_dolag_chat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 聊天
				}
			});

			Button btn_dolag_dist = (Button) convertView
					.findViewById(R.id.btn_dolag_dist);
			btn_dolag_dist.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					addDistStartTask(target.getId());
				}
			});

			TextView tv_dolag_contact_person = (TextView) convertView
					.findViewById(R.id.tv_dolag_contact_person);
			tv_dolag_contact_person.setText(StringHelper.trimToEmpty(target
					.getDistContactPerson()));
			TextView tv_dolag_contact_no = (TextView) convertView
					.findViewById(R.id.tv_dolag_contact_no);
			tv_dolag_contact_no.setText(StringHelper.trimToEmpty(target
					.getDistContactNo()));
			TextView tv_dolag_dist_address = (TextView) convertView
					.findViewById(R.id.tv_dolag_dist_address);
			tv_dolag_dist_address.setText(StringHelper.trimToEmpty(target
					.getDistAddress()));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzDistOrderItemDto target = (BzDistOrderItemDto) getChild(
					groupPosition, childPosition);
			convertView = listContainer.inflate(
					R.layout.dist_order_list_adapter_group_item, parent, false);
			AsyncImageView aiv_dolagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_dolagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				aiv_dolagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_dolagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_dolagi_product_name = (TextView) convertView
					.findViewById(R.id.tv_dolagi_product_name);
			tv_dolagi_product_name.setText(StringHelper.trimToEmpty(target
					.getBzProductDto().getProductName()));
			TextView tv_dolagi_buy_num = (TextView) convertView
					.findViewById(R.id.tv_dolagi_buy_num);
			tv_dolagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			return convertView;
		}

	}
}
