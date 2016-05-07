package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserAddressListTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshListView;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 用户发货地址
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserAddressSelect extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_USER_ADDRESS_DTO = "BzUserAddressDto";

	// 返回按钮
	private Button btn_uas_back;
	// 保存按钮
	private Button btn_uas_confirm;
	// 添加按钮
	private Button btn_uas_add;
	// 用户好友列表
	private PullToRefreshListView plv_uas_content;
	private UserAddressSelectAdapter adapter;
	private List<BzUserAddressDto> adapterContent = new ArrayList<BzUserAddressDto>();

	private BzUserAddressDto selectDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address_select);

		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_USER_ADDRESS_DTO);
		SerializableList<BzUserAddressDto> sList = (SerializableList<BzUserAddressDto>) paramObj;
		if (sList == null || sList.getTarget() == null
				|| sList.getTarget().isEmpty()) {
			AppActivityManager.getInstance().finishActivity(
					UserAddressSelect.this);
			return;
		}
		selectDto = sList.getTarget().iterator().next();

		initPageAttribute();
		initListView();

	}

	@Override
	protected void onResume() {
		super.onResume();
		addUserAddressListTask();
	}

	private void initPageAttribute() {
		btn_uas_back = (Button) this.findViewById(R.id.btn_uas_back);
		btn_uas_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAddressSelect.this));

		btn_uas_confirm = (Button) this.findViewById(R.id.btn_uas_confirm);
		btn_uas_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle mbundle = new Bundle();
				mbundle.putSerializable(AP_BZ_USER_ADDRESS_DTO,
						new SerializableList<BzUserAddressDto>(selectDto));
				Intent returnIntent = new Intent();
				returnIntent.putExtras(mbundle);
				setResult(Activity.RESULT_OK, returnIntent);
				AppActivityManager.getInstance().finishActivity(
						UserAddressSelect.this);
			}
		});
		btn_uas_add = (Button) this.findViewById(R.id.btn_uas_add);
		btn_uas_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(UserAddressSelect.this, UserAddressCreate.class);
				startActivity(intent);
			}
		});
	}

	private void initListView() {
		// ListView初始化
		plv_uas_content = (PullToRefreshListView) findViewById(R.id.plv_uas_content);
		plv_uas_content.setShowIndicator(false);// 取消箭头

		plv_uas_content.setOnRefreshListener(new OnRefreshListener<ListView>() {

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

		plv_uas_content.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				selectDto = adapterContent.get(position - 1);
				for (int i = 1; i < (parent.getChildCount() - 1); i++) {
					if (i == position) {
						view.setBackgroundResource(R.drawable.bg_round_black);
					} else {
						parent.getChildAt(i).setBackgroundResource(
								R.drawable.bg_round_white);
					}
				}
			}
		});

	}

	private void addUserAddressListTask() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(UserAddressListTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService
				.addTask(new UserAddressListTask(UserAddressSelect.this, map));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (UserAddressListTask.getTaskId() == taskId) {
			if (args[1] == null) {
				plv_uas_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常!");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				plv_uas_content.onRefreshComplete();
				return;
			}
			if (result.getContent().size() == 0) {
				btn_uas_add.setVisibility(View.VISIBLE);
				btn_uas_confirm.setVisibility(View.GONE);
			} else {
				btn_uas_add.setVisibility(View.GONE);
				btn_uas_confirm.setVisibility(View.VISIBLE);
			}
			adapterContent.clear();
			adapterContent.addAll(result.getContent());
			adapter = new UserAddressSelectAdapter(UserAddressSelect.this,
					adapterContent);
			plv_uas_content.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			plv_uas_content.getRefreshableView().setSelection(0);
			plv_uas_content.onRefreshComplete();
		}
	}

	class UserAddressSelectAdapter extends AbstractBaseAdapter {

		private List<BzUserAddressDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			TextView tv_uasa_contact_person;
			TextView tv_uasa_contact_no;
			TextView tv_uasa_address;
			TextView tv_uasa_postcode;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public UserAddressSelectAdapter(Context context,
				List<BzUserAddressDto> data) {
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
						R.layout.user_address_select_adapter, parent, false);
				listItemView = new ListItemView();
				// 获取控件对象
				listItemView.tv_uasa_contact_person = (TextView) convertView
						.findViewById(R.id.tv_uasa_contact_person);
				listItemView.tv_uasa_contact_no = (TextView) convertView
						.findViewById(R.id.tv_uasa_contact_no);
				listItemView.tv_uasa_address = (TextView) convertView
						.findViewById(R.id.tv_uasa_address);
				listItemView.tv_uasa_postcode = (TextView) convertView
						.findViewById(R.id.tv_uasa_postcode);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			BzUserAddressDto target = (BzUserAddressDto) listItems
					.get(position);
			if (selectDto != null && selectDto.getId().equals(target.getId())) {
				convertView.setBackgroundResource(R.drawable.bg_round_black);
			}
			listItemView.tv_uasa_contact_person.setText(StringHelper
					.trimToEmpty(target.getContactPerson()));
			listItemView.tv_uasa_contact_no.setText(StringHelper
					.trimToEmpty(target.getContactNo()));
			listItemView.tv_uasa_address.setText(StringHelper
					.trimToEmpty(target.getAddress()));
			listItemView.tv_uasa_postcode.setText(StringHelper
					.trimToEmpty(target.getPostcode()));
			return convertView;

		}

	}

}
