package itaf.mobile.app.services;

import itaf.mobile.app.task.PreReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.core.exception.AppException;
import itaf.mobile.core.exception.ClientException;

import java.util.ArrayList;
import java.util.List;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * 任务管理类：管理任务的添加，执行，删除
 * 
 * @see BaseActivity#SERVICE_NAME 该类的路径或者类型的修改都需要更改BaseActivity中的SERVICE_NAME路径
 * 
 * 
 * @author
 * 
 */
public class TaskService extends Service implements Runnable {

	public static List<Task> allTask = new ArrayList<Task>();
	// 执行下个线程间隔毫秒数
	public static final long SLEEP_TIME = 1000;

	public boolean isRun = true;

	// 启动线程
	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(this).start();
	}

	@Override
	public void run() {
		while (isRun) {
			if (allTask.size() > 0) {
				Task runTask = allTask.get(0);
				allTask.remove(runTask);
				MutilTaskRunThread thread = new MutilTaskRunThread();
				thread.setCurrentRunTask(runTask);
				thread.start();
			}
			try {
				Thread.sleep(SLEEP_TIME);
			} catch (Exception e) {
				e.printStackTrace();
				Log.e(this.getClass().getName(),
						"Task执行sleep()异常：" + e.getMessage());
			}
		}
	}

	// 添加任务
	public static void addTask(Task task) {
		allTask.add(task);
	}

	// 添加任务到顶部，优先执行
	public static void addTopTask(Task task) {
		allTask.add(0, task);
	}

	// 执行任务
	public void doTask(Task task) {
		try {
			if (task.getState() == Task.State.NONE) {
				return;
			}

			if (task.getState() == Task.State.NEW) {// 启动任务
				task.start();
			}

			if (task.getState() == Task.State.EXCEPTION) {// 异常，需要进行处理
				if (!task.isWriteTask()) {
					task.callback();
				}
			} else if (task.getState() == Task.State.BLOCKED) { // 网络堵塞
				if (!task.isWriteTask()) {
					task.setMsgObj(Task.ERROR_TYPE_BLOCKED);
					task.callback();
				}
			} else if (task.getState() == Task.State.NO_LOGIN) { // 没有登录
				if (!task.isWriteTask()) {
					task.setMsgObj(Task.ERROR_TYPE_NO_LOGIN);
					task.callback();
				}
			} else if (task.getState() == Task.State.WAITING) { // 任务等待
				if (task.isNeedWriteTask()) {
					addTopTask(((PreReaderTask) task).createWriteTask(40));
					task.setState(Task.State.DATA_WAITING);
				}
			} else if (task.getState() == Task.State.COMPLETED) { // 任务完成
				if (task.isWriteTask()) {
					Task readTask = task.getParentTask();
					// 读任务是否完成了数据的读写
					if (readTask != null
							&& readTask.getState() == Task.State.DATA_WAITING) {
						PreReaderTask preReaderTask = (PreReaderTask) readTask;
						preReaderTask.loadMsgObj();
						preReaderTask.callback();
					}
				} else {
					// read任务，直接发送消息刷新界面并添加write任务
					task.callback();
					if (task.isNeedWriteTask()) {
						addTask(((PreReaderTask) task).createWriteTask(20));
					}
				}
			}
		} catch (ClientException ce) {
			ce.printStackTrace();
			Log.e(this.getClass().getName(),
					"Task执行doTask()异常：msg[" + ce.getMessage() + "],cause["
							+ ce.getCause() + "]");
			task.setMsgObj(Task.ERROR_TYPE_CLIENT_EXCEPTION + ","
					+ ce.getMessage());
			task.setState(Task.State.EXCEPTION);
			task.callback();
		} catch (AppException ae) {
			ae.printStackTrace();
			Log.e(this.getClass().getName(),
					"Task执行doTask()Task.State.COMPLETED异常：" + ae.getMessage());
			task.setMsgObj(Task.ERROR_TYPE_APP_EXCEPTION);
			task.setState(Task.State.EXCEPTION);
			task.callback();
		} catch (Exception e) {
			e.printStackTrace();
			Log.e(this.getClass().getName(),
					"Task执行doTask()Task.State.COMPLETED异常：" + e.getMessage());
			task.setMsgObj(Task.ERROR_TYPE_EXCEPTION);
			task.setState(Task.State.EXCEPTION);
			task.callback();
		}
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}

	@Override
	public void onDestroy() {
		isRun = false;
		super.onDestroy();
	}

	class MutilTaskRunThread extends Thread {

		private Task currentRunTask;

		public Task getCurrentRunTask() {
			return currentRunTask;
		}

		public void setCurrentRunTask(Task currentRunTask) {
			this.currentRunTask = currentRunTask;
		}

		public void run() {
			if (currentRunTask != null) {
				doTask(currentRunTask);
			}
		}
	}
}
