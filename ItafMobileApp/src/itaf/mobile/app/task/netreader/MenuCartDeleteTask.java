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
 * 删除购物车商品任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class MenuCartDeleteTask extends ReaderTask {

	public static final String TP_BZ_CART_ITEM_IDS = "bzCartItemIds";

	WsCartItemClient client = new WsCartItemClient();

	public MenuCartDeleteTask(BaseActivity activity, Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MENU_CART_DELETE;
	}

	@Override
	public void loadMsgObj() {
		String bzCartItemIds = (String) this.getParams().get(
				TP_BZ_CART_ITEM_IDS);
		WsPageResult<BzCartItemDto> result = client.deleteByIds(bzCartItemIds);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
