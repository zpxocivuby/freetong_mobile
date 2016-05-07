package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzInvoiceDto;
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
public class WsMerchantInvoiceClient extends WsProcessProperty<BzInvoiceDto> {

	private static final String INVOICE = "invoice";
	private static final String FIND_PAGER = "findPager";

	public WsMerchantInvoiceClient() {
		super("/service/WsInvoiceService");
	}

	public WsPageResult<BzInvoiceDto> invoice(Long bzOrderId, Long bzDistCompanyId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, INVOICE);
		paramSo.addProperty("bzOrderId", bzOrderId);
		paramSo.addProperty("bzDistCompanyId", bzDistCompanyId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzInvoiceDto> findPager(String distCompanyName,
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, FIND_PAGER);
		paramSo.addProperty("distCompanyName", distCompanyName);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzInvoiceDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzInvoiceDto> result = new ArrayList<BzInvoiceDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					// SoapObject soapObj = (SoapObject) propertyObj;
				}
			}
			break;
		}
		return result;
	}
}
