package itaf.mobile.ds.ws.order;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.order.dto.BzOrderInitDto;
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
public class WsOrderInitClient extends WsProcessProperty<BzOrderInitDto> {

	private static final String ORDER_CREATE_INIT = "orderCreateInit";

	public WsOrderInitClient() {
		super("/service/WsOrderInitService");
	}

	public WsPageResult<BzOrderInitDto> orderCreateInit(Long userId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_ORDER, ORDER_CREATE_INIT);
		paramSo.addProperty("userId", userId);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzOrderInitDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzOrderInitDto> result = new ArrayList<BzOrderInitDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					 SoapObject soapObj = (SoapObject) propertyObj;
					 result.add(processBzOrderInitDto(soapObj));
					 
				}
			}
			break;
		}
		return result;
	}

	
}
