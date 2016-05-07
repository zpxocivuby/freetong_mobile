package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SearchDistCompanyTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 选择配送商列表
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class MerchantSelectDistList extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_DIST_COMPANY_DTO = "BzDistCompanyDto";

	// 返回
	private Button btn_msdl_back;
	// 搜索关键字
	private EditText et_msdl_searchkey;
	// 搜索
	private Button btn_msdl_search;
	// 距离排序
	private Button btn_msdl_distance;
	// 时间排序
	private Button btn_msdl_credit;
	// 地图
	private ImageView iv_msdl_select_map;
	// 列表
	private ImageView iv_msdl_select_list;

	private PullToRefreshListView plv_msdl_content;
	private SelectDistAdapter adapter;
	private List<BzDistCompanyDto> adapterContent = new ArrayList<BzDistCompanyDto>();
	private int currentIndex = 0;
	private int totalCount;

	private Long orderType = BzDistCompanyDto.ORDER_TYPE_DISTANCE;
	private String searchKey = "";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_select_dist_list);
		initPageAttribute();
		initListView();
		addSearchDistCompanyTask();
	}

	private void initPageAttribute() {
		btn_msdl_back = (Button) this.findViewById(R.id.btn_msdl_back);
		btn_msdl_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantSelectDistList.this));

		et_msdl_searchkey = (EditText) this
				.findViewById(R.id.et_msdl_searchkey);

		btn_msdl_search = (Button) this.findViewById(R.id.btn_msdl_search);
		btn_msdl_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				searchKey = getTextViewToString(et_msdl_searchkey);
				currentIndex = 0;
				addSearchDistCompanyTask();
			}
		});

		btn_msdl_distance = (Button) this.findViewById(R.id.btn_msdl_distance);
		btn_msdl_distance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_msdl_distance.getBackground() == getResources()
						.getDrawable(R.drawable.tab_left_red_on)) {
					return;
				}
				btn_msdl_distance
						.setBackgroundResource(R.drawable.tab_left_red_on);
				btn_msdl_credit.setBackgroundResource(R.drawable.tab_right_red);
				orderType = BzDistCompanyDto.ORDER_TYPE_DISTANCE;
				currentIndex = 0;
				addSearchDistCompanyTask();
			}
		});

		btn_msdl_credit = (Button) this.findViewById(R.id.btn_msdl_credit);
		btn_msdl_credit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_msdl_credit.getBackground() == getResources()
						.getDrawable(R.drawable.tab_right_red_on)) {
					return;
				}
				btn_msdl_distance
						.setBackgroundResource(R.drawable.tab_left_red);
				btn_msdl_credit
						.setBackgroundResource(R.drawable.tab_right_red_on);
				orderType = BzDistCompanyDto.ORDER_TYPE_CREDIT;
				currentIndex = 0;
				addSearchDistCompanyTask();
			}
		});

		iv_msdl_select_map = (ImageView) this
				.findViewById(R.id.iv_msdl_select_map);
		iv_msdl_select_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MerchantSelectDistList.this, MerchantMap.class);
				startActivity(intent);
			}
		});

		iv_msdl_select_list = (ImageView) this
				.findViewById(R.id.iv_msdl_select_list);
		iv_msdl_select_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing
			}
		});

	}

	private void initListView() {
		// 分页相关
		plv_msdl_content = (PullToRefreshListView) this
				.findViewById(R.id.plv_msdl_content);
		plv_msdl_content.setShowIndicator(false);// 取消箭头
		adapter = new SelectDistAdapter(MerchantSelectDistList.this,
				adapterContent);
		plv_msdl_content.setAdapter(adapter);

		plv_msdl_content
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
						addSearchDistCompanyTask();
					}
				});

		plv_msdl_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addSearchDistCompanyTask();
						}
					}
				});

	}

	private void addSearchDistCompanyTask() {
		Map<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchDistCompanyTask.TP_KEY_COMPANY_NAME, searchKey);
		queryMap.put(SearchDistCompanyTask.TP_KEY_SERVICE_COVERAGE, null);
		queryMap.put(SearchDistCompanyTask.TP_KEY_ORDER_TYPE, orderType);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchDistCompanyTask.TP_QUERY_MAP, queryMap);
		params.put(SearchDistCompanyTask.CURRENT_INDEX, currentIndex);
		params.put(SearchDistCompanyTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchDistCompanyTask(
				MerchantSelectDistList.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchDistCompanyTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_msdl_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzDistCompanyDto> result = (WsPageResult<BzDistCompanyDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_msdl_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			currentIndex = result.getCurrentIndex() + getPageSize();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_msdl_content.onRefreshComplete();
			return;
		}
	}

	class SelectDistAdapter extends AbstractBaseAdapter {

		private List<BzDistCompanyDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView {
			AsyncImageView aiv_msdla_merchant_ico;
			TextView tv_msdla_company_name;
			TextView tv_msdla_service_coverage;
			TextView tv_msdla_company_address;
			Button tv_msdla_select;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public SelectDistAdapter(Context context, List<BzDistCompanyDto> data) {
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
						R.layout.merchant_select_dist_list_adapter, parent,
						false);
				listItemView = new ListItemView();
				listItemView.aiv_msdla_merchant_ico = (AsyncImageView) convertView
						.findViewById(R.id.aiv_msdla_merchant_ico);
				listItemView.tv_msdla_company_name = (TextView) convertView
						.findViewById(R.id.tv_msdla_company_name);
				listItemView.tv_msdla_service_coverage = (TextView) convertView
						.findViewById(R.id.tv_msdla_service_coverage);
				listItemView.tv_msdla_company_address = (TextView) convertView
						.findViewById(R.id.tv_msdla_company_address);
				listItemView.tv_msdla_select = (Button) convertView
						.findViewById(R.id.tv_msdla_select);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzDistCompanyDto target = (BzDistCompanyDto) listItems
					.get(position);
			if (StringHelper.isNotEmpty(target.getHeadIco())) {
				listItemView.aiv_msdla_merchant_ico
						.setDefaultImageResource(R.drawable.async_loader);
				listItemView.aiv_msdla_merchant_ico.setPath(DownLoadHelper
						.getHeadIcoPath(target.getHeadIco(),
								AppConstants.IMAGE_SIZE_64X64));
			}
			listItemView.tv_msdla_company_name.setText(StringHelper
					.trimToEmpty(target.getCompanyName()));
			listItemView.tv_msdla_service_coverage.setText(StringHelper
					.longToKm(target.getServiceCoverage()));
			listItemView.tv_msdla_company_address.setText(StringHelper
					.trimToEmpty(target.getCompanyAddress()));
			listItemView.tv_msdla_select
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Bundle mbundle = new Bundle();
							mbundle.putSerializable(AP_BZ_DIST_COMPANY_DTO,
									new SerializableList<BzDistCompanyDto>(
											target));
							Intent returnIntent = new Intent();
							returnIntent.putExtras(mbundle);
							setResult(Activity.RESULT_OK, returnIntent);
							AppActivityManager.getInstance().finishActivity(
									MerchantSelectDistList.this);
						}
					});
			return convertView;

		}

	}
}
