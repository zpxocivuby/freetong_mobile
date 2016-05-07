package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementItemDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 配送单
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsDistStatementItemClient extends
		WsProcessProperty<BzDistStatementItemDto> {

	private static final String FIND_PAGER = "findPager";
	private static final String START_DIST_STATEMENT = "startDistStatement";

	public WsDistStatementItemClient() {
		super("/service/WsDistStatementItemService");
	}

	public WsPageResult<BzDistStatementItemDto> findPager(
			Map<String, Object> queryMap, int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, FIND_PAGER);
		paramSo.addProperty("queryMapString", encodeBase64(queryMap));
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistStatementItemDto> startDistStatement(
			Long bzMerchantId, Long bzDistCompanyId,
			String bzDistStatementItemIds) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				START_DIST_STATEMENT);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		paramSo.addProperty("bzDistStatementItemIds", bzDistStatementItemIds);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzDistStatementItemDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzDistStatementItemDto> result = new ArrayList<BzDistStatementItemDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzDistStatementItemDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
