package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantFavoriteDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsMerchantFavoriteClient;

import java.util.Map;

/**
 * 商品收藏任务
 * 
 * @author
 * 
 */
public class MerchantFavoriteCancelTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TP_BZ_MERCHANT_ID = "bzMerchantId";

	private WsMerchantFavoriteClient client = new WsMerchantFavoriteClient();

	public MerchantFavoriteCancelTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MERCHANT_FAVORITE_CANCEL;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzMerchantId = (Long) this.getParams().get(TP_BZ_MERCHANT_ID);
		WsPageResult<BzMerchantFavoriteDto> result = client
				.cancelMerchantFavorite(bzConsumerId, bzMerchantId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
