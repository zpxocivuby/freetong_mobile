package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderInitDto;
import itaf.framework.order.dto.BzOrderPaymentDto;
import itaf.framework.order.dto.BzPaymentTypeDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.im.ui.ImChatWindow;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderCreateInitTask;
import itaf.mobile.app.task.netreader.OrderCreateTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

/**
 * 
 * 订单创建
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月25日
 */
public class OrderCreate extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_MERCHANT_DTO = "BzMerchantDto";
	public static final int RC_ADDRESS = 1;

	// 返回
	private Button btn_oc_back;
	// 创建订单
	private Button btn_oc_create;
	// 邮费
	private TextView tv_oc_dist_price;
	// 总价
	private TextView tv_oc_amount;
	// 收货地址
	private TextView tv_oc_user_delivery_address;
	// 配送备注
	private EditText et_oc_dist_desc;
	private LinearLayout linear_oc_pay_type;

	private ExpandableListView elv_oc_content;
	private OrderCreateAdapter adapter;
	private List<BzMerchantDto> adapterContent = new ArrayList<BzMerchantDto>();

	private BzUserAddressDto userAddressDto;

	private BzPaymentTypeDto paymentTypeDto;

	private BzMerchantDto bzMerchantDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_create);

		initPageAttribute();
		initListView();
		addOrderCreateInitTask();

		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_MERCHANT_DTO);
		SerializableList<BzMerchantDto> sList = (SerializableList<BzMerchantDto>) paramObj;
		if (sList == null || sList.getTarget() == null
				|| sList.getTarget().isEmpty()) {
			AppActivityManager.getInstance().finishActivity(OrderCreate.this);
			return;
		}
		bzMerchantDto = sList.getTarget().iterator().next();
		adapterContent.clear();
		adapterContent.add(bzMerchantDto);
		for (int i = 0; i < adapterContent.size(); i++) {
			elv_oc_content.expandGroup(i);
		}
		adapter.notifyDataSetChanged();
	}

	private void initPageAttribute() {
		btn_oc_back = (Button) this.findViewById(R.id.btn_oc_back);
		btn_oc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderCreate.this));

		btn_oc_create = (Button) this.findViewById(R.id.btn_oc_create);
		btn_oc_create.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addOrderCreateTask();
			}
		});

		tv_oc_dist_price = (TextView) this.findViewById(R.id.tv_oc_dist_price);
		tv_oc_amount = (TextView) this.findViewById(R.id.tv_oc_amount);
		tv_oc_user_delivery_address = (TextView) this
				.findViewById(R.id.tv_oc_user_delivery_address);
		tv_oc_user_delivery_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle bundle = new Bundle();
				bundle.putSerializable(
						UserAddressSelect.AP_BZ_USER_ADDRESS_DTO,
						new SerializableList<BzUserAddressDto>(userAddressDto));
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(OrderCreate.this, UserAddressSelect.class);
				startActivityForResult(intent, RC_ADDRESS);
			}
		});
		et_oc_dist_desc = (EditText) this.findViewById(R.id.et_oc_dist_desc);
		linear_oc_pay_type = (LinearLayout) findViewById(R.id.linear_oc_pay_type);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			if (requestCode == RC_ADDRESS) {
				Bundle bundle = data.getExtras();
				SerializableList<BzUserAddressDto> sList = (SerializableList<BzUserAddressDto>) bundle
						.getSerializable(UserAddressSelect.AP_BZ_USER_ADDRESS_DTO);
				if (sList == null || sList.getTarget() == null
						|| sList.getTarget().size() <= 0) {
					return;
				}
				userAddressDto = sList.getTarget().iterator().next();
				tv_oc_user_delivery_address
						.setText(userAddressDto.getAddress());
			}
		}
	}

	private void initListView() {
		// 增加那个 更多按钮,必须放在Adapter之前
		elv_oc_content = (ExpandableListView) this
				.findViewById(R.id.elv_oc_content);
		adapter = new OrderCreateAdapter(OrderCreate.this);
		elv_oc_content.setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_oc_content.setCacheColorHint(0);

		elv_oc_content.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent intent = new Intent();
				intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
						adapterContent.get(groupPosition).getBzCartItemDtos()
								.get(childPosition).getBzProductId());
				intent.setClass(OrderCreate.this, ProductDetail.class);
				startActivity(intent);
				return false;
			}
		});
	}

	private void addOrderCreateTask() {
		if (userAddressDto == null || userAddressDto.getId() == null
				|| userAddressDto.getId() == 0) {
			ToastHelper.showToast(OrderCreate.this, "发货地址必须选择");
			return;
		}
		BzOrderDto dto = new BzOrderDto();
		dto.setBzUserAddressDto(userAddressDto);
		dto.setOrderAmount(getTextViewToBigDecimal(tv_oc_amount));
		dto.setOrderDesc(getTextViewToString(et_oc_dist_desc));
		dto.setBzConsumerId(AppApplication.getInstance().getSessionUser()
				.getId());
		dto.setBzMerchantId(bzMerchantDto.getId());
		BzOrderPaymentDto bzOrderPaymentDto = new BzOrderPaymentDto();
		bzOrderPaymentDto.setBzPaymentTypeDto(paymentTypeDto);
		BigDecimal payAmount = getTextViewToBigDecimal(tv_oc_amount).add(
				getTextViewToBigDecimal(tv_oc_dist_price));
		bzOrderPaymentDto.setPayAmount(payAmount);
		bzOrderPaymentDto.setPayStatus(BzOrderPaymentDto.STATUS_WAIT_PAY);
		dto.setBzOrderPaymentDto(bzOrderPaymentDto);
		for (BzCartItemDto cartItemDto : bzMerchantDto.getBzCartItemDtos()) {
			dto.getBzCartItemIds().add(cartItemDto.getId());
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderCreateTask.TP_BZ_ORDER_DTO, dto);
		TaskService.addTask(new OrderCreateTask(OrderCreate.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addOrderCreateInitTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderCreateInitTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new OrderCreateInitTask(OrderCreate.this, params));
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
		if (OrderCreateInitTask.getTaskId() == taskId) {
			WsPageResult<BzOrderInitDto> result = (WsPageResult<BzOrderInitDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderCreate.this, "加载失败");
				} else {
					ToastHelper.showToast(OrderCreate.this,
							result.getErrorMsg());
				}
				return;
			}

			BzOrderInitDto target = result.getContent().iterator().next();
			tv_oc_dist_price.setText(StringHelper.bigDecimalToRmb(target
					.getDistPrice()));
			BigDecimal amount = new BigDecimal("0");
			for (BzCartItemDto cartItemDto : bzMerchantDto.getBzCartItemDtos()) {
				amount = amount.add(cartItemDto.getItemPrice());
			}
			tv_oc_amount.setText(StringHelper.bigDecimalToRmb(amount));
			userAddressDto = target.getBzUserAddressDto();
			if (userAddressDto == null) {
				tv_oc_user_delivery_address.setText("选择发货地址");
			} else {
				tv_oc_user_delivery_address
						.setText(userAddressDto.getAddress());
			}
			final List<TextView> typeTvs = new ArrayList<TextView>();
			for (final BzPaymentTypeDto typeDto : target.getBzPaymentTypeDtos()) {
				final TextView tv = new TextView(this);
				tv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT));
				tv.setText(typeDto.getTypeName());
				tv.setTextColor(this.getResources().getColor(R.color.black));
				tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14);
				tv.setPadding(5, 5, 5, 5);
				tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
						R.drawable.form_checkbox, 0);
				tv.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						if (tv.getCompoundPaddingRight() == R.drawable.form_checkbox_on) {
							tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
									R.drawable.form_checkbox, 0);
						} else {
							for (TextView textView : typeTvs) {
								textView.setCompoundDrawablesWithIntrinsicBounds(
										0, 0, R.drawable.form_checkbox, 0);
							}
							tv.setCompoundDrawablesWithIntrinsicBounds(0, 0,
									R.drawable.form_checkbox_on, 0);
							paymentTypeDto = typeDto;
						}
					}
				});
				typeTvs.add(tv);
				linear_oc_pay_type.addView(tv);
			}

		} else if (OrderCreateTask.getTaskId() == taskId) {
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderCreate.this, "创建失败!");
				} else {
					ToastHelper.showToast(OrderCreate.this,
							result.getErrorMsg());
				}
				return;
			}
			Intent intent = new Intent();
			intent.setClass(OrderCreate.this, OrderList.class);
			startActivity(intent);
			AppActivityManager.getInstance().finishActivity(OrderCreate.this);
		}
	}

	class OrderCreateAdapter extends BaseExpandableListAdapter {

		private Context mContext;
		private LayoutInflater inflater;

		public OrderCreateAdapter(Context mContext) {
			inflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getGroupCount() {
			return adapterContent.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return adapterContent.get(groupPosition).getBzCartItemDtos().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return adapterContent.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return adapterContent.get(groupPosition).getBzCartItemDtos()
					.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			final BzMerchantDto target = (BzMerchantDto) getGroup(groupPosition);
			View view = inflater.inflate(R.layout.order_create_adapter_group,
					parent, false);

			TextView tv_ocag_merchant_name = (TextView) view
					.findViewById(R.id.tv_ocag_merchant_name);
			tv_ocag_merchant_name.setText(target.getUsername());
			Button btn_ocag_chat = (Button) view
					.findViewById(R.id.btn_ocag_chat);
			btn_ocag_chat.setText(target.getUsername());
			btn_ocag_chat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra(ImChatWindow.CHAT_JID, target.getUsername());
					intent.setClass(mContext, ImChatWindow.class);
					mContext.startActivity(intent);
				}
			});
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzCartItemDto target = (BzCartItemDto) getChild(
					groupPosition, childPosition);
			View view = inflater.inflate(
					R.layout.order_create_adapter_group_item, parent, false);
			AsyncImageView aiv_ocagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_ocagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				aiv_ocagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_ocagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_ocagi_product_name = (TextView) view
					.findViewById(R.id.tv_ocagi_product_name);
			tv_ocagi_product_name.setText(StringHelper.trimToEmpty(target
					.getBzProductDto().getProductName()));
			TextView tv_ocagi_buy_num = (TextView) view
					.findViewById(R.id.tv_ocagi_buy_num);
			tv_ocagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			TextView tv_ocagi_price = (TextView) view
					.findViewById(R.id.tv_ocagi_price);
			tv_ocagi_price.setText(StringHelper.bigDecimalToRmb(target
					.getItemUnitPrice()));
			TextView tv_ocagi_price_total = (TextView) view
					.findViewById(R.id.tv_ocagi_price_total);
			tv_ocagi_price_total.setText(StringHelper.bigDecimalToRmb(target
					.getItemPrice()));
			return view;
		}

	}
}
