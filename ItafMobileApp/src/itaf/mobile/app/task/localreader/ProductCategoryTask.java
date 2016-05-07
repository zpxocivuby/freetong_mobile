package itaf.mobile.app.task.localreader;

import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.db.mobile.ProductCategoryDb;

import java.util.List;
import java.util.Map;

public class ProductCategoryTask extends ReaderTask {

	public static final String TP_PID = "pid";

	private ProductCategoryDb db = new ProductCategoryDb(getActivity());

	public ProductCategoryTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_PRODUCT_CATEGORY;
	}

	@Override
	public void loadMsgObj() {
		Long pid = (Long) this.getParams().get(TP_PID);
		List<BzProductCategoryDto> result = db.findByPid(pid);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}