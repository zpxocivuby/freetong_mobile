package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SysUserDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.UploadHelper;
import itaf.mobile.app.util.UploadHelper.CallbackListener;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

/**
 * 用户账号信息
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class UserAccountDetail extends BaseUIActivity implements UIRefresh {

	private static final int AP_CLIP_PHOTO = 11;
	// 返回
	private TextView btn_uad_back;
	// 头像
	private AsyncImageView aiv_uad_user_ico;
	// 余额
	private TextView tv_uad_balance;
	// 真实名称
	private TextView tv_uad_relname;
	// 邮件地址
	private TextView tv_uad_email;
	// 用户昵称
	private TextView tv_uad_nickname;
	// 手机号码
	private TextView tv_uad_mobile;
	// 附件地址
	private String currFilePath;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_account_detail);

		initPageAttribute();
		addSysUserDetailTask();
	}

	private void initPageAttribute() {
		btn_uad_back = (TextView) this.findViewById(R.id.btn_uad_back);
		btn_uad_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(UserAccountDetail.this));

		aiv_uad_user_ico = (AsyncImageView) this
				.findViewById(R.id.aiv_uad_user_ico);

		aiv_uad_user_ico.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, AppConstants.RC_ANDROID_PHOTO);
			}
		});

		tv_uad_balance = (TextView) this.findViewById(R.id.tv_uad_balance);
		tv_uad_relname = (TextView) this.findViewById(R.id.tv_uad_relname);
		tv_uad_email = (TextView) this.findViewById(R.id.tv_uad_email);
		tv_uad_nickname = (TextView) this.findViewById(R.id.tv_uad_nickname);
		tv_uad_mobile = (TextView) this.findViewById(R.id.tv_uad_mobile);

	}

	private void addSysUserDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SysUserDetailTask.TP_SYS_USER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new SysUserDetailTask(UserAccountDetail.this,
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
		if (SysUserDetailTask.getTaskId() == taskId) {
			WsPageResult<SysUserDto> result = (WsPageResult<SysUserDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(UserAccountDetail.this, "加载失败");
				} else {
					ToastHelper.showToast(UserAccountDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			SysUserDto target = result.getContent().iterator().next();
			if (StringHelper.isNotEmpty(target.getHeadIco())) {
				aiv_uad_user_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_uad_user_ico.setPath(DownLoadHelper.getHeadIcoPath(
						target.getHeadIco(), AppConstants.IMAGE_SIZE_64X64));
			}
			tv_uad_balance.setText(StringHelper.bigDecimalToRmb(target
					.getAccountBalance()));
			tv_uad_relname.setText(StringHelper.trimToEmpty(target
					.getRealname()));
			tv_uad_email.setText(StringHelper.trimToEmpty(target.getEmail()));
			tv_uad_nickname.setText(StringHelper.trimToEmpty(target
					.getNickname()));
			tv_uad_mobile.setText(StringHelper.trimToEmpty(target.getMobile()));
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case AppConstants.RC_ANDROID_PHOTO:
				Uri selectedImage = intent.getData();
				currFilePath = getImagePathFromAndroid(selectedImage);
				Intent sIntent = new Intent();
				sIntent.putExtra("filePath", currFilePath);
				sIntent.setClass(UserAccountDetail.this, SysClipPhoto.class);
				startActivityForResult(sIntent, AP_CLIP_PHOTO);
				break;
			case AP_CLIP_PHOTO:
				currFilePath = intent.getStringExtra("filePath");
				Map<String, String> paramsMap = new HashMap<String, String>();
				paramsMap.put("userId", AppApplication.getInstance()
						.getSessionUser().getId().toString());
				UploadHelper uploadHelper = new UploadHelper(
						UserAccountDetail.this, AppConfig.uploadHeadIcoUrl,
						currFilePath, paramsMap);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						ToastHelper.showToast(UserAccountDetail.this, "修改成功！");
						aiv_uad_user_ico
								.setImageBitmap(getLoacalBitmap(currFilePath));
					}

				});
				uploadHelper.execute("");
				break;
			}
		}
	}

}
