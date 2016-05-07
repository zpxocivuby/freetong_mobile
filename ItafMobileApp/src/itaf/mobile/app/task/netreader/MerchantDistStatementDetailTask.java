package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsDistStatementClient;

import java.util.Map;

/**
 * 配送结算任务
 * 
 * @author
 * 
 */
public class MerchantDistStatementDetailTask extends ReaderTask {

	public static final String TP_BZ_DIST_STATEMENT_ID = "bzDistStatementId";

	private WsDistStatementClient client = new WsDistStatementClient();

	public MerchantDistStatementDetailTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MERCHANT_DIST_STATEMENT_ITEM_PAGER;
	}

	@Override
	public void loadMsgObj() {
		Long bzDistStatementId = (Long) this.getParams().get(
				TP_BZ_DIST_STATEMENT_ID);
		WsPageResult<BzDistStatementDto> result = client
				.getById(bzDistStatementId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
