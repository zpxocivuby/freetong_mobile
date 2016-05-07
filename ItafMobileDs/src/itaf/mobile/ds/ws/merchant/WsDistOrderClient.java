package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistOrderDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 配送订单列表
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsDistOrderClient extends WsProcessProperty<BzDistOrderDto> {

	private static final String FIND_PAGER = "findPager";
	private static final String START_DIST = "startDist";

	public WsDistOrderClient() {
		super("/service/WsDistOrderService");
	}

	public WsPageResult<BzDistOrderDto> findPager(Map<String, Object> queryMap,
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, FIND_PAGER);
		paramSo.addProperty("queryMapString", encodeBase64(queryMap));
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistOrderDto> startDist(Long bzDistOrderId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, START_DIST);
		paramSo.addProperty("bzDistOrderId", bzDistOrderId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzDistOrderDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzDistOrderDto> result = new ArrayList<BzDistOrderDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzDistOrderDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
