package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * 系统数据字典类型
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysCodeTypeDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -2549430001675376795L;
	private String codeType;
	private String nameZh;
	private String nameEn;
	private Long codeLenLimit;
	private Set<SysCodeListDto> sysCodeListDtos = new HashSet<SysCodeListDto>(0);

	public SysCodeTypeDto() {
	}

	public String getCodeType() {
		return codeType;
	}

	public void setCodeType(String codeType) {
		this.codeType = codeType;
	}

	public String getNameZh() {
		return nameZh;
	}

	public void setNameZh(String nameZh) {
		this.nameZh = nameZh;
	}

	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public Long getCodeLenLimit() {
		return codeLenLimit;
	}

	public void setCodeLenLimit(Long codeLenLimit) {
		this.codeLenLimit = codeLenLimit;
	}

	public Set<SysCodeListDto> getSysCodeListDtos() {
		return sysCodeListDtos;
	}

	public void setSysCodeListDtos(Set<SysCodeListDto> sysCodeListDtos) {
		this.sysCodeListDtos = sysCodeListDtos;
	}


}
