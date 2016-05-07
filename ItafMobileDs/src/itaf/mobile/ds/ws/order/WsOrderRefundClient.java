package itaf.mobile.ds.ws.order;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderRefundDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 商家分类
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsOrderRefundClient extends WsProcessProperty<BzOrderRefundDto> {

	private static final String APPLY_REFUND = "applyRefund";
	private static final String FIND_PAGER = "findPager";
	private static final String CANCEL_REFUND = "cancelRefund";
	private static final String ACCEPT_REFUND = "acceptRefund";
	private static final String REJECT_REFUND = "rejectRefund";

	public WsOrderRefundClient() {
		super("/service/WsOrderRefundService");
	}

	public WsPageResult<BzOrderRefundDto> applyRefund(Long bzConsumerId,
			Long bzOrderId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, APPLY_REFUND);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzOrderId", bzOrderId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzOrderRefundDto> findPager(String roleType,
			Long roleTypeValue, Long refundStatus, int currentIndex,
			int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, FIND_PAGER);
		paramSo.addProperty("roleType", roleType);
		paramSo.addProperty("roleTypeValue", roleTypeValue);
		paramSo.addProperty("refundStatus", refundStatus);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzOrderRefundDto> cancelRefund(Long bzConsumerId,
			Long bzOrderRefundId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, CANCEL_REFUND);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzOrderRefundId", bzOrderRefundId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzOrderRefundDto> acceptRefund(Long bzMerchantId,
			Long bzOrderRefundId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, ACCEPT_REFUND);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		paramSo.addProperty("bzOrderRefundId", bzOrderRefundId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzOrderRefundDto> rejectRefund(Long bzMerchantId,
			Long bzOrderRefundId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, REJECT_REFUND);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		paramSo.addProperty("bzOrderRefundId", bzOrderRefundId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzOrderRefundDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzOrderRefundDto> result = new ArrayList<BzOrderRefundDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzOrderRefundDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
