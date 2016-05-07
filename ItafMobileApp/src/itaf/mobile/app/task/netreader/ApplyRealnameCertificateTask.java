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
 * 实名认证任务
 * 
 * @author
 * 
 */
public class ApplyRealnameCertificateTask extends ReaderTask {

	public static final String TP_BZ_APPLY_REALNAME_CERTIFICATE_DTO = "BzApplyRealnameCertificateDto";

	private WsApplyRealnameCertificateClient client = new WsApplyRealnameCertificateClient();

	public ApplyRealnameCertificateTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_APPLY_REALNAME_CERTIFICATE;
	}

	@Override
	public void loadMsgObj() {
		BzApplyRealnameCertificateDto dto = (BzApplyRealnameCertificateDto) this
				.getParams().get(TP_BZ_APPLY_REALNAME_CERTIFICATE_DTO);
		WsPageResult<BzApplyRealnameCertificateDto> result = client
				.submitApply(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}
