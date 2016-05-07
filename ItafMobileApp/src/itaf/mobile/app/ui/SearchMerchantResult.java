package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SearchMerchantsTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.MerchantListAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopServiceProviderType;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 商家列表
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class SearchMerchantResult extends BaseUIActivity implements UIRefresh {

	public static final String AP_SEARCH_KEY = "searchKey";

	// 返回
	private Button btn_smr_back;
	// 查询关键字
	private TextView tv_smr_searck_key;
	// 商品分类
	private TextView tv_smr_merchant_category;
	// 选择地图
	private ImageView iv_smr_select_map;
	// 选择列表
	private ImageView iv_smr_select_list;

	private PullToRefreshListView plv_smr_content;
	private MerchantListAdapter adapter;
	private List<BzMerchantDto> adapterContent = new ArrayList<BzMerchantDto>();

	private int currentIndex = 0;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_merchant_result);
		initPageAttribute();
		initListView();
		String searchKey = this.getIntent().getExtras()
				.getString(AP_SEARCH_KEY);
		tv_smr_searck_key.setText(searchKey);
		addSearchMerchantsTask();
	}

	private void initPageAttribute() {
		btn_smr_back = (Button) this.findViewById(R.id.btn_smr_back);
		btn_smr_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(SearchMerchantResult.this));
		tv_smr_merchant_category = (TextView) this
				.findViewById(R.id.tv_smr_merchant_category);
		tv_smr_merchant_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopServiceProviderType popup = new PopServiceProviderType();
				popup.show(SearchMerchantResult.this,
						R.id.tv_smr_merchant_category);
			}
		});
		tv_smr_searck_key = (TextView) this
				.findViewById(R.id.tv_smr_searck_key);
		iv_smr_select_map = (ImageView) this
				.findViewById(R.id.iv_smr_select_map);
		iv_smr_select_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(MerchantMap.AP_SEARCH_KEY,
						getTextViewToString(tv_smr_searck_key));
				intent.setClass(SearchMerchantResult.this, MerchantMap.class);
				startActivity(intent);
			}
		});

		iv_smr_select_list = (ImageView) this
				.findViewById(R.id.iv_smr_select_list);
		iv_smr_select_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing
			}
		});
	}

	private void initListView() {
		plv_smr_content = (PullToRefreshListView) findViewById(R.id.plv_smr_content);
		plv_smr_content.setShowIndicator(false);// 取消箭头
		adapter = new MerchantListAdapter(SearchMerchantResult.this,
				adapterContent);
		plv_smr_content.setAdapter(adapter);

		plv_smr_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addSearchMerchantsTask();
			}
		});

		plv_smr_content
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(MerchantDetail.AP_BZ_MERCHANT_ID,
								adapterContent.get(position - 1).getId());
						intent.setClass(SearchMerchantResult.this,
								MerchantDetail.class);
						startActivity(intent);
					}
				});
		plv_smr_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (adapterContent.size() == 0) {
							return;
						}

						if (currentIndex < totalCount) {
							addSearchMerchantsTask();
						}
					}
				});

	}

	private void addSearchMerchantsTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchMerchantsTask.TP_KEY_COMPANY_NAME,
				getTextViewToString(tv_smr_searck_key));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchMerchantsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchMerchantsTask.TP_CURRENT_INDEX, currentIndex);
		params.put(SearchMerchantsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchMerchantsTask(SearchMerchantResult.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchMerchantsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_smr_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(SearchMerchantResult.this, "加载失败");
				} else {
					ToastHelper.showToast(SearchMerchantResult.this,
							result.getErrorMsg());
				}
				plv_smr_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_smr_content.onRefreshComplete();
			return;
		}
	}
}
