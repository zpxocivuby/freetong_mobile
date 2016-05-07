package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.search.WsSearchProductClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 检索商品任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class SearchProductsTask extends ReaderTask {

	public static final String TP_KEY_BZ_MERCHANT_ID = "bzMerchantId";
	public static final String TP_KEY_PRODUCT_NAME = "productName";
	public static final String TP_KEY_PRODUCT_COLOR = "productColor";
	public static final String TP_KEY_PRODUCT_DESCRIPTION = "productDescription";
	public static final String TP_KEY_PRODUCT_ON_SALE = "productOnSale";
	public static final String TP_KEY_IS_STOCK_LIMITLESS = "isStockLimitless";
	public static final String TP_KEY_IS_SHELF_LIMITLESS = "isShelfLimitless";
	public static final String TP_KEY_IS_STOCK_SUPPORT = "isStockSupport";
	public static final String TP_KEY_IS_SHELF_SUPPORT = "isShelfSupport";
	public static final String TP_KEY_BZ_PRODUCT_CATEGORY_ID = "bzProductCategoryId";
	public static final String TP_KEY_POSITION_X = "positionX";
	public static final String TP_KEY_POSITION_Y = "positionY";

	public static final String TP_QUERY_MAP = "queryMap";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsSearchProductClient client = new WsSearchProductClient();

	public SearchProductsTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_SEARCH_PRODUCTS;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadMsgObj() {
		HashMap<String, Object> queryMap = (HashMap<String, Object>) this
				.getParams().get(TP_QUERY_MAP);
		int currentIndex = (Integer) this.getParams().get(TP_CURRENT_INDEX);
		int pageSize = (Integer) this.getParams().get(TP_PAGE_SIZE);
		WsPageResult<BzProductDto> result = client.searchProducts(queryMap,
				currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
