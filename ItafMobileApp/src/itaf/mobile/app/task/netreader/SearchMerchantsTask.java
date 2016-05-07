package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.search.WsSearchMerchantClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class SearchMerchantsTask extends ReaderTask {

	public static final String TP_KEY_COMPANY_NAME = "companyName";
	public static final String TP_KEY_SERVICE_COVERAGE = "serviceCoverage";
	public static final String TP_KEY_MERCHANT_CATEGORY = "merchantCategory";
	public static final String TP_KEY_MERCHANT_CHARACTERISTICS = "merchantCharacteristics";
	public static final String TP_KEY_POSITION_X = "positionX";
	public static final String TP_KEY_POSITION_Y = "positionY";

	public static final String TP_QUERY_MAP = "queryMap";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsSearchMerchantClient client = new WsSearchMerchantClient();

	public SearchMerchantsTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_SEARCH_MERCHANTS;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void loadMsgObj() {
		HashMap<String, Object> queryMap = (HashMap<String, Object>) this
				.getParams().get(TP_QUERY_MAP);
		int currentIndex = (Integer) this.getParams().get(TP_CURRENT_INDEX);
		int pageSize = (Integer) this.getParams().get(TP_PAGE_SIZE);
		WsPageResult<BzMerchantDto> result = client.searchMerchants(queryMap,
				currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);

	}

}
