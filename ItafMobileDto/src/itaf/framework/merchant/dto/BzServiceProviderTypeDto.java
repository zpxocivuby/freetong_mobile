package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseTreeDto;

/**
 * 
 * 服务提供商类型
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzServiceProviderTypeDto extends BaseTreeDto {

	private static final long serialVersionUID = 962728639802623955L;

	private BzServiceProviderTypeDto parentDto;
	private String typeName;
	private String typeCode;

	public BzServiceProviderTypeDto() {
		//
	}

	public BzServiceProviderTypeDto getParentDto() {
		return parentDto;
	}

	public void setParentDto(BzServiceProviderTypeDto parentDto) {
		this.parentDto = parentDto;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

}
