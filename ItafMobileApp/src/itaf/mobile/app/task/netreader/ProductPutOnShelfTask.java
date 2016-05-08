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
 * 商品添加任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class ProductPutOnShelfTask extends ReaderTask {

	public static final String TP_ID = "id";

	public static final String TP_ON_SHELF_NUMBER = "onShelfNumber";

	private WsProductClient client = new WsProductClient();

	public ProductPutOnShelfTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_PUT_ON_SHELF;
	}

	@Override
	public void loadMsgObj() {
		Long id = (Long) this.getParams().get(TP_ID);
		Long onShelfNumber = (Long) this.getParams().get(TP_ON_SHELF_NUMBER);
		WsPageResult<BzProductDto> result = client.putProductOnShelf(id,
				onShelfNumber);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}