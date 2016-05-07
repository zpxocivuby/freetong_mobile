package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.order.WsOrderClient;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 调用支付宝接口
 * 
 * @author
 * 
 */
public class OrderAlipayTask extends ReaderTask {

	public static final String TP_ORDER_ID = "orderId";
	public static final String TP_PAY_AMOUNT = "payAmount";
	public static final String TP_ALIPAY_USERNAME = "alipayUsername";

	private WsOrderClient client = new WsOrderClient();

	public OrderAlipayTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_ORDER_PAGER;
	}

	@Override
	public void loadMsgObj() {
		Long orderId = (Long) this.getParams().get(TP_ORDER_ID);
		BigDecimal payAmount = (BigDecimal) this.getParams().get(TP_PAY_AMOUNT);
		String alipayUsername = (String) this.getParams().get(
				TP_ALIPAY_USERNAME);
		WsPageResult<BzOrderDto> result = client.orderAlipay(orderId,
				payAmount, alipayUsername);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
