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
public class OrderPagerTask extends ReaderTask {
	public static final String TYPE_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TYPE_BZ_MERCHANT_ID = "bzMerchantId";

	public static final String TP_ROLE_TYPE = "roleType";
	public static final String TP_ROLE_TYPE_VALUE = "roleTypeValue";
	public static final String TP_ORDER_STATUS = "orderStatus";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsOrderClient client = new WsOrderClient();

	public OrderPagerTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_PAGER;
	}

	@Override
	public void loadMsgObj() {
		String roleType = (String) this.getParams().get(TP_ROLE_TYPE);
		Long roleTypeValue = (Long) this.getParams().get(TP_ROLE_TYPE_VALUE);
		Long orderStatus = (Long) this.getParams().get(TP_ORDER_STATUS);
		int currentIndex = (Integer) this.getParams().get(TP_CURRENT_INDEX);
		int pageSize = (Integer) this.getParams().get(TP_PAGE_SIZE);
		WsPageResult<BzOrderDto> result = client.findPager(roleType,
				roleTypeValue, orderStatus, currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
