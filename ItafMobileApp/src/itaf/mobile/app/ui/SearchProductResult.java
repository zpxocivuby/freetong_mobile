package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SearchProductsTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.ProductItemAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopProductCategory;
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
import android.widget.ListView;
import android.widget.TextView;

/**
 * 商品列表
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class SearchProductResult extends BaseUIActivity implements UIRefresh {

	public static final String AP_PRODUCT_NAME = "productName";
	public static final String AP_BZ_MERCHANT_ID = "bzMerchantId";
	public static final String AP_COMPANY_NAME = "companyName";

	// 返回
	private Button btn_spr_back;
	// 查询关键字
	private TextView tv_spr_searck_key;
	// 商品分类
	private TextView tv_spr_product_category;

	private PullToRefreshListView plv_spr_content;
	private ProductItemAdapter adapter;
	private List<BzProductDto> adapterContent = new ArrayList<BzProductDto>();
	private int currentIndex = 0;
	private int totalCount;
	// 根据商品名称检索
	private String productName;
	// 检索该商家的所有商品
	private Long bzMerchantId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_product_result);
		initPageAttribute();
		initListView();

		productName = this.getIntent().getStringExtra(AP_PRODUCT_NAME);
		if (StringHelper.isNotEmpty(productName)) {
			tv_spr_searck_key.setText(productName);
		}
		bzMerchantId = this.getIntent().getLongExtra(AP_BZ_MERCHANT_ID, 0);
		String companyName = this.getIntent().getStringExtra(AP_COMPANY_NAME);
		if (StringHelper.isNotEmpty(companyName)) {
			tv_spr_searck_key.setText(companyName);
		}
		addSearchProductsTask();
	}

	private void initPageAttribute() {
		btn_spr_back = (Button) this.findViewById(R.id.btn_spr_back);
		btn_spr_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(SearchProductResult.this));
		tv_spr_product_category = (TextView) this
				.findViewById(R.id.tv_spr_product_category);
		tv_spr_product_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopProductCategory popup = new PopProductCategory();
				popup.show(SearchProductResult.this,
						R.id.tv_spr_product_category);
			}
		});
		tv_spr_searck_key = (TextView) this
				.findViewById(R.id.tv_spr_searck_key);
	}

	private void initListView() {
		plv_spr_content = (PullToRefreshListView) findViewById(R.id.plv_spr_content);
		plv_spr_content.setShowIndicator(false);// 取消箭头
		adapter = new ProductItemAdapter(SearchProductResult.this,
				adapterContent);
		plv_spr_content.setAdapter(adapter);

		plv_spr_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addSearchProductsTask();
			}
		});

		plv_spr_content
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
								adapterContent.get(position - 1).getId());
						intent.setClass(SearchProductResult.this,
								ProductDetail.class);
						startActivity(intent);
					}
				});
		plv_spr_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (adapterContent.size() == 0) {
							return;
						}

						if (currentIndex < totalCount) {
							addSearchProductsTask();
						}
					}
				});
	}

	private void addSearchProductsTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchProductsTask.TP_KEY_PRODUCT_ON_SALE,
				BzProductDto.SHELF_ON);
		if (StringHelper.isNotEmpty(productName)) {
			queryMap.put(SearchProductsTask.TP_KEY_PRODUCT_NAME, productName);
		}
		if (bzMerchantId != null && bzMerchantId > 0) {
			queryMap.put(SearchProductsTask.TP_KEY_BZ_MERCHANT_ID, bzMerchantId);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchProductsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchProductsTask.TP_CURRENT_INDEX, currentIndex);
		params.put(SearchProductsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchProductsTask(SearchProductResult.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchProductsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_spr_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(SearchProductResult.this, "加载失败");
				} else {
					ToastHelper.showToast(SearchProductResult.this,
							result.getErrorMsg());
				}
				plv_spr_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_spr_content.onRefreshComplete();
			return;
		}
	}
}
