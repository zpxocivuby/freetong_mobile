package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserDeliveryAddressCreateOrEditTask;
import itaf.mobile.app.task.netreader.UserDeliveryAddressDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * 发货地址添加或者修改
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserDeliveryAddressEdit extends BaseUIActivity implements
		UIRefresh {

	public static final String AP_BZ_USER_DELIVERY_ADDRESS_ID = "bzUserDeliveryAddressId";

	private LinearLayout linear_udae_layout;

	// 返回
	private Button btn_udae_back;
	// 添加商品
	private Button btn_udae_save;

	private EditText et_udae_contact_person;
	private EditText et_udae_contact_no;
	private EditText et_udae_address;
	private EditText et_udae_postcode;

	private Long bzUserDeliveryAddressId;

	private BzUserDeliveryAddressDto userAddressDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_delivery_address_edit);
		bzUserDeliveryAddressId = getIntent().getExtras().getLong(
				AP_BZ_USER_DELIVERY_ADDRESS_ID);
		initPageAttribute();
		addUserDeliveryAddressDetailTask();
	}

	private void initPageAttribute() {
		btn_udae_back = (Button) this.findViewById(R.id.btn_udae_back);
		btn_udae_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserDeliveryAddressEdit.this));

		linear_udae_layout = (LinearLayout) this
				.findViewById(R.id.linear_udae_layout);
		linear_udae_layout.setVisibility(View.GONE);

		btn_udae_save = (Button) this.findViewById(R.id.btn_udae_save);
		btn_udae_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUserDeliveryAddressSaveOrUpdateTask();
			}

		});

		et_udae_contact_person = (EditText) this
				.findViewById(R.id.et_udae_contact_person);
		et_udae_contact_no = (EditText) this
				.findViewById(R.id.et_udae_contact_no);
		et_udae_address = (EditText) this.findViewById(R.id.et_udae_address);
		et_udae_postcode = (EditText) this.findViewById(R.id.et_udae_postcode);
	}

	private void addUserDeliveryAddressSaveOrUpdateTask() {
		String contactPerson = getTextViewToString(et_udae_contact_person);
		if (!ValidHelper.notEmpty(UserDeliveryAddressEdit.this, contactPerson,
				"收件人")) {
			return;
		}
		String contactNo = getTextViewToString(et_udae_contact_no);
		if (!ValidHelper.notEmpty(UserDeliveryAddressEdit.this, contactNo,
				"手机号码")) {
			return;
		}
		String address = getTextViewToString(et_udae_address);
		if (!ValidHelper
				.notEmpty(UserDeliveryAddressEdit.this, address, "详细地址")) {
			return;
		}
		String postcode = getTextViewToString(et_udae_postcode);
		if (!ValidHelper.notEmpty(UserDeliveryAddressEdit.this, postcode,
				"邮政编码")) {
			return;
		}
		userAddressDto.setContactPerson(contactPerson);
		userAddressDto.setContactNo(contactNo);
		userAddressDto.setAddress(address);
		userAddressDto.setPostcode(postcode);
		// BzPositionDto positionDto = userAddressDto.getBzPositionDto();
		// userAddressDto.setBzPositionDto(positionDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				UserDeliveryAddressCreateOrEditTask.TP_BZ_USER_DELIVERY_ADDRESS_DTO,
				userAddressDto);
		TaskService.addTask(new UserDeliveryAddressCreateOrEditTask(
				UserDeliveryAddressEdit.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addUserDeliveryAddressDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				UserDeliveryAddressDetailTask.TP_BZ_USER_DELIVERY_ADDRESS_ID,
				bzUserDeliveryAddressId);
		TaskService.addTask(new UserDeliveryAddressDetailTask(
				UserDeliveryAddressEdit.this, params));
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
			linear_udae_layout.setVisibility(View.VISIBLE);
			userAddressDto = result.getContent().iterator().next();
			et_udae_contact_person.setText(StringHelper
					.trimToEmpty(userAddressDto.getContactPerson()));
			et_udae_contact_no.setText(StringHelper.trimToEmpty(userAddressDto
					.getContactNo()));
			et_udae_address.setText(StringHelper.trimToEmpty(userAddressDto
					.getAddress()));
			et_udae_postcode.setText(StringHelper.trimToEmpty(userAddressDto
					.getPostcode()));
		} else if (UserDeliveryAddressCreateOrEditTask.getTaskId() == taskId) {
			WsPageResult<BzUserDeliveryAddressDto> result = (WsPageResult<BzUserDeliveryAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserDeliveryAddressEdit.this, "修改失败");
				} else {
					ToastHelper.showToast(UserDeliveryAddressEdit.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserDeliveryAddressEdit.this, "修改成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					UserDeliveryAddressEdit.this);
		}

	}

}
