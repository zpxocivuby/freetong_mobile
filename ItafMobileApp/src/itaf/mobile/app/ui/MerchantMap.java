package itaf.mobile.app.ui;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.R;
import itaf.mobile.app.services.TaskService;
import itaf.mobile.app.task.netreader.SearchMerchantsTask;
import itaf.mobile.app.ui.base.BaseLocationMapActivity;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.ui.custom.PopServiceProviderType;
import itaf.mobile.app.util.OnClickListenerHelper;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.core.app.AppActivityManager;
import itaf.mobile.core.utils.StringHelper;

import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.CoordinateConverter;
import com.baidu.mapapi.utils.CoordinateConverter.CoordType;

/**
 * 商家地图
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月3日
 */
public class MerchantMap extends BaseLocationMapActivity implements UIRefresh {

	public static final String AP_SEARCH_KEY = "searchKey";

	// 返回
	private Button btn_mm_back;
	// 查询关键字
	private TextView tv_mm_search_key;
	// 搜索
	private ImageView iv_mm_search;
	// 商家分类
	private TextView tv_mm_merchant_category;
	// 选择地图
	private ImageView iv_mm_select_map;
	// 选择列表
	private ImageView iv_mm_select_list;

	/**
	 * MapView 是地图主控件
	 */
	private MapView mMapView;
	private BaiduMap mBaiduMap;
	private BitmapDescriptor bdPoint;
	private BitmapDescriptor bdGround;
	private BDLocation location;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.merchant_map);
		initPageAttribute();
		initBaiduMap();
		String searchKey = this.getIntent().getStringExtra(AP_SEARCH_KEY);
		if (StringHelper.isNotEmpty(searchKey)) {
			tv_mm_search_key.setText(searchKey);
		}
	}

	@Override
	protected void onResume() {
		// MapView的生命周期与Activity同步，当activity恢复时需调用MapView.onResume()
		mMapView.onResume();
		super.onResume();
	}

	private void initPageAttribute() {
		btn_mm_back = (Button) this.findViewById(R.id.btn_mm_back);
		btn_mm_back.setOnClickListener(OnClickListenerHelper
				.finishActivity(MerchantMap.this));
		tv_mm_search_key = (TextView) this.findViewById(R.id.tv_mm_search_key);
		iv_mm_search = (ImageView) this.findViewById(R.id.iv_mm_search);
		iv_mm_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MerchantMap.this, SearchProductOrMerchant.class);
				startActivity(intent);
			}
		});

		tv_mm_merchant_category = (TextView) this
				.findViewById(R.id.tv_mm_merchant_category);
		tv_mm_merchant_category.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				PopServiceProviderType popup = new PopServiceProviderType();
				popup.show(MerchantMap.this, R.id.tv_mm_merchant_category);
			}
		});
		iv_mm_select_map = (ImageView) this.findViewById(R.id.iv_mm_select_map);
		iv_mm_select_map.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// do nothing
			}
		});

		iv_mm_select_list = (ImageView) this
				.findViewById(R.id.iv_mm_select_list);
		iv_mm_select_list.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.putExtra(SearchMerchantResult.AP_SEARCH_KEY,
						getTextViewToString(tv_mm_search_key));
				intent.setClass(MerchantMap.this, SearchMerchantResult.class);
				startActivity(intent);
				AppActivityManager.getInstance().finishActivity(
						MerchantMap.this);
			}
		});
	}

	private void initBaiduMap() {
		bdPoint = BitmapDescriptorFactory
				.fromResource(R.drawable.map_icon_gcoding);
		bdGround = BitmapDescriptorFactory
				.fromResource(R.drawable.map_ground_overlay);
		mMapView = (MapView) findViewById(R.id.mv_mm_map);
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(14.0f);
		mBaiduMap.setMapStatus(msu);
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
		// initOverlay();
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
		bdPoint.recycle();
		bdGround.recycle();
	}

	private void addSearchMerchantsTask() {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put(SearchMerchantsTask.TP_KEY_POSITION_X,
				location.getLongitude());
		queryMap.put(SearchMerchantsTask.TP_KEY_POSITION_Y,
				location.getLatitude());
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(SearchMerchantsTask.TP_QUERY_MAP, queryMap);
		params.put(SearchMerchantsTask.TP_CURRENT_INDEX, 0);
		params.put(SearchMerchantsTask.TP_PAGE_SIZE, this.getPageSize());
		TaskService.addTask(new SearchMerchantsTask(MerchantMap.this, params));
		showProgressDialog(PD_LOADING);
	}

	private InfoWindow mInfoWindow;

	@SuppressWarnings("unchecked")
	@Override
	public void refresh(Object... args) {
		dismissProgressDialog();
		int taskId = (Integer) args[0];
		if (SearchMerchantsTask.getTaskId() == taskId) {
			if (args[1] == null) {
				return;
			}
			WsPageResult<BzMerchantDto> result = (WsPageResult<BzMerchantDto>) args[1];
			if (WsPageResult.STATUS_ERROR.equals(result.getStatus())) {
				if (StringHelper.isEmpty(result.getErrorMsg())) {
					ToastHelper.showToast(MerchantMap.this, "加载失败");
				} else {
					ToastHelper.showToast(MerchantMap.this,
							result.getErrorMsg());
				}
				return;
			}
			if (result.getContent() == null || result.getContent().size() <= 0) {
				return;
			}
			for (BzMerchantDto target : result.getContent()) {
				LatLng latLng = new LatLng(target.getBzPositionDto().getY()
						.doubleValue(), target.getBzPositionDto().getX()
						.doubleValue());
				CoordinateConverter converter = new CoordinateConverter();
				converter.from(CoordType.GPS);
				// sourceLatLng待转换坐标
				converter.coord(latLng);
				latLng = converter.convert();
				OverlayOptions overlayOptions = new MarkerOptions()
						.position(latLng).icon(bdPoint)
						.title(target.getCompanyName())
						.zIndex(target.getId().intValue()).draggable(false);
				mBaiduMap.addOverlay(overlayOptions);
			}
			mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
				@Override
				public boolean onMarkerClick(final Marker marker) {
					Button button = new Button(getApplicationContext());
					button.setBackgroundResource(R.drawable.map_popup);
					button.setText(marker.getTitle());
					button.setOnClickListener(new OnClickListener() {
						public void onClick(View v) {
							Intent intent = new Intent();
							intent.putExtra(MerchantDetail.AP_BZ_MERCHANT_ID,
									Long.valueOf(marker.getZIndex()));
							intent.setClass(MerchantMap.this,
									MerchantDetail.class);
							startActivity(intent);
							mBaiduMap.hideInfoWindow();
						}
					});
					mInfoWindow = new InfoWindow(button, marker.getPosition(),
							-47);
					mBaiduMap.showInfoWindow(mInfoWindow);
					return false;
				}
			});
			return;
		}

	}

	@Override
	protected BDLocationListener getDbLocationListener() {
		return new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation bDLocation) {
				location = bDLocation;
				addSearchMerchantsTask();
				LatLng latLng = new LatLng(location.getLatitude(),
						location.getLongitude());
				CoordinateConverter converter = new CoordinateConverter();
				converter.from(CoordType.GPS);
				// sourceLatLng待转换坐标
				converter.coord(latLng);
				latLng = converter.convert();
				MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
				mBaiduMap.setMapStatus(msu);
			}
		};
	}
}
