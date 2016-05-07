package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductFavoriteDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductFavoritePagerTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.ProductFavoriteAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

/**
 * 商家收藏
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class ProductFavorite extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_pf_back;

	private PullToRefreshListView plv_pf_content;
	private ProductFavoriteAdapter adapter;
	private List<BzProductFavoriteDto> adapterContent = new ArrayList<BzProductFavoriteDto>();
	private int currentIndex = 0;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_favorite);

		initPageAttribute();
		initListView();
	}

	private void initPageAttribute() {
		btn_pf_back = (Button) this.findViewById(R.id.btn_pf_back);
		btn_pf_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductFavorite.this));

	}

	private void initListView() {
		plv_pf_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_pf_content);
		plv_pf_content.setShowIndicator(false);
		adapter = new ProductFavoriteAdapter(ProductFavorite.this,
				adapterContent);
		plv_pf_content.setAdapter(adapter);

		plv_pf_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addProductFavoritePagerTask();
			}
		});

		plv_pf_content
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
								adapterContent.get(position - 1)
										.getBzProductDto().getId());
						intent.setClass(ProductFavorite.this,
								ProductDetail.class);
						startActivity(intent);
					}
				});

		plv_pf_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (adapterContent.size() == 0) {
							return;
						}

						if (currentIndex < totalCount) {
							addProductFavoritePagerTask();
						}
					}
				});

		addProductFavoritePagerTask();

	}

	private void addProductFavoritePagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductFavoritePagerTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(ProductFavoritePagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(ProductFavoritePagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new ProductFavoritePagerTask(ProductFavorite.this,
				params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (ProductFavoritePagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_pf_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzProductFavoriteDto> result = (WsPageResult<BzProductFavoriteDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductFavorite.this, "加载失败");
				} else {
					ToastHelper.showToast(ProductFavorite.this,
							result.getErrorMsg());
				}
				plv_pf_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_pf_content.onRefreshComplete();
			return;
		}
	}

}
