package itaf.mobile.app.ui;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.localreader.ProductCategoryTask;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.domain.SerializableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;

public class ProductCategorySelect extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_pcs_back;
	// 添加商品
	private Button btn_pcs_select;
	private TextView tv_pcsa_back_select;

	private ListView lv_pcs_content;
	private ProductCategorySelectAdapter adapter;
	private List<BzProductCategoryDto> adapterContent = new ArrayList<BzProductCategoryDto>();
	private List<BzProductCategoryDto> selectObjs = new ArrayList<BzProductCategoryDto>();
	private BzProductCategoryDto parentDto;
	private Long pid;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.product_category_select);

		initPageAttribute();
		initListView();
		pid = null;
		addProductCategoryTask();
	}

	private void initPageAttribute() {
		btn_pcs_back = (Button) this.findViewById(R.id.btn_pcs_back);
		btn_pcs_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(ProductCategorySelect.this));

		btn_pcs_select = (Button) this.findViewById(R.id.btn_pcs_select);
		btn_pcs_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Bundle mbundle = new Bundle();
				mbundle.putSerializable(ProductCreate.AP_BZ_PRODUCT_DTO,
						new SerializableList<BzProductCategoryDto>(selectObjs));
				Intent returnIntent = new Intent();
				returnIntent.putExtras(mbundle);
				setResult(Activity.RESULT_OK, returnIntent);
				AppActivityManager.getInstance().finishActivity(
						ProductCategorySelect.this);
			}
		});

		tv_pcsa_back_select = (TextView) this
				.findViewById(R.id.tv_pcsa_back_select);
		tv_pcsa_back_select.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pid == null) {
					tv_pcsa_back_select.setText("返回");
					return;
				}
				pid = parentDto.getParentId();
				if (pid == null) {
					tv_pcsa_back_select.setText("返回");
				}
				addProductCategoryTask();
			}
		});

	}

	// ListView初始化
	private void initListView() {
		lv_pcs_content = (ListView) findViewById(R.id.lv_pcs_content);
		adapter = new ProductCategorySelectAdapter(ProductCategorySelect.this,
				adapterContent);
		lv_pcs_content.setAdapter(adapter);
	}

	private void addProductCategoryTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(ProductCategoryTask.TP_PID, pid);
		TaskService.addTask(new ProductCategoryTask(ProductCategorySelect.this,
				params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (ProductCategoryTask.getTaskId() == taskId) {
			if (args[1] == null) {
				ToastHelper.showToast(ProductCategorySelect.this, "加载失败");
				return;
			}
			List<BzProductCategoryDto> result = (List<BzProductCategoryDto>) args[1];
			adapterContent.clear();
			adapterContent.addAll(result);
			adapter.notifyDataSetChanged();
			lv_pcs_content.setSelection(0);
		}

	}

	class ProductCategorySelectAdapter extends AbstractBaseAdapter {

		private List<BzProductCategoryDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public ProductCategorySelectAdapter(BaseActivity context,
				List<BzProductCategoryDto> data) {
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
		@SuppressLint("ViewHolder")
		public View getView(int position, View convertView, ViewGroup parent) {
			// 获取list_item布局文件的视图
			convertView = listContainer.inflate(
					R.layout.product_category_select_adapter, parent, false);
			TextView tv_pcsa_category_name = (TextView) convertView
					.findViewById(R.id.tv_pcsa_category_name);
			CheckBox cb_pcsa_category_select = (CheckBox) convertView
					.findViewById(R.id.cb_pcsa_category_select);
			// 设置控件集到convertView
			// 设置文字和图片
			final BzProductCategoryDto target = (BzProductCategoryDto) listItems
					.get(position);
			tv_pcsa_category_name.setText(target.getCategoryName());

			cb_pcsa_category_select
					.setVisibility(target.getIsLeaf() ? View.VISIBLE
							: View.GONE);
			cb_pcsa_category_select.setChecked(selectObjs.contains(target));
			cb_pcsa_category_select
					.setOnCheckedChangeListener(new OnCheckedChangeListener() {
						@Override
						public void onCheckedChanged(CompoundButton buttonView,
								boolean isChecked) {
							if (isChecked) {
								selectObjs.add(target);
							} else {
								selectObjs.remove(target);
							}
						}
					});

			tv_pcsa_category_name.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					if (target.getIsLeaf()) {
						return;
					}
					pid = target.getId();
					parentDto = target;
					tv_pcsa_back_select.setText(target.getCategoryName());
					addProductCategoryTask();
				}
			});

			if (target.getIsLeaf()) {
				tv_pcsa_category_name.setCompoundDrawablesWithIntrinsicBounds(
						null, null, null, null);
			}
			return convertView;
		}
	}

}