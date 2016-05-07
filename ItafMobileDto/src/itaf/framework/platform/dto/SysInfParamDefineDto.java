package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 系统接口参数定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysInfParamDefineDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1204727002647253234L;
	private SysInfDefineDto sysInfDefineDto;
	private String paramName;
	private String paramValue;
	private String valueType;

	public SysInfParamDefineDto() {
	}

	public SysInfDefineDto getSysInfDefineDto() {
		return sysInfDefineDto;
	}

	public void setSysInfDefineDto(SysInfDefineDto sysInfDefineDto) {
		this.sysInfDefineDto = sysInfDefineDto;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getValueType() {
		return valueType;
	}

	public void setValueType(String valueType) {
		this.valueType = valueType;
	}


}
