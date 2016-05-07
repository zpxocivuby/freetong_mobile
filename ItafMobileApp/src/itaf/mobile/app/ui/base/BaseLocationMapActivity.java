package itaf.mobile.app.ui.base;

import android.os.Bundle;
import android.util.Log;

import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;

/**
 * 
 *
 * @author XINXIN
 *
 * @UpdateDate 2015年1月12日
 */
public abstract class BaseLocationMapActivity extends BaseUIActivity {

	private LocationClient locationClient;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onResume() {
		super.onResume();
		locationClient = new LocationClient(this.getApplicationContext());
		locationClient.registerLocationListener(getDbLocationListener());
		setOption();
		locationClient.start();
	}

	protected void startOnce() {
		if (locationClient != null && locationClient.isStarted()) {
			locationClient.requestLocation();
		} else {
			Log.d("LocSDK4", "locClient is null or not started");
		}
	}

	// 之类实现Listener获取获取数据
	protected abstract BDLocationListener getDbLocationListener();

	@Override
	protected void onStop() {
		locationClient.stop();
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		locationClient.stop();
		super.onDestroy();
	}

	private void setOption() {
		LocationClientOption option = new LocationClientOption();
		// 设置定位模式
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		// 返回的定位结果是百度经纬度，默认值gcj02
		// option.setCoorType("bdll09");
		// 设置发起定位请求的间隔时间为1000ms
		// option.setScanSpan(10 * 60 * 5000);
		// 是否获取地址信息
		option.setIsNeedAddress(true);
		locationClient.setLocOption(option);
	}

}
