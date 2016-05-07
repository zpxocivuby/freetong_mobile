package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplySellingCertificateDto;
import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplySellingCertificateDetailTask;
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
 * 申请售卖服务审查
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ApplySellingCertificateCheck extends BaseUIActivity implements
		UIRefresh {

	private ServiceProviderTypeDb db = new ServiceProviderTypeDb(this);

	private ScrollView sv_ascc_layout;
	// 返回
	private Button btn_ascc_back;
	// 审核信息
	private TextView tv_ascc_approval_info;
	// 商家名称
	private TextView tv_ascc_company_name;
	// 服务类型
	private TextView tv_ascc_service_type;
	// 服务范围
	private TextView tv_ascc_service_coverage;
	// 商家地址
	private TextView tv_ascc_company_address;
	// 商家资质名称
	private TextView tv_ascc_attach_name;
	private BzApplySellingCertificateDto sellingDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_selling_certificate_check);

		initPageAttribute();
		addApplySellingCertificateDetailTask();
	}

	private void initPageAttribute() {
		sv_ascc_layout = (ScrollView) this.findViewById(R.id.sv_ascc_layout);
		sv_ascc_layout.setVisibility(View.GONE);
		btn_ascc_back = (Button) this.findViewById(R.id.btn_ascc_back);
		btn_ascc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplySellingCertificateCheck.this));

		tv_ascc_approval_info = (TextView) this
				.findViewById(R.id.tv_ascc_approval_info);
		tv_ascc_company_name = (TextView) this
				.findViewById(R.id.tv_ascc_company_name);
		tv_ascc_service_type = (TextView) this
				.findViewById(R.id.tv_ascc_service_type);
		tv_ascc_service_coverage = (TextView) this
				.findViewById(R.id.tv_ascc_service_coverage);
		tv_ascc_company_address = (TextView) this
				.findViewById(R.id.tv_ascc_company_address);
		tv_ascc_attach_name = (TextView) this
				.findViewById(R.id.tv_ascc_attach_name);
		tv_ascc_attach_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("fileId", sellingDto.getBzAttachmentId()
						.toString());
				DownLoadHelper downLoadHelper = new DownLoadHelper(
						ApplySellingCertificateCheck.this,
						AppConfig.downloadFileUrl,
						getTextViewToString(tv_ascc_attach_name), urlParams);
				downLoadHelper.showNoticeDialog();
			}
		});

	}

	private void addApplySellingCertificateDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplySellingCertificateDetailTask.TP_BZ_CONSUMER_ID,
				AppApplication.getInstance().getSessionUser().getId());
		TaskService.addTask(new ApplySellingCertificateDetailTask(
				ApplySellingCertificateCheck.this, params));
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
		if (ApplySellingCertificateDetailTask.getTaskId() == taskId) {
			WsPageResult<BzApplySellingCertificateDto> result = (WsPageResult<BzApplySellingCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ApplySellingCertificateCheck.this,
							"加载错误");
				} else {
					ToastHelper.showToast(ApplySellingCertificateCheck.this,
							result.getErrorMsg());
				}
				return;
			}
			sv_ascc_layout.setVisibility(View.VISIBLE);

			sellingDto = result.getContent().iterator().next();
			List<BzApprovalInfoDto> approvalInfos = sellingDto
					.getBzApprovalInfos();
			String approvalInfo = "";
			if (approvalInfos != null && approvalInfos.size() > 0) {
				for (BzApprovalInfoDto approvalInfoDto : approvalInfos) {
					approvalInfo += approvalInfoDto.getApprovalInfo() + "\n";
				}
			}
			tv_ascc_approval_info.setText(approvalInfo);
			tv_ascc_company_name.setText(StringHelper.trimToEmpty(sellingDto
					.getCompanyName()));
			BzServiceProviderTypeDto typeDto = db.findById(sellingDto
					.getServiceType());
			tv_ascc_service_type.setText(StringHelper.trimToEmpty(typeDto
					.getTypeName()));
			tv_ascc_service_coverage.setText(StringHelper.longToKm(sellingDto
					.getServiceCoverage()));
			tv_ascc_company_address.setText(StringHelper.trimToEmpty(sellingDto
					.getCompanyAddress()));
			tv_ascc_attach_name.setText(StringHelper.trimToEmpty(sellingDto
					.getBzAttachmentDto().getSrcFileName()));

		}
	}

}
