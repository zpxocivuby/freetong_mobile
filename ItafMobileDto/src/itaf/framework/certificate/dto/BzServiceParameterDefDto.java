package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 服务参数定义
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzServiceParameterDefDto extends BaseDto {


	/**
	 * 
	 */
	private static final long serialVersionUID = -1558545923342020082L;
	/**
	 * 
	 */
	private BzServiceDefDto bzServiceDefDto;
	private String name;
	private String description;
	private String value;
	private Long enable;

	public BzServiceParameterDefDto() {
	}


	public BzServiceDefDto getBzServiceDefDto() {
		return bzServiceDefDto;
	}


	public void setBzServiceDefDto(BzServiceDefDto bzServiceDefDto) {
		this.bzServiceDefDto = bzServiceDefDto;
	}


	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Long getEnable() {
		return this.enable;
	}

	public void setEnable(Long enable) {
		this.enable = enable;
	}

}
