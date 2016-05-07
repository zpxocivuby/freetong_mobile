package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.framework.merchant.dto.BzInvoiceDto;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.order.dto.BzOrderPaymentDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderDetailTask;
import itaf.mobile.app.ui.adapter.OrderItemAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 订单详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class OrderDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_ORDER_ID = "bzOrderId";

	// 返回
	private Button btn_od_back;
	// 订单号
	private TextView tv_od_serial_no;
	// 购买时间
	private TextView tv_od_create_date;
	// 发货时间
	private TextView tv_od_invoice_date;
	// 配送时间
	private TextView tv_od_dist_date;
	// 配送类型
	private TextView tv_od_dist_type;
	// 支付方式
	private TextView tv_od_pay_type;
	// 订单总额
	private TextView tv_od_pay_amount;

	private OrderItemAdapter adapter;
	private List<BzOrderItemDto> adapterContent = new ArrayList<BzOrderItemDto>();
	private ListView lv_od_content;

	private BzOrderDto bzOrderDto;
	private Long bzOrderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_detail);
		bzOrderId = getIntent().getExtras().getLong(AP_BZ_ORDER_ID);
		initPageAttribute();
		addOrderDetailTask();
	}

	private void initPageAttribute() {
		btn_od_back = (Button) this.findViewById(R.id.btn_od_back);
		btn_od_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderDetail.this));

		tv_od_serial_no = (TextView) this.findViewById(R.id.tv_od_serial_no);
		tv_od_create_date = (TextView) this
				.findViewById(R.id.tv_od_create_date);
		tv_od_invoice_date = (TextView) this
				.findViewById(R.id.tv_od_invoice_date);
		tv_od_dist_date = (TextView) this.findViewById(R.id.tv_od_dist_date);
		tv_od_dist_type = (TextView) this.findViewById(R.id.tv_od_dist_type);
		tv_od_pay_type = (TextView) this.findViewById(R.id.tv_od_pay_type);
		tv_od_pay_amount = (TextView) this.findViewById(R.id.tv_od_pay_amount);

		lv_od_content = (ListView) findViewById(R.id.lv_od_content);
		adapter = new OrderItemAdapter(OrderDetail.this, adapterContent);
		lv_od_content.setAdapter(adapter);
		lv_od_content.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent();
				intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID, adapterContent
						.get(position - 1).getId());
				intent.setClass(OrderDetail.this, ProductDetail.class);
				startActivity(intent);
			}
		});
	}

	private void addOrderDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderDetailTask.TP_BZ_ORDER_ID, bzOrderId);
		TaskService.addTask(new OrderDetailTask(OrderDetail.this, params));
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
		if (OrderDetailTask.getTaskId() == taskId) {
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderDetail.this, "加载失败");
				} else {
					ToastHelper.showToast(OrderDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			bzOrderDto = result.getContent().iterator().next();
			tv_od_create_date.setText(DateUtil.formatDate(bzOrderDto
					.getCreatedDate()));
			tv_od_serial_no.setText(StringHelper.trimToEmpty(bzOrderDto
					.getOrderSerialNo()));
			BzInvoiceDto bzInvoiceDto = bzOrderDto.getBzInvoiceDto();
			if (bzInvoiceDto != null) {
				tv_od_invoice_date.setText(DateUtil.formatDate(bzInvoiceDto
						.getCreatedDate()));
				BzDistOrderDto bzDistOrderDto = bzInvoiceDto
						.getBzDistOrderDto();
				if (bzDistOrderDto != null) {
					tv_od_dist_date.setText(DateUtil.formatDate(bzDistOrderDto
							.getOrderEdc()));
				}
				BzDistCompanyDto bzDistCompanyDto = bzInvoiceDto
						.getBzDistCompanyDto();
				if (bzDistCompanyDto != null) {
					tv_od_dist_type.setText(StringHelper
							.trimToEmpty(bzDistCompanyDto.getUsername()));
				}
			}
			BzOrderPaymentDto bzOrderPaymentDto = bzOrderDto
					.getBzOrderPaymentDto();
			if (bzOrderPaymentDto != null
					&& bzOrderPaymentDto.getBzPaymentTypeDto() != null) {
				tv_od_pay_type.setText(StringHelper
						.trimToEmpty(bzOrderPaymentDto.getBzPaymentTypeDto()
								.getTypeName()));
			}
			tv_od_pay_amount.setText(StringHelper.bigDecimalToRmb(bzOrderDto
					.getOrderAmount()));
			adapterContent.clear();
			adapterContent.addAll(bzOrderDto.getBzOrderItemDtos());
			adapter.notifyDataSetChanged();
		}
	}

}
