package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyDistCertificateDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.certificate.WsApplyDistCertificateClient;

import java.util.Map;

/**
 * 申请配送服务任务
 * 
 * @author
 * 
 */
public class ApplyDistCertificateTask extends ReaderTask {

	public static final String TP_BZ_APPLY_DIST_CERTIFICATE_DTO = "BzApplyDistCertificateDto";

	private WsApplyDistCertificateClient client = new WsApplyDistCertificateClient();

	public ApplyDistCertificateTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_APPLY_DIST_SERVICE;
	}

	@Override
	public void loadMsgObj() {
		BzApplyDistCertificateDto dto = (BzApplyDistCertificateDto) this
				.getParams().get(TP_BZ_APPLY_DIST_CERTIFICATE_DTO);
		WsPageResult<BzApplyDistCertificateDto> result = client
				.submitApply(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
