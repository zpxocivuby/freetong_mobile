package itaf.mobile.ds.ws.platform;

import itaf.framework.base.dto.WsPageResult;
import itaf.framework.platform.dto.SysUserDto;
import itaf.mobile.ds.ws.base.WsProcessProperty;

import java.util.ArrayList;
import java.util.List;

import org.ksoap2.serialization.SoapObject;

/**
 * 用户注册
 *
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class WsSysRegisterClient extends WsProcessProperty<SysUserDto> {

	private static final String REGISTER = "register";

	public WsSysRegisterClient() {
		super("/service/WsSysRegisterService");
	}

	public WsPageResult<SysUserDto> register(String username, String password,
			String mobile) {
		SoapObject paramSo = new SoapObject(NAMESPACE_PLATFORM, REGISTER);
		paramSo.addProperty("username", username);
		paramSo.addProperty("password", password);
		paramSo.addProperty("mobile", mobile);
		return callSoapMethod(paramSo, 1);
	}

	@Override
	protected List<SysUserDto> processContent(SoapObject target, int contentWhat) {
		List<SysUserDto> result = new ArrayList<SysUserDto>();
		switch (contentWhat) {
		case 0:
			// Do Nothing
			break;
		case 1:
			for (int i = 0; i < target.getPropertyCount(); i++) {
				Object propertyObj = target.getProperty(i);
				if (propertyObj instanceof SoapObject) {
					SoapObject soapObj = (SoapObject) propertyObj;
					result.add(processSysUserDto(soapObj));
				}
			}
			break;
		}
		return result;
	}

}
