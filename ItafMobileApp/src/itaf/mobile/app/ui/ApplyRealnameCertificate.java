package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyRealnameCertificateDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplyRealnameCertificateTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopCommon;
import itaf.mobile.app.util.FileHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.UploadHelper;
import itaf.mobile.app.util.UploadHelper.CallbackListener;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 实名认证
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class ApplyRealnameCertificate extends BaseUIActivity implements
		UIRefresh {

	public static final String AP_BZ_APPLY_REALNAME_CERTIFICATE_DTO = "BzApplyRealnameCertificateDto";

	// 返回
	private Button btn_arc_back;
	// 提交申请
	private Button btn_arc_submit_apply;
	// 真实姓名
	private EditText et_arc_relname;
	// 性别男
	private RadioButton rb_arc_sex_male;
	// 性别女
	private RadioButton rb_arc_sex_female;
	// 出生日期
	private TextView tv_arc_birthday;
	// 手机号码
	private EditText et_arc_mobile;
	// 证件类型
	private TextView tv_arc_id_type;
	// 证件类型值
	private TextView tv_arc_id_type_value;
	// 证件号码
	private EditText et_arc_id_no;
	// 证件附件
	private Button btn_arc_attach;
	// 证件附件ID
	private TextView tv_arc_attach_name;
	// 附件地址
	private String currFilePath;
	private BzApplyRealnameCertificateDto realnameDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_realname_certificate);

		initPageAttribute();
		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_APPLY_REALNAME_CERTIFICATE_DTO);
		SerializableList<BzApplyRealnameCertificateDto> sList = (SerializableList<BzApplyRealnameCertificateDto>) paramObj;
		if (sList != null && sList.getTarget() != null
				&& !sList.getTarget().isEmpty()) {
			Collection<BzApplyRealnameCertificateDto> realnameDtos = sList
					.getTarget();
			realnameDto = realnameDtos.iterator().next();
			et_arc_relname.setText(StringHelper.trimToEmpty(realnameDto
					.getName()));
			boolean isMale = BzApplyRealnameCertificateDto.SEX_MALE
					.equals(realnameDto.getSex());
			rb_arc_sex_male.setChecked(isMale);
			rb_arc_sex_female.setChecked(!isMale);
			tv_arc_birthday.setText(DateUtil.formatDate(realnameDto
					.getBirthdate()));
			et_arc_mobile.setText(StringHelper.trimToEmpty(realnameDto
					.getMobile()));
			tv_arc_id_type
					.setText(BzApplyRealnameCertificateDto.ID_TYPE_CARD
							.equals(realnameDto.getIdType()) ? BzApplyRealnameCertificateDto.ID_CARD
							: BzApplyRealnameCertificateDto.ID_PASSPORT);
			tv_arc_id_type_value.setText(StringHelper.longToString(realnameDto
					.getIdType()));
			et_arc_id_no
					.setText(StringHelper.trimToEmpty(realnameDto.getIdNo()));
			tv_arc_attach_name.setText(StringHelper.trimToEmpty(realnameDto
					.getBzAttachmentDto().getSrcFileName()));
		}
	}

	private void initPageAttribute() {
		btn_arc_back = (Button) this.findViewById(R.id.btn_arc_back);
		btn_arc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplyRealnameCertificate.this));

		btn_arc_submit_apply = (Button) this
				.findViewById(R.id.btn_arc_submit_apply);
		btn_arc_submit_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final String relname = getTextViewToString(et_arc_relname);
				if (!ValidHelper.notEmpty(ApplyRealnameCertificate.this,
						relname, "真实姓名")) {
					return;
				}
				final Date birthdate = getTextViewToDate(tv_arc_birthday);
				if (birthdate == null) {
					ToastHelper.showToast(ApplyRealnameCertificate.this,
							"出生日期不能为空!");
					return;
				}
				final String mobile = getTextViewToString(et_arc_mobile);
				if (!ValidHelper.isPhone(ApplyRealnameCertificate.this, mobile)) {
					return;
				}
				final Long typeValue = getTextViewToLong(tv_arc_id_type_value);
				final String idNo = getTextViewToString(et_arc_id_no);
				if (BzApplyRealnameCertificateDto.ID_TYPE_CARD
						.equals(typeValue)) {
					if (!ValidHelper.isIdCard(ApplyRealnameCertificate.this,
							idNo)) {
						return;
					}
				} else {
					// TODO 验证护照
				}
				if (StringHelper
						.isEmpty(getTextViewToString(tv_arc_attach_name))) {
					ToastHelper.showToast(ApplyRealnameCertificate.this,
							"证件附件不能为空！");
					return;
				}
				if (realnameDto == null) {
					realnameDto = new BzApplyRealnameCertificateDto();
				}
				realnameDto.setName(relname);
				realnameDto.setBirthdate(birthdate);
				realnameDto.setMobile(mobile);
				realnameDto.setIdType(typeValue);
				realnameDto.setIdNo(idNo);
				realnameDto.setSex(getRadioButtonToLong(rb_arc_sex_male));
				SysUserDto su = new SysUserDto();
				su.setId(AppApplication.getInstance().getSessionUser().getId());
				realnameDto.setSysUserDto(su);
				if (StringHelper.isEmpty(currFilePath)) {
					addApplyRealnameCertificateTask();
					return;
				}
				UploadHelper uploadHelper = new UploadHelper(
						ApplyRealnameCertificate.this, AppConfig.uploadFileUrl,
						currFilePath, null);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						realnameDto.setBzAttachmentId(Long.valueOf(dataId));
						addApplyRealnameCertificateTask();
					}

				});
				uploadHelper.execute("");
			}

		});

		et_arc_relname = (EditText) this.findViewById(R.id.et_arc_relname);
		rb_arc_sex_male = (RadioButton) this.findViewById(R.id.rb_arc_sex_male);
		rb_arc_sex_female = (RadioButton) this
				.findViewById(R.id.rb_arc_sex_female);
		tv_arc_birthday = (TextView) this.findViewById(R.id.tv_arc_birthday);
		tv_arc_birthday.setOnClickListener(showDatePickerDialog(
				ApplyRealnameCertificate.this, tv_arc_birthday));
		et_arc_mobile = (EditText) this.findViewById(R.id.et_arc_mobile);
		tv_arc_id_type = (TextView) this.findViewById(R.id.tv_arc_id_type);
		tv_arc_id_type_value = (TextView) this
				.findViewById(R.id.tv_arc_id_type_value);
		tv_arc_id_type.setText(BzApplyRealnameCertificateDto.ID_CARD);
		tv_arc_id_type_value.setText(BzApplyRealnameCertificateDto.ID_TYPE_CARD
				+ "");
		tv_arc_id_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showPopCommon();
			}

		});
		et_arc_id_no = (EditText) this.findViewById(R.id.et_arc_id_no);
		btn_arc_attach = (Button) this.findViewById(R.id.btn_arc_attach);
		btn_arc_attach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, AppConstants.RC_ANDROID_PHOTO);
				System.out.println(intent);
			}
		});
		tv_arc_attach_name = (TextView) this
				.findViewById(R.id.tv_arc_attach_name);

	}

	private void showPopCommon() {
		List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
		popItems.add(new PopupWindowItem(BzApplyRealnameCertificateDto.ID_CARD,
				new OnClickListener() {
					public void onClick(View v) {
						PopCommon.dismiss();
						tv_arc_id_type
								.setText(BzApplyRealnameCertificateDto.ID_CARD);
						tv_arc_id_type_value
								.setText(BzApplyRealnameCertificateDto.ID_TYPE_CARD
										+ "");
					}
				}));
		popItems.add(new PopupWindowItem(
				BzApplyRealnameCertificateDto.ID_PASSPORT,
				new OnClickListener() {
					public void onClick(View v) {
						PopCommon.dismiss();
						tv_arc_id_type
								.setText(BzApplyRealnameCertificateDto.ID_PASSPORT);
						tv_arc_id_type_value
								.setText(BzApplyRealnameCertificateDto.ID_TYPE_PASSPORT
										+ "");
					}
				}));
		PopCommon.show(ApplyRealnameCertificate.this, popItems);
	}

	private void addApplyRealnameCertificateTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				ApplyRealnameCertificateTask.TP_BZ_APPLY_REALNAME_CERTIFICATE_DTO,
				realnameDto);
		TaskService.addTask(new ApplyRealnameCertificateTask(
				ApplyRealnameCertificate.this, params));
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
		if (ApplyRealnameCertificateTask.getTaskId() == taskId) {
			WsPageResult<BzApplyRealnameCertificateDto> result = (WsPageResult<BzApplyRealnameCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper
							.showToast(ApplyRealnameCertificate.this, "添加失败");
				} else {
					ToastHelper.showToast(ApplyRealnameCertificate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ApplyRealnameCertificate.this, "添加成功");
			AppApplication.getInstance().getSessionUser().setRealnameStatus(1L);
			AppActivityManager.getInstance().finishActivity(
					ApplyRealnameCertificate.this);
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {
		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case AppConstants.RC_ANDROID_PHOTO:
				Uri selectedImage = intent.getData();
				//modify by xiaoly for4.4.2 change method for getImagePathFromUri4kitkat
				//currFilePath = getImagePathFromAndroid(selectedImage);
				currFilePath = getImagePathFromUri4kitkat(this,selectedImage);
				tv_arc_attach_name.setText(FileHelper
						.getSrcFileName(currFilePath));
				break;
			}
		}
	}
}
