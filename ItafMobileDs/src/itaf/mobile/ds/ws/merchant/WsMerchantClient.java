package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantDto;
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
public class WsMerchantClient extends WsProcessProperty<BzMerchantDto> {

	private static final String GET_BY_ID = "getById";

	public WsMerchantClient() {
		super("/service/WsMerchantService");
	}

	public WsPageResult<BzMerchantDto> getById(Long bzConsumerId,
			Long bzMerchantId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, GET_BY_ID);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzMerchantDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzMerchantDto> result = new ArrayList<BzMerchantDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzMerchantDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
