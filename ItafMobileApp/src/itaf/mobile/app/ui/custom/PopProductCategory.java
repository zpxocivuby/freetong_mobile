package itaf.mobile.app.ui.custom;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.app.R;
import itaf.mobile.app.ui.adapter.PopProductCategoryLeftAdapter;
import itaf.mobile.app.ui.adapter.PopProductCategoryRightAdapter;
import itaf.mobile.app.ui.widget.CategoryListView;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;

import java.util.List;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 分类菜单
 * 
 * @author
 * 
 * @update 2013年11月06日
 */
public class PopProductCategory {

	private CategoryItemOnClickListener categoryItemOnClickListener;

	// 商品品类
	private ProductCategoryDb productCategoryDb;
	private List<BzProductCategoryDto> leftContent;
	private List<BzProductCategoryDto> rightContent;

	// 分类左边的ListView
	private CategoryListView clv_pc_left;
	// 分类右边的ListView
	private CategoryListView clv_pc_right;
	// 分类左边的ListView适配器
	private PopProductCategoryLeftAdapter leftAdapter;
	// 分类右边的ListView适配器
	private PopProductCategoryRightAdapter rightAdapter;
	// 窗口对象
	private PopupWindow popup_window;

	public void setCategoryItemOnClickListener(
			CategoryItemOnClickListener categoryItemOnClickListener) {
		this.categoryItemOnClickListener = categoryItemOnClickListener;
	}

	public void show(final Activity mActivity, Integer positionId) {
		if (popup_window != null && popup_window.isShowing()) {
			popup_window.dismiss();
		}
		productCategoryDb = new ProductCategoryDb(mActivity);
		leftContent = productCategoryDb.findByPid(null);
		rightContent = productCategoryDb.findByPid(leftContent.get(0).getId());
		LinearLayout parentView = new LinearLayout(mActivity);
		LinearLayout popLayout = (LinearLayout) LayoutInflater.from(mActivity)
				.inflate(R.layout.pop_category, parentView);

		clv_pc_left = (CategoryListView) popLayout
				.findViewById(R.id.clv_pc_left);
		clv_pc_right = (CategoryListView) popLayout
				.findViewById(R.id.clv_pc_right);
		leftAdapter = new PopProductCategoryLeftAdapter(mActivity, leftContent);
		clv_pc_left.setAdapter(leftAdapter);
		leftAdapter.setSelectedPosition(0);
		leftAdapter.notifyDataSetInvalidated();

		rightAdapter = new PopProductCategoryRightAdapter(mActivity,
				rightContent);
		clv_pc_right.setAdapter(rightAdapter);
		clv_pc_right.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BzProductCategoryDto dto = rightContent.get(position);
				if (dto.getIsLeaf()) {
					categoryItemOnClickListener.callBack(dto.getId());
					popup_window.dismiss();
				} else {
					leftContent.clear();
					leftContent.addAll(rightContent);
					leftAdapter = new PopProductCategoryLeftAdapter(mActivity,
							leftContent);
					clv_pc_left.setAdapter(leftAdapter);
					leftAdapter.setSelectedPosition(0);
					leftAdapter.notifyDataSetChanged();

					rightContent.clear();
					rightContent = productCategoryDb.findByPid(dto.getId());
					rightAdapter = new PopProductCategoryRightAdapter(
							mActivity, rightContent);
					clv_pc_right.setAdapter(rightAdapter);
					rightAdapter.notifyDataSetChanged();
				}

			}
		});

		clv_pc_left.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				BzProductCategoryDto dto = leftContent.get(position);
				if (position == 0 && dto.getParentId() != null) {
					rightContent.clear();
					rightContent.addAll(leftContent);
					rightAdapter = new PopProductCategoryRightAdapter(
							mActivity, rightContent);
					clv_pc_right.setAdapter(rightAdapter);
					rightAdapter.notifyDataSetChanged();

					leftContent.clear();
					BzProductCategoryDto target = productCategoryDb
							.findById(dto.getParentId());
					leftContent = productCategoryDb.findByPid(target
							.getParentId());
					leftAdapter = new PopProductCategoryLeftAdapter(mActivity,
							leftContent);
					clv_pc_left.setAdapter(leftAdapter);
					leftAdapter.setSelectedPosition(0);
					leftAdapter.notifyDataSetChanged();
					return;
				}

				leftAdapter.setSelectedPosition(position);
				leftAdapter.notifyDataSetInvalidated();
				rightContent = productCategoryDb.findByPid(dto.getId());
				rightAdapter = new PopProductCategoryRightAdapter(mActivity,
						rightContent);
				clv_pc_right.setAdapter(rightAdapter);
				clv_pc_right.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						BzProductCategoryDto dto = rightContent.get(position);
						if (dto.getIsLeaf()) {
							categoryItemOnClickListener.callBack(dto.getId());
							popup_window.dismiss();
						} else {
							leftContent.clear();
							leftContent.addAll(rightContent);
							leftAdapter = new PopProductCategoryLeftAdapter(
									mActivity, leftContent);
							clv_pc_left.setAdapter(leftAdapter);
							leftAdapter.setSelectedPosition(0);
							leftAdapter.notifyDataSetChanged();

							rightContent.clear();
							rightContent = productCategoryDb.findByPid(dto
									.getId());
							rightAdapter = new PopProductCategoryRightAdapter(
									mActivity, rightContent);
							clv_pc_right.setAdapter(rightAdapter);
							rightAdapter.notifyDataSetChanged();
						}

					}
				});
			}
		});
		// 默认值
		DisplayMetrics dm = new DisplayMetrics();
		mActivity.getWindowManager().getDefaultDisplay().getMetrics(dm);
		popup_window = new PopupWindow(popLayout, dm.widthPixels,
				dm.heightPixels * 2 / 3, true);
		// 加上下面两行可以用back键关闭PopupWindow，否则必须调用dismiss();
		Resources res = null;
		popup_window.setBackgroundDrawable(new BitmapDrawable(res));
		popup_window.update();
		popup_window.setOutsideTouchable(true);
		popup_window.setFocusable(true);
		popup_window.setContentView(popLayout);
		// 需要指定Gravity，默认情况是center.
		popup_window.showAsDropDown(mActivity.findViewById(positionId));
	}

	public void dismiss() {
		if (popup_window != null && popup_window.isShowing()) {
			popup_window.dismiss();
		}
	}

	public interface CategoryItemOnClickListener {
		void callBack(Long dateId);
	}

}