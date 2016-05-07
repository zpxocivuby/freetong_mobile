package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.utils.StringHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * 搜索页面
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月12日
 */
public class SearchProductOrMerchant extends BaseUIActivity {

	private static final int SEARCH_TYPE_PRODUCT = 1;
	private static final int SEARCH_TYPE_MERCHANT = 2;

	// 返回
	private Button btn_spom_back;
	// 商品
	private Button btn_spom_product;
	// 商家
	private Button btn_spom_marchant;
	// 搜索关键字
	private EditText et_spom_searchkey;
	// 搜索
	private Button btn_spom_search;
	//
	private int searchType = SEARCH_TYPE_PRODUCT;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.search_product_or_merchant);
		initPageAttribute();
	}

	private void initPageAttribute() {
		btn_spom_back = (Button) this.findViewById(R.id.btn_spom_back);
		btn_spom_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(SearchProductOrMerchant.this));

		btn_spom_product = (Button) this.findViewById(R.id.btn_spom_product);
		btn_spom_product.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_spom_product.getBackground() == getResources()
						.getDrawable(R.drawable.tab_left_red_on)) {
					return;
				}
				btn_spom_product
						.setBackgroundResource(R.drawable.tab_left_red_on);
				btn_spom_marchant
						.setBackgroundResource(R.drawable.tab_right_red);
				searchType = SEARCH_TYPE_PRODUCT;
			}
		});
		btn_spom_marchant = (Button) this.findViewById(R.id.btn_spom_marchant);
		btn_spom_marchant.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (btn_spom_marchant.getBackground() == getResources()
						.getDrawable(R.drawable.tab_right_red_on)) {
					return;
				}
				btn_spom_product.setBackgroundResource(R.drawable.tab_left_red);
				btn_spom_marchant
						.setBackgroundResource(R.drawable.tab_right_red_on);
				searchType = SEARCH_TYPE_MERCHANT;
			}
		});
		et_spom_searchkey = (EditText) this
				.findViewById(R.id.et_spom_searchkey);
		btn_spom_search = (Button) this.findViewById(R.id.btn_spom_search);

		btn_spom_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String searchKey = getTextViewToString(et_spom_searchkey);
				if (StringHelper.isEmpty(searchKey)) {
					ToastHelper.showToast(SearchProductOrMerchant.this,
							"搜索的关键词不能为空");
					return;
				}
				// 如果选中，是商品搜索
				if (searchType == SEARCH_TYPE_PRODUCT) {
					Intent intent = new Intent();
					intent.putExtra(SearchProductResult.AP_PRODUCT_NAME,
							searchKey);
					intent.setClass(SearchProductOrMerchant.this,
							SearchProductResult.class);
					startActivity(intent);
					return;
				}
				Intent intent = new Intent();
				intent.putExtra(SearchMerchantResult.AP_SEARCH_KEY, searchKey);
				intent.setClass(SearchProductOrMerchant.this,
						SearchMerchantResult.class);
				startActivity(intent);
			}
		});
	}

}
