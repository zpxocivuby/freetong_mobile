package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.framework.merchant.dto.BzInvoiceDto;
import itaf.framework.order.dto.BzOrderDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MerchantInvoiceTask;
import itaf.mobile.app.ui.adapter.MerchantInvoiceAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

/**
 * 商家发货
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantInvoice extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_ORDER_DTO = "BzOrderDto";
	public static final int RC_SELECT_DIST_COMP = 1;

	// 返回
	private Button btn_mi_back;
	// 发货
	private Button btn_mi_invoice;
	// 选择配送商
	private TextView tv_mi_select_dist_company;

	private ExpandableListView elv_mi_content;
	private MerchantInvoiceAdapter adapter;
	private List<BzOrderDto> adapterContent = new ArrayList<BzOrderDto>();
	private BzOrderDto bzOrderDto;

	private BzDistCompanyDto bzDistCompanyDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_invoice);
		initPageAttribute();
		initListView();

		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_ORDER_DTO);
		SerializableList<BzOrderDto> sList = (SerializableList<BzOrderDto>) paramObj;
		if (sList == null || sList.getTarget() == null
				|| sList.getTarget().isEmpty()) {
			AppActivityManager.getInstance().finishActivity(
					MerchantInvoice.this);
			return;
		}
		bzOrderDto = sList.getTarget().iterator().next();
		adapterContent.clear();
		adapterContent.add(bzOrderDto);
		for (int i = 0; i < adapterContent.size(); i++) {
			elv_mi_content.expandGroup(i);
		}
		adapter.notifyDataSetChanged();

	}

	private void initPageAttribute() {
		btn_mi_back = (Button) this.findViewById(R.id.btn_mi_back);
		btn_mi_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantInvoice.this));
		btn_mi_invoice = (Button) this.findViewById(R.id.btn_mi_invoice);
		btn_mi_invoice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addMerchantInvoiceTask();
			}

		});

		tv_mi_select_dist_company = (TextView) findViewById(R.id.tv_mi_select_dist_company);
		tv_mi_select_dist_company.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MerchantInvoice.this,
						MerchantSelectDistList.class);
				startActivityForResult(intent, RC_SELECT_DIST_COMP);
			}
		});
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_SELECT_DIST_COMP) {
				Bundle bundle = data.getExtras();
				SerializableList<BzDistCompanyDto> sList = (SerializableList<BzDistCompanyDto>) bundle
						.getSerializable(MerchantSelectDistList.AP_BZ_DIST_COMPANY_DTO);
				if (sList != null && sList.getTarget() != null
						&& sList.getTarget().size() > 0) {
					bzDistCompanyDto = sList.getTarget().iterator().next();
					tv_mi_select_dist_company.setText(bzDistCompanyDto
							.getCompanyName());
				}
			}
		}
	}

	private void initListView() {
		// 增加那个 更多按钮,必须放在Adapter之前
		elv_mi_content = (ExpandableListView) this
				.findViewById(R.id.elv_mi_content);
		adapter = new MerchantInvoiceAdapter(MerchantInvoice.this,
				adapterContent);
		elv_mi_content.setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_mi_content.setCacheColorHint(0);

		elv_mi_content.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
						adapterContent.get(groupPosition).getBzOrderItemDtos()
								.get(childPosition).getBzProductDto().getId());
				intent.setClass(MerchantInvoice.this, ProductDetail.class);
				startActivity(intent);
				return false;
			}
		});
	}

	private void addMerchantInvoiceTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantInvoiceTask.TP_BZ_ORDER_ID, bzOrderDto.getId());
		params.put(MerchantInvoiceTask.TP_BZ_DIST_COMPANY_ID,
				bzDistCompanyDto.getId());
		TaskService.addTask(new MerchantInvoiceTask(MerchantInvoice.this,
				params));
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
		if (MerchantInvoiceTask.getTaskId() == taskId) {
			WsPageResult<BzInvoiceDto> result = (WsPageResult<BzInvoiceDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantInvoice.this, "发货失败");
				} else {
					ToastHelper.showToast(MerchantInvoice.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(MerchantInvoice.this, "发货成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					MerchantInvoice.this);
		}
	}
}
