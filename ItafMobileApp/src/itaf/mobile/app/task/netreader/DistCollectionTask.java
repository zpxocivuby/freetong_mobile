package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzCollectionOrderDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsCollectionOrderClient;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 收款任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class DistCollectionTask extends ReaderTask {

	public static final String TP_BZ_COLLECTION_ORDER_ID = "bzCollectionOrderId";
	public static final String TP_ACTUAL_AMOUNT = "actualAmount";

	WsCollectionOrderClient client = new WsCollectionOrderClient();

	public DistCollectionTask(BaseActivity activity, Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_DIST_COLLECTION;
	}

	@Override
	public void loadMsgObj() {
		Long bzCollectionOrderId = (Long) this.getParams().get(
				TP_BZ_COLLECTION_ORDER_ID);
		BigDecimal actualAmount = (BigDecimal) this.getParams().get(
				TP_ACTUAL_AMOUNT);
		WsPageResult<BzCollectionOrderDto> result = client.confirmCollection(
				bzCollectionOrderId, actualAmount);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
