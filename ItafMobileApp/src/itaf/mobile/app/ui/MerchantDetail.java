package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.R;
import itaf.mobile.app.im.ui.ImChatWindow;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MerchantDetailTask;
import itaf.mobile.app.task.netreader.MerchantFavoriteCancelTask;
import itaf.mobile.app.task.netreader.MerchantFavoriteTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 商家详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class MerchantDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_MERCHANT_ID = "bzMerchantId";

	// 返回
	private Button btn_md_back;
	// 商品照片
	private AsyncImageView aiv_md_merchant_ico;
	// 分类
	private TextView tv_md_category;
	// 商家信用
	private RatingBar rb_md_credit;
	// 配送范围
	private TextView tv_md_dist_range;
	// 配送地址
	private TextView tv_md_dist_address;
	// 收藏
	private TextView tv_md_favorite;
	// 热卖商品
	private TextView tv_md_hot_product;
	// 聊聊
	private TextView tv_md_chat;
	// 商家ID
	private Long merchantId;
	// 商家
	private BzMerchantDto merchantDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_detail);

		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		Object objParam = bundle.get(AP_BZ_MERCHANT_ID);
		if (objParam != null && objParam.toString().length() > 0) {
			merchantId = (Long) objParam;
		}
		initPageAttribute();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addMerchantDetailTask();
	}

	private void initPageAttribute() {
		btn_md_back = (Button) this.findViewById(R.id.btn_md_back);
		btn_md_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantDetail.this));

		tv_md_favorite = (TextView) this.findViewById(R.id.tv_md_favorite);
		tv_md_favorite.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (merchantDto.getIsFavorited()) {
					addCancelMerchantFavoriteTask();
				} else {
					addMerchantFavoriteTask();
				}
			}
		});
		tv_md_hot_product = (TextView) this
				.findViewById(R.id.tv_md_hot_product);
		tv_md_hot_product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(SearchProductResult.AP_BZ_MERCHANT_ID,
						merchantId);
				intent.putExtra(SearchProductResult.AP_COMPANY_NAME,
						merchantDto.getCompanyName());
				intent.setClass(MerchantDetail.this, SearchProductResult.class);
				startActivity(intent);
			}
		});
		tv_md_chat = (TextView) this.findViewById(R.id.tv_md_chat);
		tv_md_chat.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MerchantDetail.this, ImChatWindow.class);
				startActivity(intent);
			}
		});
		aiv_md_merchant_ico = (AsyncImageView) this
				.findViewById(R.id.aiv_md_merchant_ico);
		tv_md_category = (TextView) this.findViewById(R.id.tv_md_category);
		rb_md_credit = (RatingBar) this.findViewById(R.id.rb_md_credit);
		tv_md_dist_range = (TextView) this.findViewById(R.id.tv_md_dist_range);
		tv_md_dist_address = (TextView) this
				.findViewById(R.id.tv_md_dist_address);
	}

	private void addMerchantDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantDetailTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(MerchantDetailTask.TP_BZ_MERCHANT_ID, merchantId);
		TaskService
				.addTask(new MerchantDetailTask(MerchantDetail.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addMerchantFavoriteTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantFavoriteTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(MerchantFavoriteTask.TP_BZ_MERCHANT_ID, merchantId);
		TaskService.addTask(new MerchantFavoriteTask(MerchantDetail.this,
				params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addCancelMerchantFavoriteTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantFavoriteCancelTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(MerchantFavoriteCancelTask.TP_BZ_MERCHANT_ID,
				merchantDto.getId());
		TaskService.addTask(new MerchantFavoriteCancelTask(MerchantDetail.this,
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
		if (MerchantDetailTask.getTaskId() == taskId) {
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantDetail.this, "加载失败！");
				} else {
					ToastHelper.showToast(MerchantDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			merchantDto = result.getContent().iterator().next();
			if (StringHelper.isNotEmpty(merchantDto.getHeadIco())) {
				aiv_md_merchant_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_md_merchant_ico
						.setPath(DownLoadHelper.getHeadIcoPath(
								merchantDto.getHeadIco(),
								AppConstants.IMAGE_SIZE_96X96));
			}
			// TODO 从DB中取出
			tv_md_category.setText(StringHelper.longToString(merchantDto
					.getMerchantCategory()));
			tv_md_dist_range.setText(StringHelper.longToKm(merchantDto
					.getServiceCoverage()));
			tv_md_dist_address.setText(StringHelper.trimToEmpty(merchantDto
					.getCompanyAddress()));
			if (merchantDto.getIsFavorited()) {
				tv_md_favorite.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btp_collect_press, 0, 0, 0);
			} else {
				tv_md_favorite.setCompoundDrawablesWithIntrinsicBounds(
						R.drawable.btp_collect, 0, 0, 0);
			}
			rb_md_credit.setRating(merchantDto.getRatingScore() == null ? 0f
					: merchantDto.getRatingScore());
		} else if (MerchantFavoriteTask.getTaskId() == taskId) {
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantDetail.this, "收藏失败！");
				} else {
					ToastHelper.showToast(MerchantDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			merchantDto.setIsFavorited(true);
			tv_md_favorite.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.btp_collect_press, 0, 0, 0);
			ToastHelper.showToast(MerchantDetail.this, "收藏成功！");
		} else if (MerchantFavoriteCancelTask.getTaskId() == taskId) {
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantDetail.this, "取消收藏失败！");
				} else {
					ToastHelper.showToast(MerchantDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			merchantDto.setIsFavorited(false);
			tv_md_favorite.setCompoundDrawablesWithIntrinsicBounds(
					R.drawable.btp_collect, 0, 0, 0);
			ToastHelper.showToast(MerchantDetail.this, "取消收藏成功！");
		}
	}

}
