package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductDeleteTask;
import itaf.mobile.app.task.netreader.ProductRemoveFromShelfTask;
import itaf.mobile.app.task.netreader.SearchProductsTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.ProductItemMechantAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.app.ui.custom.PopProductCategory;
import itaf.mobile.app.ui.custom.PopProductCategory.CategoryItemOnClickListener;
import itaf.mobile.app.util.AlertDialogHelper;
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
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 商品管理
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class ProductManage extends BaseUIActivity implements UIRefresh {

	private static final int RC_REFRESH = 1;

	// 返回
	private Button btn_pm_back;
	// 添加商品
	private Button btn_pm_add;
	// 分类
	private TextView tv_pm_product_category;
	// 商品
	private Button btn_pm_on_sale;
	// 商家
	private Button btn_pm_off_sale;

	private PullToRefreshListView plv_pm_content;
	private ProductItemMechantAdapter adapter;
	private List<BzProductDto> adapterContent = new ArrayList<BzProductDto>();
	private int currentIndex = 0;
	private int totalCount;
	private Long onSale = BzProductDto.SHELF_ON;
	private Long bzProductCategoryId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_manage);

		initPageAttribute();
		initListView();
		addSearchProductsTask();
	}

	private void initPageAttribute() {
		btn_pm_back = (Button) this.findViewById(R.id.btn_pm_back);
		btn_pm_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductManage.this));

		btn_pm_add = (Button) this.findViewById(R.id.btn_pm_add);
		btn_pm_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductManage.this, ProductCreate.class);
				startActivityForResult(intent, RC_REFRESH);
			}
		});

		tv_pm_product_category = (TextView) this
				.findViewById(R.id.tv_pm_product_category);
		tv_pm_product_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopProductCategory popup = new PopProductCategory();
				popup.show(ProductManage.this, R.id.tv_pm_product_category);
				popup.setCategoryItemOnClickListener(new CategoryItemOnClickListener() {
					@Override
					public void callBack(Long dateId) {
						bzProductCategoryId = dateId;
						currentIndex = 0;
						addSearchProductsTask();
					}
				});
			}
		});

		btn_pm_on_sale = (Button) this.findViewById(R.id.btn_pm_on_sale);
		btn_pm_on_sale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_pm_on_sale.getBackground() == getResources()
						.getDrawable(R.drawable.tab_left_red_on)) {
					return;
				}
				btn_pm_on_sale
						.setBackgroundResource(R.drawable.tab_left_red_on);
				btn_pm_off_sale.setBackgroundResource(R.drawable.tab_right_red);
				onSale = BzProductDto.SHELF_ON;
				currentIndex = 0;
				addSearchProductsTask();
			}
		});
		btn_pm_off_sale = (Button) this.findViewById(R.id.btn_pm_off_sale);
		btn_pm_off_sale.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_pm_off_sale.getBackground() == getResources()
						.getDrawable(R.drawable.tab_right_red_on)) {
					return;
				}
				btn_pm_on_sale.setBackgroundResource(R.drawable.tab_left_red);
				btn_pm_off_sale
						.setBackgroundResource(R.drawable.tab_right_red_on);
				onSale = BzProductDto.SHELF_DOWN;
				currentIndex = 0;
				addSearchProductsTask();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_REFRESH) {
				currentIndex = 0;
				addSearchProductsTask();
			}
		}
	}

	private void initListView() {
		plv_pm_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_pm_content);
		plv_pm_content.setShowIndicator(false);// 取消箭头
		adapter = new ProductItemMechantAdapter(ProductManage.this,
				adapterContent);
		plv_pm_content.setAdapter(adapter);

		plv_pm_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
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

		plv_pm_content
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductMerchantDetail.AP_PRODUCT_ID,
								adapterContent.get(position - 1).getId());
						intent.setClass(ProductManage.this,
								ProductMerchantDetail.class);
						startActivity(intent);
					}
				});
		plv_pm_content.getRefreshableView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					@Override
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						showLongClickPop(adapterContent.get(position - 1));
						return false;
					}
				});

		plv_pm_content
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

	private void showLongClickPop(final BzProductDto productDto) {
		List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
		popItems.add(new PopupWindowItem("商品修改", new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(ProductEdit.AP_BZ_PRODUCT_DTO,
						new SerializableList<BzProductDto>(productDto));
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(ProductManage.this, ProductEdit.class);
				startActivityForResult(intent, RC_REFRESH);
				PopCommon.dismiss();
			}
		}));
		if (AppConstants.CHECKED.equals(productDto.getProductOnSale())) {
			popItems.add(new PopupWindowItem("下架", new OnClickListener() {
				@Override
				public void onClick(View v) {
					PopCommon.dismiss();
					AlertDialogHelper.showDialog(ProductManage.this, "通知",
							"确定要下架全部吗？", "确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									addProductRemoveFromShelfTask(productDto
											.getId());
								}
							});

				}
			}));
		} else {
			popItems.add(new PopupWindowItem("删除", new OnClickListener() {
				@Override
				public void onClick(View v) {
					PopCommon.dismiss();
					AlertDialogHelper.showDialog(ProductManage.this, "通知",
							"确定要删除吗？", "确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									addProductDeleteTask(productDto.getId());
								}
							});
				}
			}));
			popItems.add(new PopupWindowItem("上架", new OnClickListener() {
				@Override
				public void onClick(View v) {
					PopCommon.dismiss();
					AlertDialogHelper.showDialog(ProductManage.this, "通知",
							"确定要上架吗？", "确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									Bundle bundle = new Bundle();
									bundle.putSerializable(
											ProductOnShelf.AP_BZ_PRODUCT_DTO,
											new SerializableList<BzProductDto>(
													productDto));
									Intent intent = new Intent();
									intent.putExtras(bundle);
									intent.setClass(ProductManage.this,
											ProductOnShelf.class);
									startActivityForResult(intent, RC_REFRESH);
								}
							});
				}
			}));

		}
		PopCommon.show(ProductManage.this, popItems);
	}

	private void addSearchProductsTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchProductsTask.TP_KEY_BZ_MERCHANT_ID, AppApplication
				.getInstance().getSessionUser().getId());
		queryMap.put(SearchProductsTask.TP_KEY_PRODUCT_ON_SALE, onSale);
		queryMap.put(SearchProductsTask.TP_KEY_BZ_PRODUCT_CATEGORY_ID,
				bzProductCategoryId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchProductsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchProductsTask.TP_CURRENT_INDEX, currentIndex);
		params.put(SearchProductsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchProductsTask(ProductManage.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addProductDeleteTask(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductDeleteTask.TP_BZ_PRODUCT_ID, id);
		TaskService.addTask(new ProductDeleteTask(ProductManage.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addProductRemoveFromShelfTask(Long id) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductRemoveFromShelfTask.TP_BZ_PRODUCT_ID, id);
		TaskService.addTask(new ProductRemoveFromShelfTask(ProductManage.this,
				params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchProductsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_pm_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductManage.this, "加载失败");
				} else {
					ToastHelper.showToast(ProductManage.this,
							result.getErrorMsg());
				}
				plv_pm_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_pm_content.onRefreshComplete();
			return;
		} else if (ProductDeleteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductManage.this, "删除失败");
				} else {
					ToastHelper.showToast(ProductManage.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ProductManage.this, "删除成功");
			currentIndex = 0;
			addSearchProductsTask();
		} else if (ProductRemoveFromShelfTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductManage.this, "下架失败");
				} else {
					ToastHelper.showToast(ProductManage.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ProductManage.this, "下架成功");
			currentIndex = 0;
			addSearchProductsTask();
		}

	}

}
