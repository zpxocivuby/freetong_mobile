package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.UserAddressDetailTask;
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
 * 收货地址详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserAddressDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_USER_ADDRESS_ID = "bzUserAddressId";

	private LinearLayout linear_uad_layout;

	// 返回
	private Button btn_uad_back;

	private TextView et_uad_contact_person;
	private TextView et_uad_contact_no;
	private TextView et_uad_address;
	private TextView et_uad_postcode;

	private Long bzUserAddressId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_address_detail);
		// 获取值
		bzUserAddressId = getIntent().getExtras()
				.getLong(AP_BZ_USER_ADDRESS_ID);

		initPageAttribute();
		addUserAddressDetailTask();
	}

	private void initPageAttribute() {
		linear_uad_layout = (LinearLayout) this
				.findViewById(R.id.linear_uad_layout);
		linear_uad_layout.setVisibility(View.GONE);
		btn_uad_back = (Button) this.findViewById(R.id.btn_uad_back);
		btn_uad_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAddressDetail.this));
		et_uad_contact_person = (TextView) this
				.findViewById(R.id.et_uad_contact_person);
		et_uad_contact_no = (TextView) this
				.findViewById(R.id.et_uad_contact_no);
		et_uad_address = (TextView) this.findViewById(R.id.et_uad_address);
		et_uad_postcode = (TextView) this.findViewById(R.id.et_uad_postcode);
	}

	private void addUserAddressDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UserAddressDetailTask.TP_BZ_USER_ADDRESS_ID, bzUserAddressId);
		TaskService.addTask(new UserAddressDetailTask(UserAddressDetail.this,
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
			linear_uad_layout.setVisibility(View.VISIBLE);
			BzUserAddressDto target = result.getContent().iterator().next();
			et_uad_contact_person.setText(StringHelper.trimToEmpty(target
					.getContactPerson()));
			et_uad_contact_no.setText(StringHelper.trimToEmpty(target
					.getContactNo()));
			et_uad_address
					.setText(StringHelper.trimToEmpty(target.getAddress()));
			et_uad_postcode.setText(StringHelper.trimToEmpty(target
					.getPostcode()));

		}
	}

}
