package itaf.mobile.ds.ws.product;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.product.dto.BzProductCategoryDto;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 商品品类
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月10日
 */
public class WsProductCategoryClient extends WsProcessProperty<BzProductCategoryDto> {

	private static final String SYNC_PAGER2_CLIENT = "syncPager2Client";

	public WsProductCategoryClient() {
		super("/service/WsSyncProductCategoryService");
	}

	public WsPageResult<BzProductCategoryDto> syncPager2Client(
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PRODUCT,
				SYNC_PAGER2_CLIENT);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzProductCategoryDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzProductCategoryDto> result = new ArrayList<BzProductCategoryDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(getBzProductCategoryDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

	private BzProductCategoryDto getBzProductCategoryDto(SoapObject soapObj) {
		BzProductCategoryDto dto = new BzProductCategoryDto();
		dto.setId(getProperty2Long(soapObj, "id"));
		dto.setCategoryName(getProperty2String(soapObj, "categoryName"));
		dto.setCategoryCode(getProperty2String(soapObj, "categoryCode"));
		dto.setParentId(getProperty2Long(soapObj, "parentId"));
		dto.setIsLeaf(getProperty2Boolean(soapObj, "isLeaf"));
		dto.setOrderNo(getProperty2Long(soapObj, "orderNo"));
		dto.setMarkForDelete(getProperty2Boolean(soapObj, "markForDelete"));
		dto.setCreatedBy(getProperty2Long(soapObj, "createdBy"));
		dto.setCreatedDate(getProperty2Date(soapObj, "createdDate",
				DateUtil.FORMAT_DATETIME_DEFAULT));
		dto.setUpdatedBy(getProperty2Long(soapObj, "updatedBy"));
		dto.setUpdatedDate(getProperty2Date(soapObj, "updatedDate",
				DateUtil.FORMAT_DATETIME_DEFAULT));
		return dto;
	}
}
