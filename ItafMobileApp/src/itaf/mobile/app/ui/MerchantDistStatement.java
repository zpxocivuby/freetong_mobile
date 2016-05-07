package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistOrderPagerTask;
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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 配送结算批次
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantDistStatement extends BaseUIActivity implements UIRefresh {

	// 返回按钮
	private Button btn_mds_back;
	// 搜索输入
	private EditText et_mds_searchkey;
	// 搜索按钮
	private Button btn_mds_search;

	private PullToRefreshListView plv_mds_content;
	private MerchantDistStatementAdapter adapter;
	private List<BzDistStatementDto> adapterContent = new ArrayList<BzDistStatementDto>();
	private int currentIndex = 0;
	private int totalCount;

	private String searchKey = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_dist_statement);
		initPageAttribute();
		initListView();
		addDistStatementPagerTask();
	}

	private void initPageAttribute() {
		btn_mds_back = (Button) this.findViewById(R.id.btn_mds_back);
		btn_mds_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantDistStatement.this));
		et_mds_searchkey = (EditText) this.findViewById(R.id.et_mds_searchkey);
		btn_mds_search = (Button) this.findViewById(R.id.btn_mds_search);
		btn_mds_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchKey = getTextViewToString(et_mds_searchkey);
				if (StringHelper.isEmpty(searchKey)) {
					ToastHelper.showToast(MerchantDistStatement.this,
							"关键字不能为空！");
					return;
				}
				currentIndex = 0;
				addDistStatementPagerTask();
			}
		});

	}

	private void initListView() {
		// 分页相关
		plv_mds_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_mds_content);
		plv_mds_content.setShowIndicator(false);// 取消箭头
		adapter = new MerchantDistStatementAdapter(MerchantDistStatement.this,
				adapterContent);
		plv_mds_content.getRefreshableView().setAdapter(adapter);
		plv_mds_content.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addDistStatementPagerTask();
			}
		});

		plv_mds_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addDistStatementPagerTask();
						}
					}
				});
	}

	private void addDistStatementPagerTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(DistOrderPagerTask.TP_KEY_BZ_MERCHANT_ID, AppApplication
				.getInstance().getSessionUser().getId());
		queryMap.put(DistOrderPagerTask.TP_KEY_COMPANY_NAME, searchKey);
		queryMap.put(DistOrderPagerTask.TP_KEY_ORDER_STATUS,
				BzDistStatementDto.STATUS_PROCESSING);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistOrderPagerTask.TP_QUERY_MAP, queryMap);
		params.put(DistOrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(DistOrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new MerchantDistStatementPagerTask(
				MerchantDistStatement.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (MerchantDistStatementPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_mds_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistStatementDto> result = (WsPageResult<BzDistStatementDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_mds_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_mds_content.onRefreshComplete();
		}
	}

	class MerchantDistStatementAdapter extends AbstractBaseAdapter {

		private List<BzDistStatementDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			TextView tv_mdsa_statement_serial_no;
			TextView tv_mdsa_dist_company_name;
			TextView tv_mdsa_statement_time;
			TextView tv_mdsa_merchant_receivable_amount;
			TextView tv_mdsa_merchant_received_amount;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public MerchantDistStatementAdapter(Context context,
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
				convertView = listContainer
						.inflate(R.layout.merchant_dist_statement_adapter,
								parent, false);
				listItemView = new ListItemView();
				listItemView.tv_mdsa_statement_serial_no = (TextView) convertView
						.findViewById(R.id.tv_mdsa_statement_serial_no);
				listItemView.tv_mdsa_dist_company_name = (TextView) convertView
						.findViewById(R.id.tv_mdsa_dist_company_name);
				listItemView.tv_mdsa_statement_time = (TextView) convertView
						.findViewById(R.id.tv_mdsa_statement_time);
				listItemView.tv_mdsa_merchant_receivable_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsa_merchant_receivable_amount);
				listItemView.tv_mdsa_merchant_received_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsa_merchant_received_amount);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzDistStatementDto target = (BzDistStatementDto) listItems
					.get(position);
			listItemView.tv_mdsa_statement_serial_no.setText(StringHelper
					.trimToEmpty(target.getStatementSerialNo()));
			listItemView.tv_mdsa_statement_serial_no
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra(
									MerchantDistStatementDetail.AP_BZ_DIST_STATEMENT_ID,
									target.getId());
							intent.setClass(MerchantDistStatement.this,
									MerchantDistStatementDetail.class);
							startActivity(intent);
						}
					});
			listItemView.tv_mdsa_dist_company_name
					.setText(StringHelper.trimToEmpty(target
							.getBzDistCompanyDto().getCompanyName()));
			listItemView.tv_mdsa_statement_time.setText(DateUtil
					.formatDate(target.getStatementTime()));
			listItemView.tv_mdsa_merchant_receivable_amount
					.setText(StringHelper.bigDecimalToRmb(target
							.getMerchantReceivableAmount()));
			listItemView.tv_mdsa_merchant_received_amount.setText(StringHelper
					.bigDecimalToRmb(target.getMerchantReceivedAmount()));
			return convertView;
		}
	}
}
