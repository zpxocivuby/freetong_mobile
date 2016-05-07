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
 * 收货地址添加或者修改任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class UserAddressCreateOrEditTask extends ReaderTask {

	public static final String TP_BZ_USER_ADDRESS_DTO = "BzUserAddressDto";

	private WsUserAddressClient client = new WsUserAddressClient();

	public UserAddressCreateOrEditTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_ADDRESS_CREATE_OR_EDIT;
	}

	@Override
	public void loadMsgObj() {
		BzUserAddressDto dto = (BzUserAddressDto) this.getParams().get(
				TP_BZ_USER_ADDRESS_DTO);
		WsPageResult<BzUserAddressDto> result = client.saveOrUpdate(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
