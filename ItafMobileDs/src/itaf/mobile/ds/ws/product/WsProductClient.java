package itaf.mobile.ds.ws.product;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductDto;
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
public class WsProductClient extends WsProcessProperty<BzProductDto> {

	private static final String GET_BY_ID = "getById";
	private static final String CREATE_OR_UPDATE = "createOrUpdate";
	private static final String DELETE_BY_ID = "deleteById";
	private static final String PUT_PRODUCT_ON_SHELF = "putProductOnShelf";
	private static final String REMOVE_PRODUCT_FROM_SHELF = "removeProductFromShelf";

	public WsProductClient() {
		super("/service/WsProductService");
	}

	public WsPageResult<BzProductDto> getById(Long bzConsumerId,
			Long bzProductId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT, GET_BY_ID);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzProductId", bzProductId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzProductDto> createOrUpdate(BzProductDto dto) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT, CREATE_OR_UPDATE);
		paramSo.addProperty("dtoString", encodeBase64(dto));
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzProductDto> deleteById(Long bzProductId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT, DELETE_BY_ID);
		paramSo.addProperty("bzProductId", bzProductId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzProductDto> putProductOnShelf(Long bzProductId,
			Long onShelfNumber) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT,
				PUT_PRODUCT_ON_SHELF);
		paramSo.addProperty("bzProductId", bzProductId);
		paramSo.addProperty("onShelfNumber", onShelfNumber);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzProductDto> removeProductFromShelf(Long bzProductId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT,
				REMOVE_PRODUCT_FROM_SHELF);
		paramSo.addProperty("bzProductId", bzProductId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzProductDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzProductDto> result = new ArrayList<BzProductDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzProductDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
