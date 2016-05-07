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
 * 获取备货单列表
 * 
 * @author
 * 
 */
public class StockOrderListTask extends ReaderTask {

	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";

	private WsMerchantStockOrderClient client = new WsMerchantStockOrderClient();

	public StockOrderListTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_STOCK_ORDER_LIST;
	}

	@Override
	public void loadMsgObj() {
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		WsPageResult<BzStockOrderDto> result = client.getList(bzMerchantId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
