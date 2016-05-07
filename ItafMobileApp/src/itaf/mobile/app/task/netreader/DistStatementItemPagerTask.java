package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementItemDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsDistStatementItemClient;

import java.util.HashMap;
import java.util.Map;

/**
 * 配送结算任务
 * 
 * @author
 * 
 */
public class DistStatementItemPagerTask extends ReaderTask {

	public static final String TP_KEY_BZ_DIST_COMPANY_ID = "bzDistCompanyId";
	public static final String TP_KEY_BZ_MERCHANT_ID = "bzMerchantId";
	public static final String TP_KEY_COMPANY_NAME = "companyName";
	public static final String TP_KEY_STATEMENT_STATUS = "statementStatus";

	public static final String TP_QUERY_MAP = "queryMap";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsDistStatementItemClient client = new WsDistStatementItemClient();

	public DistStatementItemPagerTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_DIST_STATEMENT_ITEM_PAGER;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void loadMsgObj() {
		HashMap<String, Object> queryMap = (HashMap<String, Object>) this
				.getParams().get(TP_QUERY_MAP);
		int currentIndex = (Integer) this.getParams().get(TP_CURRENT_INDEX);
		int pageSize = (Integer) this.getParams().get(TP_PAGE_SIZE);
		WsPageResult<BzDistStatementItemDto> result = client.findPager(
				queryMap, currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
