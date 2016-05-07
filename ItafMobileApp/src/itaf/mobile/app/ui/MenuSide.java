package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SearchMerchantsTask;
import itaf.mobile.app.task.netreader.SearchProductsTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.MerchantListAdapter;
import itaf.mobile.app.ui.adapter.ProductItemAdapter;
import itaf.mobile.app.ui.base.BaseLocationMapActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopProductCategory;
import itaf.mobile.app.ui.custom.PopServiceProviderType;
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

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * 身边
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class MenuSide extends BaseLocationMapActivity implements UIRefresh {

	// 商品
	private Button btn_ms_product;
	// 商家
	private Button btn_ms_marchant;
	// 搜索
	private ImageView iv_ms_search;
	// 商品分类
	private TextView tv_ms_product_category;
	// 商家分类
	private TextView tv_ms_merchant_category;
	// 选择地图
	private ImageView iv_ms_select_map;
	// 选择列表
	private ImageView iv_ms_select_list;

	// 商品分页数据
	private PullToRefreshListView plv_ms_content_product;
	private ProductItemAdapter productAdapter;
	private List<BzProductDto> productAdapterContent = new ArrayList<BzProductDto>();
	private int productCurrentIndex = 0;
	private int productTotalCount;

	// 商家分页数据
	private PullToRefreshListView plv_ms_content_merchant;
	private MerchantListAdapter merchantAdapter;
	private List<BzMerchantDto> merchantAdapterContent = new ArrayList<BzMerchantDto>();
	private int merchantCurrentIndex = 0;
	private int merchantTotalCount;

	private int type = 1;

	private BDLocation location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_side);
		initPageAttribute();
		initProductListView();
		initMerchantListView();
	}

	private void initPageAttribute() {
		btn_ms_product = (Button) this.findViewById(R.id.btn_ms_product);
		btn_ms_product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_ms_product.getBackground() == getResources()
						.getDrawable(R.drawable.tab_left_red_on)) {
					return;
				}
				btn_ms_product
						.setBackgroundResource(R.drawable.tab_left_red_on);
				btn_ms_marchant.setBackgroundResource(R.drawable.tab_right_red);
				plv_ms_content_product.setVisibility(View.VISIBLE);
				plv_ms_content_merchant.setVisibility(View.GONE);
				tv_ms_product_category.setVisibility(View.VISIBLE);
				tv_ms_merchant_category.setVisibility(View.GONE);
				addSearchProductsTask();
				type = 1;
			}
		});
		btn_ms_marchant = (Button) this.findViewById(R.id.btn_ms_marchant);
		btn_ms_marchant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_ms_marchant.getBackground() == getResources()
						.getDrawable(R.drawable.tab_right_red_on)) {
					return;
				}
				btn_ms_product.setBackgroundResource(R.drawable.tab_left_red);
				btn_ms_marchant
						.setBackgroundResource(R.drawable.tab_right_red_on);
				plv_ms_content_product.setVisibility(View.GONE);
				plv_ms_content_merchant.setVisibility(View.VISIBLE);
				tv_ms_product_category.setVisibility(View.GONE);
				tv_ms_merchant_category.setVisibility(View.VISIBLE);
				addSearchMerchantsTask();
				type = 2;
			}
		});

		iv_ms_search = (ImageView) this.findViewById(R.id.iv_ms_search);
		iv_ms_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuSide.this, SearchProductOrMerchant.class);
				startActivity(intent);
			}
		});

		tv_ms_product_category = (TextView) this
				.findViewById(R.id.tv_ms_product_category);
		tv_ms_product_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopProductCategory popup = new PopProductCategory();
				popup.show(MenuSide.this, R.id.tv_ms_product_category);
			}
		});
		tv_ms_merchant_category = (TextView) this
				.findViewById(R.id.tv_ms_merchant_category);
		tv_ms_merchant_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopServiceProviderType popup = new PopServiceProviderType();
				popup.show(MenuSide.this, R.id.tv_ms_merchant_category);
			}
		});
		iv_ms_select_map = (ImageView) this.findViewById(R.id.iv_ms_select_map);
		iv_ms_select_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuSide.this, MerchantMap.class);
				startActivity(intent);
			}
		});

		iv_ms_select_list = (ImageView) this
				.findViewById(R.id.iv_ms_select_list);
		iv_ms_select_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing but must have
			}
		});

	}

	private void initProductListView() {
		plv_ms_content_product = (PullToRefreshListView) findViewById(R.id.plv_ms_content_product);
		plv_ms_content_product.setShowIndicator(false);// 取消箭头
		productAdapter = new ProductItemAdapter(MenuSide.this,
				productAdapterContent);
		plv_ms_content_product.setAdapter(productAdapter);

		plv_ms_content_product
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
						productCurrentIndex = 0;
						addSearchProductsTask();
					}
				});

		plv_ms_content_product
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
								productAdapterContent.get(position - 1).getId());
						intent.setClass(MenuSide.this, ProductDetail.class);
						startActivity(intent);
					}
				});
		plv_ms_content_product
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (productAdapterContent.size() == 0) {
							return;
						}

						if (productCurrentIndex < productTotalCount) {
							addSearchProductsTask();
						}
					}
				});

	}

	private void initMerchantListView() {
		plv_ms_content_merchant = (PullToRefreshListView) findViewById(R.id.plv_ms_content_merchant);
		plv_ms_content_merchant.setShowIndicator(false);// 取消箭头
		merchantAdapter = new MerchantListAdapter(MenuSide.this,
				merchantAdapterContent);
		plv_ms_content_merchant.setAdapter(merchantAdapter);

		plv_ms_content_merchant
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
						merchantCurrentIndex = 0;
						addSearchMerchantsTask();
					}
				});

		plv_ms_content_merchant
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(MerchantDetail.AP_BZ_MERCHANT_ID,
								merchantAdapterContent.get(position - 1)
										.getId());
						intent.setClass(MenuSide.this, MerchantDetail.class);
						startActivity(intent);
					}
				});
		plv_ms_content_merchant
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (merchantAdapterContent.size() == 0) {
							return;
						}

						if (merchantCurrentIndex < merchantTotalCount) {
							addSearchMerchantsTask();
						}
					}
				});

	}

	private void addSearchProductsTask() {
		if (location == null) {
			ToastHelper.showToast(MenuSide.this, "正在定位中...");
			return;
		}
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchProductsTask.TP_KEY_PRODUCT_ON_SALE,
				BzProductDto.SHELF_ON);
		queryMap.put(SearchProductsTask.TP_KEY_POSITION_X,
				location.getLongitude());
		queryMap.put(SearchProductsTask.TP_KEY_POSITION_Y,
				location.getLatitude());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchProductsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchProductsTask.TP_CURRENT_INDEX, productCurrentIndex);
		params.put(SearchProductsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchProductsTask(MenuSide.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addSearchMerchantsTask() {
		if (location == null) {
			ToastHelper.showToast(MenuSide.this, "正在定位中...");
			return;
		}
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchMerchantsTask.TP_KEY_POSITION_X,
				location.getLongitude());
		queryMap.put(SearchMerchantsTask.TP_KEY_POSITION_Y,
				location.getLatitude());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchMerchantsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchMerchantsTask.TP_CURRENT_INDEX, merchantCurrentIndex);
		params.put(SearchMerchantsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchMerchantsTask(MenuSide.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchProductsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_ms_content_product.onRefreshComplete();
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuSide.this, "加载失败");
				} else {
					ToastHelper.showToast(MenuSide.this, result.getErrorMsg());
				}
				plv_ms_content_product.onRefreshComplete();
				return;
			}
			if (productCurrentIndex == 0) {
				productAdapterContent.clear();
			}
			productTotalCount = result.getTotalCount();
			productCurrentIndex = result.getCurrentIndex() + getPageSize();
			productAdapterContent.addAll(result.getContent());
			productAdapter.notifyDataSetChanged();
			plv_ms_content_product.onRefreshComplete();
			return;
		} else if (SearchMerchantsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_ms_content_merchant.onRefreshComplete();
				return;
			}
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuSide.this, "加载失败");
				} else {
					ToastHelper.showToast(MenuSide.this, result.getErrorMsg());
				}
				plv_ms_content_merchant.onRefreshComplete();
				return;
			}
			if (merchantCurrentIndex == 0) {
				merchantAdapterContent.clear();
			}
			merchantTotalCount = result.getTotalCount();
			merchantCurrentIndex = result.getCurrentIndex() + getPageSize();
			merchantAdapterContent.addAll(result.getContent());
			merchantAdapter.notifyDataSetChanged();
			plv_ms_content_merchant.onRefreshComplete();
			return;
		}
	}

	@Override
	protected BDLocationListener getDbLocationListener() {
		return new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				location = bDLocation;
				if (type == 1) {
					addSearchProductsTask();
				} else {
					addSearchMerchantsTask();
				}
			}
		};
	}
}
