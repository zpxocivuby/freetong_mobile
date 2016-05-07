package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserDeliveryAddressDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 发货地址详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserDeliveryAddressDetail extends BaseUIActivity implements
		UIRefresh {

	public static final String AP_BZ_USER_DELIVERY_ADDRESS_ID = "bzUserDeliveryAddressId";

	private LinearLayout linear_udad_layout;

	// 返回
	private Button btn_udad_back;

	private TextView et_udad_contact_person;
	private TextView et_udad_contact_no;
	private TextView et_udad_address;
	private TextView et_udad_postcode;

	private Long bzUserDeliveryAddressId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_delivery_address_detail);
		// 获取值
		bzUserDeliveryAddressId = getIntent().getExtras().getLong(
				AP_BZ_USER_DELIVERY_ADDRESS_ID);

		initPageAttribute();
		addUserDeliveryAddressDetailTask();
	}

	private void initPageAttribute() {
		linear_udad_layout = (LinearLayout) this
				.findViewById(R.id.linear_udad_layout);
		linear_udad_layout.setVisibility(View.GONE);
		btn_udad_back = (Button) this.findViewById(R.id.btn_udad_back);
		btn_udad_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserDeliveryAddressDetail.this));
		et_udad_contact_person = (TextView) this
				.findViewById(R.id.et_udad_contact_person);
		et_udad_contact_no = (TextView) this
				.findViewById(R.id.et_udad_contact_no);
		et_udad_address = (TextView) this.findViewById(R.id.et_udad_address);
		et_udad_postcode = (TextView) this.findViewById(R.id.et_udad_postcode);
	}

	private void addUserDeliveryAddressDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				UserDeliveryAddressDetailTask.TP_BZ_USER_DELIVERY_ADDRESS_ID,
				bzUserDeliveryAddressId);
		TaskService.addTask(new UserDeliveryAddressDetailTask(
				UserDeliveryAddressDetail.this, params));
		showProgressDialog(PD_LOADING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		if (args[1] == null) {
			return;
		}
		int taskId = (Integer) args[0];
		if (UserDeliveryAddressDetailTask.getTaskId() == taskId) {
			WsPageResult<BzUserDeliveryAddressDto> result = (WsPageResult<BzUserDeliveryAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载失败!");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			linear_udad_layout.setVisibility(View.VISIBLE);
			BzUserDeliveryAddressDto target = result.getContent().iterator()
					.next();
			et_udad_contact_person.setText(StringHelper.trimToEmpty(target
					.getContactPerson()));
			et_udad_contact_no.setText(StringHelper.trimToEmpty(target
					.getContactNo()));
			et_udad_address.setText(StringHelper.trimToEmpty(target
					.getAddress()));
			et_udad_postcode.setText(StringHelper.trimToEmpty(target
					.getPostcode()));

		}
	}

}
