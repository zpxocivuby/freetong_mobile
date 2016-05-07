package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SysUserDetailTask;
import itaf.mobile.app.ui.base.BaseMenuActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.bean.SessionUser;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

/**
 * 菜单：我的账号
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月2日
 */
public class MenuAccount extends BaseMenuActivity implements UIRefresh {

	private LinearLayout linear_ma_layout;
	// 用户账号
	private TextView tv_ma_username;
	// 余额
	private TextView tv_ma_balance;
	// 待付款
	private RadioButton rb_ma_wait_pay;
	// 待发货
	private RadioButton rb_ma_wait_delivery;
	// 待收货
	private RadioButton rb_ma_wait_receipt;
	// 待评价
	private RadioButton rb_ma_wait_evaluation;
	// 退款
	private RadioButton rb_ma_wait_refund;
	// 全部订单
	private TextView tv_ma_all_order;
	// 商品收藏
	private TextView tv_ma_product_favorite;
	// 商家收藏
	private TextView tv_ma_merchant_favorite;
	// 实名认证
	private TextView tv_ma_realname_certificate;
	// 服务申请
	private TextView tv_ma_service_apply;
	// 商品管理
	private TextView tv_ma_merchant_product_manage;
	// 备货及发货
	private TextView tv_ma_merchant_stock_and_invoice;
	// 处理退款单
	private TextView tv_ma_merchant_process_refund_order;
	// 配送结算条目
	private TextView tv_ma_merchant_dist_statement_item;
	// 配送
	private TextView tv_ma_dist_order;
	// 收款
	private TextView tv_ma_dist_collection;
	// 结算信息
	private TextView tv_ma_dist_statement_summary;
	// 收货地址
	private TextView tv_ma_user_address;
	// 发货地址
	private TextView tv_ma_user_delivery_address;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_account);
		initPageAttribute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addSessionUserRefreshTask();
	}

	private void initPageAttribute() {
		linear_ma_layout = (LinearLayout) findViewById(R.id.linear_ma_layout);
		linear_ma_layout.setVisibility(View.GONE);

		tv_ma_username = (TextView) findViewById(R.id.tv_ma_username);
		tv_ma_username.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, UserAccountDetail.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_balance = (TextView) findViewById(R.id.tv_ma_balance);

		rb_ma_wait_pay = (RadioButton) findViewById(R.id.rb_ma_wait_pay);
		rb_ma_wait_pay.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderWaitPay.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		rb_ma_wait_delivery = (RadioButton) findViewById(R.id.rb_ma_wait_delivery);
		rb_ma_wait_delivery.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderWaitDelivery.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		rb_ma_wait_receipt = (RadioButton) findViewById(R.id.rb_ma_wait_receipt);
		rb_ma_wait_receipt.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderWaitReceipt.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		rb_ma_wait_evaluation = (RadioButton) findViewById(R.id.rb_ma_wait_evaluation);
		rb_ma_wait_evaluation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderWaitEvaluation.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		rb_ma_wait_refund = (RadioButton) findViewById(R.id.rb_ma_wait_refund);
		rb_ma_wait_refund.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderWaitRefund.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_all_order = (TextView) findViewById(R.id.tv_ma_all_order);
		tv_ma_all_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, OrderList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_product_favorite = (TextView) findViewById(R.id.tv_ma_product_favorite);
		tv_ma_product_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, ProductFavorite.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_merchant_favorite = (TextView) findViewById(R.id.tv_ma_merchant_favorite);
		tv_ma_merchant_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, MerchantFavorite.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_realname_certificate = (TextView) findViewById(R.id.tv_ma_realname_certificate);
		tv_ma_realname_certificate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				Long realnameStatus = AppApplication.getInstance()
						.getSessionUser().getRealnameStatus();
				if (realnameStatus == SessionUser.STATUS_NONE) {
					intent.setClass(MenuAccount.this,
							ApplyRealnameCertificate.class);
				} else if (realnameStatus == SessionUser.STATUS_CERTIFICATING) {
					intent.setClass(MenuAccount.this,
							ApplyRealnameCertificateCheck.class);
				} else if (realnameStatus == SessionUser.STATUS_FAILED) {
					intent.setClass(MenuAccount.this,
							ApplyRealnameCertificateResult.class);
				}
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_service_apply = (TextView) findViewById(R.id.tv_ma_service_apply);
		tv_ma_service_apply.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, ApplyCertificate.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_merchant_product_manage = (TextView) findViewById(R.id.tv_ma_merchant_product_manage);
		tv_ma_merchant_product_manage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, ProductManage.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_merchant_stock_and_invoice = (TextView) findViewById(R.id.tv_ma_merchant_stock_and_invoice);
		tv_ma_merchant_stock_and_invoice
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MenuAccount.this,
								MerchantStockAndInvoice.class);
						startActivity(intent);
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_left_out);
					}
				});
		tv_ma_merchant_process_refund_order = (TextView) findViewById(R.id.tv_ma_merchant_process_refund_order);
		tv_ma_merchant_process_refund_order
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MenuAccount.this,
								MerchantProcessRefundOrder.class);
						startActivity(intent);
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_left_out);
					}
				});
		tv_ma_merchant_dist_statement_item = (TextView) findViewById(R.id.tv_ma_merchant_dist_statement_item);
		tv_ma_merchant_dist_statement_item
				.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Intent intent = new Intent();
						intent.setClass(MenuAccount.this,
								MerchantDistStatementItem.class);
						startActivity(intent);
						overridePendingTransition(R.anim.push_right_in,
								R.anim.push_left_out);
					}
				});
		tv_ma_dist_order = (TextView) findViewById(R.id.tv_ma_dist_order);
		tv_ma_dist_order.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, DistOrderList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_dist_collection = (TextView) findViewById(R.id.tv_ma_dist_collection);
		tv_ma_dist_collection.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, DistCollectionList.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

		tv_ma_dist_statement_summary = (TextView) findViewById(R.id.tv_ma_dist_statement_summary);
		tv_ma_dist_statement_summary.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, DistStatement.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_user_address = (TextView) findViewById(R.id.tv_ma_user_address);
		tv_ma_user_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, UserAddress.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});
		tv_ma_user_delivery_address = (TextView) findViewById(R.id.tv_ma_user_delivery_address);
		tv_ma_user_delivery_address.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuAccount.this, UserDeliveryAddress.class);
				startActivity(intent);
				overridePendingTransition(R.anim.push_right_in,
						R.anim.push_left_out);
			}
		});

	}

	private void addSessionUserRefreshTask() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(SysUserDetailTask.TP_SYS_USER_ID, AppApplication.getInstance()
				.getSessionUser().getId());
		TaskService.addTask(new SysUserDetailTask(MenuAccount.this, map));
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
		if (SysUserDetailTask.getTaskId() == taskId) {
			WsPageResult<SysUserDto> result = (WsPageResult<SysUserDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "登录失败");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			SysUserDto target = result.getContent().iterator().next();
			// 设置SessionUser
			SessionUser su = new SessionUser();
			su.setId(target.getId());
			su.setUsername(target.getUsername());
			su.setPassword(target.getPassword());
			su.setType(target.getType());
			su.setRealnameStatus(target.getRealnameStatus());
			su.setSellingStatus(target.getSellingStatus());
			su.setDistStatus(target.getDistStatus());
			su.setRealname(target.getRealname());
			su.setNickname(target.getNickname());
			su.setMobile(target.getMobile());
			su.setEmail(target.getEmail());
			su.setAccountBalance(target.getAccountBalance());
			AppApplication.getInstance().setSessionUser(su);
			tv_ma_username.setText(StringHelper.trimToEmpty(su.getUsername()));
			tv_ma_balance.setText(StringHelper.bigDecimalToRmb(su
					.getAccountBalance()));
			Long distStatus = su.getDistStatus();
			Long sellingStatus = su.getSellingStatus();
			Long realnameStatus = su.getRealnameStatus();
			// 实名认证未完成
			if (realnameStatus != SessionUser.STATUS_CERTIFICATED) {
				tv_ma_realname_certificate.setVisibility(View.VISIBLE);
			} else {
				tv_ma_realname_certificate.setVisibility(View.GONE);
			}
			// 实名认证完成，售卖服务和配送服务任何一个未完成
			if ((realnameStatus == SessionUser.STATUS_CERTIFICATED)
					&& (sellingStatus != SessionUser.STATUS_CERTIFICATED || distStatus != SessionUser.STATUS_CERTIFICATED)) {
				tv_ma_service_apply.setVisibility(View.VISIBLE);
			} else {
				tv_ma_service_apply.setVisibility(View.GONE);
			}
			// 售卖服务完成
			if (sellingStatus == SessionUser.STATUS_CERTIFICATED) {
				tv_ma_merchant_product_manage.setVisibility(View.VISIBLE);
				tv_ma_merchant_stock_and_invoice.setVisibility(View.VISIBLE);
				tv_ma_merchant_process_refund_order.setVisibility(View.VISIBLE);
				tv_ma_merchant_dist_statement_item.setVisibility(View.VISIBLE);
			} else {
				tv_ma_merchant_product_manage.setVisibility(View.GONE);
				tv_ma_merchant_stock_and_invoice.setVisibility(View.GONE);
				tv_ma_merchant_process_refund_order.setVisibility(View.GONE);
				tv_ma_merchant_dist_statement_item.setVisibility(View.GONE);
			}
			// 配送服务完成
			if (distStatus == SessionUser.STATUS_CERTIFICATED) {
				tv_ma_dist_order.setVisibility(View.VISIBLE);
				tv_ma_dist_collection.setVisibility(View.VISIBLE);
				tv_ma_dist_statement_summary.setVisibility(View.VISIBLE);
			} else {
				tv_ma_dist_order.setVisibility(View.GONE);
				tv_ma_dist_collection.setVisibility(View.GONE);
				tv_ma_dist_statement_summary.setVisibility(View.GONE);
			}
			// 售卖服务和配送服务任何一个完成
			if (sellingStatus == SessionUser.STATUS_CERTIFICATED
					|| distStatus == SessionUser.STATUS_CERTIFICATED) {
				tv_ma_user_delivery_address.setVisibility(View.VISIBLE);
			} else {
				tv_ma_user_delivery_address.setVisibility(View.GONE);
			}
			linear_ma_layout.setVisibility(View.VISIBLE);
		}

	}
}
