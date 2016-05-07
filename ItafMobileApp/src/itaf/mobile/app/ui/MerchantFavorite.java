package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantFavoriteDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MerchantFavoritePagerTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.MerchantFavoriteAdapter;
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
 * 商品收藏
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantFavorite extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_mf_back;

	private PullToRefreshListView plv_mf_content;
	private MerchantFavoriteAdapter adapter;
	private List<BzMerchantFavoriteDto> adapterContent = new ArrayList<BzMerchantFavoriteDto>();
	private int currentIndex = 0;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_favorite);

		initPageAttribute();
		initListView();
	}

	private void initPageAttribute() {
		btn_mf_back = (Button) this.findViewById(R.id.btn_mf_back);
		btn_mf_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantFavorite.this));

	}

	private void initListView() {
		plv_mf_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_mf_content);
		plv_mf_content.setShowIndicator(false);
		adapter = new MerchantFavoriteAdapter(MerchantFavorite.this,
				adapterContent);
		plv_mf_content.setAdapter(adapter);

		plv_mf_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				currentIndex = 0;
				addMerchantFavoritePagerTask();
			}
		});

		plv_mf_content
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						Intent intent = new Intent();
						intent.putExtra(MerchantDetail.AP_BZ_MERCHANT_ID,
								adapterContent.get(position - 1)
										.getBzMerchantDto().getId());
						intent.setClass(MerchantFavorite.this,
								MerchantDetail.class);
						startActivity(intent);
					}
				});

		plv_mf_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						// 数据为空--不用继续下面代码了
						if (adapterContent.size() == 0) {
							return;
						}

						if (currentIndex < totalCount) {
							addMerchantFavoritePagerTask();
						}
					}
				});

		addMerchantFavoritePagerTask();

	}

	private void addMerchantFavoritePagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantFavoritePagerTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(MerchantFavoritePagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(MerchantFavoritePagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new MerchantFavoritePagerTask(
				MerchantFavorite.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (MerchantFavoritePagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_mf_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzMerchantFavoriteDto> result = (WsPageResult<BzMerchantFavoriteDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantFavorite.this, "加载失败");
				} else {
					ToastHelper.showToast(MerchantFavorite.this,
							result.getErrorMsg());
				}
				plv_mf_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_mf_content.onRefreshComplete();
			return;
		}
	}

}
