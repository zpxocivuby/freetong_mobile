package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductDetailTask;
import itaf.mobile.app.task.netreader.ProductFavoriteCancelTask;
import itaf.mobile.app.task.netreader.ProductFavoriteTask;
import itaf.mobile.app.task.netreader.PutProductInCartTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 商品详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ProductDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_PRODUCT_ID = "bzProductId";

	// 返回
	private Button btn_pd_back;
	// 商品照片
	private AsyncImageView aiv_pd_product_ico;
	// 店家名称
	private TextView tv_pd_merchant_name;
	// 商品名称
	private TextView tv_pd_product_name;
	// 商品价格
	private TextView tv_pd_price;
	// 剩余数量
	private TextView tv_pd_stock_num;
	// 商品描述
	private TextView tv_pd_product_desc;
	// 购买
	private TextView tv_pd_buy;
	// 收藏
	private TextView tv_pd_favorite;
	// 加入购物车
	private TextView tv_pd_add_cart;
	// 商品ID
	private Long productId;

	private BzProductDto productDto;
	private int putType;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_detail);

		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		Object objParam = bundle.get(AP_BZ_PRODUCT_ID);
		if (objParam != null && objParam.toString().length() > 0) {
			productId = (Long) objParam;
		}
		initPageAttribute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addProductDetailTask();
	}

	private void initPageAttribute() {
		btn_pd_back = (Button) this.findViewById(R.id.btn_pd_back);
		btn_pd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductDetail.this));

		aiv_pd_product_ico = (AsyncImageView) this
				.findViewById(R.id.aiv_pd_product_ico);
		tv_pd_merchant_name = (TextView) this
				.findViewById(R.id.tv_pd_merchant_name);
		tv_pd_product_name = (TextView) this
				.findViewById(R.id.tv_pd_product_name);
		tv_pd_price = (TextView) this.findViewById(R.id.tv_pd_price);
		tv_pd_stock_num = (TextView) this.findViewById(R.id.tv_pd_stock_num);

		tv_pd_product_desc = (TextView) this
				.findViewById(R.id.tv_pd_product_desc);
		tv_pd_favorite = (TextView) this.findViewById(R.id.tv_pd_favorite);
		tv_pd_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (productDto.getIsFavorited()) {
					addCancelProductFavoriteTask();
				} else {
					addProductFavoriteTask();
				}
			}
		});
		tv_pd_buy = (TextView) this.findViewById(R.id.tv_pd_buy);
		tv_pd_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				putType = PutProductInCartTask.PUT_TYPE_BUY;
				addPutProductInCartTask();
			}
		});
		tv_pd_add_cart = (TextView) this.findViewById(R.id.tv_pd_add_cart);
		tv_pd_add_cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				putType = PutProductInCartTask.PUT_TYPE_CART;
				addPutProductInCartTask();
			}
		});
	}

	private void addProductDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductDetailTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(ProductDetailTask.TP_BZ_PRODUCT_ID, productId);
		TaskService.addTask(new ProductDetailTask(ProductDetail.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addProductFavoriteTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductFavoriteTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(ProductFavoriteTask.TP_BZ_PRODUCT_ID, productDto.getId());
		TaskService
				.addTask(new ProductFavoriteTask(ProductDetail.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addCancelProductFavoriteTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductFavoriteCancelTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(ProductFavoriteCancelTask.TP_BZ_PRODUCT_ID,
				productDto.getId());
		TaskService.addTask(new ProductFavoriteCancelTask(ProductDetail.this,
				params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addPutProductInCartTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(PutProductInCartTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(PutProductInCartTask.TP_BZ_PRODUCT_ID, productDto.getId());
		params.put(PutProductInCartTask.TP_PUT_TYPE, putType);
		TaskService
				.addTask(new PutProductInCartTask(ProductDetail.this, params));
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
		if (ProductDetailTask.getTaskId() == taskId) {
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductDetail.this, "加载失败");
				} else {
					ToastHelper.showToast(ProductDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			productDto = result.getContent().iterator().next();
			if (productDto.getBzProductAttachmentIds() != null
					&& productDto.getBzProductAttachmentIds().size() > 0) {
				aiv_pd_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_pd_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(productDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			BzMerchantDto bzMerchantDto = productDto.getBzMerchantDto();
			tv_pd_merchant_name.setText(StringHelper.trimToEmpty(bzMerchantDto
					.getUsername()));
			tv_pd_product_name.setText(StringHelper.trimToEmpty(productDto
					.getProductName()));
			tv_pd_price.setText(StringHelper.bigDecimalToRmb(productDto
					.getProductPrice()));
			tv_pd_product_desc.setText(StringHelper.trimToEmpty(productDto
					.getProductDescription()));
			if (productDto.getIsFavorited()) {
				tv_pd_favorite.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btp_collect_press, 0, 0, 0);
			} else {
				tv_pd_favorite.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btp_collect, 0, 0, 0);
			}
			tv_pd_stock_num.setText(StringHelper.longToString(productDto
					.getStockNum()));

		} else if (ProductFavoriteTask.getTaskId() == taskId) {
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductDetail.this, "收藏失败");
				} else {
					ToastHelper.showToast(ProductDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			productDto.setIsFavorited(true);
			tv_pd_favorite.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.btp_collect_press, 0, 0, 0);
			ToastHelper.showToast(ProductDetail.this, "收藏成功！");
		} else if (ProductFavoriteCancelTask.getTaskId() == taskId) {
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductDetail.this, "取消收藏失败");
				} else {
					ToastHelper.showToast(ProductDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			productDto.setIsFavorited(false);
			tv_pd_favorite.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.btp_collect, 0, 0, 0);
			ToastHelper.showToast(ProductDetail.this, "取消收藏成功！");
		} else if (PutProductInCartTask.getTaskId() == taskId) {
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					if (putType == PutProductInCartTask.PUT_TYPE_CART) {
						ToastHelper.showToast(ProductDetail.this, "加入购物车失败！");
					} else {
						ToastHelper.showToast(ProductDetail.this, "购买失败！");
					}
				} else {
					ToastHelper.showToast(ProductDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			if (putType == PutProductInCartTask.PUT_TYPE_CART) {
				ToastHelper.showToast(ProductDetail.this, "加入购物车成功！");
			} else {
				BzMerchantDto dto = result.getContent().iterator().next()
						.getBzMerchantDto();
				dto.getBzCartItemDtos().addAll(result.getContent());
				Bundle bundle = new Bundle();
				bundle.putSerializable(OrderCreate.AP_BZ_MERCHANT_DTO,
						new SerializableList<BzMerchantDto>(dto));
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(ProductDetail.this, OrderCreate.class);
				startActivity(intent);
			}
		}
	}

}
