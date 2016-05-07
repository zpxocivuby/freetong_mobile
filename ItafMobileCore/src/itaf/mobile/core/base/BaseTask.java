package itaf.mobile.core.base;

import java.util.Map;

public class BaseTask {

	private int taskId;
	private Map<String, Object> params;
	public static final int TASK_LOGIN = 1;
	public static final int TASK_LOGOUT = 0;

	public BaseTask() {
		super();
	}

	public BaseTask(int taskId, Map<String, Object> params) {
		super();
		this.taskId = taskId;
		this.params = params;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}
}
