package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementSummaryDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistStatementSummaryTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 配送结算批次
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class DistStatement extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_ds_back;
	// 结算详情
	private Button btn_ds_detail;
	// 配送商名称
	private TextView tv_ds_dist_company_name;
	// 结算完结总额
	private TextView tv_ds_processed_amount;
	// 未发起结算应收总额
	private TextView tv_ds_unprocessed_receivable_amount;
	// 未发起结算应退总额
	private TextView tv_ds_unprocessed_refund_amount;
	// 已发起结算应收总额
	private TextView tv_ds_processing_receivable_amount;
	// 已发起结算应退总额
	private TextView tv_ds_processing_refund_amount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_statement);

		initPageAttribute();
		addDistStatementSummaryTask();
	}

	private void initPageAttribute() {
		btn_ds_back = (Button) this.findViewById(R.id.btn_ds_back);
		btn_ds_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistStatement.this));

		btn_ds_detail = (Button) this.findViewById(R.id.btn_ds_detail);
		btn_ds_detail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DistStatement.this, DistStatementItem.class);
				startActivity(intent);
			}
		});

		tv_ds_dist_company_name = (TextView) this
				.findViewById(R.id.tv_ds_dist_company_name);
		tv_ds_processed_amount = (TextView) this
				.findViewById(R.id.tv_ds_processed_amount);
		tv_ds_unprocessed_receivable_amount = (TextView) this
				.findViewById(R.id.tv_ds_unprocessed_receivable_amount);
		tv_ds_unprocessed_refund_amount = (TextView) this
				.findViewById(R.id.tv_ds_unprocessed_refund_amount);
		tv_ds_processing_receivable_amount = (TextView) this
				.findViewById(R.id.tv_ds_processing_receivable_amount);
		tv_ds_processing_refund_amount = (TextView) this
				.findViewById(R.id.tv_ds_processing_refund_amount);

	}

	private void addDistStatementSummaryTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistStatementSummaryTask.BZ_DIST_COMPANY_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new DistStatementSummaryTask(DistStatement.this,
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
		if (DistStatementSummaryTask.getTaskId() == taskId) {
			WsPageResult<BzDistStatementSummaryDto> result = (WsPageResult<BzDistStatementSummaryDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			BzDistStatementSummaryDto target = result.getContent().iterator()
					.next();
			tv_ds_dist_company_name.setText(AppApplication.getInstance()
					.getSessionUser().getUsername());
			tv_ds_processed_amount.setText(StringHelper.bigDecimalToRmb(target
					.getProcessedAmount()));
			tv_ds_unprocessed_receivable_amount.setText(StringHelper
					.bigDecimalToRmb(target.getUnprocessedReceivableAmount()));
			tv_ds_unprocessed_refund_amount.setText(StringHelper
					.bigDecimalToRmb(target.getUnprocessedRefundAmount()));
			tv_ds_processing_receivable_amount.setText(StringHelper
					.bigDecimalToRmb(target.getProcessingReceivableAmount()));
			tv_ds_processing_refund_amount.setText(StringHelper
					.bigDecimalToRmb(target.getProcessingRefundAmount()));
			return;
		}
	}

}
