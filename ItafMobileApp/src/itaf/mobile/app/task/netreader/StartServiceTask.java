package itaf.mobile.app.task.netreader;

import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.app.AppServiceManager;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;

import java.util.Map;

import android.util.Log;

/**
 * 启动Service的Task
 * 
 * 
 * @author
 * 
 * @update 2013年12月13日
 */
public class StartServiceTask extends ReaderTask {

	public StartServiceTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_START_SERVICE;
	}

	@Override
	public void loadMsgObj() {
		try {
			AppServiceManager.getInstance().startOneService(
					this.getActivity().getApplicationContext(),
					AppServiceManager.SERVICE_AUTO_SYNCHRONIZE);
			this.setMsgObj(AppConstants.SUCCESS);
			this.setState(Task.State.COMPLETED);
		} catch (Exception e) {
			Log.e(this.getClass().getName(),
					"StartServiceTask执行loadMsgObj()异常：" + e.getMessage());
			this.setMsgObj(AppConstants.ERROR);
			this.setState(Task.State.COMPLETED);
		}

	}
}
