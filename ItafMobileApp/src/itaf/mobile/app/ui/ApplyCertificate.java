package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 申请服务列表
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ApplyCertificate extends BaseUIActivity {

	// 返回
	private Button btn_ac_back;
	// 申请售卖服务
	private TextView tv_ac_apply_selling_service;
	// 申请配送服务
	private TextView tv_ac_apply_dist_service;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.apply_certificate);

		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_ac_back = (Button) this.findViewById(R.id.btn_ac_back);
		btn_ac_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ApplyCertificate.this));

		tv_ac_apply_selling_service = (TextView) this
				.findViewById(R.id.tv_ac_apply_selling_service);

		tv_ac_apply_dist_service = (TextView) this
				.findViewById(R.id.tv_ac_apply_dist_service);
		SessionUser su = AppApplication.getInstance().getSessionUser();
		final Long distStatus = su.getDistStatus();
		final Long sellingStatus = su.getSellingStatus();
		if (sellingStatus != SessionUser.STATUS_CERTIFICATED) {
			tv_ac_apply_selling_service.setVisibility(View.VISIBLE);
			tv_ac_apply_selling_service
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							if (sellingStatus == SessionUser.STATUS_NONE) {
								intent.setClass(ApplyCertificate.this,
										ApplySellingCertificate.class);
							} else if (sellingStatus == SessionUser.STATUS_CERTIFICATING) {
								intent.setClass(ApplyCertificate.this,
										ApplySellingCertificateCheck.class);
							} else if (sellingStatus == SessionUser.STATUS_FAILED) {
								intent.setClass(ApplyCertificate.this,
										ApplySellingCertificateResult.class);
							}
							startActivity(intent);
							AppActivityManager.getInstance().finishActivity(
									ApplyCertificate.this);
						}
					});
		} else {
			tv_ac_apply_selling_service.setVisibility(View.GONE);
		}

		if (distStatus != SessionUser.STATUS_CERTIFICATED) {
			tv_ac_apply_dist_service.setVisibility(View.VISIBLE);
			tv_ac_apply_dist_service.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					if (distStatus == SessionUser.STATUS_NONE) {
						intent.setClass(ApplyCertificate.this,
								ApplyDistCertificate.class);
					} else if (distStatus == SessionUser.STATUS_CERTIFICATING) {
						intent.setClass(ApplyCertificate.this,
								ApplyDistCertificateCheck.class);
					} else if (distStatus == SessionUser.STATUS_FAILED) {
						intent.setClass(ApplyCertificate.this,
								ApplyDistCertificateResult.class);
					}
					startActivity(intent);
					AppActivityManager.getInstance().finishActivity(
							ApplyCertificate.this);
				}
			});
		} else {
			tv_ac_apply_dist_service.setVisibility(View.GONE);
		}

	}

}
