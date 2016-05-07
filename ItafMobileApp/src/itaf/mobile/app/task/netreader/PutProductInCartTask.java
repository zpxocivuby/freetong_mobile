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
 * 添加商品到购物车
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class PutProductInCartTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";
	public static final String TP_BZ_PRODUCT_ID = "bzProductId";
	public static final String TP_PUT_TYPE = "putType";

	public static final int PUT_TYPE_CART = 1;
	public static final int PUT_TYPE_BUY = 2;

	WsCartItemClient client = new WsCartItemClient();

	public PutProductInCartTask(BaseActivity activity,
			Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PUT_PRODUCT_IN_CART;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		Long bzProductId = (Long) this.getParams().get(TP_BZ_PRODUCT_ID);
		Integer putType = (Integer) this.getParams().get(TP_PUT_TYPE);
		WsPageResult<BzCartItemDto> result = client.putProductInCart(
				bzConsumerId, bzProductId, putType);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
