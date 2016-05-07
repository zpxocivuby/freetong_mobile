package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderPagerTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnLastItemVisibleListener;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshExpandableListView;
import itaf.mobile.app.ui.adapter.OrderListAdapter;
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
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

/**
 * 全部订单
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class OrderList extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_ol_back;

	private PullToRefreshExpandableListView elv_ol_content;
	private OrderListAdapter adapter;
	private List<BzOrderDto> adapterContent = new ArrayList<BzOrderDto>();
	private int currentIndex;
	private int totalCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_list);

		initPageAttribute();
		initListView();
		addOrderPagerTask();
	}

	private void initPageAttribute() {
		btn_ol_back = (Button) this.findViewById(R.id.btn_ol_back);
		btn_ol_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderList.this));
	}

	private void initListView() {
		elv_ol_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_ol_content);
		elv_ol_content.setShowIndicator(false);
		adapter = new OrderListAdapter(OrderList.this, adapterContent);
		elv_ol_content.getRefreshableView().setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_ol_content.getRefreshableView().setCacheColorHint(0);

		elv_ol_content
				.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						currentIndex = 0;
						addOrderPagerTask();
					}
				});

		elv_ol_content
				.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
					@Override
					public void onLastItemVisible() {
						if (currentIndex < totalCount) {
							addOrderPagerTask();
						}
					}
				});
		elv_ol_content.getRefreshableView().setOnChildClickListener(
				new OnChildClickListener() {
					@Override
					public boolean onChildClick(ExpandableListView parent,
							View v, int groupPosition, int childPosition,
							long id) {
						Intent intent = new Intent();
						intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
								adapterContent.get(groupPosition)
										.getBzOrderItemDtos()
										.get(childPosition).getBzProductDto()
										.getId());
						intent.setClass(OrderList.this, ProductDetail.class);
						startActivity(intent);
						return false;
					}
				});

	}

	private void addOrderPagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderPagerTask.TP_ROLE_TYPE,
				OrderPagerTask.TYPE_BZ_CONSUMER_ID);
		params.put(OrderPagerTask.TP_ROLE_TYPE_VALUE, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(OrderPagerTask.TP_ORDER_STATUS, BzOrderDto.STATUS_ALL);
		params.put(OrderPagerTask.TP_CURRENT_INDEX, currentIndex);
		params.put(OrderPagerTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new OrderPagerTask(OrderList.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (OrderPagerTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_ol_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderList.this, "加载失败");
				} else {
					ToastHelper.showToast(OrderList.this, result.getErrorMsg());
				}
				elv_ol_content.onRefreshComplete();
				return;
			}
			if (currentIndex == 0) {
				adapterContent.clear();
			}
			totalCount = result.getTotalCount();
			adapterContent.addAll(result.getContent());
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_ol_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_ol_content.getRefreshableView().setSelection(currentIndex);
			currentIndex += getPageSize();
			elv_ol_content.onRefreshComplete();
			return;
		}
	}

}
