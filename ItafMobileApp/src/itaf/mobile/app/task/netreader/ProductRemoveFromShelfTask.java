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
public class ProductRemoveFromShelfTask extends ReaderTask {

	public static final String TP_BZ_PRODUCT_ID = "bzProductId";

	private WsProductClient client = new WsProductClient();

	public ProductRemoveFromShelfTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_REMOVE_FROM_SHELF;
	}

	@Override
	public void loadMsgObj() {
		Long bzProductId = (Long) this.getParams().get(TP_BZ_PRODUCT_ID);
		WsPageResult<BzProductDto> result = client
				.removeProductFromShelf(bzProductId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
