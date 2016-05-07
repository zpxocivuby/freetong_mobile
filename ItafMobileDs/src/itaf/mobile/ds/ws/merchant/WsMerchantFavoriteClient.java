package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzMerchantFavoriteDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 
 * 商家收藏
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsMerchantFavoriteClient extends
		WsProcessProperty<BzMerchantFavoriteDto> {

	private static final String MERCHANT_FAVORITE = "merchantFavorite";
	private static final String FIND_PAGER = "findPager";
	private static final String CANCEL_MERCHANT_FAVORITE = "cancelMerchantFavorite";

	public WsMerchantFavoriteClient() {
		super("/service/WsMerchantFavoriteService");
	}

	public WsPageResult<BzMerchantFavoriteDto> merchantFavorite(
			Long bzConsumerId, Long bzMerchantId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				MERCHANT_FAVORITE);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		return callSoapMethod(paramSo, 0);
	}

	public WsPageResult<BzMerchantFavoriteDto> findPager(Long bzConsumerId,
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, FIND_PAGER);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	public WsPageResult<BzMerchantFavoriteDto> cancelMerchantFavorite(
			Long bzConsumerId, Long bzMerchantId) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT,
				CANCEL_MERCHANT_FAVORITE);
		paramSo.addProperty("bzConsumerId", bzConsumerId);
		paramSo.addProperty("bzMerchantId", bzMerchantId);
		return callSoapMethod(paramSo, 0);
	}

	@Override
	protected List<BzMerchantFavoriteDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzMerchantFavoriteDto> result = new ArrayList<BzMerchantFavoriteDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processBzMerchantFavoriteDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
