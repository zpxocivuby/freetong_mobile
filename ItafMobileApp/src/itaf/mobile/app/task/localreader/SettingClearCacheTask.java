package itaf.mobile.app.task.localreader;

import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.app.util.FileHelper;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.ds.db.mobile.ClearCacheDb;

import java.util.Map;

/**
 * 按照关键字进行搜索
 * 
 * @author
 * 
 * @update 2013年8月27日
 */
public class SettingClearCacheTask extends ReaderTask {

	public SettingClearCacheTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_CLEAR_CACHE;
	}

	@Override
	public void loadMsgObj() {
		String userCode = (String) this.getParams().get("userCode");
		ClearCacheDb dao = new ClearCacheDb(getActivity());
		dao.clearCacheByCurrUsername(userCode);
		FileHelper.deleteByPath(FileHelper.getAppCacheDir(this.getActivity()));
		this.setMsgObj(AppConstants.SUCCESS);
		this.setState(Task.State.COMPLETED);
	}

}
