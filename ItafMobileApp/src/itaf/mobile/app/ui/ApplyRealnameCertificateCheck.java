package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyRealnameCertificateDto;
import itaf.framework.workflow.dto.BzApprovalInfoDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ApplyRealnameCertificateDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

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
 * 实名认证审查
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class ApplyRealnameCertificateCheck extends BaseUIActivity implements
		UIRefresh {

	private ScrollView sv_arcc_layout;
	// 返回
	private Button btn_arcc_back;
	// 审核信息
	private TextView tv_arcc_approval_info;
	// 真实姓名
	private TextView tv_arcc_relname;
	// 性别
	private TextView tv_arcc_sex;
	// 出生日期
	private TextView tv_arcc_birthday;
	// 手机号码
	private TextView tv_arcc_mobile;
	// 证件类型
	private TextView tv_arcc_id_type;
	// 证件号码
	private TextView tv_arcc_id_no;
	// 证件附件ID
	private TextView tv_arcc_attach_name;

	private BzApplyRealnameCertificateDto realnameDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_realname_certificate_check);

		initPageAttribute();
		addApplyRealnameDetailTask();
	}

	private void initPageAttribute() {
		sv_arcc_layout = (ScrollView) this.findViewById(R.id.sv_arcc_layout);
		sv_arcc_layout.setVisibility(View.GONE);

		btn_arcc_back = (Button) this.findViewById(R.id.btn_arcc_back);
		btn_arcc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplyRealnameCertificateCheck.this));
		tv_arcc_approval_info = (TextView) this
				.findViewById(R.id.tv_arcc_approval_info);
		tv_arcc_relname = (TextView) this.findViewById(R.id.tv_arcc_relname);
		tv_arcc_sex = (TextView) this.findViewById(R.id.tv_arcc_sex);
		tv_arcc_birthday = (TextView) this.findViewById(R.id.tv_arcc_birthday);
		tv_arcc_mobile = (TextView) this.findViewById(R.id.tv_arcc_mobile);
		tv_arcc_id_type = (TextView) this.findViewById(R.id.tv_arcc_id_type);
		tv_arcc_id_no = (TextView) this.findViewById(R.id.tv_arcc_id_no);
		tv_arcc_attach_name = (TextView) this
				.findViewById(R.id.tv_arcc_attach_name);
		tv_arcc_attach_name.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Map<String, String> urlParams = new HashMap<String, String>();
				urlParams.put("fileId", realnameDto.getBzAttachmentId()
						.toString());
				DownLoadHelper downLoadHelper = new DownLoadHelper(
						ApplyRealnameCertificateCheck.this,
						AppConfig.downloadFileUrl,
						getTextViewToString(tv_arcc_attach_name), urlParams);
				downLoadHelper.showNoticeDialog();
			}
		});

	}

	private void addApplyRealnameDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ApplyRealnameCertificateDetailTask.TP_BZ_CONSUMER_ID,
				AppApplication.getInstance().getSessionUser().getId());
		TaskService.addTask(new ApplyRealnameCertificateDetailTask(
				ApplyRealnameCertificateCheck.this, params));
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
		if (ApplyRealnameCertificateDetailTask.getTaskId() == taskId) {
			WsPageResult<BzApplyRealnameCertificateDto> result = (WsPageResult<BzApplyRealnameCertificateDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ApplyRealnameCertificateCheck.this,
							"加载错误!");
				} else {
					ToastHelper.showToast(ApplyRealnameCertificateCheck.this,
							result.getErrorMsg());
				}
				return;
			}
			sv_arcc_layout.setVisibility(View.VISIBLE);

			realnameDto = result.getContent().iterator().next();
			List<BzApprovalInfoDto> approvalInfos = realnameDto
					.getBzApprovalInfos();
			String approvalInfo = "";
			if (approvalInfos != null && approvalInfos.size() > 0) {
				for (BzApprovalInfoDto approvalInfoDto : approvalInfos) {
					approvalInfo += approvalInfoDto.getApprovalInfo() + "\n";
				}
			}
			tv_arcc_approval_info.setText(approvalInfo);
			tv_arcc_relname.setText(StringHelper.trimToEmpty(realnameDto
					.getName()));
			tv_arcc_sex
					.setText(BzApplyRealnameCertificateDto.SEX_MALE
							.equals(realnameDto.getSex()) ? BzApplyRealnameCertificateDto.MALE
							: BzApplyRealnameCertificateDto.FEMALE);
			tv_arcc_birthday.setText(DateUtil.formatDate(realnameDto
					.getBirthdate()));
			tv_arcc_mobile.setText(StringHelper.trimToEmpty(realnameDto
					.getMobile()));
			tv_arcc_id_type
					.setText(BzApplyRealnameCertificateDto.ID_TYPE_CARD
							.equals(realnameDto.getIdType()) ? BzApplyRealnameCertificateDto.ID_CARD
							: BzApplyRealnameCertificateDto.ID_PASSPORT);
			tv_arcc_id_no.setText(StringHelper.trimToEmpty(realnameDto
					.getIdNo()));
			tv_arcc_attach_name.setText(StringHelper.trimToEmpty(realnameDto
					.getBzAttachmentDto().getSrcFileName()));
		}
	}
}
