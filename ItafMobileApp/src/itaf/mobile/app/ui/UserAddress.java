package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserAddressDeleteTask;
import itaf.mobile.app.task.netreader.UserAddressListTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.UserAddressAdapter;
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
public class UserAddress extends BaseUIActivity implements UIRefresh {

	private static final int RC_REFRESH = 1;

	// 返回按钮
	private Button btn_ua_back;
	// 保存按钮
	private Button btn_ua_add;
	// 用户好友列表
	private PullToRefreshListView plv_ua_content;
	private UserAddressAdapter adapter;
	private List<BzUserAddressDto> adapterContent = new ArrayList<BzUserAddressDto>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address);

		initPageAttribute();
		initListView();

		addUserAddressListTask();
	}

	private void initPageAttribute() {
		btn_ua_back = (Button) this.findViewById(R.id.btn_ua_back);
		btn_ua_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAddress.this));

		btn_ua_add = (Button) this.findViewById(R.id.btn_ua_add);
		btn_ua_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserAddress.this, UserAddressCreate.class);
				startActivityForResult(intent, RC_REFRESH);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_REFRESH) {
				addUserAddressListTask();
			}
		}
	}

	private void initListView() {
		// ListView初始化
		plv_ua_content = (PullToRefreshListView) findViewById(R.id.plv_ua_content);
		plv_ua_content.setShowIndicator(false);// 取消箭头
		adapter = new UserAddressAdapter(UserAddress.this, adapterContent);
		plv_ua_content.setAdapter(adapter);

		plv_ua_content.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				String label = DateUtils.formatDateTime(
						getApplicationContext(), System.currentTimeMillis(),
						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);
				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				addUserAddressListTask();
			}
		});

		plv_ua_content.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BzUserAddressDto dto = adapterContent.get(position - 1);
				Intent intent = new Intent();
				intent.putExtra(UserAddressDetail.AP_BZ_USER_ADDRESS_ID,
						dto.getId());
				intent.setClass(UserAddress.this, UserAddressDetail.class);
				startActivity(intent);
			}
		});

		plv_ua_content.getRefreshableView().setOnItemLongClickListener(
				new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> parent,
							View view, int position, long id) {
						BzUserAddressDto dto = adapterContent.get(position - 1);
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
				intent.putExtra(UserAddressEdit.AP_BZ_USER_ADDRESS_ID,
						addressId);
				intent.setClass(UserAddress.this, UserAddressEdit.class);
				startActivityForResult(intent, RC_REFRESH);
			}
		}));
		popItems.add(new PopupWindowItem("删除", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				AlertDialogHelper.showDialog(UserAddress.this, "提醒", "确认要删除吗？",
						"确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								addUserAddressDeleteTask(addressId);
							}

						});
			}

		}));
		PopCommon.show(UserAddress.this, popItems);
	}

	private void addUserAddressDeleteTask(final Long addressId) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UserAddressDeleteTask.TP_BZ_USER_ADDRESS_ID, addressId);
		TaskService
				.addTask(new UserAddressDeleteTask(UserAddress.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addUserAddressListTask() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserAddressListTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new UserAddressListTask(UserAddress.this, map));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (UserAddressListTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_ua_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_ua_content.onRefreshComplete();
				return;
			}
			adapterContent.clear();
			adapterContent.addAll(result.getContent());
			adapter.notifyDataSetChanged();
			plv_ua_content.getRefreshableView().setSelection(0);
			plv_ua_content.onRefreshComplete();
		} else if (UserAddressDeleteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserAddress.this, "删除失败");
				} else {
					ToastHelper.showToast(UserAddress.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserAddress.this, "删除成功");
			addUserAddressListTask();
		}
	}

}
