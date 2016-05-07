package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductDetailTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
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
public class ProductMerchantDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_PRODUCT_ID = "productId";

	// 返回
	private Button btn_pmd_back;
	// 商品照片
	private AsyncImageView aiv_pmd_product_ico;
	// 商品名称
	private TextView tv_pmd_product_name;
	// 商品类型
	private TextView tv_pmd_category;
	// 商品价格
	private TextView tv_pmd_price;
	// 库存数量
	private TextView tv_pmd_stock_num;
	// 上架数量
	public TextView tv_pmd_shelf_num;
	// 商品描述
	private TextView tv_pmd_product_desc;
	// 商品ID
	private Long productId;

	private BzProductDto productDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_merchant_detail);

		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		Object objParam = bundle.get(AP_PRODUCT_ID);
		if (objParam != null && objParam.toString().length() > 0) {
			productId = (Long) objParam;
		}

		initPageAttribute();
		addProductDetailTask();
	}

	private void initPageAttribute() {
		btn_pmd_back = (Button) this.findViewById(R.id.btn_pmd_back);
		btn_pmd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductMerchantDetail.this));

		aiv_pmd_product_ico = (AsyncImageView) this
				.findViewById(R.id.aiv_pmd_product_ico);
		tv_pmd_product_name = (TextView) this
				.findViewById(R.id.tv_pmd_product_name);
		tv_pmd_category = (TextView) this.findViewById(R.id.tv_pmd_category);
		tv_pmd_price = (TextView) this.findViewById(R.id.tv_pmd_price);
		tv_pmd_stock_num = (TextView) this.findViewById(R.id.tv_pmd_stock_num);
		tv_pmd_shelf_num = (TextView) this.findViewById(R.id.tv_pmd_shelf_num);

		tv_pmd_product_desc = (TextView) this
				.findViewById(R.id.tv_pmd_product_desc);
	}

	private void addProductDetailTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductDetailTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		params.put(ProductDetailTask.TP_BZ_PRODUCT_ID, productId);
		TaskService.addTask(new ProductDetailTask(ProductMerchantDetail.this,
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
		if (ProductDetailTask.getTaskId() == taskId) {
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductMerchantDetail.this, "加载失败");
				} else {
					ToastHelper.showToast(ProductMerchantDetail.this,
							result.getErrorMsg());
				}
				return;
			}
			productDto = result.getContent().iterator().next();
			if (productDto.getBzProductAttachmentIds() != null
					&& productDto.getBzProductAttachmentIds().size() > 0) {
				aiv_pmd_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_pmd_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(productDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}

			tv_pmd_product_name.setText(StringHelper.trimToEmpty(productDto
					.getProductName()));
			ProductCategoryDb db = new ProductCategoryDb(
					ProductMerchantDetail.this);
			String categoryName = "";
			for (Long id : productDto.getBzProductCategoryIds()) {
				BzProductCategoryDto dto = db.findById(id);
				categoryName += dto.getCategoryName() + ";";
			}
			tv_pmd_category.setText(categoryName);
			tv_pmd_price.setText(StringHelper.bigDecimalToRmb(productDto
					.getProductPrice()));

			tv_pmd_stock_num.setText(StringHelper.longToString(productDto
					.getStockNum()));
			tv_pmd_shelf_num.setText(StringHelper.longToString(productDto
					.getShelfNum()));
			tv_pmd_product_desc.setText(StringHelper.trimToEmpty(productDto
					.getProductDescription()));
		}
	}

}
