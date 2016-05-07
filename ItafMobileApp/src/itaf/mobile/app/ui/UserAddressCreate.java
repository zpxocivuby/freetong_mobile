package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.framework.position.dto.BzPositionDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserAddressCreateOrEditTask;
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
 * 收货地址添加
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserAddressCreate extends BaseLocationMapActivity implements
		UIRefresh {

	// 返回
	private Button btn_uac_back;
	// 添加商品
	private Button btn_uac_save;

	private EditText et_uac_contact_person;
	private EditText et_uac_contact_no;
	private EditText et_uac_address;
	private EditText et_uac_postcode;
	private BDLocation location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address_create);

		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_uac_back = (Button) this.findViewById(R.id.btn_uac_back);
		btn_uac_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAddressCreate.this));

		btn_uac_save = (Button) this.findViewById(R.id.btn_uac_save);
		btn_uac_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addUserAddressSaveOrUpdateTask();
			}

		});

		et_uac_contact_person = (EditText) this
				.findViewById(R.id.et_uac_contact_person);
		et_uac_contact_no = (EditText) this
				.findViewById(R.id.et_uac_contact_no);
		et_uac_address = (EditText) this.findViewById(R.id.et_uac_address);
		et_uac_postcode = (EditText) this.findViewById(R.id.et_uac_postcode);
	}

	private void addUserAddressSaveOrUpdateTask() {
		BzUserAddressDto dto = new BzUserAddressDto();
		dto.setId(null);
		String contactPerson = getTextViewToString(et_uac_contact_person);
		if (!ValidHelper.notEmpty(UserAddressCreate.this, contactPerson, "收件人")) {
			return;
		}
		String contactNo = getTextViewToString(et_uac_contact_no);
		if (!ValidHelper.notEmpty(UserAddressCreate.this, contactNo, "手机号码")) {
			return;
		}
		String address = getTextViewToString(et_uac_address);
		if (!ValidHelper.notEmpty(UserAddressCreate.this, address, "详细地址")) {
			return;
		}
		String postcode = getTextViewToString(et_uac_postcode);
		if (!ValidHelper.notEmpty(UserAddressCreate.this, postcode, "邮政编码")) {
			return;
		}
		if (location == null) {
			ToastHelper.showToast(UserAddressCreate.this, "定位失败！");
			return;
		}
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
		params.put(UserAddressCreateOrEditTask.TP_BZ_USER_ADDRESS_DTO, dto);
		TaskService.addTask(new UserAddressCreateOrEditTask(
				UserAddressCreate.this, params));
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
		if (UserAddressCreateOrEditTask.getTaskId() == taskId) {
			WsPageResult<BzUserAddressDto> result = (WsPageResult<BzUserAddressDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserAddressCreate.this, "添加失败");
				} else {
					ToastHelper.showToast(UserAddressCreate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(UserAddressCreate.this, "添加成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					UserAddressCreate.this);
		}

	}

	@Override
	protected BDLocationListener getDbLocationListener() {
		return new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				location = bDLocation;
				et_uac_address.setText(location.getAddrStr());
			}
		};
	}

}
