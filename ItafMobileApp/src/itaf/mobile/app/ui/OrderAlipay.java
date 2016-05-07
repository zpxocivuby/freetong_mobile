package itaf.mobile.app.ui;

import itaf.framework.order.dto.BzOrderDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderAlipayTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 支付宝支付
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class OrderAlipay extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_ORDER_DTO = "BzOrderDto";

	// 返回
	private Button btn_op_back;
	// 结算
	private Button btn_op_order_pay;
	// 订单号
	private TextView tv_op_order_no;
	// 支付金额
	private TextView tv_op_pay_amount;
	// 支付宝账号
	private EditText et_op_alipay_username;

	private BzOrderDto orderDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_alipay);

		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_ORDER_DTO);
		SerializableList<BzOrderDto> sList = (SerializableList<BzOrderDto>) paramObj;
		Collection<BzOrderDto> orderDtos = sList.getTarget();
		if (orderDtos == null || orderDtos.isEmpty()) {
			AppActivityManager.getInstance().finishActivity(OrderAlipay.this);
			return;
		}
		orderDto = orderDtos.iterator().next();
		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_op_back = (Button) this.findViewById(R.id.btn_op_back);
		btn_op_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderAlipay.this));

		btn_op_order_pay = (Button) this.findViewById(R.id.btn_op_order_pay);
		btn_op_order_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String alipayUsername = getTextViewToString(et_op_alipay_username);
				if (StringHelper.isEmpty(alipayUsername)) {
					ToastHelper.showToast(OrderAlipay.this, "账号不能为空！ ");
					return;
				}
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(OrderAlipayTask.TP_ORDER_ID, orderDto.getId());
				String payAmount = getTextViewToString(tv_op_pay_amount);
				params.put(OrderAlipayTask.TP_PAY_AMOUNT, new BigDecimal(
						payAmount));
				params.put(OrderAlipayTask.TP_ALIPAY_USERNAME, alipayUsername);
				TaskService.addTask(new OrderAlipayTask(OrderAlipay.this,
						params));
				showProgressDialog(PD_LOADING);
			}
		});

		tv_op_order_no = (TextView) this.findViewById(R.id.tv_op_order_no);
		tv_op_order_no.setText(orderDto.getOrderSerialNo());
		tv_op_pay_amount = (TextView) this.findViewById(R.id.tv_op_pay_amount);
		tv_op_pay_amount.setText(orderDto.getOrderAmount() + "");
		et_op_alipay_username = (EditText) this
				.findViewById(R.id.et_op_alipay_username);

	}

	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (OrderAlipayTask.getTaskId() == taskId) {
			if (args[1] == null) {
				ToastHelper.showToast(OrderAlipay.this, "支付失败！ ");
				return;
			}
			ToastHelper.showToast(OrderAlipay.this, "支付成功！ ");
			AppActivityManager.getInstance().finishActivity(OrderAlipay.this);
		}
	}

}
