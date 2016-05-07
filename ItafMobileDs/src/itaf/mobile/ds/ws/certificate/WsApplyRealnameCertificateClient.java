package itaf.mobile.ds.ws.certificate;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.certificate.dto.BzApplyRealnameCertificateDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 实名认证服务
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsApplyRealnameCertificateClient extends
		WsProcessProperty<BzApplyRealnameCertificateDto> {

	private static final String SUBMIT_APPLY = "submitApply";
	private static final String GET_BY_BZ_CONSUMER_ID = "getByBzConsumerId";

	public WsApplyRealnameCertificateClient() {
		super("/service/WsApplyRealnameCertificateService");
	}

	public WsPageResult<BzApplyRealnameCertificateDto> submitApply(
			BzApplyRealnameCertificateDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CERTIFICATE, SUBMIT_APPLY);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzApplyRealnameCertificateDto> getByBzConsumerId(
			Long bzConsumerId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CERTIFICATE,
				GET_BY_BZ_CONSUMER_ID);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzApplyRealnameCertificateDto> processContent(
			SoapObject target, int contentWhat) {
		List<BzApplyRealnameCertificateDto> result = new ArrayList<BzApplyRealnameCertificateDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzApplyRealnameCertificateDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
