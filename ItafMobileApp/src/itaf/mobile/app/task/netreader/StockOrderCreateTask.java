package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzStockOrderDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsMerchantStockOrderClient;

import java.util.Map;

/**
 * 生成备货单任务
 * 
 * @author
 * 
 */
public class StockOrderCreateTask extends ReaderTask {

	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";

	public static final String TP_BZ_ORDER_ITEM_IDS = "bzOrderItemIds";

	private WsMerchantStockOrderClient client = new WsMerchantStockOrderClient();

	public StockOrderCreateTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_STOCK_ORDER_CREATE;
	}

	@Override
	public void loadMsgObj() {
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		String bzOrderItemIds = (String) this.getParams().get(
				TP_BZ_ORDER_ITEM_IDS);
		WsPageResult<BzStockOrderDto> result = client.createStockOrder(
				bzMerchantId, bzOrderItemIds);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
