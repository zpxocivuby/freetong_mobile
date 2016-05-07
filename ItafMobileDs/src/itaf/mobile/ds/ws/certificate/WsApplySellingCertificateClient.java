package itaf.mobile.ds.ws.certificate;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplySellingCertificateDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 申请售卖服务
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsApplySellingCertificateClient extends
		WsProcessProperty<BzApplySellingCertificateDto> {

	private static final String SUBMIT_APPLY = "submitApply";
	private static final String GET_BY_BZ_CONSUMER_ID = "getByBzConsumerId";

	public WsApplySellingCertificateClient() {
		super("/service/WsApplySellingCertificateService");
	}

	public WsPageResult<BzApplySellingCertificateDto> submitApply(
			BzApplySellingCertificateDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CERTIFICATE, SUBMIT_APPLY);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzApplySellingCertificateDto> getByBzConsumerId(
			Long bzConsumerId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CERTIFICATE,
				GET_BY_BZ_CONSUMER_ID);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzApplySellingCertificateDto> processContent(
			SoapObject target, int contentWhat) {
		List<BzApplySellingCertificateDto> result = new ArrayList<BzApplySellingCertificateDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzApplySellingCertificateDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
