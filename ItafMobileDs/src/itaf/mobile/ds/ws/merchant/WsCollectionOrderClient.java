package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzCollectionOrderDto;
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
public class WsCollectionOrderClient extends
		WsProcessProperty<BzCollectionOrderDto> {

	private static final String METHOD_NAME = "confirmCollection";

	public WsCollectionOrderClient() {
		super("/service/WsCollectionOrderService");
	}

	public WsPageResult<BzCollectionOrderDto> confirmCollection(
			Long bzCollectionOrderId, BigDecimal actualAmount) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, METHOD_NAME);
		paramSo.addProperty("bzCollectionOrderId", bzCollectionOrderId);
		paramSo.addProperty("actualAmountString", actualAmount.toString());
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzCollectionOrderDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzCollectionOrderDto> result = new ArrayList<BzCollectionOrderDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzCollectionOrderDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
