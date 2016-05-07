package itaf.mobile.ds.ws.consumer;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzUserDeliveryAddressDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 发货地址WS客户端
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月24日
 */
public class WsUserDeliveryAddressClient extends
		WsProcessProperty<BzUserDeliveryAddressDto> {

	private static final String GET_LIST = "getList";
	private static final String GET_BY_ID = "getById";
	private static final String SAVE_OR_UPDATE = "saveOrUpdate";
	private static final String DELETE_BY_ID = "deleteById";

	public WsUserDeliveryAddressClient() {
		super("/service/WsUserDeliveryAddressService");
	}

	public WsPageResult<BzUserDeliveryAddressDto> getList(Long bzMerchantId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, GET_LIST);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzUserDeliveryAddressDto> getById(
			Long bzUserDeliveryAddressId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, GET_BY_ID);
		paramSo.addProperty("bzUserDeliveryAddressId", bzUserDeliveryAddressId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzUserDeliveryAddressDto> saveOrUpdate(
			BzUserDeliveryAddressDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, SAVE_OR_UPDATE);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzUserDeliveryAddressDto> deleteById(
			Long bzUserDeliveryAddressId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, DELETE_BY_ID);
		paramSo.addProperty("bzUserDeliveryAddressId", bzUserDeliveryAddressId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzUserDeliveryAddressDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzUserDeliveryAddressDto> result = new ArrayList<BzUserDeliveryAddressDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzUserDeliveryAddressDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}