package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SysRegisterTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.KeyboardHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 系统注册
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysRegister extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_sr_back;
	// 注册
	private Button btn_sr_register;
	// 用户名
	private EditText et_sr_username;
	// 密码
	private EditText et_sr_password;
	// 确认密码
	private EditText et_sr_confirm_password;
	// 是否同意
	private CheckBox cb_sr_agree;
	// 协议
	private TextView tv_sr_protocol;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_register);

		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_sr_back = (Button) this.findViewById(R.id.btn_sr_back);
		btn_sr_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(SysRegister.this));

		btn_sr_register = (Button) this.findViewById(R.id.btn_sr_register);
		btn_sr_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String username = getTextViewToString(et_sr_username);
				if (!ValidHelper.notEmpty(SysRegister.this, username, "用户名")) {
					return;
				}
				String password = getTextViewToString(et_sr_password);
				if (!ValidHelper.notEmpty(SysRegister.this, password, "密码")) {
					return;
				}

				String confirmPassword = getTextViewToString(et_sr_confirm_password);
				if (!ValidHelper.notEmpty(SysRegister.this, confirmPassword,
						"确认密码")) {
					return;
				}

				if (!ValidHelper.mustEquals(SysRegister.this, password,
						confirmPassword, "两次输入的密码")) {
					return;
				}

				if (AppConstants.SET_OFF
						.equals(getCheckBoxToString(cb_sr_agree))) {
					ToastHelper.showToast(SysRegister.this, "必须同意协议");
					return;
				}

				addRegisterTask(username, password);
			}

		});

		et_sr_username = (EditText) this.findViewById(R.id.et_sr_username);
		KeyboardHelper.showSoftInputNoExtractUi(et_sr_username);
		et_sr_password = (EditText) this.findViewById(R.id.et_sr_password);
		et_sr_confirm_password = (EditText) this
				.findViewById(R.id.et_sr_confirm_password);
		cb_sr_agree = (CheckBox) this.findViewById(R.id.cb_sr_agree);

		tv_sr_protocol = (TextView) this.findViewById(R.id.tv_sr_protocol);
		// 下划线
		tv_sr_protocol.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		tv_sr_protocol.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(SysRegister.this, SysProtocol.class));
			}
		});
	}

	private void addRegisterTask(String username, String password) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SysRegisterTask.TP_USERNAME, username);
		params.put(SysRegisterTask.TP_PASSWORD, password);
		params.put(SysRegisterTask.TP_MOBILE, AppApplication.getInstance()
				.getMobileNo(SysRegister.this));
		TaskService.addTask(new SysRegisterTask(SysRegister.this, params));
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
		if (SysRegisterTask.getTaskId() == taskId) {
			WsPageResult<SysUserDto> result = (WsPageResult<SysUserDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "注册失败");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(this, "注册成功");
			// 注册成功登陆到首页面
			Intent intent = new Intent();
			intent.putExtra(SysLogin.AP_USERNAME,
					getTextViewToString(et_sr_username));
			setResult(Activity.RESULT_OK, intent);
			AppActivityManager.getInstance().finishActivity(SysRegister.this);
		}

	}
}
