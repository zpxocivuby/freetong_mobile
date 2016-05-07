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
 * 
 * 发货地址预读任务
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月16日
 */
public class UserDeliveryAddressListTask extends ReaderTask {

	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";

	private WsUserDeliveryAddressClient client = new WsUserDeliveryAddressClient();

	public UserDeliveryAddressListTask(BaseActivity activity,
			Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_USER_DELIVERY_ADDRESS_LIST;
	}

	@Override
	public void loadMsgObj() {
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		WsPageResult<BzUserDeliveryAddressDto> result = client
				.getList(bzMerchantId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
