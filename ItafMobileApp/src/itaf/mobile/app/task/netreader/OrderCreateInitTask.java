package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderInitDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.order.WsOrderInitClient;

import java.util.Map;

/**
 * 订单列表任务
 * 
 * @author
 * 
 */
public class OrderCreateInitTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	private WsOrderInitClient client = new WsOrderInitClient();

	public OrderCreateInitTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_CREATE_INIT;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		WsPageResult<BzOrderInitDto> result = client
				.orderCreateInit(bzConsumerId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
