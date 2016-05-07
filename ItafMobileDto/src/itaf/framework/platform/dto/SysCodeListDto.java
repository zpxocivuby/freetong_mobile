package itaf.framework.platform.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 系统数据字典值列表
 *
 * @author
 *
 * @UpdateDate 2014年9月4日
 */
public class SysCodeListDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = 2718433561912636511L;
	private SysCodeTypeDto sysCodeTypeDto;
	private String codeValue;
	private String nameZh;
	private String nameEn;
	private Long sortNo;

	public SysCodeListDto() {
	}

	public SysCodeTypeDto getSysCodeTypeDto() {
		return sysCodeTypeDto;
	}

	public void setSysCodeTypeDto(SysCodeTypeDto sysCodeTypeDto) {
		this.sysCodeTypeDto = sysCodeTypeDto;
	}

	public String getCodeValue() {
		return codeValue;
	}

	public void setCodeValue(String codeValue) {
		this.codeValue = codeValue;
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

	public Long getSortNo() {
		return sortNo;
	}

	public void setSortNo(Long sortNo) {
		this.sortNo = sortNo;
	}


}
