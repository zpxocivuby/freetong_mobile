package itaf.mobile.app.task;

import itaf.mobile.core.base.BaseActivity;

import java.util.Map;

/**
 * 本地读&网络预读任务基础类
 * 
 * @author
 * 
 * @update 2013年9月2日
 */
public abstract class PreReaderTask extends Task {

	public PreReaderTask(BaseActivity activity, int msgWhat,
			Map<String, Object> params) {
		super(activity, msgWhat, params);
	}

	public abstract Task createWriteTask(int count);

}
