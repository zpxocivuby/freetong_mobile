package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.consumer.WsUserAddressClient;

import java.util.Map;

/**
 * 获取收货地址任务
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class UserAddressDetailTask extends ReaderTask {

	public static final String TP_BZ_USER_ADDRESS_ID = "bzUserAddressId";

	private WsUserAddressClient client = new WsUserAddressClient();

	public UserAddressDetailTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_ADDRESS_DETAIL;
	}

	@Override
	public void loadMsgObj() {
		Long bzUserAddressId = (Long) this.getParams().get(
				TP_BZ_USER_ADDRESS_ID);
		WsPageResult<BzUserAddressDto> result = client.getById(bzUserAddressId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
