package itaf.mobile.ds.ws.merchant;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.merchant.dto.BzServiceProviderTypeDto;
import itaf.mobile.core.utils.DateUtil;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 同步服务类型
 *
 * @author XINXIN
 *
 * @UpdateDate 2014年11月3日
 */
public class WsServiceProviderTypeClient extends
		WsProcessProperty<BzServiceProviderTypeDto> {

	private static final String METHOD_NAME = "syncPager2Client";

	public WsServiceProviderTypeClient() {
		super("/service/WsSyncServiceProviderTypeService");
	}

	public WsPageResult<BzServiceProviderTypeDto> syncPager2Client(
			int currentIndex, int pageSize) {
		SoapObject paramSo = new SoapObject(NAMESPACE_MERCHANT, METHOD_NAME);
		paramSo.addProperty("currentIndex", currentIndex);
		paramSo.addProperty("pageSize", pageSize);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<BzServiceProviderTypeDto> processContent(SoapObject target,
			int contentWhat) {
		List<BzServiceProviderTypeDto> result = new ArrayList<BzServiceProviderTypeDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(getBzServiceProviderTypeDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

	private BzServiceProviderTypeDto getBzServiceProviderTypeDto(
			SoapObject target) {
		BzServiceProviderTypeDto result = new BzServiceProviderTypeDto();
		result.setId(getProperty2Long(target, "id"));
		result.setTypeName(getProperty2String(target, "typeName"));
		result.setTypeCode(getProperty2String(target, "typeCode"));
		result.setParentId(getProperty2Long(target, "parentId"));
		result.setIsLeaf(getProperty2Boolean(target, "isLeaf"));
		result.setOrderNo(getProperty2Long(target, "orderNo"));
		result.setMarkForDelete(getProperty2Boolean(target, "markForDelete"));
		result.setCreatedBy(getProperty2Long(target, "createdBy"));
		result.setCreatedDate(getProperty2Date(target, "createdDate",
				DateUtil.FORMAT_DATETIME_DEFAULT));
		result.setUpdatedBy(getProperty2Long(target, "updatedBy"));
		result.setUpdatedDate(getProperty2Date(target, "updatedDate",
				DateUtil.FORMAT_DATETIME_DEFAULT));
		return result;
	}

}
