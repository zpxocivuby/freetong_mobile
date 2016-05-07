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
import itaf.mobile.ds.db.mobile.ProductCategoryDb;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
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
public class ProductEdit extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_PRODUCT_DTO = "BzProductDto";

	private static final int RC_CATEGROY = 10;
	private static final int AP_CLIP_PHOTO = 11;

	// 返回
	private Button btn_pe_back;
	// 添加商品
	private Button btn_pe_save;
	// 上传按钮
	private Button btn_pe_upload;
	// 商品名称
	private EditText et_pe_product_name;
	// 商品分类
	private TextView tv_pe_product_category;
	// 库存数量
	private EditText et_pe_stock_num;
	// 商品价格
	private EditText et_pe_price;
	// 商品描述
	private EditText et_pe_description;
	// 证件附件ID
	private TextView tv_pe_attach_name;
	// 附件地址
	private String currFilePath;
	private List<Long> bzProductCategoryIds = new ArrayList<Long>();
	private BzProductDto productDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_edit);

		initPageAttribute();
		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_PRODUCT_DTO);
		SerializableList<BzProductDto> sList = (SerializableList<BzProductDto>) paramObj;
		if (sList != null && sList.getTarget() != null
				&& !sList.getTarget().isEmpty()) {
			productDto = sList.getTarget().iterator().next();
			// TODO
			// iv_pe_product_ico_big.setImageURI();
			et_pe_product_name.setText(StringHelper.trimToEmpty(productDto
					.getProductName()));
			String categoryName = "";
			List<Long> idsList = productDto.getBzProductCategoryIds();
			if (idsList != null && idsList.size() > 0) {
				bzProductCategoryIds.addAll(idsList);
				ProductCategoryDb db = new ProductCategoryDb(ProductEdit.this);
				for (Long id : idsList) {
					BzProductCategoryDto dto = db.findById(id);
					categoryName += dto.getCategoryName() + ";";
				}
			}
			tv_pe_product_category.setText(categoryName);
			et_pe_stock_num.setText(StringHelper.longToString(productDto
					.getStockNum()));
			et_pe_price.setText(StringHelper.bigDecimalToRmb(productDto
					.getProductPrice()));
			et_pe_description.setText(StringHelper.trimToEmpty(productDto
					.getProductDescription()));
		}
	}

	private void initPageAttribute() {
		btn_pe_back = (Button) this.findViewById(R.id.btn_pe_back);
		btn_pe_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductEdit.this));

		btn_pe_save = (Button) this.findViewById(R.id.btn_pe_save);
		btn_pe_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String productName = getTextViewToString(et_pe_product_name);
				if (!ValidHelper
						.notEmpty(ProductEdit.this, productName, "商品名称")) {
					return;
				}
				BigDecimal productPrice = getTextViewToBigDecimal(et_pe_price);
				String productDesc = getTextViewToString(et_pe_description);
				if (!ValidHelper
						.notEmpty(ProductEdit.this, productDesc, "商品描述")) {
					return;
				}
				productDto.setBzMerchantId(AppApplication.getInstance()
						.getSessionUser().getId());
				productDto.setProductName(productName);
				productDto.setProductPrice(productPrice);
				productDto.setProductDescription(productDesc);
				productDto.setStockNum(getTextViewToLong(et_pe_stock_num));
				productDto.setBzProductCategoryIds(bzProductCategoryIds);
				productDto.setBzProductAttachmentIds(new ArrayList<Long>());

				if (StringHelper.isEmpty(currFilePath)) {
					addProductCreateOrEditTask();
					return;
				}
				UploadHelper uploadHelper = new UploadHelper(ProductEdit.this,
						AppConfig.uploadProductUrl, currFilePath, null);
				uploadHelper.setCallbackListener(new CallbackListener() {
					@Override
					public void callback(String dataId) {
						List<Long> target = new ArrayList<Long>();
						target.add(Long.valueOf(dataId));
						productDto.setBzProductAttachmentIds(target);
						addProductCreateOrEditTask();
					}

				});
				uploadHelper.execute("");
			}

		});

		btn_pe_upload = (Button) this.findViewById(R.id.btn_pe_upload);
		btn_pe_upload.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent, AppConstants.RC_ANDROID_PHOTO);
			}
		});

		et_pe_product_name = (EditText) this
				.findViewById(R.id.et_pe_product_name);

		tv_pe_product_category = (TextView) this
				.findViewById(R.id.tv_pe_product_category);
		tv_pe_product_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(ProductEdit.this, ProductCategorySelect.class);
				startActivityForResult(intent, RC_CATEGROY);
			}
		});

		et_pe_stock_num = (EditText) this.findViewById(R.id.et_pe_stock_num);
		et_pe_price = (EditText) this.findViewById(R.id.et_pe_price);
		et_pe_description = (EditText) this
				.findViewById(R.id.et_pe_description);
		tv_pe_attach_name = (TextView) this
				.findViewById(R.id.tv_pe_attach_name);
	}

	private void addProductCreateOrEditTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductCreateOrEditTask.TP_DTO, productDto);
		TaskService.addTask(new ProductCreateOrEditTask(ProductEdit.this,
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
					ToastHelper.showToast(ProductEdit.this, "添加失败");
				} else {
					ToastHelper.showToast(ProductEdit.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ProductEdit.this, "添加成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(ProductEdit.this);
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
				sIntent.setClass(ProductEdit.this, SysClipPhoto.class);
				startActivityForResult(sIntent, AP_CLIP_PHOTO);
				break;
			case AP_CLIP_PHOTO:
				currFilePath = intent.getStringExtra("filePath");
				tv_pe_attach_name.setText(FileHelper
						.getSrcFileName(currFilePath));
				break;
			case RC_CATEGROY:
				Bundle bundle = intent.getExtras();
				SerializableList<BzProductCategoryDto> sList = (SerializableList<BzProductCategoryDto>) bundle
						.getSerializable(ProductEdit.AP_BZ_PRODUCT_DTO);
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
				tv_pe_product_category.setText(categoryName);
				break;
			}

		}
	}

}
