package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 接口字段值定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class SysInfFieldDefineDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1101728818124188765L;
	private SysInfDefineDto sysInfDefineDto;
	private String filedName;
	private String filedDesc;
	private String filedType;
	private Set<SysInfFieldValueDefineDto> sysInfFieldValueDefineDtos = new HashSet<SysInfFieldValueDefineDto>(
			0);

	public SysInfFieldDefineDto() {
	}

	public SysInfDefineDto getSysInfDefineDto() {
		return sysInfDefineDto;
	}

	public void setSysInfDefineDto(SysInfDefineDto sysInfDefineDto) {
		this.sysInfDefineDto = sysInfDefineDto;
	}

	public String getFiledName() {
		return filedName;
	}

	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getFiledDesc() {
		return filedDesc;
	}

	public void setFiledDesc(String filedDesc) {
		this.filedDesc = filedDesc;
	}

	public String getFiledType() {
		return filedType;
	}

	public void setFiledType(String filedType) {
		this.filedType = filedType;
	}

	public Set<SysInfFieldValueDefineDto> getSysInfFieldValueDefineDtos() {
		return sysInfFieldValueDefineDtos;
	}

	public void setSysInfFieldValueDefineDtos(
			Set<SysInfFieldValueDefineDto> sysInfFieldValueDefineDtos) {
		this.sysInfFieldValueDefineDtos = sysInfFieldValueDefineDtos;
	}


}
