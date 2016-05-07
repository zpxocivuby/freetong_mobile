package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductFavoriteDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.product.WsProductFavoriteClient;

import java.util.Map;

/**
 * 商品收藏任务
 * 
 * @author
 * 
 */
public class ProductFavoriteCancelTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	public static final String TP_BZ_PRODUCT_ID = "bzProductId";

	private WsProductFavoriteClient client = new WsProductFavoriteClient();

	public ProductFavoriteCancelTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_FAVORITE_CANCEL;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzProductId = (Long) this.getParams().get(TP_BZ_PRODUCT_ID);
		WsPageResult<BzProductFavoriteDto> result = client
				.cancelProductFavorite(bzConsumerId, bzProductId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
