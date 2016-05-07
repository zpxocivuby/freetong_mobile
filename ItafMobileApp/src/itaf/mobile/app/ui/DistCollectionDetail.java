package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzCollectionOrderDto;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.framework.merchant.dto.BzDistOrderItemDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.DistCollectionTask;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

/**
 * 配送收款
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class DistCollectionDetail extends BaseUIActivity implements UIRefresh {

	public static final String AP_BZ_DIST_ORDER_DTO = "BzDistOrderDto";

	// 返回按钮
	private Button btn_dcd_back;
	// 确认收款
	private Button btn_dcd_confirm;
	// 应收金额
	private TextView tv_dcd_receivable_amount;
	// 实收金额
	private EditText et_dcd_actual_amount;

	private ExpandableListView elv_dcd_content;
	private DistCollectionListAdapter adapter;
	private List<BzDistOrderDto> adapterContent = new ArrayList<BzDistOrderDto>();

	private BzDistOrderDto distOrderDto;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_collection_detail);

		Serializable paramObj = getIntent().getSerializableExtra(
				AP_BZ_DIST_ORDER_DTO);
		SerializableList<BzDistOrderDto> sList = (SerializableList<BzDistOrderDto>) paramObj;
		Collection<BzDistOrderDto> target = sList.getTarget();
		if (target == null || target.size() <= 0) {
			return;
		}
		distOrderDto = target.iterator().next();
		initPageAttribute();
		initListView();
	}

	private void initPageAttribute() {
		btn_dcd_back = (Button) this.findViewById(R.id.btn_dcd_back);
		btn_dcd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistCollectionDetail.this));

		btn_dcd_confirm = (Button) this.findViewById(R.id.btn_dcd_confirm);
		btn_dcd_confirm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				addDistCollectionTask();
			}

		});

		tv_dcd_receivable_amount = (TextView) this
				.findViewById(R.id.tv_dcd_receivable_amount);
		tv_dcd_receivable_amount.setText(StringHelper
				.bigDecimalToRmb(distOrderDto.getBzCollectionOrderDto()
						.getReceivableAmount()));
		et_dcd_actual_amount = (EditText) this
				.findViewById(R.id.et_dcd_actual_amount);
		et_dcd_actual_amount.setText(distOrderDto.getBzCollectionOrderDto()
				.getReceivableAmount() + "");

	}

	private void initListView() {
		elv_dcd_content = (ExpandableListView) findViewById(R.id.elv_dcd_content);
		adapterContent.add(distOrderDto);
		adapter = new DistCollectionListAdapter(DistCollectionDetail.this,
				adapterContent);
		elv_dcd_content.setAdapter(adapter);
		for (int i = 0; i < adapterContent.size(); i++) {
			elv_dcd_content.expandGroup(i);
		}
	}

	private void addDistCollectionTask() {
		BigDecimal actualAmount = getTextViewToBigDecimal(et_dcd_actual_amount);
		if (actualAmount.equals(new BigDecimal(0))) {
			ToastHelper.showToast(DistCollectionDetail.this, "实收金额不能为0");
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(DistCollectionTask.TP_BZ_COLLECTION_ORDER_ID, distOrderDto
				.getBzCollectionOrderDto().getId());
		params.put(DistCollectionTask.TP_ACTUAL_AMOUNT, actualAmount);
		TaskService.addTask(new DistCollectionTask(DistCollectionDetail.this,
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
		if (DistCollectionTask.getTaskId() == taskId) {
			WsPageResult<BzCollectionOrderDto> result = (WsPageResult<BzCollectionOrderDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "收款失败！");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(DistCollectionDetail.this, "收款成功！");
			setResult(Activity.RESULT_OK);
			AppActivityManager.getInstance().finishActivity(
					DistCollectionDetail.this);
		}

	}

	class DistCollectionListAdapter extends BaseExpandableListAdapter {

		private List<BzDistOrderDto> listItems;// 数据集合
		private LayoutInflater listContainer;

		public DistCollectionListAdapter(Context context,
				List<BzDistOrderDto> data) {
			this.listContainer = LayoutInflater.from(context); // 创建视图容器并设置上下文
			this.listItems = data;
		}

		@Override
		public int getGroupCount() {
			return listItems.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return listItems.get(groupPosition).getBzDistOrderItemDtos().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return listItems.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return listItems.get(groupPosition).getBzDistOrderItemDtos()
					.get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) {
			final BzDistOrderDto target = (BzDistOrderDto) getGroup(groupPosition);
			convertView = listContainer.inflate(
					R.layout.dist_collection_detail_adapter_group, parent,
					false);

			Button btn_dclag_chat = (Button) convertView
					.findViewById(R.id.btn_dclag_chat);
			btn_dclag_chat.setText(target.getBzMerchantDto().getUsername());
			btn_dclag_chat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// TODO 聊天
				}
			});

			TextView tv_dclag_contact_person = (TextView) convertView
					.findViewById(R.id.tv_dclag_contact_person);
			tv_dclag_contact_person.setText(StringHelper.trimToEmpty(target
					.getDistContactPerson()));
			TextView tv_dclag_contact_no = (TextView) convertView
					.findViewById(R.id.tv_dclag_contact_no);
			tv_dclag_contact_no.setText(StringHelper.trimToEmpty(target
					.getDistContactNo()));
			TextView tv_dclag_dist_address = (TextView) convertView
					.findViewById(R.id.tv_dclag_dist_address);
			tv_dclag_dist_address.setText(StringHelper.trimToEmpty(target
					.getDistAddress()));
			return convertView;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzDistOrderItemDto target = (BzDistOrderItemDto) getChild(
					groupPosition, childPosition);
			convertView = listContainer.inflate(
					R.layout.dist_collection_detail_adapter_group_item, parent,
					false);
			AsyncImageView aiv_dclagi_product_ico = (AsyncImageView) convertView
					.findViewById(R.id.aiv_dclagi_product_ico);
			BzProductDto productDto = target.getBzProductDto();
			if (productDto.getBzProductAttachmentIds() != null
					&& productDto.getBzProductAttachmentIds().size() > 0) {
				aiv_dclagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				aiv_dclagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(productDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_dclagi_product_name = (TextView) convertView
					.findViewById(R.id.tv_dclagi_product_name);
			tv_dclagi_product_name.setText(StringHelper.trimToEmpty(target
					.getBzProductDto().getProductName()));
			TextView tv_dclagi_buy_num = (TextView) convertView
					.findViewById(R.id.tv_dclagi_buy_num);
			tv_dclagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			return convertView;
		}

	}

}
