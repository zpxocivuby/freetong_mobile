package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 接口字段可选值定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysInfFieldValueDefineDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -9186163573955958223L;
	private SysInfFieldDefineDto sysInfFieldDefineDto;
	private String value;
	private String description;

	public SysInfFieldValueDefineDto() {
	}

	public SysInfFieldDefineDto getSysInfFieldDefineDto() {
		return sysInfFieldDefineDto;
	}

	public void setSysInfFieldDefineDto(SysInfFieldDefineDto sysInfFieldDefineDto) {
		this.sysInfFieldDefineDto = sysInfFieldDefineDto;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}


}
