package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.R;
import itaf.mobile.app.im.ui.ImChatWindow;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.MenuCartDeleteTask;
import itaf.mobile.app.task.netreader.MenuCartListTask;
import itaf.mobile.app.task.netreader.MerchantFavoriteTask;
import itaf.mobile.app.task.netreader.ProductFavoriteTask;
import itaf.mobile.app.task.netreader.UpdateBuyNumTask;
import itaf.mobile.app.third.pull.PullToRefreshBase;
import itaf.mobile.app.third.pull.PullToRefreshBase.OnRefreshListener;
import itaf.mobile.app.third.pull.PullToRefreshExpandableListView;
import itaf.mobile.app.ui.base.BaseMenuActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.widget.AsyncImageView;
import itaf.mobile.app.util.AlertDialogHelper;
import itaf.mobile.app.util.DownLoadHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.core.utils.StringHelper;
import itaf.mobile.ds.domain.SerializableList;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 菜单：购物车
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月2日
 */
public class MenuCart extends BaseMenuActivity implements UIRefresh {

	// 总价
	private TextView tv_mc_price_total;
	// 删除
	private TextView tv_mc_delete;
	// 结算
	private Button btn_mc_balance;

	private PullToRefreshExpandableListView elv_mc_content;
	private MenuCartAdapter adapter;
	private List<BzMerchantDto> adapterContent = new ArrayList<BzMerchantDto>();
	private List<BzCartItemDto> selectDtos = new ArrayList<BzCartItemDto>();
	private List<Long> selectIds = new ArrayList<Long>();
	private BigDecimal priceTotal = new BigDecimal("0.0");

