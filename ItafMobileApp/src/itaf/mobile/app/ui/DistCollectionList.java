package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.framework.merchant.dto.BzDistOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistOrderPagerTask;
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
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * 配送收款列表
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class DistCollectionList extends BaseUIActivity implements UIRefresh {

	protected static final int RC_REFRESH = 0;

	public static final String AP_BZ_DIST_COMPANY_DTO = "BzDistOrderDto";

	// 返回
	private Button btn_dcl_back;

	private PullToRefreshExpandableListView elv_dcl_content;
	private DistCollectionListAdapter adapter;
	private List<BzDistOrderDto> adapterContent = new ArrayList<BzDistOrderDto>();
	private int currentIndex = 0;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_collection_list);
		initPageAttribute();
		initListView();
		addDistOrderPagerTask();
	}

	private void initPageAttribute() {
		btn_dcl_back = (Button) this.findViewById(R.id.btn_dcl_back);
		btn_dcl_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistCollectionList.this));

	}

	private void initListView() {
		// 分页相关
		elv_dcl_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_dcl_content);
		elv_dcl_content.setShowIndicator(false);// 取消箭头
		adapter = new DistCollectionListAdapter(DistCollectionList.this,
				adapterContent);
		elv_dcl_content.getRefreshableView().setAdapter(adapter);
		elv_dcl_content
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

		elv_dcl_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addDistOrderPagerTask();
						}
					}
				});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_REFRESH) {
				currentIndex = 0;
				addDistOrderPagerTask();
			}
		}
	}

	private void addDistOrderPagerTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(DistOrderPagerTask.TP_KEY_BZ_DIST_COMPANY_ID,
				AppApplication.getInstance().getSessionUser().getId());
		queryMap.put(DistOrderPagerTask.TP_KEY_ORDER_STATUS,
				BzDistOrderDto.STATUS_WAIT_COLLECTION);
		queryMap.put(DistOrderPagerTask.TP_KEY_ORDER_TYPE,
				BzDistOrderDto.ORDER_TYPE_TIME);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistOrderPagerTask.TP_QUERY_MAP, queryMap);
		params.put(DistOrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(DistOrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new DistOrderPagerTask(DistCollectionList.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (DistOrderPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_dcl_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistOrderDto> result = (WsPageResult<BzDistOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				elv_dcl_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_dcl_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_dcl_content.onRefreshComplete();
		}
	}

	class DistCollectionListAdapter extends BaseExpandableListAdapter {

		private List<BzDistOrderDto> listItems;// 数据集合
		private LayoutInflater listContainer;

		public DistCollectionListAdapter(Context context,
				List<BzDistOrderDto> data) {
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
					R.layout.dist_collection_list_adapter_group, parent, false);

			Button btn_dclag_chat = (Button) convertView
					.findViewById(R.id.btn_dclag_chat);
			// btn_dclag_chat.setText(target.getBzMerchantDto().getUsername());
			btn_dclag_chat.setText("admin");
			btn_dclag_chat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 聊天
				}
			});

			Button btn_dclag_collection = (Button) convertView
					.findViewById(R.id.btn_dclag_collection);
			btn_dclag_collection.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Bundle bundle = new Bundle();
					bundle.putSerializable(
							DistCollectionDetail.AP_BZ_DIST_ORDER_DTO,
							new SerializableList<BzDistOrderDto>(target));
					Intent intent = new Intent();
					intent.putExtras(bundle);
					intent.setClass(DistCollectionList.this,
							DistCollectionDetail.class);
					startActivityForResult(intent, RC_REFRESH);
				}
			});

			TextView tv_dclag_contact_person = (TextView) convertView
					.findViewById(R.id.tv_dclag_contact_person);
			tv_dclag_contact_person.setText(StringHelper.trimToEmpty(target
					.getDistContactPerson()));
			TextView tv_dclag_contact_no = (TextView) convertView
					.findViewById(R.id.tv_dclag_contact_no);
			tv_dclag_contact_no.setText(StringHelper.trimToEmpty(target
					.getDistContactNo()));
			TextView tv_dclag_dist_address = (TextView) convertView
					.findViewById(R.id.tv_dclag_dist_address);
			tv_dclag_dist_address.setText(StringHelper.trimToEmpty(target
					.getDistAddress()));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzDistOrderItemDto target = (BzDistOrderItemDto) getChild(
					groupPosition, childPosition);
			convertView = listContainer.inflate(
					R.layout.dist_collection_list_adapter_group_item, parent,
					false);
			AsyncImageView aiv_dclagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_dclagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				aiv_dclagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_dclagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_dclagi_product_name = (TextView) convertView
					.findViewById(R.id.tv_dclagi_product_name);
			tv_dclagi_product_name.setText(StringHelper
					.trimToEmpty(bzProductDto.getProductName()));
			TextView tv_dclagi_buy_num = (TextView) convertView
					.findViewById(R.id.tv_dclagi_buy_num);
			tv_dclagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			return convertView;
		}

	}
}
