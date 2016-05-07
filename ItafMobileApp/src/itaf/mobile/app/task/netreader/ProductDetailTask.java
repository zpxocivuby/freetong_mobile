package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.product.WsProductClient;

import java.util.Map;

/**
 * 商家详情任务
 * 
 * @author
 * 
 */
public class ProductDetailTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	public static final String TP_BZ_PRODUCT_ID = "bzProductId";

	private WsProductClient client = new WsProductClient();

	public ProductDetailTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_DETAIL;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzProductId = (Long) this.getParams().get(TP_BZ_PRODUCT_ID);
		WsPageResult<BzProductDto> result = client.getById(bzConsumerId,
				bzProductId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
