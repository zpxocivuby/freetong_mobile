package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.framework.position.dto.BzPositionDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserDeliveryAddressCreateOrEditTask;
import itaf.mobile.app.ui.base.BaseLocationMapActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;

/**
 * 发货地址添加或者修改
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserDeliveryAddressCreate extends BaseLocationMapActivity
		implements UIRefresh {

	// 返回
	private Button btn_udac_back;
	// 添加商品
	private Button btn_udac_save;

	private EditText et_udac_contact_person;
	private EditText et_udac_contact_no;
	private EditText et_udac_address;
	private EditText et_udac_postcode;
	private BDLocation location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_delivery_address_create);
		initPageAttribute();

	}

	private void initPageAttribute() {
		btn_udac_back = (Button) this.findViewById(R.id.btn_udac_back);
		btn_udac_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserDeliveryAddressCreate.this));

		btn_udac_save = (Button) this.findViewById(R.id.btn_udac_save);
		btn_udac_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUserDeliveryAddressSaveOrUpdateTask();
			}

		});

		et_udac_contact_person = (EditText) this
				.findViewById(R.id.et_udac_contact_person);
		et_udac_contact_no = (EditText) this
				.findViewById(R.id.et_udac_contact_no);
		et_udac_address = (EditText) this.findViewById(R.id.et_udac_address);
		et_udac_postcode = (EditText) this.findViewById(R.id.et_udac_postcode);
	}

	private void addUserDeliveryAddressSaveOrUpdateTask() {
		String contactPerson = getTextViewToString(et_udac_contact_person);
		if (!ValidHelper.notEmpty(UserDeliveryAddressCreate.this,
				contactPerson, "收件人")) {
			return;
		}
		String contactNo = getTextViewToString(et_udac_contact_no);
		if (!ValidHelper.notEmpty(UserDeliveryAddressCreate.this, contactNo,
				"手机号码")) {
			return;
		}
		String address = getTextViewToString(et_udac_address);
		if (!ValidHelper.notEmpty(UserDeliveryAddressCreate.this, address,
				"详细地址")) {
			return;
		}
		String postcode = getTextViewToString(et_udac_postcode);
		if (!ValidHelper.notEmpty(UserDeliveryAddressCreate.this, postcode,
				"邮政编码")) {
			return;
		}
		if (location == null) {
			ToastHelper.showToast(UserDeliveryAddressCreate.this, "定位失败！");
			return;
		}
		BzUserDeliveryAddressDto dto = new BzUserDeliveryAddressDto();
		dto.setId(null);
		dto.setContactPerson(contactPerson);
		dto.setContactNo(contactNo);
		dto.setAddress(address);
		dto.setPostcode(postcode);
		BzPositionDto positionDto = new BzPositionDto();
		positionDto.setX(new BigDecimal(location.getLongitude()));
		positionDto.setY(new BigDecimal(location.getLatitude()));
		positionDto.setZ(new BigDecimal(location.getAltitude()));
		dto.setBzPositionDto(positionDto);
		dto.setSysUserId(AppApplication.getInstance().getSessionUser().getId());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				UserDeliveryAddressCreateOrEditTask.TP_BZ_USER_DELIVERY_ADDRESS_DTO,
				dto);
		TaskService.addTask(new UserDeliveryAddressCreateOrEditTask(
				UserDeliveryAddressCreate.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		if (args[1] == null) {
			return;
		}
		int taskId = (Integer) args[0];
		if (UserDeliveryAddressCreateOrEditTask.getTaskId() == taskId) {
			WsPageResult<BzUserDeliveryAddressDto> result = (WsPageResult<BzUserDeliveryAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserDeliveryAddressCreate.this,
							"添加失败");
				} else {
					ToastHelper.showToast(UserDeliveryAddressCreate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserDeliveryAddressCreate.this, "添加成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					UserDeliveryAddressCreate.this);
		}

	}

	@Override
	protected BDLocationListener getDbLocationListener() {
		return new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				location = bDLocation;
				et_udac_address.setText(location.getAddrStr());
			}
		};
	}

}
