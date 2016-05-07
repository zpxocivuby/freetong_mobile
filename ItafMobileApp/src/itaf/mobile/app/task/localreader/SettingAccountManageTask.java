package itaf.mobile.app.task.localreader;

import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.constant.AppConstants;
import itaf.mobile.ds.db.mobile.LoginHistoryRecordDb;

import java.util.Map;

/**
 * 账号管理
 * 
 * 
 * @author
 * 
 * @update 2013年11月13日
 */
public class SettingAccountManageTask extends ReaderTask {

	public SettingAccountManageTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_SETTING_ACCOUNT_MANAGE;
	}

	@Override
	public void loadMsgObj() {
		Long auto = (Long) this.getParams().get("auto");
		String userCode = (String) this.getParams().get("userCode");
		LoginHistoryRecordDb dao = new LoginHistoryRecordDb(getActivity());
		dao.updateAutoLogin(userCode, auto);
		this.setMsgObj(AppConstants.SUCCESS);
		this.setState(Task.State.COMPLETED);
	}
}
