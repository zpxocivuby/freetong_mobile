package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplySellingCertificateDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.certificate.WsApplySellingCertificateClient;

import java.util.Map;

/**
 * 申请售卖服务任务
 * 
 * @author
 * 
 */
public class ApplySellingCertificateTask extends ReaderTask {

	public static final String TP_BZ_APPLY_SELLING_CERTIFICATE_DTO = "BzApplySellingCertificateDto";

	private WsApplySellingCertificateClient client = new WsApplySellingCertificateClient();

	public ApplySellingCertificateTask(BaseActivity activity,
			Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_APPLY_SELLING_SERVICE;
	}

	@Override
	public void loadMsgObj() {
		BzApplySellingCertificateDto dto = (BzApplySellingCertificateDto) this
				.getParams().get(TP_BZ_APPLY_SELLING_CERTIFICATE_DTO);
		WsPageResult<BzApplySellingCertificateDto> result = client
				.submitApply(dto);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}

}