	// TODO 检查结算
	private boolean isCanBalance = true;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_cart);
		initPageAttribute();
		initListView();
	}

	@Override
	protected void onResume() {
		super.onResume();
		addMenuCartListTask();
	}

	private void initPageAttribute() {
		tv_mc_price_total = (TextView) this
				.findViewById(R.id.tv_mc_price_total);
		tv_mc_delete = (TextView) this.findViewById(R.id.tv_mc_delete);
		tv_mc_delete.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectIds.size() == 0) {
					ToastHelper.showToast(MenuCart.this, "请选择要删除的选项");
					return;
				}
				AlertDialogHelper.showDialog(MenuCart.this, "提醒", "确认要删除吗？",
						"确定", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								addMenuCartDeleteTask();
							}

						});
			}
		});
		btn_mc_balance = (Button) this.findViewById(R.id.btn_mc_balance);
		btn_mc_balance.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (selectDtos == null || selectDtos.size() == 0) {
					ToastHelper.showToast(MenuCart.this, "选择要结算的选项");
					return;
				}
				if (!isCanBalance) {
					ToastHelper.showToast(MenuCart.this, "不同的商家只能分开结算!");
					return;
				}
				BzMerchantDto dto = selectDtos.get(0).getBzMerchantDto();
				dto.setBzCartItemDtos(selectDtos);
				Bundle bundle = new Bundle();
				bundle.putSerializable(OrderCreate.AP_BZ_MERCHANT_DTO,
						new SerializableList<BzMerchantDto>(dto));
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(MenuCart.this, OrderCreate.class);
				startActivity(intent);
			}
		});

	}

	private void initListView() {
		// 增加那个 更多按钮,必须放在Adapter之前
		elv_mc_content = (PullToRefreshExpandableListView) this
				.findViewById(R.id.elv_mc_content);
		elv_mc_content.setShowIndicator(false);
		adapter = new MenuCartAdapter(MenuCart.this);
		elv_mc_content.getRefreshableView().setAdapter(adapter);
		// 设置拖动列表的时候防止出现黑色背景
		elv_mc_content.getRefreshableView().setCacheColorHint(0);
		elv_mc_content
				.setOnRefreshListener(new OnRefreshListener<ExpandableListView>() {
					@Override
					public void onRefresh(
							PullToRefreshBase<ExpandableListView> refreshView) {
						String label = DateUtils.formatDateTime(
								getApplicationContext(),
								System.currentTimeMillis(),
								DateUtils.FORMAT_SHOW_TIME
										| DateUtils.FORMAT_SHOW_DATE
										| DateUtils.FORMAT_ABBREV_ALL);
						// Update the LastUpdatedLabel
						refreshView.getLoadingLayoutProxy()
								.setLastUpdatedLabel(label);
						addMenuCartListTask();
					}
				});

	}

	private void addMenuCartListTask() {
		selectIds.clear();
		selectDtos.clear();
		tv_mc_price_total.setText("0.0");
		priceTotal = new BigDecimal("0.0");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MenuCartListTask.TP_BZ_CONSUMER_ID, AppApplication
				.getInstance().getSessionUser().getId());
		TaskService.addTask(new MenuCartListTask(MenuCart.this, params));
		showProgressDialog(PD_LOADING);
	}

	private void addMenuCartDeleteTask() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MenuCartDeleteTask.TP_BZ_CART_ITEM_IDS,
				StringHelper.collectionToString(selectIds));
		TaskService.addTask(new MenuCartDeleteTask(MenuCart.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	private void addUpdateBuyNumTask(Long bzCartItemId, Long buyNum) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(UpdateBuyNumTask.TP_BZ_CART_ITEM_ID, bzCartItemId);
		params.put(UpdateBuyNumTask.TP_ITEM_NUM, buyNum);
		TaskService.addTask(new UpdateBuyNumTask(MenuCart.this, params));
		showProgressDialog(PD_SUBMITTING);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (MenuCartListTask.getTaskId() == taskId) {
			if (args[1] == null) {
				elv_mc_content.onRefreshComplete();
				return;
			}
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuCart.this, "加载失败");
				} else {
					ToastHelper.showToast(MenuCart.this, result.getErrorMsg());
				}
				elv_mc_content.onRefreshComplete();
				return;
			}
			adapterContent.clear();
			List<BzMerchantDto> invlids = new ArrayList<BzMerchantDto>();
			for (BzCartItemDto dto : result.getContent()) {
				if (dto.getItemStatus() == null || dto.getItemStatus() == 0) {
					BzMerchantDto merchantDto = dto.getBzMerchantDto();
					int pos = invlids.indexOf(merchantDto);
					if (pos > -1) {
						merchantDto = invlids.get(pos);
					} else {
						invlids.add(merchantDto);
					}
					merchantDto.getBzCartItemDtos().add(dto);
				} else {
					BzMerchantDto merchantDto = dto.getBzMerchantDto();
					int pos = adapterContent.indexOf(merchantDto);
					if (pos > -1) {
						merchantDto = adapterContent.get(pos);
					} else {
						adapterContent.add(merchantDto);
					}
					merchantDto.getBzCartItemDtos().add(dto);
				}
			}
			adapterContent.addAll(invlids);
			for (int i = 0; i < adapterContent.size(); i++) {
				elv_mc_content.getRefreshableView().expandGroup(i);
			}
			adapter.notifyDataSetChanged();
			elv_mc_content.onRefreshComplete();
			return;
		} else if (MenuCartDeleteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuCart.this, "删除失败");
				} else {
					ToastHelper.showToast(MenuCart.this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(MenuCart.this, "删除成功");
			addMenuCartListTask();
		} else if (ProductFavoriteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuCart.this, "收藏失败");
				} else {
					ToastHelper.showToast(MenuCart.this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(MenuCart.this, "收藏成功");
		} else if (MerchantFavoriteTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuCart.this, "收藏失败");
				} else {
					ToastHelper.showToast(MenuCart.this, result.getErrorMsg());
				}
				return;
			}
			ToastHelper.showToast(MenuCart.this, "收藏成功");
		} else if (UpdateBuyNumTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzCartItemDto> result = (WsPageResult<BzCartItemDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MenuCart.this, "操作失败");
				} else {
					ToastHelper.showToast(MenuCart.this, result.getErrorMsg());
				}
				return;
			}
			addMenuCartListTask();
		}

	}

	class MenuCartAdapter extends BaseExpandableListAdapter {

		private Context mContext;
		private LayoutInflater inflater;

		public MenuCartAdapter(Context mContext) {
			inflater = LayoutInflater.from(mContext);
		}

		@Override
		public int getGroupCount() {
			return adapterContent.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return adapterContent.get(groupPosition).getBzCartItemDtos().size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return adapterContent.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return adapterContent.get(groupPosition).getBzCartItemDtos()
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
			final BzMerchantDto target = (BzMerchantDto) getGroup(groupPosition);
			View view = inflater.inflate(R.layout.menu_cart_adapter_group,
					parent, false);
			TextView tv_mcag_merchant_name = (TextView) view
					.findViewById(R.id.tv_mcag_merchant_name);
			tv_mcag_merchant_name.setText(target.getUsername());
			Button btn_mcag_chat = (Button) view
					.findViewById(R.id.btn_mcag_chat);
			btn_mcag_chat.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.putExtra(ImChatWindow.CHAT_JID, target.getUsername());
					intent.setClass(mContext, ImChatWindow.class);
					mContext.startActivity(intent);
				}
			});
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			final BzCartItemDto target = (BzCartItemDto) getChild(
					groupPosition, childPosition);
			View view = inflater.inflate(R.layout.menu_cart_adapter_group_item,
					parent, false);
			AsyncImageView iv_mcagi_product_ico = (AsyncImageView) view
					.findViewById(R.id.iv_mcagi_product_ico);
			BzProductDto bzProductDto = target.getBzProductDto();
			if (bzProductDto.getBzProductAttachmentIds() != null
					&& bzProductDto.getBzProductAttachmentIds().size() > 0) {
				iv_mcagi_product_ico
						.setDefaultImageResource(R.drawable.async_loader);
				iv_mcagi_product_ico.setPath(DownLoadHelper
						.getDownloadProductPath(bzProductDto
								.getBzProductAttachmentIds().get(0),
								AppConstants.IMAGE_SIZE_64X64));
			}
			TextView tv_mcagi_product_name = (TextView) view
					.findViewById(R.id.tv_mcagi_product_name);
			ImageView iv_mcagi_del = (ImageView) view
					.findViewById(R.id.iv_mcagi_del);

			String productName = StringHelper.trimToEmpty(bzProductDto
					.getProductName());
			CheckBox cb_mcagi_select = (CheckBox) view
					.findViewById(R.id.cb_mcagi_select);

			if (target.getItemStatus() == null || target.getItemStatus() == 0) {
				iv_mcagi_del.setVisibility(View.VISIBLE);
				cb_mcagi_select.setVisibility(View.GONE);
				productName = "(已下架)" + productName;
				iv_mcagi_del.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						Map<String, Object> params = new HashMap<String, Object>();
						params.put(MenuCartDeleteTask.TP_BZ_CART_ITEM_IDS,
								target.getId().toString());
						TaskService.addTask(new MenuCartDeleteTask(
								MenuCart.this, params));
						showProgressDialog(PD_SUBMITTING);
					}
				});

			} else {
				iv_mcagi_del.setVisibility(View.GONE);
				cb_mcagi_select.setVisibility(View.VISIBLE);
				cb_mcagi_select
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {
							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								if (isChecked) {
									selectIds.add(target.getId());
									selectDtos.add(target);
									priceTotal = priceTotal.add(target
											.getItemPrice());
								} else {
									selectIds.remove(target.getId());
									selectDtos.remove(target);
									priceTotal = priceTotal.subtract(target
											.getItemPrice());
								}
								tv_mc_price_total.setText(priceTotal + "");
							}
						});
			}
			tv_mcagi_product_name.setText(productName);

			final EditText et_mcagi_buy_num = (EditText) view
					.findViewById(R.id.et_mcagi_buy_num);
			et_mcagi_buy_num.setText(StringHelper.longToString(target
					.getItemNum()));
			ImageView iv_mcagi_subtract_buy_num = (ImageView) view
					.findViewById(R.id.iv_mcagi_subtract_buy_num);
			iv_mcagi_subtract_buy_num.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Long buyNum = getTextViewToLong(et_mcagi_buy_num);
					if (buyNum == 1L) {
						return;
					}
					addUpdateBuyNumTask(target.getId(), buyNum - 1);
				}

			});

			ImageView iv_mcagi_add_buy_num = (ImageView) view
					.findViewById(R.id.iv_mcagi_add_buy_num);
			iv_mcagi_add_buy_num.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Long buyNum = getTextViewToLong(et_mcagi_buy_num);
					addUpdateBuyNumTask(target.getId(), buyNum + 1);
				}
			});
			TextView tv_mcagi_price = (TextView) view
					.findViewById(R.id.tv_mcagi_price);
			tv_mcagi_price.setText(StringHelper.bigDecimalToRmb(target
					.getItemUnitPrice()));
			TextView tv_mcagi_price_total = (TextView) view
					.findViewById(R.id.tv_mcagi_price_total);
			tv_mcagi_price_total.setText(StringHelper.bigDecimalToRmb(target
					.getItemPrice()));
			return view;
		}

	}

}
