package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserDeliveryAddressDeleteTask;
import itaf.mobile.app.task.netreader.UserDeliveryAddressListTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.UserDeliveryAddressAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.app.util.AlertDialogHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

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
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 用户发货地址
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserDeliveryAddress extends BaseUIActivity implements UIRefresh {

	private static final int RC_REFRESH = 1;

	// 返回按钮
	private Button btn_uda_back;
	// 保存按钮
	private Button btn_uda_add;
	// 用户好友列表
	private PullToRefreshListView plv_uda_content;
	private UserDeliveryAddressAdapter adapter;
	private List<BzUserDeliveryAddressDto> adapterContent = new ArrayList<BzUserDeliveryAddressDto>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_delivery_address);

		initPageAttribute();
		initListView();

		addUserDeliveryAddressListTask();
	}

	private void initPageAttribute() {
		btn_uda_back = (Button) this.findViewById(R.id.btn_uda_back);
		btn_uda_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserDeliveryAddress.this));

		btn_uda_add = (Button) this.findViewById(R.id.btn_uda_add);
		btn_uda_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserDeliveryAddress.this,
						UserDeliveryAddressCreate.class);
				startActivityForResult(intent, RC_REFRESH);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_REFRESH) {
				addUserDeliveryAddressListTask();
			}
		}
	}

	private void initListView() {
		// ListView初始化
		plv_uda_content = (PullToRefreshListView) findViewById(R.id.plv_uda_content);
		plv_uda_content.setShowIndicator(false);// 取消箭头
		adapter = new UserDeliveryAddressAdapter(UserDeliveryAddress.this,
				adapterContent);
		plv_uda_content.setAdapter(adapter);
		plv_uda_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				addUserDeliveryAddressListTask();
			}
		});

		plv_uda_content.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BzUserDeliveryAddressDto dto = adapterContent.get(position - 1);
				Intent intent = new Intent();
				intent.putExtra(
						UserDeliveryAddressDetail.AP_BZ_USER_DELIVERY_ADDRESS_ID,
						dto.getId());
				intent.setClass(UserDeliveryAddress.this,
						UserDeliveryAddressDetail.class);
				startActivity(intent);
			}
		});

		plv_uda_content.getRefreshableView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						BzUserDeliveryAddressDto dto = adapterContent
								.get(position - 1);
						final Long addressId = dto.getId();
						showPopCommon(addressId);
						return false;
					}

				});

	}

	private void showPopCommon(final Long addressId) {
		List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
		popItems.add(new PopupWindowItem("修改", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				Intent intent = new Intent();
				intent.putExtra(
						UserDeliveryAddressEdit.AP_BZ_USER_DELIVERY_ADDRESS_ID,
						addressId);
				intent.setClass(UserDeliveryAddress.this,
						UserDeliveryAddressEdit.class);
				startActivityForResult(intent, RC_REFRESH);
			}
		}));
		popItems.add(new PopupWindowItem("删除", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				AlertDialogHelper.showDialog(UserDeliveryAddress.this, "提醒",
						"确认要删除吗？", "确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								addUserDeliveryAddressDeleteTask(addressId);
							}

						});
			}

		}));
		PopCommon.show(UserDeliveryAddress.this, popItems);
	}

	private void addUserDeliveryAddressDeleteTask(final Long addressId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				UserDeliveryAddressDeleteTask.TP_BZ_USER_DELIVERY_ADDRESS_ID,
				addressId);
		TaskService.addTask(new UserDeliveryAddressDeleteTask(
				UserDeliveryAddress.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addUserDeliveryAddressListTask() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserDeliveryAddressListTask.TP_BZ_MERCHANT_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new UserDeliveryAddressListTask(
				UserDeliveryAddress.this, map));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (UserDeliveryAddressListTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_uda_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzUserDeliveryAddressDto> result = (WsPageResult<BzUserDeliveryAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_uda_content.onRefreshComplete();
				return;
			}
			adapterContent.clear();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_uda_content.getRefreshableView().setSelection(0);
			plv_uda_content.onRefreshComplete();
		} else if (UserDeliveryAddressDeleteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzUserDeliveryAddressDto> result = (WsPageResult<BzUserDeliveryAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserDeliveryAddress.this, "删除失败");
				} else {
					ToastHelper.showToast(UserDeliveryAddress.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserDeliveryAddress.this, "删除成功");
			addUserDeliveryAddressListTask();
		}
	}

}
