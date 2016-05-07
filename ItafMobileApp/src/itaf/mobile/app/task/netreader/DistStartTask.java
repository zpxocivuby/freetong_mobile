package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsDistOrderClient;

import java.util.Map;

public class DistStartTask extends ReaderTask {

	public static final String TP_BZ_DIST_ORDER_ID = "bzDistOrderId";

	private WsDistOrderClient client = new WsDistOrderClient();

	public DistStartTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_DIST_START;
	}

	@Override
	public void loadMsgObj() {
		Long bzDistOrderId = (Long) this.getParams().get(TP_BZ_DIST_ORDER_ID);
		WsPageResult<BzDistOrderDto> result = client.startDist(bzDistOrderId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}