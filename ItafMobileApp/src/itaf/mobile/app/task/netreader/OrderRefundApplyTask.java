package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderRefundDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.order.WsOrderRefundClient;

import java.util.Map;

/**
 * 配送结算任务
 * 
 * @author
 * 
 */
public class OrderRefundApplyTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TP_BZ_ORDER_ID = "bzOrderId";

	private WsOrderRefundClient client = new WsOrderRefundClient();

	public OrderRefundApplyTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_REFUND_APPLY;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzOrderId = (Long) this.getParams().get(TP_BZ_ORDER_ID);

		WsPageResult<BzOrderRefundDto> result = client.applyRefund(
				bzConsumerId, bzOrderId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
