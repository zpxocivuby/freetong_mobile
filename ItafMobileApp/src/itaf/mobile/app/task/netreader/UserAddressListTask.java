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
 * 
 * 收货地址预读任务
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月16日
 */
public class UserAddressListTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	private WsUserAddressClient client = new WsUserAddressClient();

	public UserAddressListTask(BaseActivity activity, Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_ADDRESS_LIST;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		WsPageResult<BzUserAddressDto> result = client.getList(bzConsumerId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
