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
 * 收货地址删除任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class UserAddressDeleteTask extends ReaderTask {

	public static final String TP_BZ_USER_ADDRESS_ID = "bzUserAddressId";

	private WsUserAddressClient client = new WsUserAddressClient();

	public UserAddressDeleteTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_ADDRESS_DELETE;
	}

	@Override
	public void loadMsgObj() {
		Long bzUserAddressId = (Long) this.getParams().get(
				TP_BZ_USER_ADDRESS_ID);
		WsPageResult<BzUserAddressDto> result = client
				.deleteById(bzUserAddressId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
