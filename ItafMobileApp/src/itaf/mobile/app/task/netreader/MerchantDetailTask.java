package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsMerchantClient;

import java.util.Map;

/**
 * 商家详情任务
 * 
 * @author
 * 
 */
public class MerchantDetailTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";

	public WsMerchantClient client = new WsMerchantClient();

	public MerchantDetailTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MERCHANT_DETAIL;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		WsPageResult<BzMerchantDto> result = client.getById(bzConsumerId,
				bzMerchantId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
