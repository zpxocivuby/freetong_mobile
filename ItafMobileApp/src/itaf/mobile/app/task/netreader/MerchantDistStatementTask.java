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
public class MerchantDistStatementTask extends ReaderTask {

	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";
	public static final String TP_BZ_DIST_COMPANY_ID = "bzDistCompanyId";
	public static final String TP_BZ_DIST_STATEMENT_ITEM_IDS = "bzDistStatementItemIds";

	private WsDistStatementClient client = new WsDistStatementClient();

	public MerchantDistStatementTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MERCHANT_DIST_STATEMENT;
	}

	@Override
	public void loadMsgObj() {
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		Long bzDistCompanyId = (Long) this.getParams().get(
				TP_BZ_DIST_COMPANY_ID);
		String bzDistStatementItemIds = (String) this.getParams().get(
				TP_BZ_DIST_STATEMENT_ITEM_IDS);
		WsPageResult<BzDistStatementDto> result = client.startDistStatement(
				bzMerchantId, bzDistCompanyId, bzDistStatementItemIds);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
