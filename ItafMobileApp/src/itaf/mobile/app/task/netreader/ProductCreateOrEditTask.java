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
public class ProductCreateOrEditTask extends ReaderTask {

	public static final String TP_DTO = "dto";

	private WsProductClient client = new WsProductClient();

	public ProductCreateOrEditTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_CREATE_OR_EDIT;
	}

	@Override
	public void loadMsgObj() {
		BzProductDto dto = (BzProductDto) this.getParams().get(TP_DTO);
		WsPageResult<BzProductDto> result = client.createOrUpdate(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
