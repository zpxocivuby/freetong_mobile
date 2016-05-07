package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.StartServiceTask;
import itaf.mobile.app.ui.base.BaseMenuActivity;
import itaf.mobile.app.ui.base.UIRefresh;

import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;

/**
 * 菜单：首页
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月2日
 */
public class MenuHome extends BaseMenuActivity implements OnPageChangeListener,
		UIRefresh {

	// ViewPager
	private ViewPager vp_mh_pic;

	// 装点点的ImageView数组
	private ImageView[] tips;

	// 装ImageView数组
	private ImageView[] mImageViews;

	// 图片资源id
	private int[] imgIdArray;

	// 搜索
	private ImageView iv_mh_search;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_home);

		initPageAttribute();
		// 开启服务
		TaskService.addTask(new StartServiceTask(MenuHome.this,
				new HashMap<String, Object>()));
	}

	private void initPageAttribute() {
		iv_mh_search = (ImageView) this.findViewById(R.id.iv_mh_search);
		iv_mh_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MenuHome.this, SearchProductOrMerchant.class);
				startActivity(intent);
			}
		});

		ViewGroup group = (ViewGroup) findViewById(R.id.linear_mh_pic);
		vp_mh_pic = (ViewPager) findViewById(R.id.vp_mh_pic);

		// 载入图片资源ID
		imgIdArray = new int[] { R.drawable.btp_home_1f_banner,
				R.drawable.btp_home_2f_banner, R.drawable.btp_home_3f_banner };

		// 将点点加入到ViewGroup中
		tips = new ImageView[imgIdArray.length];
		for (int i = 0; i < tips.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10, 10));
			tips[i] = imageView;
			if (i == 0) {
				tips[i].setBackgroundResource(R.drawable.bg_point);
			} else {
				tips[i].setBackgroundResource(R.drawable.bg_point_on);
			}

			group.addView(imageView);
		}

		// 将图片装载到数组中
		mImageViews = new ImageView[imgIdArray.length];
		for (int i = 0; i < mImageViews.length; i++) {
			ImageView imageView = new ImageView(this);
			mImageViews[i] = imageView;
			imageView.setBackgroundResource(imgIdArray[i]);
		}

		// 设置Adapter
		vp_mh_pic.setAdapter(new MyAdapter());
		// 设置监听，主要是设置点点的背景
		vp_mh_pic.setOnPageChangeListener(this);
		// 设置ViewPager的默认项, 设置为长度的100倍，这样子开始就能往左滑动
		vp_mh_pic.setCurrentItem((mImageViews.length) * 100);

	}

	public class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(View container, int position, Object object) {
			// ((ViewPager) container).removeView(mImageViews[position
			// % mImageViews.length]);
		}

		/**
		 * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
		 */
		@Override
		public Object instantiateItem(View container, int position) {
			try {
				((ViewPager) container).addView(mImageViews[position
						% mImageViews.length], 0);
			} catch (Exception e) {
				// handler something
			}
			return mImageViews[position % mImageViews.length];
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		setImageBackground(arg0 % mImageViews.length);
	}

	/**
	 * 设置选中的tip的背景
	 * 
	 * @param selectItems
	 */
	private void setImageBackground(int selectItems) {
		for (int i = 0; i < tips.length; i++) {
			if (i == selectItems) {
				tips[i].setBackgroundResource(R.drawable.bg_point);
			} else {
				tips[i].setBackgroundResource(R.drawable.bg_point_on);
			}
		}
	}

	@Override
	public void refresh(Object... args) {
		// do nothing but must be had
	}

}
