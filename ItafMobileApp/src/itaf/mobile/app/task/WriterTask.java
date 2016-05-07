package itaf.mobile.app.task;

import itaf.mobile.core.base.BaseActivity;

import java.util.Map;

/**
 * 写数据库任务基础类
 * 
 * @author
 * 
 * @update 2013年9月2日
 */
public abstract class WriterTask extends Task {

	/**
	 * 写任务的构造方法
	 * 
	 * @param activity
	 *            Activity
	 * @param parentId
	 *            父Id
	 * @param params
	 *            参数
	 */
	public WriterTask(BaseActivity activity, Task parentTask,
			Map<String, Object> params) {
		super(activity, -1, params);
		this.setParentTask(parentTask);
	}

}
