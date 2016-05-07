package itaf.mobile.app.task.netreader;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzInvoiceDto;
import itaf.mobile.app.task.ReaderTask;
import itaf.mobile.app.task.Task;
import itaf.mobile.app.task.TaskIds;
import itaf.mobile.core.base.BaseActivity;
import itaf.mobile.ds.ws.merchant.WsMerchantInvoiceClient;

import java.util.Map;

/**
 * 发货
 * 
 * @author
 * 
 */
public class MerchantInvoiceTask extends ReaderTask {

	public static final String TP_BZ_ORDER_ID = "bzOrderId";
	public static final String TP_BZ_DIST_COMPANY_ID = "bzDistCompanyId";

	private WsMerchantInvoiceClient client = new WsMerchantInvoiceClient();

	public MerchantInvoiceTask(BaseActivity activity, Map<String, Object> map) {
		super(activity, getTaskId(), map);
	}

	public static int getTaskId() {
		return TaskIds.TASK_MERCHANT_INVOICE;
	}

	@Override
	public void loadMsgObj() {
		Long bzOrderId = (Long) this.getParams().get(TP_BZ_ORDER_ID);
		Long bzDistCompanyId = (Long) this.getParams().get(
				TP_BZ_DIST_COMPANY_ID);
		WsPageResult<BzInvoiceDto> result = client.invoice(bzOrderId,
				bzDistCompanyId);
		this.setMsgObj(result);
		this.setState(Task.State.COMPLETED);
	}
}
