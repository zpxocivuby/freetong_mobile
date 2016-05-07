package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.order.WsOrderClient;

import java.util.Map;

/**
 * 确认收货
 * 
 * @author
 * 
 */
public class OrderConfirmReceivedTask extends ReaderTask {

	public static final String TP_BZ_ORDER_ID = "bzOrderId";

	private WsOrderClient client = new WsOrderClient();

	public OrderConfirmReceivedTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_CONFIRM_RECEIVED;
	}

	@Override
	public void loadMsgObj() {
		Long bzOrderId = (Long) this.getParams().get(TP_BZ_ORDER_ID);
		WsPageResult<BzOrderDto> result = client.confirmReceived(bzOrderId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
