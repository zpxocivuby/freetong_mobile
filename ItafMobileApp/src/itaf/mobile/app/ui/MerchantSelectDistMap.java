package itaf.mobile.app.ui;

import itaf.mobile.app.R;
import itaf.mobile.app.ui.base.BaseUIActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.OnClickListenerHelper;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

/**
 * 选择配送商地图
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class MerchantSelectDistMap extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_msdm_back;
	// 搜索关键字
	private EditText et_msdm_search;
	// 分类
	private TextView et_msdm_category;
	// 是否选择列表
	private CheckBox cb_msdm_select_list;

	private String searchKey;

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;

	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bd = BitmapDescriptorFactory
			.fromResource(R.drawable.map_icon_gcoding);
	BitmapDescriptor bdGround = BitmapDescriptorFactory
			.fromResource(R.drawable.map_ground_overlay);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_select_dist_map);

		initPageAttribute();
		initBaiduMap();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
		cb_msdm_select_list.setChecked(false);
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		Object paramObj = bundle.get("searchKey");
		if (paramObj != null && paramObj.toString().length() > 0) {
			searchKey = paramObj.toString();
			et_msdm_search.setText(searchKey);
		}
	}

	private void initPageAttribute() {
		btn_msdm_back = (Button) this.findViewById(R.id.btn_msdm_back);
		btn_msdm_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantSelectDistMap.this));
		et_msdm_search = (EditText) this.findViewById(R.id.et_msdm_search);
		et_msdm_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MerchantSelectDistMap.this,
						SearchProductOrMerchant.class);
				startActivity(intent);
			}
		});

		et_msdm_category = (TextView) this.findViewById(R.id.et_msdm_category);
		et_msdm_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 分类
			}
		});

		// cb_msdm_select_list = (CheckBox) this
		// .findViewById(R.id.cb_msdm_select_list);
		// cb_msdm_select_list
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (isChecked) {
		// Intent intent = new Intent();
		// intent.setClass(MerchantSelectDistMap.this,
		// SearchProductResult.class);
		// startActivity(intent);
		// AppActivityManager.getInstance().finishActivity(
		// MerchantSelectDistMap.this);
		// }
		// }
		// });

	}

	private void initBaiduMap() {
		mMapView = (MapView) findViewById(R.id.mv_msdm_map);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		initOverlay();
	}

	public void initOverlay() {

		// add ground overlay
		LatLng southwest = new LatLng(39.92235, 116.380338);
		LatLng northeast = new LatLng(39.947246, 116.414977);
		LatLngBounds bounds = new LatLngBounds.Builder().include(northeast)
				.include(southwest).build();

		OverlayOptions ooGround = new GroundOverlayOptions()
				.positionFromBounds(bounds).image(bdGround).transparency(0.8f);
		mBaiduMap.addOverlay(ooGround);

		MapStatusUpdate u = MapStatusUpdateFactory
				.newLatLng(bounds.getCenter());
		mBaiduMap.setMapStatus(u);

		mBaiduMap.setOnMarkerDragListener(new OnMarkerDragListener() {
			public void onMarkerDrag(Marker marker) {
			}

			public void onMarkerDragEnd(Marker marker) {
				Toast.makeText(
						MerchantSelectDistMap.this,
						"拖拽结束，新位置：" + marker.getPosition().latitude + ", "
								+ marker.getPosition().longitude,
						Toast.LENGTH_LONG).show();
			}

			public void onMarkerDragStart(Marker marker) {
			}
		});
	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay();
	}

	@Override
	protected void onPause() {
		// MapView的生命周期与Activity同步，当activity挂起时需调用MapView.onPause()
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		super.onDestroy();
		// 回收 bitmap 资源
		bd.recycle();
		bdGround.recycle();
	}

	@Override
	public void refresh(Object... args) {
		//

	}
}
