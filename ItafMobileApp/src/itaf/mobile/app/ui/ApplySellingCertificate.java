package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplySellingCertificateDto;
import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.bean.PopupWindowItem;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplySellingCertificateTask;
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
 * 申请售卖服务
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ApplySellingCertificate extends BaseUIActivity implements
		UIRefresh {

	public static final String AP_BZ_APPLY_SELLING_CERTIFICATE_DTO = "BzApplySellingCertificateDto";
	private ServiceProviderTypeDb db = new ServiceProviderTypeDb(this);
	// 返回
	private Button btn_asc_back;
	// 提交申请
	private Button btn_asc_submit_apply;
	// 商家名称
	private EditText et_asc_company_name;
	// 服务类型
	private TextView tv_asc_service_type;
	// 服务类型
	private TextView tv_asc_service_type_value;
	// 服务范围
	private TextView tv_asc_service_coverage;
	// 服务范围
	private TextView tv_asc_service_coverage_value;
	// 商家地址
	private EditText et_asc_company_address;
	// 商家资质
	private Button btn_asc_attach;
	// 商家资质ID
	private TextView tv_asc_attach_name;
	// 附件地址
	private String currFilePath;
	private BzApplySellingCertificateDto sellingDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_selling_certificate);

		initPageAttribute();
		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_APPLY_SELLING_CERTIFICATE_DTO);
		SerializableList<BzApplySellingCertificateDto> sList = (SerializableList<BzApplySellingCertificateDto>) paramObj;
		if (sList != null && sList.getTarget() != null
				&& !sList.getTarget().isEmpty()) {
			sellingDto = sList.getTarget().iterator().next();
			et_asc_company_name.setText(StringHelper.trimToEmpty(sellingDto
					.getCompanyName()));
			BzServiceProviderTypeDto dto = db.findById(sellingDto
					.getServiceType());
			tv_asc_service_type.setText(StringHelper.trimToEmpty(dto
					.getTypeName()));
			tv_asc_service_type_value.setText(StringHelper
					.longToString(sellingDto.getServiceType()));
			tv_asc_service_coverage.setText(StringHelper.longToKm(sellingDto
					.getServiceCoverage()));
			tv_asc_service_coverage_value.setText(StringHelper
					.longToString(sellingDto.getServiceCoverage()));
			et_asc_company_address.setText(StringHelper.trimToEmpty(sellingDto
					.getCompanyAddress()));
			tv_asc_attach_name.setText(StringHelper.trimToEmpty(sellingDto
					.getBzAttachmentDto().getSrcFileName()));
		}

	}

	private void initPageAttribute() {
		btn_asc_back = (Button) this.findViewById(R.id.btn_asc_back);
		btn_asc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplySellingCertificate.this));

		btn_asc_submit_apply = (Button) this
				.findViewById(R.id.btn_asc_submit_apply);
		btn_asc_submit_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				final Long serviceType = getTextViewToLong(tv_asc_service_type_value);
				final Long serviceCoverage = getTextViewToLong(tv_asc_service_coverage_value);
				final String companyName = getTextViewToString(et_asc_company_name);
				if (!ValidHelper.notEmpty(ApplySellingCertificate.this,
						companyName, "商家名称")) {
					return;
				}
				final String companyAddress = getTextViewToString(et_asc_company_address);
				if (!ValidHelper.notEmpty(ApplySellingCertificate.this,
						companyAddress, "商家地址")) {
					return;
				}
				if (StringHelper
						.isEmpty(getTextViewToString(tv_asc_attach_name))) {
					ToastHelper.showToast(ApplySellingCertificate.this,
							"商家资质不能为空！");
					return;
				}
				if (sellingDto == null) {
					sellingDto = new BzApplySellingCertificateDto();
				}
				sellingDto.setServiceType(serviceType);
				sellingDto.setServiceCoverage(serviceCoverage);
				sellingDto.setCompanyName(companyName);
				sellingDto.setCompanyAddress(companyAddress);
				SysUserDto su = new SysUserDto();
				su.setId(AppApplication.getInstance().getSessionUser().getId());
				sellingDto.setSysUserDto(su);
				if (StringHelper.isEmpty(currFilePath)) {
					addBzSellingCertificateApplyTask();
					return;
				}
				UploadHelper uploadHelper = new UploadHelper(
						ApplySellingCertificate.this, AppConfig.uploadFileUrl,
						currFilePath, null);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						sellingDto.setBzAttachmentId(Long.valueOf(dataId));
						addBzSellingCertificateApplyTask();
					}
				});
				uploadHelper.execute("");
			}
		});
		et_asc_company_name = (EditText) this
				.findViewById(R.id.et_asc_company_name);
		tv_asc_service_type = (TextView) this
				.findViewById(R.id.tv_asc_service_type);
		tv_asc_service_type_value = (TextView) this
				.findViewById(R.id.tv_asc_service_type_value);
		tv_asc_service_type.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				List<BzServiceProviderTypeDto> target = db.findByPid(null);
				List<PopupWindowItem> popItems = new ArrayList<PopupWindowItem>();
				for (final BzServiceProviderTypeDto typeDto : target) {
					popItems.add(new PopupWindowItem(typeDto.getTypeName(),
							new OnClickListener() {
								public void onClick(View v) {
									PopCommon.dismiss();
									tv_asc_service_type.setText(typeDto
											.getTypeName());
									tv_asc_service_type_value.setText(typeDto
											.getId() + "");
								}
							}));
				}
				PopCommon.show(ApplySellingCertificate.this, popItems);
			}
		});
		tv_asc_service_coverage = (TextView) this
				.findViewById(R.id.tv_asc_service_coverage);
		tv_asc_service_coverage_value = (TextView) this
				.findViewById(R.id.tv_asc_service_coverage_value);
		tv_asc_service_coverage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showServiceCoverage();
			}
		});
		et_asc_company_address = (EditText) this
				.findViewById(R.id.et_asc_company_address);
		btn_asc_attach = (Button) this.findViewById(R.id.btn_asc_attach);
		tv_asc_attach_name = (TextView) this
				.findViewById(R.id.tv_asc_attach_name);

		btn_asc_attach.setOnClickListener(new OnClickListener() {
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
				tv_asc_service_coverage.setText("1Km");
				tv_asc_service_coverage_value.setText("1");
			}
		}));
		popItems.add(new PopupWindowItem("2Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_asc_service_coverage.setText("2Km");
				tv_asc_service_coverage_value.setText("2");
			}
		}));
		popItems.add(new PopupWindowItem("3Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_asc_service_coverage.setText("3Km");
				tv_asc_service_coverage_value.setText("3");
			}
		}));
		popItems.add(new PopupWindowItem("4Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_asc_service_coverage.setText("4Km");
				tv_asc_service_coverage_value.setText("4");
			}
		}));
		popItems.add(new PopupWindowItem("5Km", new OnClickListener() {
			public void onClick(View v) {
				PopCommon.dismiss();
				tv_asc_service_coverage.setText("5Km");
				tv_asc_service_coverage_value.setText("5");
			}
		}));
		PopCommon.show(ApplySellingCertificate.this, popItems);
	}

	private void addBzSellingCertificateApplyTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(
				ApplySellingCertificateTask.TP_BZ_APPLY_SELLING_CERTIFICATE_DTO,
				sellingDto);
		TaskService.addTask(new ApplySellingCertificateTask(
				ApplySellingCertificate.this, params));
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
		if (ApplySellingCertificateTask.getTaskId() == taskId) {
			WsPageResult<BzApplySellingCertificateDto> result = (WsPageResult<BzApplySellingCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper
							.showToast(ApplySellingCertificate.this, "提交失败!");
				} else {
					ToastHelper.showToast(ApplySellingCertificate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ApplySellingCertificate.this, "提交成功!");
			AppApplication.getInstance().getSessionUser().setSellingStatus(1L);
			AppActivityManager.getInstance().finishActivity(
					ApplySellingCertificate.this);
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
				tv_asc_attach_name.setText(FileHelper
						.getSrcFileName(currFilePath));
				break;
			}
		}
	}

}
