package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderRefundDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MerchantDistStatementPagerTask;
import itaf.mobile.app.task.netreader.OrderRefundAcceptTask;
import itaf.mobile.app.task.netreader.OrderRefundPagerTask;
import itaf.mobile.app.task.netreader.OrderRefundRejectTask;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
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
 * 商家处理退款单
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantProcessRefundOrder extends BaseUIActivity implements
		UIRefresh {

	// 返回
	private Button btn_mpro_back;
	// 已发起结清
	private Button btn_mpro_ayyly;
	// 已完结结算
	private Button btn_mpro_result;

	private PullToRefreshListView plv_mpro_content;
	private MerchantProcessRefundOrderAdapter adapter;
	private List<BzOrderRefundDto> adapterContent = new ArrayList<BzOrderRefundDto>();
	private int currentIndex = 0;
	private int totalCount;

	private Long refundStatus = BzOrderRefundDto.STATUS_AYYLY;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_process_refund_order);
		initPageAttribute();
		initListView();
		addOrderRefundPagerTask();
	}

	private void initPageAttribute() {
		btn_mpro_back = (Button) this.findViewById(R.id.btn_mpro_back);
		btn_mpro_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantProcessRefundOrder.this));
		btn_mpro_ayyly = (Button) this.findViewById(R.id.btn_mpro_ayyly);
		btn_mpro_ayyly.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refundStatus = BzOrderRefundDto.STATUS_AYYLY;
				currentIndex = 0;
				addOrderRefundPagerTask();
			}
		});
		btn_mpro_result = (Button) this.findViewById(R.id.btn_mpro_result);
		btn_mpro_result.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				refundStatus = BzOrderRefundDto.STATUS_ACCEPT_REJECT;
				currentIndex = 0;
				addOrderRefundPagerTask();
			}
		});
	}

	private void initListView() {
		plv_mpro_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_mpro_content);
		plv_mpro_content.setShowIndicator(false);// 取消箭头
		adapter = new MerchantProcessRefundOrderAdapter(
				MerchantProcessRefundOrder.this, adapterContent);
		plv_mpro_content.setAdapter(adapter);
		plv_mpro_content
				.setOnRefreshListener(new OnRefreshListener<ListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
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
						addOrderRefundPagerTask();
					}
				});

		plv_mpro_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addOrderRefundPagerTask();
						}
					}
				});
	}

	private void addOrderRefundPagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderRefundPagerTask.TP_ROLE_TYPE,
				OrderRefundPagerTask.TYPE_BZ_MERCHANT_ID);
		params.put(OrderRefundPagerTask.TP_ROLE_TYPE_VALUE, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(OrderRefundPagerTask.TP_REFUND_STATUS, refundStatus);
		params.put(MerchantDistStatementPagerTask.TP_CURRENT_INDEX,
				currentIndex);
		params.put(MerchantDistStatementPagerTask.TP_PAGE_SIZE,
				this.getPageSize());
		TaskService.addTask(new OrderRefundPagerTask(
				MerchantProcessRefundOrder.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addOrderRefundAcceptTask(Long bzOrderRefundId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderRefundAcceptTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(OrderRefundAcceptTask.TP_BZ_ORDER_REFUND_ID, bzOrderRefundId);
		TaskService.addTask(new OrderRefundAcceptTask(
				MerchantProcessRefundOrder.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addOrderRefundRejectTask(Long bzOrderRefundId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderRefundRejectTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(OrderRefundRejectTask.TP_BZ_ORDER_REFUND_ID, bzOrderRefundId);
		TaskService.addTask(new OrderRefundRejectTask(
				MerchantProcessRefundOrder.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (OrderRefundPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_mpro_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzOrderRefundDto> result = (WsPageResult<BzOrderRefundDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_mpro_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_mpro_content.onRefreshComplete();
		} else if (OrderRefundAcceptTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzOrderRefundDto> result = (WsPageResult<BzOrderRefundDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			currentIndex = 0;
			addOrderRefundPagerTask();
		} else if (OrderRefundRejectTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzOrderRefundDto> result = (WsPageResult<BzOrderRefundDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			currentIndex = 0;
			addOrderRefundPagerTask();
		}
	}

	class MerchantProcessRefundOrderAdapter extends AbstractBaseAdapter {

		private List<BzOrderRefundDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			TextView tv_mproa_serial_no;
			Button btn_mproa_reject;
			Button btn_mproa_accept;
			TextView tv_mproa_username;
			TextView tv_mproa_create_time;
			TextView tv_mproa_order_serial_no;
			TextView tv_mproa_refund_amount;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public MerchantProcessRefundOrderAdapter(Context context,
				List<BzOrderRefundDto> data) {
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
						R.layout.merchant_process_refund_order_adapter, parent,
						false);
				listItemView = new ListItemView();
				listItemView.tv_mproa_serial_no = (TextView) convertView
						.findViewById(R.id.tv_mproa_serial_no);
				listItemView.btn_mproa_reject = (Button) convertView
						.findViewById(R.id.btn_mproa_reject);
				listItemView.btn_mproa_accept = (Button) convertView
						.findViewById(R.id.btn_mproa_accept);
				listItemView.tv_mproa_username = (TextView) convertView
						.findViewById(R.id.tv_mproa_username);
				listItemView.tv_mproa_create_time = (TextView) convertView
						.findViewById(R.id.tv_mproa_create_time);
				listItemView.tv_mproa_order_serial_no = (TextView) convertView
						.findViewById(R.id.tv_mproa_order_serial_no);
				listItemView.tv_mproa_refund_amount = (TextView) convertView
						.findViewById(R.id.tv_mproa_refund_amount);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzOrderRefundDto target = (BzOrderRefundDto) listItems
					.get(position);
			listItemView.tv_mproa_serial_no.setText(StringHelper
					.trimToEmpty(target.getRefundSerialNo()));
			listItemView.btn_mproa_reject
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							addOrderRefundRejectTask(target.getId());
						}
					});
			listItemView.btn_mproa_accept
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							addOrderRefundAcceptTask(target.getId());
						}
					});
			listItemView.tv_mproa_username.setText(StringHelper
					.trimToEmpty(target.getBzConsumerDto().getUsername()));
			listItemView.tv_mproa_create_time.setText(DateUtil
					.formatDate(target.getCreatedDate()));
			listItemView.tv_mproa_order_serial_no.setText(StringHelper
					.trimToEmpty(target.getBzOrderDto().getOrderSerialNo()));
			listItemView.tv_mproa_refund_amount.setText(StringHelper
					.bigDecimalToRmb(target.getRefundAmount()));
			return convertView;
		}
	}
}
