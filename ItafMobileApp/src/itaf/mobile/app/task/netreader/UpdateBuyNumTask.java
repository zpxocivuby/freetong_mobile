package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.cart.WsCartItemClient;

import java.util.Map;

/**
 * 更新购买数量
 * 
 * @author
 * 
 */
public class UpdateBuyNumTask extends ReaderTask {

	public static final String TP_BZ_CART_ITEM_ID = "bzCartItemId";

	public static final String TP_ITEM_NUM = "itemNum";

	private WsCartItemClient client = new WsCartItemClient();

	public UpdateBuyNumTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_UPDATE_BUY_NUM;
	}

	@Override
	public void loadMsgObj() {
		Long bzCartItemId = (Long) this.getParams().get(TP_BZ_CART_ITEM_ID);
		Long itemNum = (Long) this.getParams().get(TP_ITEM_NUM);
		WsPageResult<BzCartItemDto> result = client.updateItemNum(bzCartItemId,
				itemNum);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
