package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyRealnameCertificateDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.certificate.WsApplyRealnameCertificateClient;

import java.util.Map;

/**
 * 申请配送服务任务
 * 
 * @author
 * 
 */
public class ApplyRealnameCertificateDetailTask extends ReaderTask {

	public static final String TP_BZ_CONSUMER_ID = "bzConsumerId";

	private WsApplyRealnameCertificateClient client = new WsApplyRealnameCertificateClient();

	public ApplyRealnameCertificateDetailTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_APPLY_REALNAME_CERTIFICATE_DETAIL;
	}

	@Override
	public void loadMsgObj() {
		Long bzConsumerId = (Long) this.getParams().get(TP_BZ_CONSUMER_ID);
		WsPageResult<BzApplyRealnameCertificateDto> result = client
				.getByBzConsumerId(bzConsumerId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
