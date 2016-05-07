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
 * 订单列表任务
 * 
 * @author
 * 
 */
public class OrderCreateTask extends ReaderTask {

	public static final String TP_BZ_ORDER_DTO = "BzOrderDto";

	private WsOrderClient client = new WsOrderClient();

	public OrderCreateTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_CREATE;
	}

	@Override
	public void loadMsgObj() {
		BzOrderDto dto = (BzOrderDto) this.getParams().get(TP_BZ_ORDER_DTO);
		WsPageResult<BzOrderDto> result = client.submitOrder(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
