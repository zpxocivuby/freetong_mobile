package itaf.mobile.ds.ws.product;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductFavoriteDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 商品收藏
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsProductFavoriteClient extends
		WsProcessProperty<BzProductFavoriteDto> {

	private static final String PRODUCT_FAVORITE = "productFavorite";
	private static final String FIND_PAGER = "findPager";
	private static final String CANCEL_PRODUCT_FAVORITE = "cancelProductFavorite";

	public WsProductFavoriteClient() {
		super("/service/WsProductFavoriteService");
	}

	public WsPageResult<BzProductFavoriteDto> findPager(Long bzConsumerId,
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT, FIND_PAGER);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzProductFavoriteDto> productFavorite(
			Long bzConsumerId, Long bzProductId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT, PRODUCT_FAVORITE);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzProductId", bzProductId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzProductFavoriteDto> cancelProductFavorite(
			Long bzConsumerId, Long bzProductId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT,
				CANCEL_PRODUCT_FAVORITE);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzProductId", bzProductId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzProductFavoriteDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzProductFavoriteDto> result = new ArrayList<BzProductFavoriteDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzProductFavoriteDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
