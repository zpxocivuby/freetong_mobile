package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzDistStatementSummaryDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

public class WsDistStatementSummaryClient extends
		WsProcessProperty<BzDistStatementSummaryDto> {

	private static final String GET_BZ_DIST_STATEMENT_SUMMARY_DTO = "getBzDistStatementSummaryDto";

	public WsDistStatementSummaryClient() {
		super("/service/WsDistStatementService");
	}

	public WsPageResult<BzDistStatementSummaryDto> getBzDistStatementSummaryDto(
			Long bzDistCompanyId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				GET_BZ_DIST_STATEMENT_SUMMARY_DTO);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzDistStatementSummaryDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzDistStatementSummaryDto> result = new ArrayList<BzDistStatementSummaryDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzDistStatementSummaryDto(soapObj));
				}
			}
			break;
		}
		return result;
	}
}