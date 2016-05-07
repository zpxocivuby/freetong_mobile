package itaf.mobile.ds.ws.cart;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.cart.dto.BzCartItemDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 购物车WS客户端
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年11月17日
 */
public class WsCartItemClient extends WsProcessProperty<BzCartItemDto> {

	private static final String FIND_LIST = "findList";
	private static final String PUT_PRODUCT_IN_CART = "putProductInCart";
	private static final String DELETE_BY_IDS = "deleteByIds";
	private static final String UPDATE_ITEM_NUM = "updateItemNum";

	public WsCartItemClient() {
		super("/service/WsCartItemService");
	}

	public WsPageResult<BzCartItemDto> putProductInCart(Long bzConsumerId,
			Long bzProductId, int putType) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CART, PUT_PRODUCT_IN_CART);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzProductId", bzProductId);
		paramSo.addProperty("putType", putType);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzCartItemDto> findList(Long bzConsumerId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CART, FIND_LIST);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzCartItemDto> deleteByIds(String bzCartItemIds) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CART, DELETE_BY_IDS);
		paramSo.addProperty("bzCartItemIds", bzCartItemIds);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzCartItemDto> updateItemNum(Long bzCartItemId,
			Long itemNum) {
		SoapObject paramSo = new SoapObject(NAMESPACE_CART, UPDATE_ITEM_NUM);
		paramSo.addProperty("bzCartItemId", bzCartItemId);
		paramSo.addProperty("itemNum", itemNum);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzCartItemDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzCartItemDto> result = new ArrayList<BzCartItemDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzCartItemDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}