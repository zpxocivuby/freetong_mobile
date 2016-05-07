package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementDto;
import itaf.framework.merchant.dto.BzDistStatementItemDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MerchantDistStatementDetailTask;
import itaf.mobile.app.ui.adapter.AbstractBaseAdapter;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.core.utils.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 配送结算批次详情
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月15日
 */
public class MerchantDistStatementDetail extends BaseUIActivity implements
		UIRefresh {

	public static final String AP_BZ_DIST_STATEMENT_ID = "bzDistStatementId";

	// 返回按钮
	private Button btn_mdsd_back;

	private ListView lv_mdsd_content;
	private MerchantDistStatementItemAdapter adapter;
	private List<BzDistStatementItemDto> adapterContent = new ArrayList<BzDistStatementItemDto>();

	private Long bzDistStatementId;
	private BzDistStatementDto bzDistStatementDto;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_dist_statement_detail);
		bzDistStatementId = this.getIntent().getLongExtra(
				AP_BZ_DIST_STATEMENT_ID, 0);
		initPageAttribute();
		initListView();
		addDistStatementItemPagerTask();
	}

	private void initPageAttribute() {
		btn_mdsd_back = (Button) this.findViewById(R.id.btn_mdsd_back);
		btn_mdsd_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantDistStatementDetail.this));

	}

	private void initListView() {
		// 分页相关
		lv_mdsd_content = (ListView) this.findViewById(R.id.lv_mdsd_content);
		adapter = new MerchantDistStatementItemAdapter(
				MerchantDistStatementDetail.this, adapterContent);
		lv_mdsd_content.setAdapter(adapter);
	}

	private void addDistStatementItemPagerTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MerchantDistStatementDetailTask.TP_BZ_DIST_STATEMENT_ID,
				bzDistStatementId);
		TaskService.addTask(new MerchantDistStatementDetailTask(
				MerchantDistStatementDetail.this, params));
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
		if (MerchantDistStatementDetailTask.getTaskId() == taskId) {
			WsPageResult<BzDistStatementDto> result = (WsPageResult<BzDistStatementDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(this, "加载异常");
				} else {
					ToastHelper.showToast(this, result.getErrorMsg());
				}
				return;
			}
			bzDistStatementDto = result.getContent().iterator().next();
			adapterContent.clear();
			adapterContent.addAll(bzDistStatementDto
					.getBzDistStatementItemDtos());
			adapter.notifyDataSetChanged();
		}
	}

	class MerchantDistStatementItemAdapter extends AbstractBaseAdapter {

		private List<BzDistStatementItemDto> listItems;// 数据集合
		private LayoutInflater listContainer;// 视图容器

		class ListItemView { // 自定义控件集合
			TextView tv_mdsda_dist_company_name;
			TextView tv_mdsda_statement_time;
			TextView tv_mdsda_merchant_receivable_amount;
			TextView tv_mdsda_merchant_received_amount;
		}

		/**
		 * 实例化Adapter
		 * 
		 * @param context
		 * @param data
		 * @param resource
		 */
		public MerchantDistStatementItemAdapter(Context context,
				List<BzDistStatementItemDto> data) {
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
						R.layout.merchant_dist_statement_detail_adapter,
						parent, false);
				listItemView = new ListItemView();
				listItemView.tv_mdsda_dist_company_name = (TextView) convertView
						.findViewById(R.id.tv_mdsda_dist_company_name);
				listItemView.tv_mdsda_statement_time = (TextView) convertView
						.findViewById(R.id.tv_mdsda_statement_time);
				listItemView.tv_mdsda_merchant_receivable_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsda_merchant_receivable_amount);
				listItemView.tv_mdsda_merchant_received_amount = (TextView) convertView
						.findViewById(R.id.tv_mdsda_merchant_received_amount);
				// 设置控件集到convertView
				convertView.setTag(listItemView);
			} else {
				listItemView = (ListItemView) convertView.getTag();
			}
			// 设置文字和图片
			final BzDistStatementItemDto target = (BzDistStatementItemDto) listItems
					.get(position);
			listItemView.tv_mdsda_dist_company_name.setText(StringHelper
					.trimToEmpty(bzDistStatementDto.getBzDistCompanyDto()
							.getCompanyName()));
			listItemView.tv_mdsda_statement_time.setText(DateUtil
					.formatDate(target.getStatementTime()));
			listItemView.tv_mdsda_merchant_receivable_amount
					.setText(StringHelper.bigDecimalToRmb(target
							.getMerchantReceivableAmount()));
			listItemView.tv_mdsda_merchant_received_amount.setText(StringHelper
					.bigDecimalToRmb(target.getMerchantReceivedAmount()));
			return convertView;
		}
	}
}
