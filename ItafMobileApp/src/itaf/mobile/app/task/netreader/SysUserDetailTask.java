package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.platform.WsSysUserClient;

import java.util.Map;

/**
 * 调用支付宝接口
 * 
 * @author
 * 
 */
public class SysUserDetailTask extends ReaderTask {

	public static final String TP_SYS_USER_ID = "sysUserId";

	private WsSysUserClient client = new WsSysUserClient();

	public SysUserDetailTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_SYS_USER_DETAIL;
	}

	@Override
	public void loadMsgObj() {
		Long sysUserId = (Long) this.getParams().get(TP_SYS_USER_ID);
		WsPageResult<SysUserDto> result = client.getById(sysUserId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
