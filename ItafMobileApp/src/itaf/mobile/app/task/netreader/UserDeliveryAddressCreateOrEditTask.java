package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.consumer.WsUserDeliveryAddressClient;

import java.util.Map;

/**
 * 发货地址添加或者修改任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class UserDeliveryAddressCreateOrEditTask extends ReaderTask {

	public static final String TP_BZ_USER_DELIVERY_ADDRESS_DTO = "BzUserDeliveryAddressDto";

	private WsUserDeliveryAddressClient client = new WsUserDeliveryAddressClient();

	public UserDeliveryAddressCreateOrEditTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_DELIVERY_ADDRESS_CREATE_OR_EDIT;
	}

	@Override
	public void loadMsgObj() {
		BzUserDeliveryAddressDto dto = (BzUserDeliveryAddressDto) this
				.getParams().get(TP_BZ_USER_DELIVERY_ADDRESS_DTO);
		WsPageResult<BzUserDeliveryAddressDto> result = client
				.saveOrUpdate(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
