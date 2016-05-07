package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsDistCompanyClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 选择配送商列表任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class SearchDistCompanyTask extends ReaderTask {

	public static final String TP_KEY_COMPANY_NAME = "companyName";
	public static final String TP_KEY_SERVICE_COVERAGE = "serviceCoverage";
	public static final String TP_KEY_ORDER_TYPE = "orderType";

	public static final String TP_QUERY_MAP = "queryMap";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsDistCompanyClient client = new WsDistCompanyClient();

	public SearchDistCompanyTask(BaseActivity activity,
			Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_DIST_COMPANY_PAGER;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadMsgObj() {
		HashMap<String, Object> queryMap = (HashMap<String, Object>) this
				.getParams().get("queryMap");
		int currentIndex = (Integer) this.getParams().get("currentIndex");
		int pageSize = (Integer) this.getParams().get("pageSize");
		WsPageResult<BzDistCompanyDto> result = client.searchDistCompanys(
				queryMap, currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
