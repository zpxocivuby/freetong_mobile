package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.platform.WsSysLoginClient;

import java.util.Map;

/**
 * 登陆任务
 * 
 * @author
 * 
 */
public class SysLoginTask extends ReaderTask {

	public static final String TP_USERNAME = "username";
	public static final String TP_PASSWORD = "password";
	public static final String TP_MOBILE = "mobile";

	private WsSysLoginClient client = new WsSysLoginClient();

	public SysLoginTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_LOGIN;
	}

	@Override
	public void loadMsgObj() {
		String username = (String) this.getParams().get(TP_USERNAME);
		String password = (String) this.getParams().get(TP_PASSWORD);
		String mobile = (String) this.getParams().get(TP_MOBILE);
		WsPageResult<SysUserDto> result = client.login(username.trim(),
				password, mobile);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
