package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementItemDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistOrderPagerTask;
import itaf.mobile.app.task.netreader.MerchantDistStatementItemPagerTask;
import itaf.mobile.app.task.netreader.MerchantDistStatementTask;
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
import android.support.v4.util.LongSparseArray;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 配送结算条目
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantDistStatementItem extends BaseUIActivity implements
		UIRefresh {

	// 返回按钮
	private Button btn_mdsi_back;
	// 搜索输入
	private EditText et_mdsi_searchkey;
	// 搜索按钮
	private Button btn_mdsi_search;
	// 结算
	private Button btn_mdsi_statement;

	private PullToRefreshListView plv_mdsi_content;
	private MerchantDistStatementItemAdapter adapter;
	private List<BzDistStatementItemDto> adapterContent = new ArrayList<BzDistStatementItemDto>();
	private int currentIndex = 0;
	private int totalCount;

	private String searchKey = "";

	private LongSparseArray<List<Long>> selectSparseArray = new LongSparseArray<List<Long>>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_dist_statement_item);
		initPageAttribute();
		initListView();
		addDistStatementItemPagerTask();
	}

	private void initPageAttribute() {
		btn_mdsi_back = (Button) this.findViewById(R.id.btn_mdsi_back);
		btn_mdsi_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantDistStatementItem.this));
		et_mdsi_searchkey = (EditText) this
				.findViewById(R.id.et_mdsi_searchkey);
		btn_mdsi_search = (Button) this.findViewById(R.id.btn_mdsi_search);
		btn_mdsi_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchKey = getTextViewToString(et_mdsi_searchkey);
				if (StringHelper.isEmpty(searchKey)) {
					ToastHelper.showToast(MerchantDistStatementItem.this,
							"关键字不能为空！");
					return;
				}
				currentIndex = 0;
				addDistStatementItemPagerTask();
			}
		});

		btn_mdsi_statement = (Button) this
				.findViewById(R.id.btn_mdsi_statement);
		btn_mdsi_statement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectSparseArray.size() == 0) {
					Intent intent = new Intent();
					intent.setClass(MerchantDistStatementItem.this,
							MerchantDistStatement.class);
					startActivity(intent);
					return;
				}
				if (selectSparseArray.size() > 1) {
					ToastHelper.showToast(MerchantDistStatementItem.this,
							"一次只能和一个配送商结算！");
					return;
				}
				currentIndex = 0;
				addMerchantDistStatementTask();
			}
		});

	}

	private void initListView() {
		// 分页相关
		plv_mdsi_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_mdsi_content);
		plv_mdsi_content.setShowIndicator(false);// 取消箭头
		adapter = new MerchantDistStatementItemAdapter(
				MerchantDistStatementItem.this, adapterContent);
		plv_mdsi_content.getRefreshableView().setAdapter(adapter);
		plv_mdsi_content
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
						addDistStatementItemPagerTask();
					}
				});

		plv_mdsi_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addDistStatementItemPagerTask();
						}
					}
				});
	}

	private void addDistStatementItemPagerTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(MerchantDistStatementItemPagerTask.TP_KEY_BZ_MERCHANT_ID,
				AppApplication.getInstance().getSessionUser().getId());
		queryMap.put(MerchantDistStatementItemPagerTask.TP_KEY_COMPANY_NAME,
				searchKey);
		queryMap.put(
				MerchantDistStatementItemPagerTask.TP_KEY_STATEMENT_STATUS,
				BzDistStatementItemDto.STATUS_UNPROCESSED);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistOrderPagerTask.TP_QUERY_MAP, queryMap);
		params.put(DistOrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(DistOrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new MerchantDistStatementItemPagerTask(
				MerchantDistStatementItem.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addMerchantDistStatementTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantDistStatementTask.TP_BZ_MERCHANT_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(MerchantDistStatementTask.TP_BZ_DIST_COMPANY_ID,
				selectSparseArray.keyAt(0));
		params.put(MerchantDistStatementTask.TP_BZ_DIST_STATEMENT_ITEM_IDS,
				StringHelper.collectionToString(selectSparseArray
						.get(selectSparseArray.keyAt(0))));
		TaskService.addTask(new MerchantDistStatementTask(
				MerchantDistStatementItem.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (MerchantDistStatementItemPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_mdsi_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistStatementItemDto> result = (WsPageResult<BzDistStatementItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_mdsi_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_mdsi_content.onRefreshComplete();
		} else if (MerchantDistStatementTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzDistStatementItemDto> result = (WsPageResult<BzDistStatementItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "结算异常！");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(this, "结算成功！");
			currentIndex = 0;
			addDistStatementItemPagerTask();
		}
	}

	class MerchantDistStatementItemAdapter extends AbstractBaseAdapter {

		private List<BzDistStatementItemDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			CheckBox cb_mdsia_select;
			TextView tv_mdsia_dist_company_name;
			TextView tv_mdsia_statement_time;
			TextView tv_mdsia_merchant_receivable_amount;
			TextView tv_mdsia_merchant_received_amount;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public MerchantDistStatementItemAdapter(Context context,
				List<BzDistStatementItemDto> data) {
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
						R.layout.merchant_dist_statement_item_adapter, parent,
						false);
				listItemView = new ListItemView();
				listItemView.cb_mdsia_select = (CheckBox) convertView
						.findViewById(R.id.cb_mdsia_select);
				listItemView.tv_mdsia_dist_company_name = (TextView) convertView
						.findViewById(R.id.tv_mdsia_dist_company_name);
				listItemView.tv_mdsia_statement_time = (TextView) convertView
						.findViewById(R.id.tv_mdsia_statement_time);
				listItemView.tv_mdsia_merchant_receivable_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsia_merchant_receivable_amount);
				listItemView.tv_mdsia_merchant_received_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsia_merchant_received_amount);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzDistStatementItemDto target = (BzDistStatementItemDto) listItems
					.get(position);
			listItemView.cb_mdsia_select
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							Long bzDistCompanyId = target.getBzDistCompanyDto()
									.getId();
							if (isChecked) {
								if (selectSparseArray.get(bzDistCompanyId) == null) {
									selectSparseArray.put(bzDistCompanyId,
											new ArrayList<Long>());
								}
								selectSparseArray.get(bzDistCompanyId).add(
										target.getId());
							} else {
								selectSparseArray.get(bzDistCompanyId).remove(
										target.getId());
							}
						}
					});
			listItemView.cb_mdsia_select.setSelected(false);
			listItemView.tv_mdsia_dist_company_name
					.setText(StringHelper.trimToEmpty(target
							.getBzDistCompanyDto().getCompanyName()));
			listItemView.tv_mdsia_statement_time.setText(DateUtil
					.formatDate(target.getStatementTime()));
			listItemView.tv_mdsia_merchant_receivable_amount
					.setText(StringHelper.bigDecimalToRmb(target
							.getMerchantReceivableAmount()));
			listItemView.tv_mdsia_merchant_received_amount.setText(StringHelper
					.bigDecimalToRmb(target.getMerchantReceivedAmount()));
			return convertView;
		}
	}
}
