package itaf.mobile.app.task;

import itaf.mobile.app.ui.SysLogin;
import itaf.mobile.app.ui.base.UIRefresh;
import itaf.mobile.app.util.ToastHelper;
import itaf.mobile.app.util.UIHelper;
import itaf.mobile.core.app.AppApplication;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.utils.NetWorkHelper;

import java.lang.ref.WeakReference;
import java.util.Map;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

/**
 * //本地读:1000-1999; 本地读&网络预读:2000-2999; 网络读:3000+; 网络预读:-1
 * 
 * @author
 * 
 * @update 2013年8月30日
 */
public abstract class Task {

	public static String PAGER_PAGESIZE = "pageSize";
	public static String CURRENT_INDEX = "currentIndex";

	public static String ERROR_TYPE_BLOCKED = "error_type_blocked";
	public static String ERROR_TYPE_CLIENT_EXCEPTION = "error_type_client_exception";
	public static String ERROR_TYPE_APP_EXCEPTION = "error_type_app_exception";
	public static String ERROR_TYPE_EXCEPTION = "error_type_exception";
	public static String ERROR_TYPE_NO_LOGIN = "error_type_no_login";

	// 任务的状态
	public enum State {
		NEW, BLOCKED, WAITING, DATA_WAITING, COMPLETED, EXCEPTION, NO_LOGIN, NONE
	}

	// 父任务
	private Task parentTask;
	// taskId本地读:1000-1999; 本地读&网络预读:2000-2999; 网络读:3000+; 网络预读:-1
	protected int msgWhat;
	// 返回的数据对象
	protected Object msgObj;
	// 传入的参数信息
	private Map<String, Object> params;
	// 任务状态
	private State status = State.NEW;
	// Activity环境
	private BaseActivity activity;

	public Task(BaseActivity activity, int msgWhat, Map<String, Object> params) {
		super();
		this.activity = activity;
		this.msgWhat = msgWhat;
		this.params = params;
	}

	public void start() {
		// 检查网络
		if (isNeedNetworkConnected()
				&& !NetWorkHelper.isNetworkConnected(activity)) {
			// 需要网络且网络不通，设置可以返回
			this.status = State.BLOCKED;
			return;
		}

		if (isNeedLogin() && !AppApplication.getInstance().isLogin()) {
			this.status = State.NO_LOGIN;
			return;
		}
		loadMsgObj();
	}

	private boolean isNeedLogin() {
		return this.msgWhat >= 5000 || this.msgWhat == -1;
	}

	/**
	 * need @Override 1、数据库获取 2、网络获取 3、数据库获取且网络预取
	 * 
	 * 设置setMsgObj, status
	 */
	public abstract void loadMsgObj();

	// 发送消息
	public void callback() {
		Handler handler = new TaskHandler(this.getActivity());
		Message msg = handler.obtainMessage();
		msg.what = getMsgWhat();
		msg.obj = getMsgObj();
		handler.sendMessage(msg);
	}

	public int getMsgWhat() {
		return msgWhat;
	}

	public Object getMsgObj() {
		return this.msgObj;
	}

	public void setMsgObj(Object msgObj) {
		this.msgObj = msgObj;
	}

	public boolean isNeedNetworkConnected() {
		return this.msgWhat > 3000 || this.msgWhat == -1;
	}

	public boolean isWriteTask() {
		return this.msgWhat == -1;
	}

	public boolean isNeedWriteTask() {
		return this.msgWhat > 2000 && this.msgWhat < 3000;
	}

	public State getState() {
		return status;
	}

	public void setState(State state) {
		this.status = state;
	}

	public BaseActivity getActivity() {
		return activity;
	}

	public void setActivity(BaseActivity activity) {
		this.activity = activity;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}
}

class TaskHandler extends Handler {

	WeakReference<UIRefresh> wrUIRefresh;

	private BaseActivity baseActivity;

	public TaskHandler(BaseActivity baseActivity) {
		super(baseActivity.getMainLooper());
		this.baseActivity = baseActivity;
		wrUIRefresh = new WeakReference<UIRefresh>((UIRefresh) baseActivity);
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		Object errorTypeObj = msg.obj;
		if (errorTypeObj != null) {
			String errorType = errorTypeObj.toString();
			if (Task.ERROR_TYPE_BLOCKED.equals(errorType)) {
				msg.obj = null;
				ToastHelper.showToast(baseActivity, "当前网络不可用，请检查网络！",
						Toast.LENGTH_LONG);
			} else if (Task.ERROR_TYPE_NO_LOGIN.equals(errorType)) {
				msg.obj = null;
				ToastHelper.showToast(baseActivity, "需要登陆！");
				// 跳转到登陆页面
				Intent intent = new Intent();
				intent.setClass(baseActivity, SysLogin.class);
				baseActivity.startActivity(intent);
			} else if (Task.ERROR_TYPE_CLIENT_EXCEPTION.startsWith(errorType)) {
				msg.obj = null;
				ToastHelper.showToast(baseActivity,
						errorType.substring(errorType.indexOf(",") + 1),
						Toast.LENGTH_LONG);
			} else if (Task.ERROR_TYPE_APP_EXCEPTION.equals(errorType)) {
				msg.obj = null;
				ToastHelper.showToast(baseActivity, "服务端没有响应！",
						Toast.LENGTH_LONG);
			} else if (Task.ERROR_TYPE_EXCEPTION.equals(errorType)) {
				msg.obj = null;
				ToastHelper.showToast(baseActivity, "App出现异常，App异常退出！",
						Toast.LENGTH_LONG);
				UIHelper.clearApp(baseActivity);
			}
		}
		wrUIRefresh.get().refresh(msg.what, msg.obj);
	}
};
