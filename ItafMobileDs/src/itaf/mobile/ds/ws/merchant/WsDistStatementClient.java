package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementDto;
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
public class WsDistStatementClient extends
		WsProcessProperty<BzDistStatementDto> {

	private static final String FIND_PAGER = "findPager";
	private static final String GET_BY_ID = "getById";
	private static final String START_DIST_STATEMENT = "startDistStatement";
	private static final String ACCEPT_DIST_STATEMENT = "acceptDistStatement";
	private static final String REJECT_DIST_STATEMENT = "rejectDistStatement";

	public WsDistStatementClient() {
		super("/service/WsDistStatementService");
	}

	public WsPageResult<BzDistStatementDto> getById(Long bzDistStatementId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, GET_BY_ID);
		paramSo.addProperty("bzDistStatementId", bzDistStatementId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistStatementDto> startDistStatement(
			Long bzMerchantId, Long bzDistCompanyId,
			String bzDistStatementItemIds) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				START_DIST_STATEMENT);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		paramSo.addProperty("bzDistStatementItemIds", bzDistStatementItemIds);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistStatementDto> acceptDistStatement(
			Long bzDistCompanyId, Long bzDistStatementId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				ACCEPT_DIST_STATEMENT);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		paramSo.addProperty("bzDistStatementId", bzDistStatementId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistStatementDto> rejectDistStatement(
			Long bzDistCompanyId, Long bzDistStatementId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				REJECT_DIST_STATEMENT);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		paramSo.addProperty("bzDistStatementId", bzDistStatementId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzDistStatementDto> findPager(
			Map<String, Object> queryMap, int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, FIND_PAGER);
		paramSo.addProperty("queryMapString", encodeBase64(queryMap));
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzDistStatementDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzDistStatementDto> result = new ArrayList<BzDistStatementDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzDistStatementDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}
