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
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerDragListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.GroundOverlayOptions;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;

/**
 * 配送订单地图
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class DistOrderMap extends BaseUIActivity implements UIRefresh {

	// 返回
	private Button btn_dom_back;
	// 搜索关键字
	private EditText et_dom_search;
	// 分类
	private TextView et_dom_category;
	// 是否选择列表
	private CheckBox cb_dom_select_list;

	private String searchKey;

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private Marker mMarkerA;
	private Marker mMarkerB;
	private Marker mMarkerC;
	private Marker mMarkerD;
	private InfoWindow mInfoWindow;

	// 初始化全局 bitmap 信息，不用时及时 recycle
	BitmapDescriptor bd = BitmapDescriptorFactory
			.fromResource(R.drawable.map_icon_gcoding);
	BitmapDescriptor bdGround = BitmapDescriptorFactory
			.fromResource(R.drawable.map_ground_overlay);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dist_order_map);

		initPageAttribute();
		initBaiduMap();
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
		cb_dom_select_list.setChecked(false);
		Bundle bundle = getIntent().getExtras();
		if (bundle == null) {
			return;
		}
		Object paramObj = bundle.get("searchKey");
		if (paramObj != null && paramObj.toString().length() > 0) {
			searchKey = paramObj.toString();
			et_dom_search.setText(searchKey);
		}
	}

	private void initPageAttribute() {
		btn_dom_back = (Button) this.findViewById(R.id.btn_dom_back);
		btn_dom_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(DistOrderMap.this));
		et_dom_search = (EditText) this.findViewById(R.id.et_dom_search);
		et_dom_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(DistOrderMap.this,
						SearchProductOrMerchant.class);
				startActivity(intent);
			}
		});

		et_dom_category = (TextView) this.findViewById(R.id.et_dom_category);
		et_dom_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO 分类
			}
		});

		// cb_dom_select_list = (CheckBox) this
		// .findViewById(R.id.cb_dom_select_list);
		// cb_dom_select_list
		// .setOnCheckedChangeListener(new OnCheckedChangeListener() {
		// @Override
		// public void onCheckedChanged(CompoundButton buttonView,
		// boolean isChecked) {
		// if (isChecked) {
		// Intent intent = new Intent();
		// intent.setClass(DistOrderMap.this,
		// SearchProductResult.class);
		// startActivity(intent);
		// AppActivityManager.getInstance().finishActivity(
		// DistOrderMap.this);
		// }
		// }
		// });

	}

	private void initBaiduMap() {
		mMapView = (MapView) findViewById(R.id.mv_dom_map);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
		initOverlay();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				Button button = new Button(getApplicationContext());
				button.setBackgroundResource(R.drawable.map_popup);
				OnInfoWindowClickListener listener = null;
				if (marker == mMarkerA || marker == mMarkerD) {
					button.setText("更改位置");
					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {
							LatLng ll = marker.getPosition();
							LatLng llNew = new LatLng(ll.latitude + 0.005,
									ll.longitude + 0.005);
							marker.setPosition(llNew);
							mBaiduMap.hideInfoWindow();
						}
					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(button), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerB) {
					button.setText("更改图标");
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							marker.setIcon(bd);
							mBaiduMap.hideInfoWindow();
						}
					});
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(button, ll, -47);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerC) {
					button.setText("删除");
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							marker.remove();
							mBaiduMap.hideInfoWindow();
						}
					});
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(button, ll, -47);
					mBaiduMap.showInfoWindow(mInfoWindow);
				}
				return true;
			}
		});

	}

	public void initOverlay() {
		// add marker overlay

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
						DistOrderMap.this,
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
