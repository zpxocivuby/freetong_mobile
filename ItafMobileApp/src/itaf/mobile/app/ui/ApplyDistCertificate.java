package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyDistCertificateDto;
import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplyDistCertificateTask;
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
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.ServiceProviderTypeDb;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.util.ArrayList;
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
import android.widget.TextView;

/**
 * 申请配送服务
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ApplyDistCertificate extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_APPLY_DIST_CERTIFICATE_DTO = "BzApplyDistCertificateDto";
	private ServiceProviderTypeDb db = new ServiceProviderTypeDb(this);
	// 返回
	private Button btn_adc_back;
	// 提交申请
	private Button btn_adc_submit_apply;
	// 公司名称
	private EditText et_adc_company_name;
	// 服务类型
	private TextView tv_adc_service_type;
	// 服务类型
	private TextView tv_adc_service_type_value;
	// 服务范围
	private TextView tv_adc_service_coverage;
	// 服务范围
	private TextView tv_adc_service_coverage_value;
	// 公司地址
	private EditText et_adc_company_address;
	// 配送资质
	private Button btn_adc_attach;
	// 配送资质ID
	private TextView tv_adc_attach_name;
	// 附件地址
	private String currFilePath;
	private BzApplyDistCertificateDto distDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_dist_certificate);

		initPageAttribute();
		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_APPLY_DIST_CERTIFICATE_DTO);
		SerializableList<BzApplyDistCertificateDto> sList = (SerializableList<BzApplyDistCertificateDto>) paramObj;
		if (sList != null && sList.getTarget() != null
				&& !sList.getTarget().isEmpty()) {
			distDto = sList.getTarget().iterator().next();
			et_adc_company_name.setText(StringHelper.trimToEmpty(distDto
					.getCompanyName()));
			BzServiceProviderTypeDto dto = db
					.findById(distDto.getServiceType());
			tv_adc_service_type.setText(StringHelper.trimToEmpty(dto
					.getTypeName()));
			tv_adc_service_type_value.setText(StringHelper.longToString(distDto
					.getServiceType()));
			tv_adc_service_coverage.setText(StringHelper.longToKm(distDto
					.getServiceCoverage()));
			tv_adc_service_coverage_value.setText(StringHelper
					.longToString(distDto.getServiceCoverage()));
			et_adc_company_address.setText(StringHelper.trimToEmpty(distDto
					.getCompanyAddress()));
			tv_adc_attach_name.setText(StringHelper.trimToEmpty(distDto
					.getBzAttachmentDto().getSrcFileName()));
		}

	}

	private void initPageAttribute() {
		btn_adc_back = (Button) this.findViewById(R.id.btn_adc_back);
		btn_adc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplyDistCertificate.this));

		btn_adc_submit_apply = (Button) this
				.findViewById(R.id.btn_adc_submit_apply);
		btn_adc_submit_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Long serviceType = getTextViewToLong(tv_adc_service_type_value);
				final Long serviceCoverage = getTextViewToLong(tv_adc_service_coverage_value);
				final String companyName = getTextViewToString(et_adc_company_name);
				if (!ValidHelper.notEmpty(ApplyDistCertificate.this,
						companyName, "公司名称")) {
					return;
				}
				final String companyAddress = getTextViewToString(et_adc_company_address);
				if (!ValidHelper.notEmpty(ApplyDistCertificate.this,
						companyAddress, "公司地址")) {
					return;
				}
				if (StringHelper
						.isEmpty(getTextViewToString(tv_adc_attach_name))) {
					ToastHelper.showToast(ApplyDistCertificate.this,
							"配送资质不能为空！");
					return;
				}
				if (distDto == null) {
					distDto = new BzApplyDistCertificateDto();
				}
				distDto.setServiceType(serviceType);
				distDto.setServiceCoverage(serviceCoverage);

				distDto.setCompanyName(companyName);
				distDto.setCompanyAddress(companyAddress);
				SysUserDto su = new SysUserDto();
				su.setId(AppApplication.getInstance().getSessionUser().getId());
				distDto.setSysUserDto(su);
				if (StringHelper.isEmpty(currFilePath)) {
					addApplyDistCertificateTask();
					return;
				}
				UploadHelper uploadHelper = new UploadHelper(
						ApplyDistCertificate.this, AppConfig.uploadFileUrl,
						currFilePath, null);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						distDto.setBzAttachmentId(Long.valueOf(dataId));
						addApplyDistCertificateTask();
					}
				});
				uploadHelper.execute("");
			}

		});

		et_adc_company_name = (EditText) this
				.findViewById(R.id.et_adc_company_name);
		tv_adc_service_type = (TextView) this
				.findViewById(R.id.tv_adc_service_type);
		tv_adc_service_type_value = (TextView) this
				.findViewById(R.id.tv_adc_service_type_value);
		tv_adc_service_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<BzServiceProviderTypeDto> target = db.findByPid(null);
				List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
				for (final BzServiceProviderTypeDto typeDto : target) {
					popItems.add(new PopupWindowItem(typeDto.getTypeName(),
							new OnClickListener() {
								public void onClick(View v) {
									PopCommon.dismiss();
									tv_adc_service_type.setText(typeDto
											.getTypeName());
									tv_adc_service_type_value.setText(typeDto
											.getId() + "");
								}
							}));
				}
				PopCommon.show(ApplyDistCertificate.this, popItems);
			}
		});
		tv_adc_service_coverage = (TextView) this
				.findViewById(R.id.tv_adc_service_coverage);
		tv_adc_service_coverage_value = (TextView) this
				.findViewById(R.id.tv_adc_service_coverage_value);
		tv_adc_service_coverage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showServiceCoverage();
			}
		});
		et_adc_company_address = (EditText) this
				.findViewById(R.id.et_adc_company_address);
		btn_adc_attach = (Button) this.findViewById(R.id.btn_adc_attach);
		tv_adc_attach_name = (TextView) this
				.findViewById(R.id.tv_adc_attach_name);

		btn_adc_attach.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, AppConstants.RC_ANDROID_PHOTO);
			}
		});

	}

	private void showServiceCoverage() {
		List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
		popItems.add(new PopupWindowItem("1Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_adc_service_coverage.setText("1Km");
				tv_adc_service_coverage_value.setText("1");
			}
		}));
		popItems.add(new PopupWindowItem("2Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_adc_service_coverage.setText("2Km");
				tv_adc_service_coverage_value.setText("2");
			}
		}));
		popItems.add(new PopupWindowItem("3Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_adc_service_coverage.setText("3Km");
				tv_adc_service_coverage_value.setText("3");
			}
		}));
		popItems.add(new PopupWindowItem("4Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_adc_service_coverage.setText("4Km");
				tv_adc_service_coverage_value.setText("4");
			}
		}));
		popItems.add(new PopupWindowItem("5Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_adc_service_coverage.setText("5Km");
				tv_adc_service_coverage_value.setText("5");
			}
		}));
		PopCommon.show(ApplyDistCertificate.this, popItems);
	}

	private void addApplyDistCertificateTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplyDistCertificateTask.TP_BZ_APPLY_DIST_CERTIFICATE_DTO,
				distDto);
		TaskService.addTask(new ApplyDistCertificateTask(
				ApplyDistCertificate.this, params));
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
		if (ApplyDistCertificateTask.getTaskId() == taskId) {
			WsPageResult<BzApplyDistCertificateDto> result = (WsPageResult<BzApplyDistCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ApplyDistCertificate.this, "提交失败");
				} else {
					ToastHelper.showToast(ApplyDistCertificate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ApplyDistCertificate.this, "提交成功");
			AppApplication.getInstance().getSessionUser().setDistStatus(1L);
			AppActivityManager.getInstance().finishActivity(
					ApplyDistCertificate.this);
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
				tv_adc_attach_name.setText(FileHelper
						.getSrcFileName(currFilePath));
				break;
			}
		}
	}

}
