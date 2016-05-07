package itaf.mobile.ds.ws.consumer;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.consumer.dto.BzUserAddressDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 收货地址WS客户端
 *
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年9月24日
 */
public class WsUserAddressClient extends WsProcessProperty<BzUserAddressDto> {

	private static final String GET_LIST = "getList";
	private static final String GET_BY_ID = "getById";
	private static final String SAVE_OR_UPDATE = "saveOrUpdate";
	private static final String DELETE_BY_ID = "deleteById";

	public WsUserAddressClient() {
		super("/service/WsUserAddressService");
	}

	public WsPageResult<BzUserAddressDto> getList(Long bzConsumerId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CONSUMER, GET_LIST);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzUserAddressDto> getById(Long bzUserAddressId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CONSUMER, GET_BY_ID);
		paramSo.addProperty("bzUserAddressId", bzUserAddressId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzUserAddressDto> saveOrUpdate(BzUserAddressDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CONSUMER, SAVE_OR_UPDATE);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzUserAddressDto> deleteById(Long bzUserAddressId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CONSUMER, DELETE_BY_ID);
		paramSo.addProperty("bzUserAddressId", bzUserAddressId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzUserAddressDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzUserAddressDto> result = new ArrayList<BzUserAddressDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzUserAddressDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}