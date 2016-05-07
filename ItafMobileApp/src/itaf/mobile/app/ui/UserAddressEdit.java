package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserAddressCreateOrEditTask;
import itaf.mobile.app.task.netreader.UserAddressDetailTask;
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
 * 收货地址修改
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserAddressEdit extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_USER_ADDRESS_ID = "bzUserAddressId";

	private LinearLayout linear_uae_layout;

	// 返回
	private Button btn_uae_back;
	// 添加商品
	private Button btn_uae_save;

	private EditText et_uae_contact_person;
	private EditText et_uae_contact_no;
	private EditText et_uae_address;
	private EditText et_uae_postcode;

	private Long bzUserAddressId;

	private BzUserAddressDto userAddressDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address_edit);
		bzUserAddressId = getIntent().getExtras()
				.getLong(AP_BZ_USER_ADDRESS_ID);
		initPageAttribute();
		addUserAddressDetailTask();
	}

	private void initPageAttribute() {
		btn_uae_back = (Button) this.findViewById(R.id.btn_uae_back);
		btn_uae_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAddressEdit.this));

		linear_uae_layout = (LinearLayout) this
				.findViewById(R.id.linear_uae_layout);
		linear_uae_layout.setVisibility(View.GONE);

		btn_uae_save = (Button) this.findViewById(R.id.btn_uae_save);
		btn_uae_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUserAddressSaveOrUpdateTask();
			}

		});

		et_uae_contact_person = (EditText) this
				.findViewById(R.id.et_uae_contact_person);
		et_uae_contact_no = (EditText) this
				.findViewById(R.id.et_uae_contact_no);
		et_uae_address = (EditText) this.findViewById(R.id.et_uae_address);
		et_uae_postcode = (EditText) this.findViewById(R.id.et_uae_postcode);
	}

	private void addUserAddressSaveOrUpdateTask() {
		String contactPerson = getTextViewToString(et_uae_contact_person);
		if (!ValidHelper.notEmpty(UserAddressEdit.this, contactPerson, "收件人")) {
			return;
		}
		String contactNo = getTextViewToString(et_uae_contact_no);
		if (!ValidHelper.notEmpty(UserAddressEdit.this, contactNo, "手机号码")) {
			return;
		}
		String address = getTextViewToString(et_uae_address);
		if (!ValidHelper.notEmpty(UserAddressEdit.this, address, "详细地址")) {
			return;
		}
		String postcode = getTextViewToString(et_uae_postcode);
		if (!ValidHelper.notEmpty(UserAddressEdit.this, postcode, "邮政编码")) {
			return;
		}
		userAddressDto.setContactPerson(contactPerson);
		userAddressDto.setContactNo(contactNo);
		userAddressDto.setAddress(address);
		userAddressDto.setPostcode(postcode);
		// BzPositionDto positionDto = userAddressDto.getBzPositionDto();
		// userAddressDto.setBzPositionDto(positionDto);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UserAddressCreateOrEditTask.TP_BZ_USER_ADDRESS_DTO,
				userAddressDto);
		TaskService.addTask(new UserAddressCreateOrEditTask(
				UserAddressEdit.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addUserAddressDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UserAddressDetailTask.TP_BZ_USER_ADDRESS_ID, bzUserAddressId);
		TaskService.addTask(new UserAddressDetailTask(UserAddressEdit.this,
				params));
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
		if (UserAddressDetailTask.getTaskId() == taskId) {
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载失败!");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			linear_uae_layout.setVisibility(View.VISIBLE);
			userAddressDto = result.getContent().iterator().next();
			et_uae_contact_person.setText(StringHelper
					.trimToEmpty(userAddressDto.getContactPerson()));
			et_uae_contact_no.setText(StringHelper.trimToEmpty(userAddressDto
					.getContactNo()));
			et_uae_address.setText(StringHelper.trimToEmpty(userAddressDto
					.getAddress()));
			et_uae_postcode.setText(StringHelper.trimToEmpty(userAddressDto
					.getPostcode()));
		} else if (UserAddressCreateOrEditTask.getTaskId() == taskId) {
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserAddressEdit.this, "修改失败");
				} else {
					ToastHelper.showToast(UserAddressEdit.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserAddressEdit.this, "修改成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					UserAddressEdit.this);
		}

	}

}
