package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzStockOrderDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 生成备货单
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsMerchantStockOrderClient extends
		WsProcessProperty<BzStockOrderDto> {

	private static final String CREATE_STOCK_ORDER = "createStockOrder";
	private static final String GET_LIST = "getList";
	private static final String FINISHED_STOCK_ORDER = "finishedStockOrder";

	public WsMerchantStockOrderClient() {
		super("/service/WsStockOrderService");
	}

	public WsPageResult<BzStockOrderDto> createStockOrder(Long bzMerchantId,
			String bzOrderItemIds) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				CREATE_STOCK_ORDER);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		paramSo.addProperty("bzOrderItemIds", bzOrderItemIds);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzStockOrderDto> getList(Long bzMerchantId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, GET_LIST);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzStockOrderDto> finishedStockOrder(Long bzStockOrderId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				FINISHED_STOCK_ORDER);
		paramSo.addProperty("bzStockOrderId", bzStockOrderId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzStockOrderDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzStockOrderDto> result = new ArrayList<BzStockOrderDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzStockOrderDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
