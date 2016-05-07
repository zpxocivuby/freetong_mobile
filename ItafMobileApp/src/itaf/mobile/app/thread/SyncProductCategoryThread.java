package itaf.mobile.app.thread;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;
import itaf.mobile.ds.ws.product.WsProductCategoryClient;

import java.util.Map;

import android.content.Context;
import android.util.Log;

/**
 * 同步商品分类
 * 
 * 
 * @author
 * 
 * @updateDate 2014年3月14日
 */
public class SyncProductCategoryThread extends BaseSyncThread {

	// 休息的毫秒数
	private static final int SLEEP_TIME = 1000;

	private ProductCategoryDb db = new ProductCategoryDb(getContext());

	public SyncProductCategoryThread(Context context, Map<String, Object> params) {
		super(context, params);
	}

	@Override
	public void doSyncRuning() {
		int currentIndex = 0;
		int pageSize = getPageSize();
		boolean isFirstSync = true;
		int tryTimes = 0;

		WsProductCategoryClient client = new WsProductCategoryClient();
		WsPageResult<BzProductCategoryDto> pager = null;
		try {
			while (isRun()) {
				try {
					pager = client.syncPager2Client(currentIndex, pageSize);
					if (isFirstSync) {
						// 第一次同步，清除同步过的事件
						db.clearData();
						isFirstSync = false;
					}
					if (pager == null || pager.getContent() == null
							|| pager.getContent().size() == 0) {
						setRun(false);
						return;
					}
					currentIndex = pager.getCurrentIndex() + pageSize;
					db.saveList(pager.getContent());
					Log.e(this.getClass().getName(),
							"共有[" + pager.getTotalCount() + "]条，已下载["
									+ pager.getCurrentIndex() + "]条");
				} catch (Exception e) {
					e.printStackTrace();
					if (tryTimes >= getTryRate()) {
						setRun(false);
					} else {
						tryTimes++;
						currentIndex = 0;
						Log.e(this.getClass().getName(), "服务端下载数据异常，正在重试：["
								+ tryTimes + "]");
					}
				} finally {
					SyncProductCategoryThread.sleep(SLEEP_TIME);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.i(this.getClass().getName(), e.getMessage());
		} finally {
			setRun(false);
		}
	}
}
