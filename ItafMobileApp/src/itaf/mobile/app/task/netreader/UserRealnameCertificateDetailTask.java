package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.consumer.WsUserDeliveryAddressClient;

import java.util.Map;

public class UserRealnameCertificateDetailTask extends ReaderTask {

	public static final String TP_USER_ID = "userId";

	private WsUserDeliveryAddressClient client = new WsUserDeliveryAddressClient();

	public UserRealnameCertificateDetailTask(BaseActivity activity,
			Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.PRE_TASK_USER_DELIVERY_ADDRESS;
	}

	@Override
	public void loadMsgObj() {
		Long userId = (Long) this.getParams().get(TP_USER_ID);
		WsPageResult<BzUserDeliveryAddressDto> result = client.getById(userId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
