package itaf.mobile.ds.ws.search;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 搜索
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsSearchProductClient extends WsProcessProperty<BzProductDto> {

	private static final String SEARCH_PRODUCTS = "searchProducts";

	public WsSearchProductClient() {
		super("/service/WsSearchService");
	}

	public WsPageResult<BzProductDto> searchProducts(
			HashMap<String, Object> queryMap, int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_SEARCH, SEARCH_PRODUCTS);
		paramSo.addProperty("queryMapString", encodeBase64(queryMap));
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzProductDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzProductDto> result = new ArrayList<BzProductDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzProductDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
