package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.platform.WsSysRegisterClient;

import java.util.Map;

/**
 * 系统注册任务
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月9日
 */
public class SysRegisterTask extends ReaderTask {

	public static final String TP_USERNAME = "username";
	public static final String TP_PASSWORD = "password";
	public static final String TP_MOBILE = "mobile";

	private WsSysRegisterClient client = new WsSysRegisterClient();

	public SysRegisterTask(BaseActivity activity, Map<String, Object> params) {
		super(activity, getTaskId(), params);
	}

	public static int getTaskId() {
		return TaskIds.TASK_SYS_REGISTER;
	}

	@Override
	public void loadMsgObj() {
		String username = (String) this.getParams().get(TP_USERNAME);
		String password = (String) this.getParams().get(TP_PASSWORD);
		String mobile = (String) this.getParams().get(TP_MOBILE);
		WsPageResult<SysUserDto> userInfo = client.register(username, password,
				mobile);
		this.setMsgObj(userInfo);
		this.setState(Task.State.COMPLETED);
	}

}
