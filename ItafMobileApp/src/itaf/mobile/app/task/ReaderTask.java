package itaf.mobile.app.task;

import itaf.mobile.core.base.BaseActivity;

import java.util.Map;

/**
 * 读任务基础类
 * 
 * @author
 * 
 * @update 2013年9月2日
 */
public abstract class ReaderTask extends Task {

	public ReaderTask(BaseActivity activity, int msgWhat,
			Map<String, Object> params) {
		super(activity, msgWhat, params);
	}

}
