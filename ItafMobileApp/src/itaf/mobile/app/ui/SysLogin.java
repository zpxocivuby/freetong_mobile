package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SysLoginTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.KeyboardHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.DESCipher;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.LoginHistoryRecordDb;
import itaf.mobile.ds.domain.LoginHistoryRecord;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 登陆
 * 
 * 
 * @author
 * 
 * @update 2013年11月22日
 */
public class SysLogin extends BaseUIActivity implements UIRefresh {

	private static final String ENCRYPT_KEY = "encrypt_key_password";

	public static final String AP_USERNAME = "username";

	protected static final int RC_USERNAME = 1;

	// 布局页面
	private LinearLayout linear_sl_page;

	// 返回
	private Button btn_sl_back;
	// 登陆
	private Button btn_sl_login;
	// 用户名
	private EditText et_sl_username;
	// 密码
	private EditText et_sl_password;
	// 记住密码
	private CheckBox cb_sl_remember_password;
	// 自动登录
	private CheckBox cb_sl_auto_login;
	// 注册账号
	private TextView tv_sl_register;

	private LoginHistoryRecordDb loginDb = new LoginHistoryRecordDb(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sys_login);

		initPageAttribute();
		initPageValue();
	}

	private void initPageAttribute() {
		linear_sl_page = (LinearLayout) this.findViewById(R.id.linear_sl_page);
		linear_sl_page.setVisibility(View.GONE);
		btn_sl_back = (Button) this.findViewById(R.id.btn_sl_back);
		btn_sl_back.setOnClickListener(OnClickListenerHelper
				.openHomePage(SysLogin.this));
		// 登陆按钮
		btn_sl_login = (Button) this.findViewById(R.id.btn_sl_login);
		btn_sl_login.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String username = getTextViewToString(et_sl_username);
				if (!ValidHelper.notEmpty(SysLogin.this, username, "用户名")) {
					return;
				}
				String password = getTextViewToString(et_sl_password);
				if (!ValidHelper.notEmpty(SysLogin.this, password, "密码")) {
					return;
				}
				addLoginTask(username, password, AppApplication.getInstance()
						.getMobileNo(SysLogin.this));
			}

		});

		et_sl_username = (EditText) this.findViewById(R.id.et_sl_username);
		Selection.setSelection(et_sl_username.getText(),
				et_sl_username.length());
		et_sl_password = (EditText) this.findViewById(R.id.et_sl_password);
		cb_sl_remember_password = (CheckBox) this
				.findViewById(R.id.cb_sl_remember_password);
		// 记住密码取消选中，自动登录也要取消选中
		cb_sl_remember_password
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (!isChecked) {
							cb_sl_auto_login.setChecked(false);
						}
					}
				});
		cb_sl_auto_login = (CheckBox) this.findViewById(R.id.cb_sl_auto_login);
		// 自动登录选中，记住密码也要选中
		cb_sl_auto_login
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						if (isChecked) {
							cb_sl_remember_password.setChecked(true);
						}
					}
				});

		// 跳转到注册页面
		tv_sl_register = (TextView) this.findViewById(R.id.tv_sl_register);
		tv_sl_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SysLogin.this, SysRegister.class);
				startActivityForResult(intent, RC_USERNAME);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_USERNAME) {
				Bundle bundle = data.getExtras();
				// 注册跳转的时候UserName有值
				String username = bundle.getString(AP_USERNAME);
				if (username != null && username.length() > 0) {
					et_sl_username.setText(username);
					et_sl_password.setText("");
					cb_sl_remember_password.setChecked(false);
					cb_sl_auto_login.setChecked(false);
					KeyboardHelper.showSoftInput(et_sl_password);
				}
			}
		}
	}

	private void addLoginTask(String username, String password, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysLoginTask.TP_USERNAME, username);
		map.put(SysLoginTask.TP_PASSWORD, password);
		map.put(SysLoginTask.TP_MOBILE, mobile);
		TaskService.addTask(new SysLoginTask(SysLogin.this, map));
		showProgressDialog(PD_LOGINING);
	}

	private void initPageValue() {
		LoginHistoryRecord latestLoginRecord = loginDb.findLatestLoginRecord();
		// 如果为空，第一次登陆，键盘定位到用户名
		if (latestLoginRecord == null) {
			KeyboardHelper.showSoftInput(et_sl_username);
			linear_sl_page.setVisibility(View.VISIBLE);
			return;
		}
		// 自动登陆
		if (latestLoginRecord.getAutoLogin() == AppConstants.CHECKED) {
			// 自动登录的时候，只更新时间
			linear_sl_page.setVisibility(View.GONE);
			addLoginTask(latestLoginRecord.getUsername(), DESCipher.decrypt(
					latestLoginRecord.getPassword(), ENCRYPT_KEY),
					latestLoginRecord.getMobile());
			return;
		}
		// 记住密码
		if (latestLoginRecord.getRememberPassword() == AppConstants.CHECKED) {
			KeyboardHelper.hideSoftInput(this);
			et_sl_username.setText(latestLoginRecord.getUsername());
			et_sl_username.addTextChangedListener(new TextWatcher() {
				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
				}

				@Override
				public void afterTextChanged(Editable s) {
					et_sl_password.setText("");
				}
			});
			et_sl_password.setText(DESCipher.decrypt(
					latestLoginRecord.getPassword(), ENCRYPT_KEY));
			cb_sl_remember_password.setChecked(true);
			cb_sl_auto_login.setChecked(false);
		}
		linear_sl_page.setVisibility(View.VISIBLE);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		if (args[1] == null) {
			return;
		}
		int taskId = (Integer) args[0];
		if (SysLoginTask.getTaskId() == taskId) {
			WsPageResult<SysUserDto> result = (WsPageResult<SysUserDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "登录失败");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(this, "登录成功");
			SysUserDto target = result.getContent().iterator().next();
			String password = DESCipher.encrypt(
					getTextViewToString(et_sl_password), ENCRYPT_KEY);
			// 非自动登录获取用户信息
			LoginHistoryRecord loginHistoryRecord = new LoginHistoryRecord();
			loginHistoryRecord.setId(null);
			loginHistoryRecord.setUsername(target.getUsername());
			loginHistoryRecord.setPassword(password);
			loginHistoryRecord.setMobile(target.getMobile());
			loginHistoryRecord
					.setAutoLogin(getCheckBoxToInteger(cb_sl_auto_login));
			loginHistoryRecord
					.setRememberPassword(getCheckBoxToInteger(cb_sl_remember_password));
			loginDb.saveOrUpdate(loginHistoryRecord);
			// 设置SessionUser
			SessionUser su = new SessionUser();
			su.setId(target.getId());
			su.setUsername(target.getUsername());
			su.setPassword(password);
			su.setType(target.getType());
			su.setRealnameStatus(target.getRealnameStatus());
			su.setSellingStatus(target.getSellingStatus());
			su.setDistStatus(target.getDistStatus());
			su.setRealname(target.getRealname());
			su.setNickname(target.getNickname());
			su.setMobile(target.getMobile());
			su.setEmail(target.getEmail());
			su.setAccountBalance(target.getAccountBalance());
			AppApplication.getInstance().setSessionUser(su);
			AppActivityManager.getInstance().finishActivity(SysLogin.this);
		}
	}

	/** 重写父类方法，返回弹出是否退出应用对话框 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AppActivityManager.getInstance().finishActivity(SysLogin.this);
			((AppMain) AppActivityManager.getInstance().getActivity(
					AppMain.class.getName()))
					.setRadioGroupChecked(R.id.radio_menu_home);
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
