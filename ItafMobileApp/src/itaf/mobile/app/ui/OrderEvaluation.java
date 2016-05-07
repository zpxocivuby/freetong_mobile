package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantRatingDto;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderItemDto;
import itaf.framework.order.dto.BzOrderRatingDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.framework.product.dto.BzProductRatingDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.OrderDetailTask;
import itaf.mobile.app.task.netreader.OrderEvaluationTask;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * 订单评价
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class OrderEvaluation extends BaseUIActivity implements UIRefresh {

	protected static final String AP_BZ_ORDER_ID = "bzOrderId";
	// 返回
	private Button btn_oe_back;
	// 评价
	private Button btn_oe_rating;
	// 物流服务
	private RatingBar rb_oe_logistics_score;
	// 描述相符
	private RatingBar rb_oe_description_score;
	// 服务态度
	private RatingBar rb_oe_attitude_score;
	// 发货速度
	private RatingBar rb_oe_delivery_score;

	private ListView lv_oe_content;
	private OrderEvaluationAdapter adapter;
	private List<BzOrderItemDto> adapterContent = new ArrayList<BzOrderItemDto>();

	private BzOrderDto bzOrderDto;
	private Long bzOrderId;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.order_evaluation);
		bzOrderId = this.getIntent().getLongExtra(AP_BZ_ORDER_ID, 0L);

		initPageAttribute();
		initListView();
		addOrderDetailTask();
	}

	private void initPageAttribute() {
		btn_oe_back = (Button) this.findViewById(R.id.btn_oe_back);
		btn_oe_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(OrderEvaluation.this));
		btn_oe_rating = (Button) this.findViewById(R.id.btn_oe_rating);
		btn_oe_rating.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addOrderEvaluationTask(bzOrderId);
			}
		});
		rb_oe_logistics_score = (RatingBar) this
				.findViewById(R.id.rb_oe_logistics_score);
		rb_oe_description_score = (RatingBar) this
				.findViewById(R.id.rb_oe_description_score);
		rb_oe_attitude_score = (RatingBar) this
				.findViewById(R.id.rb_oe_attitude_score);
		rb_oe_delivery_score = (RatingBar) this
				.findViewById(R.id.rb_oe_delivery_score);
	}

	private void initListView() {
		lv_oe_content = (ListView) this.findViewById(R.id.lv_oe_content);
		adapter = new OrderEvaluationAdapter(OrderEvaluation.this,
				adapterContent);
		lv_oe_content.setAdapter(adapter);
	}

	private void addOrderDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderDetailTask.TP_BZ_ORDER_ID, bzOrderId);
		TaskService.addTask(new OrderDetailTask(OrderEvaluation.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addOrderEvaluationTask(Long bzOrderId) {
		BzOrderRatingDto target = new BzOrderRatingDto();
		BzMerchantRatingDto bzMerchantRatingDto = new BzMerchantRatingDto();
		bzMerchantRatingDto.setBzMerchantId(bzOrderDto.getBzMerchantId());
		Long bzConsumerId = AppApplication.getInstance().getSessionUser()
				.getId();
		bzMerchantRatingDto.setBzConsumerId(bzConsumerId);
		bzMerchantRatingDto.setBzOrderId(bzOrderId);
		bzMerchantRatingDto
				.setDescriptionScore(getRatingBarToLong(rb_oe_description_score));
		bzMerchantRatingDto
				.setAttitudeScore(getRatingBarToLong(rb_oe_attitude_score));
		bzMerchantRatingDto
				.setDeliveryScore(getRatingBarToLong(rb_oe_delivery_score));
		bzMerchantRatingDto
				.setLogisticsScore(getRatingBarToLong(rb_oe_logistics_score));
		target.setBzMerchantRatingDto(bzMerchantRatingDto);
		for (int i = 0; i < lv_oe_content.getChildCount(); i++) {
			LinearLayout layout = (LinearLayout) lv_oe_content.getChildAt(i);// 获得子item的layout
			TextView tv_oea_product_id = (TextView) layout
					.findViewById(R.id.tv_oea_product_id);
			RatingBar rb_oea_product_evaluate_score = (RatingBar) layout
					.findViewById(R.id.rb_oea_product_evaluate_score);
			EditText et_oea_evaluate_content = (EditText) layout
					.findViewById(R.id.et_oea_evaluate_content);
			BzProductRatingDto bzProductRatingDto = new BzProductRatingDto();
			bzProductRatingDto
					.setBzProductId(getTextViewToLong(tv_oea_product_id));
			bzProductRatingDto.setBzConsumerId(bzConsumerId);
			bzProductRatingDto.setBzOrderId(bzOrderId);
			bzProductRatingDto
					.setRate(getRatingBarToLong(rb_oea_product_evaluate_score));
			bzProductRatingDto
					.setContent(getTextViewToString(et_oea_evaluate_content));
			target.getBzProductRatingDtos().add(bzProductRatingDto);
		}

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(OrderEvaluationTask.TP_BZ_ORDER_ID, bzOrderId);
		params.put(OrderEvaluationTask.TP_BZ_ORDER_RATING_DTO, target);
		TaskService.addTask(new OrderEvaluationTask(OrderEvaluation.this,
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
		if (OrderDetailTask.getTaskId() == taskId) {
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderEvaluation.this, "加载失败");
				} else {
					ToastHelper.showToast(OrderEvaluation.this,
							result.getErrorMsg());
				}
				return;
			}
			bzOrderDto = result.getContent().iterator().next();
			adapterContent.clear();
			adapterContent.addAll(bzOrderDto.getBzOrderItemDtos());
			lv_oe_content.setSelection(0);
			adapter.notifyDataSetChanged();
			return;
		} else if (OrderEvaluationTask.getTaskId() == taskId) {
			WsPageResult<BzOrderDto> result = (WsPageResult<BzOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(OrderEvaluation.this, "评价失败");
				} else {
					ToastHelper.showToast(OrderEvaluation.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(OrderEvaluation.this, "评价成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					OrderEvaluation.this);
			return;
		}

	}

	class OrderEvaluationAdapter extends AbstractBaseAdapter {

		private List<BzOrderItemDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView {
			AsyncImageView aiv_oea_product_ico;
			TextView tv_oea_product_name;
			TextView tv_oea_product_id;
			RatingBar rb_oea_product_evaluate_score;
			EditText et_oea_evaluate_content;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public OrderEvaluationAdapter(BaseActivity context,
				List<BzOrderItemDto> data) {
			this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = data;
		}

		public int getCount() {
			return listItems.size();
		}

		public Object getItem(int position) {
			return this.listItems.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		/**
		 * ListView Item设置
		 */
		public View getView(int position, View convertView, ViewGroup parent) {
			// 自定义视图
			ListItemView listItemView = null;
			if (convertView == null) {
				// 获取list_item布局文件的视图
				convertView = listContainer.inflate(
						R.layout.order_evaluation_adapter, parent, false);
				listItemView = new ListItemView();
				listItemView.aiv_oea_product_ico = (AsyncImageView) convertView
						.findViewById(R.id.aiv_oea_product_ico);
				listItemView.tv_oea_product_name = (TextView) convertView
						.findViewById(R.id.tv_oea_product_name);
				listItemView.tv_oea_product_id = (TextView) convertView
						.findViewById(R.id.tv_oea_product_id);
				listItemView.rb_oea_product_evaluate_score = (RatingBar) convertView
						.findViewById(R.id.rb_oea_product_evaluate_score);
				listItemView.et_oea_evaluate_content = (EditText) convertView
						.findViewById(R.id.et_oea_evaluate_content);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzOrderItemDto target = (BzOrderItemDto) listItems
					.get(position);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				listItemView.aiv_oea_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				listItemView.aiv_oea_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			listItemView.aiv_oea_product_ico
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra(ProductDetail.AP_BZ_PRODUCT_ID,
									target.getBzProductDto().getId());
							intent.setClass(OrderEvaluation.this,
									ProductDetail.class);
							startActivity(intent);
						}
					});
			listItemView.tv_oea_product_name.setText(StringHelper
					.trimToEmpty(target.getBzProductDto().getProductName()));
			listItemView.tv_oea_product_id.setText(StringHelper
					.longToString(target.getBzProductDto().getId()));
			return convertView;
		}
	}

}
