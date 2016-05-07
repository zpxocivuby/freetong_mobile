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
 * 收款任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class MenuCartListTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	WsCartItemClient client = new WsCartItemClient();

	public MenuCartListTask(BaseActivity activity, Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MENU_CART_LIST;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		WsPageResult<BzCartItemDto> result = client.findList(bzConsumerId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
