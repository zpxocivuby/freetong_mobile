package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderRefundDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.order.WsOrderRefundClient;

import java.util.Map;

/**
 * 订单列表任务
 * 
 * @author
 * 
 */
public class OrderRefundPagerTask extends ReaderTask {
	public static final String TYPE_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TYPE_BZ_MERCHANT_ID = "bzMerchantId";

	public static final String TP_ROLE_TYPE = "roleType";
	public static final String TP_ROLE_TYPE_VALUE = "roleTypeValue";
	public static final String TP_REFUND_STATUS = "refundStatus";
	public static final String TP_CURRENT_INDEX = "currentIndex";
	public static final String TP_PAGE_SIZE = "pageSize";

	private WsOrderRefundClient client = new WsOrderRefundClient();

	public OrderRefundPagerTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_REFUND_PAGER;
	}

	@Override
	public void loadMsgObj() {
		String roleType = (String) this.getParams().get(TP_ROLE_TYPE);
		Long roleTypeValue = (Long) this.getParams().get(TP_ROLE_TYPE_VALUE);
		Long refundStatus = (Long) this.getParams().get(TP_REFUND_STATUS);
		int currentIndex = (Integer) this.getParams().get(TP_CURRENT_INDEX);
		int pageSize = (Integer) this.getParams().get(TP_PAGE_SIZE);
		WsPageResult<BzOrderRefundDto> result = client.findPager(roleType,
				roleTypeValue, refundStatus, currentIndex, pageSize);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
