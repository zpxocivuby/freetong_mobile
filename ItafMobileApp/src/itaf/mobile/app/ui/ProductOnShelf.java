package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.ProductPutOnShelfTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * 商品上架
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年11月13日
 */
public class ProductOnShelf extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_PRODUCT_DTO = "BzProductDto";
	// 返回
	private Button id_pos_back;
	// 确认
	private Button id_pos_confirm;
	// 商品名称
	private TextView tv_pos_product_name;
	// 库存数量
	private TextView tv_pos_stock_num;
	// 上架数量
	private EditText et_pos_on_shelf_num;

	private BzProductDto productDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_on_shelf);
		initPageAttribute();
		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_PRODUCT_DTO);
		SerializableList<BzProductDto> sList = (SerializableList<BzProductDto>) paramObj;
		Collection<BzProductDto> dtos = sList.getTarget();
		if (dtos != null && !dtos.isEmpty()) {
			productDto = dtos.iterator().next();
			tv_pos_product_name.setText(productDto.getProductName());
			tv_pos_stock_num.setText(productDto.getStockNum() + "");
		}

	}

	private void initPageAttribute() {
		id_pos_back = (Button) findViewById(R.id.btn_pos_back);
		id_pos_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(this));
		id_pos_confirm = (Button) findViewById(R.id.id_pos_confirm);
		id_pos_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Long onShelfNumber = getTextViewToLong(et_pos_on_shelf_num);
				if (onShelfNumber <= 0) {
					ToastHelper.showToast(ProductOnShelf.this, "输入的上架数量必须大于0!");
					return;
				}
				if (onShelfNumber > productDto.getStockNum()) {
					ToastHelper.showToast(ProductOnShelf.this, "上架数量不能大于库存数量!");
					return;
				}
				addProductPutOnShelfTask(onShelfNumber);
			}
		});
		tv_pos_product_name = (TextView) findViewById(R.id.tv_pos_product_name);
		tv_pos_stock_num = (TextView) findViewById(R.id.tv_pos_stock_num);
		et_pos_on_shelf_num = (EditText) findViewById(R.id.et_pos_on_shelf_num);
	}

	private void addProductPutOnShelfTask(Long onShelfNumber) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductPutOnShelfTask.TP_ID, productDto.getId());
		params.put(ProductPutOnShelfTask.TP_ON_SHELF_NUMBER, onShelfNumber);
		TaskService.addTask(new ProductPutOnShelfTask(ProductOnShelf.this,
				params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (ProductPutOnShelfTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzProductDto> result = (WsPageResult<BzProductDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(ProductOnShelf.this, "上架失败");
				} else {
					ToastHelper.showToast(ProductOnShelf.this,
							result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(ProductOnShelf.this, "上架成功");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance()
					.finishActivity(ProductOnShelf.this);
		}
	}

}
