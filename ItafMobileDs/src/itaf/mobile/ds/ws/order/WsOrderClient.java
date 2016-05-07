package itaf.mobile.ds.ws.order;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderDto;
import itaf.framework.order.dto.BzOrderRatingDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.math.BigDecimal;
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
public class WsOrderClient extends WsProcessProperty<BzOrderDto> {

	private static final String FIND_PAGER = "findPager";
	private static final String SUBMIT_ORDER = "submitOrder";
	private static final String ORDER_ALIPAY = "orderAlipay";
	private static final String GET_BY_ID = "getById";
	private static final String CONFIRM_RECEIVED = "confirmReceived";
	private static final String ORDER_RATING = "orderRating";

	public WsOrderClient() {
		super("/service/WsOrderService");
	}

	public WsPageResult<BzOrderDto> findPager(String roleType,
			Long roleTypeValue, Long orderStatus, int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, FIND_PAGER);
		paramSo.addProperty("roleType", roleType);
		paramSo.addProperty("roleTypeValue", roleTypeValue);
		paramSo.addProperty("orderStatus", orderStatus);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzOrderDto> submitOrder(BzOrderDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, SUBMIT_ORDER);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzOrderDto> orderAlipay(Long bzOrderId,
			BigDecimal payAmount, String alipayUsername) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, ORDER_ALIPAY);
		paramSo.addProperty("bzOrderId", bzOrderId);
		paramSo.addProperty("payAmount", payAmount);
		paramSo.addProperty("alipayUsername", alipayUsername);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzOrderDto> getById(Long bzOrderId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, GET_BY_ID);
		paramSo.addProperty("bzOrderId", bzOrderId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzOrderDto> confirmReceived(Long bzOrderId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, CONFIRM_RECEIVED);
		paramSo.addProperty("bzOrderId", bzOrderId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzOrderDto> orderRating(Long bzOrderId,
			BzOrderRatingDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, ORDER_RATING);
		paramSo.addProperty("bzOrderId", bzOrderId);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzOrderDto> processContent(SoapObject target, int contentWhat) {
		List<BzOrderDto> result = new ArrayList<BzOrderDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzOrderDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
