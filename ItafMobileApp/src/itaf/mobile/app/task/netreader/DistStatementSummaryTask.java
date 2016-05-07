package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementSummaryDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsDistStatementSummaryClient;

import java.util.Map;

/**
 * 配送结算任务
 * 
 * @author
 * 
 */
public class DistStatementSummaryTask extends ReaderTask {

	public static final String BZ_DIST_COMPANY_ID = "bzDistCompanyId";

	private WsDistStatementSummaryClient client = new WsDistStatementSummaryClient();

	public DistStatementSummaryTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_DIST_STATEMENT_SUMMARY;
	}

	@Override
	public void loadMsgObj() {
		Long bzDistCompanyId = (Long) this.getParams().get(BZ_DIST_COMPANY_ID);
		WsPageResult<BzDistStatementSummaryDto> result = client
				.getBzDistStatementSummaryDto(bzDistCompanyId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
