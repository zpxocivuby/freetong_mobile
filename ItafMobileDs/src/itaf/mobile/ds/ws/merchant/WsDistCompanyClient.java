package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistCompanyDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.HashMap;
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
public class WsDistCompanyClient extends WsProcessProperty<BzDistCompanyDto> {

	private static final String SEARCH_DIST_COMPANYS = "searchDistCompanys";

	public WsDistCompanyClient() {
		super("/service/WsSearchService");
	}

	public WsPageResult<BzDistCompanyDto> searchDistCompanys(
			HashMap<String, Object> queryMap, int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_SEARCH,
				SEARCH_DIST_COMPANYS);
		paramSo.addProperty("queryMapString", encodeBase64(queryMap));
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzDistCompanyDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzDistCompanyDto> result = new ArrayList<BzDistCompanyDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzDistCompanyDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
