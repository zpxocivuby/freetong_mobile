package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductCreateOrEditTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.FileHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.UploadHelper;
import itaf.mobile.app.util.UploadHelper.CallbackListener;
import itaf.mobile.app.util.ValidHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.app.AppConfig;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 商品添加
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月11日
 */
public class ProductCreate extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_PRODUCT_DTO = "BzProductDto";

	private static final int RC_CATEGROY = 10;
	private static final int AP_CLIP_PHOTO = 11;

	// 返回
	private Button btn_pc_back;
	// 添加商品
	private Button btn_pc_save;
	// 上传按钮
	private Button btn_pc_upload;
	// 商品名称
	private EditText et_pc_product_name;
	// 商品分类
	private TextView tv_pc_product_category;
	// 库存数量
	private EditText et_pc_stock_num;
	// 商品价格
	private EditText et_pc_price;
	// 是否上架
	private CheckBox cb_pc_on_sheft;
	// 商品描述
	private EditText et_pc_description;
	// 证件附件ID
	private TextView tv_pc_attach_name;
	// 附件地址
	private String currFilePath;
	private List<Long> bzProductCategoryIds = new ArrayList<Long>();

	private BzProductDto bzProductDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_create);

		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_pc_back = (Button) this.findViewById(R.id.btn_pc_back);
		btn_pc_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductCreate.this));

		btn_pc_save = (Button) this.findViewById(R.id.btn_pc_save);
		btn_pc_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (bzProductDto == null) {
					bzProductDto = new BzProductDto();
				}
				String productName = getTextViewToString(et_pc_product_name);
				if (!ValidHelper.notEmpty(ProductCreate.this, productName,
						"商品名称不能为空!")) {
					return;
				}
				BigDecimal productPrice = getTextViewToBigDecimal(et_pc_price);
				String productDesc = getTextViewToString(et_pc_description);
				if (!ValidHelper.notEmpty(ProductCreate.this, productDesc,
						"商品描述不能为空!")) {
					return;
				}
				Long stockNum = getTextViewToLong(et_pc_stock_num);
				if (cb_pc_on_sheft.isChecked()) {
					if (stockNum <= 0) {
						ToastHelper.showToast(ProductCreate.this, "上架数量需大于0!");
						return;
					}
					bzProductDto.setShelfNum(stockNum);
				} else {
					bzProductDto.setShelfNum(0L);
				}
				bzProductDto.setBzMerchantId(AppApplication.getInstance()
						.getSessionUser().getId());
				bzProductDto.setProductName(productName);
				bzProductDto.setProductPrice(productPrice);
				bzProductDto
						.setProductOnSale(getCheckBoxToLong(cb_pc_on_sheft));
				bzProductDto.setProductDescription(productDesc);

				bzProductDto.setStockNum(stockNum);
				bzProductDto.setBzProductCategoryIds(bzProductCategoryIds);

				if (StringHelper.isEmpty(currFilePath)) {
					addProductCreateOrEditTask();
					return;
				}
				UploadHelper uploadHelper = new UploadHelper(
						ProductCreate.this, AppConfig.uploadProductUrl,
						currFilePath, null);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						List<Long> target = new ArrayList<Long>();
						target.add(Long.valueOf(dataId));
						bzProductDto.setBzProductAttachmentIds(target);
						addProductCreateOrEditTask();
					}

				});
				uploadHelper.execute("");
			}
		});

		btn_pc_upload = (Button) this.findViewById(R.id.btn_pc_upload);
		btn_pc_upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, AppConstants.RC_ANDROID_PHOTO);
			}
		});

		et_pc_product_name = (EditText) this
				.findViewById(R.id.et_pc_product_name);

		tv_pc_product_category = (TextView) this
				.findViewById(R.id.tv_pc_product_category);
		tv_pc_product_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductCreate.this, ProductCategorySelect.class);
				startActivityForResult(intent, RC_CATEGROY);
			}
		});

		et_pc_stock_num = (EditText) this.findViewById(R.id.et_pc_stock_num);
		et_pc_price = (EditText) this.findViewById(R.id.et_pc_price);
		cb_pc_on_sheft = (CheckBox) this.findViewById(R.id.cb_pc_on_sheft);
		et_pc_description = (EditText) this
				.findViewById(R.id.et_pc_description);

		tv_pc_attach_name = (TextView) this
				.findViewById(R.id.tv_pc_attach_name);
	}

	private void addProductCreateOrEditTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductCreateOrEditTask.TP_DTO, bzProductDto);
		TaskService.addTask(new ProductCreateOrEditTask(ProductCreate.this,
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
		if (ProductCreateOrEditTask.getTaskId() == taskId) {
			WsPageResult<BzProductCategoryDto> result = (WsPageResult<BzProductCategoryDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductCreate.this, "添加失败");
				} else {
					ToastHelper.showToast(ProductCreate.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ProductCreate.this, "添加成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(ProductCreate.this);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
			Intent intent) {

		if (resultCode == Activity.RESULT_OK) {
			switch (requestCode) {
			case AppConstants.RC_ANDROID_PHOTO:
				Uri selectedImage = intent.getData();
				currFilePath = getImagePathFromAndroid(selectedImage);
				Intent sIntent = new Intent();
				sIntent.putExtra("filePath", currFilePath);
				sIntent.setClass(ProductCreate.this, SysClipPhoto.class);
				startActivityForResult(sIntent, AP_CLIP_PHOTO);
				break;
			case AP_CLIP_PHOTO:
				currFilePath = intent.getStringExtra("filePath");
				tv_pc_attach_name.setText(FileHelper
						.getSrcFileName(currFilePath));
				break;
			case RC_CATEGROY:
				Bundle bundle = intent.getExtras();
				SerializableList<BzProductCategoryDto> sList = (SerializableList<BzProductCategoryDto>) bundle
						.getSerializable(ProductCreate.AP_BZ_PRODUCT_DTO);
				if (sList == null || sList.getTarget() == null
						|| sList.getTarget().size() <= 0) {
					return;
				}
				bzProductCategoryIds.clear();
				String categoryName = "";
				for (BzProductCategoryDto dto : sList.getTarget()) {
					categoryName += dto.getCategoryName() + "; ";
					bzProductCategoryIds.add(dto.getId());
				}
				tv_pc_product_category.setText(categoryName);
				break;
			}
		}
	}

}
