package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistStatementAcceptTask;
import itaf.mobile.app.task.netreader.DistStatementRejectTask;
import itaf.mobile.app.task.netreader.MerchantDistStatementPagerTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.math.BigDecimal;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 配送结算批次详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class DistStatementItem extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_dsi_back;
	// 已发起结清
	private Button btn_dsi_processing;
	// 已完结结算
	private Button btn_dsi_processed;
	// 金额小计
	private TextView tv_dsi_receivable_amount;
	// 金额小计
	private TextView tv_dsi_refund_amount;

	private PullToRefreshListView plv_dsi_content;
	private DistStatementAdapter adapter;
	private List<BzDistStatementDto> adapterContent = new ArrayList<BzDistStatementDto>();
	private int currentIndex = 0;
	private int totalCount;

	private Long statementStatus = BzDistStatementDto.STATUS_PROCESSING;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_statement_item);
		initPageAttribute();
		initListView();
		addMerchantDistStatementPagerTask();
	}

	private void initPageAttribute() {
		btn_dsi_back = (Button) this.findViewById(R.id.btn_dsi_back);
		btn_dsi_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistStatementItem.this));
		btn_dsi_processing = (Button) this
				.findViewById(R.id.btn_dsi_processing);
		btn_dsi_processing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentIndex = 0;
				statementStatus = BzDistStatementDto.STATUS_PROCESSING;
				addMerchantDistStatementPagerTask();
			}
		});
		btn_dsi_processed = (Button) this.findViewById(R.id.btn_dsi_processed);
		btn_dsi_processed.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				currentIndex = 0;
				statementStatus = BzDistStatementDto.STATUS_PROCESSED;
				addMerchantDistStatementPagerTask();
			}
		});
		tv_dsi_receivable_amount = (TextView) this
				.findViewById(R.id.tv_dsi_receivable_amount);
		tv_dsi_refund_amount = (TextView) this
				.findViewById(R.id.tv_dsi_refund_amount);
	}

	private void initListView() {
		plv_dsi_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_dsi_content);
		plv_dsi_content.setShowIndicator(false);// 取消箭头
		adapter = new DistStatementAdapter(DistStatementItem.this,
				adapterContent);
		plv_dsi_content.setAdapter(adapter);
		plv_dsi_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addMerchantDistStatementPagerTask();
			}
		});

		plv_dsi_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addMerchantDistStatementPagerTask();
						}
					}
				});
	}

	private void addMerchantDistStatementPagerTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(MerchantDistStatementPagerTask.TP_KEY_BZ_DIST_COMPANY_ID,
				AppApplication.getInstance().getSessionUser().getId());
		queryMap.put(MerchantDistStatementPagerTask.TP_KEY_STATEMENT_STATUS,
				statementStatus);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantDistStatementPagerTask.TP_QUERY_MAP, queryMap);
		params.put(MerchantDistStatementPagerTask.TP_CURRENT_INDEX,
				currentIndex);
		params.put(MerchantDistStatementPagerTask.TP_PAGE_SIZE,
				this.getPageSize());
		TaskService.addTask(new MerchantDistStatementPagerTask(
				DistStatementItem.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addDistStatementAcceptTask(Long bzDistStatementId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistStatementAcceptTask.TP_BZ_DIST_COMPANY_ID,
				AppApplication.getInstance().getSessionUser().getId());
		params.put(DistStatementAcceptTask.TP_BZ_DIST_STATEMENT_ID,
				bzDistStatementId);
		TaskService.addTask(new DistStatementAcceptTask(DistStatementItem.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	private void addDistStatementRejectTask(Long bzDistStatementId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistStatementRejectTask.TP_BZ_DIST_COMPANY_ID,
				AppApplication.getInstance().getSessionUser().getId());
		params.put(DistStatementRejectTask.TP_BZ_DIST_STATEMENT_ID,
				bzDistStatementId);
		TaskService.addTask(new DistStatementRejectTask(DistStatementItem.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (MerchantDistStatementPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_dsi_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistStatementDto> result = (WsPageResult<BzDistStatementDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_dsi_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			tv_dsi_receivable_amount.setText(StringHelper
					.bigDecimalToRmb((BigDecimal) result.getParams().get(
							"receivableAmount")));
			tv_dsi_refund_amount.setText(StringHelper
					.bigDecimalToRmb((BigDecimal) result.getParams().get(
							"refundAmount")));
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_dsi_content.onRefreshComplete();
		} else if (DistStatementAcceptTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzDistStatementDto> result = (WsPageResult<BzDistStatementDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "接受失败！");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			currentIndex = 0;
			addMerchantDistStatementPagerTask();
		} else if (DistStatementRejectTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzDistStatementDto> result = (WsPageResult<BzDistStatementDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "拒绝失败！");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			currentIndex = 0;
			addMerchantDistStatementPagerTask();
		}
	}

	class DistStatementAdapter extends AbstractBaseAdapter {

		private List<BzDistStatementDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			TextView tv_dsia_statement_serial_no;
			Button btn_dsia_reject;
			Button btn_dsia_accept;
			TextView tv_dsia_dist_company_name;
			TextView tv_dsia_statement_time;
			TextView tv_dsia_merchant_receivable_amount;
			TextView tv_dsia_merchant_received_amount;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public DistStatementAdapter(Context context,
				List<BzDistStatementDto> data) {
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
						R.layout.dist_statement_item_adapter, parent, false);
				listItemView = new ListItemView();
				listItemView.tv_dsia_statement_serial_no = (TextView) convertView
						.findViewById(R.id.tv_dsia_statement_serial_no);
				listItemView.btn_dsia_reject = (Button) convertView
						.findViewById(R.id.btn_dsia_reject);
				listItemView.btn_dsia_accept = (Button) convertView
						.findViewById(R.id.btn_dsia_accept);
				listItemView.tv_dsia_dist_company_name = (TextView) convertView
						.findViewById(R.id.tv_dsia_dist_company_name);
				listItemView.tv_dsia_statement_time = (TextView) convertView
						.findViewById(R.id.tv_dsia_statement_time);
				listItemView.tv_dsia_merchant_receivable_amount = (TextView) convertView
						.findViewById(R.id.tv_dsia_merchant_receivable_amount);
				listItemView.tv_dsia_merchant_received_amount = (TextView) convertView
						.findViewById(R.id.tv_dsia_merchant_received_amount);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzDistStatementDto target = (BzDistStatementDto) listItems
					.get(position);
			listItemView.tv_dsia_statement_serial_no.setText(StringHelper
					.trimToEmpty(target.getStatementSerialNo()));
			listItemView.tv_dsia_statement_serial_no
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra(
									MerchantDistStatementDetail.AP_BZ_DIST_STATEMENT_ID,
									target.getId());
							intent.setClass(DistStatementItem.this,
									MerchantDistStatementDetail.class);
							startActivity(intent);
						}
					});
			if (statementStatus.equals(BzDistStatementDto.STATUS_PROCESSING)) {
				listItemView.btn_dsia_reject.setVisibility(View.VISIBLE);
				listItemView.btn_dsia_accept.setVisibility(View.VISIBLE);
			} else {
				listItemView.btn_dsia_reject.setVisibility(View.GONE);
				listItemView.btn_dsia_accept.setVisibility(View.GONE);
			}
			listItemView.btn_dsia_reject
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							addDistStatementRejectTask(target.getId());
						}
					});
			listItemView.btn_dsia_accept
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							addDistStatementAcceptTask(target.getId());
						}
					});
			listItemView.tv_dsia_dist_company_name
					.setText(StringHelper.trimToEmpty(target
							.getBzDistCompanyDto().getCompanyName()));
			listItemView.tv_dsia_statement_time.setText(DateUtil
					.formatDate(target.getStatementTime()));
			listItemView.tv_dsia_merchant_receivable_amount
					.setText(StringHelper.bigDecimalToRmb(target
							.getMerchantReceivableAmount()));
			listItemView.tv_dsia_merchant_received_amount.setText(StringHelper
					.bigDecimalToRmb(target.getMerchantReceivedAmount()));
			return convertView;
		}
	}
}
