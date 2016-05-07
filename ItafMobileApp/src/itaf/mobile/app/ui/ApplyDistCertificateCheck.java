package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyDistCertificateDto;
import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplyDistCertificateDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.ServiceProviderTypeDb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * 申请配送服务审查
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ApplyDistCertificateCheck extends BaseUIActivity implements
		UIRefresh {

	private ServiceProviderTypeDb db = new ServiceProviderTypeDb(this);

	private ScrollView sv_adcc_layout;
	// 返回
	private Button btn_adcc_back;
	// 审核信息
	private TextView tv_adcc_approval_info;
	// 公司名称
	private TextView tv_adcc_company_name;
	// 服务类型
	private TextView tv_adcc_service_type;
	// 服务范围
	private TextView tv_adcc_service_coverage;
	// 公司地址
	private TextView tv_adcc_company_address;
	// 公司资质名称
	private TextView tv_adcc_attach_name;
	private BzApplyDistCertificateDto distDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_dist_certificate_check);

		initPageAttribute();
		loadDistCertificateApply();
	}

	private void initPageAttribute() {
		sv_adcc_layout = (ScrollView) this.findViewById(R.id.sv_adcc_layout);
		sv_adcc_layout.setVisibility(View.GONE);

		btn_adcc_back = (Button) this.findViewById(R.id.btn_adcc_back);
		btn_adcc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplyDistCertificateCheck.this));
		tv_adcc_approval_info = (TextView) this
				.findViewById(R.id.tv_adcc_approval_info);
		tv_adcc_company_name = (TextView) this
				.findViewById(R.id.tv_adcc_company_name);
		tv_adcc_service_type = (TextView) this
				.findViewById(R.id.tv_adcc_service_type);
		tv_adcc_service_coverage = (TextView) this
				.findViewById(R.id.tv_adcc_service_coverage);
		tv_adcc_company_address = (TextView) this
				.findViewById(R.id.tv_adcc_company_address);
		tv_adcc_attach_name = (TextView) this
				.findViewById(R.id.tv_adcc_attach_name);
		tv_adcc_attach_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("fileId", distDto.getBzAttachmentId().toString());
				DownLoadHelper downLoadHelper = new DownLoadHelper(
						ApplyDistCertificateCheck.this,
						AppConfig.downloadFileUrl,
						getTextViewToString(tv_adcc_attach_name), urlParams);
				downLoadHelper.showNoticeDialog();
			}
		});

	}

	private void loadDistCertificateApply() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplyDistCertificateDetailTask.TP_BZ_CONSUMER_ID,
				AppApplication.getInstance().getSessionUser().getId());
		TaskService.addTask(new ApplyDistCertificateDetailTask(
				ApplyDistCertificateCheck.this, params));
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
		if (ApplyDistCertificateDetailTask.getTaskId() == taskId) {
			WsPageResult<BzApplyDistCertificateDto> result = (WsPageResult<BzApplyDistCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ApplyDistCertificateCheck.this,
							"加载错误");
				} else {
					ToastHelper.showToast(ApplyDistCertificateCheck.this,
							result.getErrorMsg());
				}
				return;
			}
			sv_adcc_layout.setVisibility(View.VISIBLE);
			distDto = result.getContent().iterator().next();
			List<BzApprovalInfoDto> approvalInfos = distDto
					.getBzApprovalInfos();
			String approvalInfo = "";
			if (approvalInfos != null && approvalInfos.size() > 0) {
				for (BzApprovalInfoDto approvalInfoDto : approvalInfos) {
					approvalInfo += approvalInfoDto.getApprovalInfo() + "\n";
				}
			}
			tv_adcc_approval_info.setText(approvalInfo);
			tv_adcc_company_name.setText(StringHelper.trimToEmpty(distDto
					.getCompanyName()));
			BzServiceProviderTypeDto typeDto = db.findById(distDto
					.getServiceType());
			tv_adcc_service_type.setText(StringHelper.trimToEmpty(typeDto
					.getTypeName()));
			tv_adcc_service_coverage.setText(StringHelper.longToKm(distDto
					.getServiceCoverage()));
			tv_adcc_company_address.setText(StringHelper.trimToEmpty(distDto
					.getCompanyAddress()));
			tv_adcc_attach_name.setText(StringHelper.trimToEmpty(distDto
					.getBzAttachmentDto().getSrcFileName()));
		}
	}

}
