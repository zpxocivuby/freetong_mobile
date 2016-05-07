package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.position.dto.BzPositionDto;

/**
 * 
 * 配送商
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzDistCompanyDto extends SysUserDto {

	private static final long serialVersionUID = 2832977905323350128L;
	// 距离
	public static final Long ORDER_TYPE_DISTANCE = 1L;
	// 信用度
	public static final Long ORDER_TYPE_CREDIT = 2L;
	// 时间
	public static final Long ORDER_TYPE_TIME = 3L;

	private String companyName;
	private String companyAddress;
	private Long serviceCoverage;
	private Long certificateStatus;
	private BzPositionDto bzPositionDto;
	private BzServiceProviderTypeDto bzServiceProviderTypeDto;

	public BzDistCompanyDto() {
		//
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return this.companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Long getServiceCoverage() {
		return this.serviceCoverage;
	}

	public void setServiceCoverage(Long serviceCoverage) {
		this.serviceCoverage = serviceCoverage;
	}

	public Long getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(Long certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public BzPositionDto getBzPositionDto() {
		return bzPositionDto;
	}

	public void setBzPositionDto(BzPositionDto bzPositionDto) {
		this.bzPositionDto = bzPositionDto;
	}

	public BzServiceProviderTypeDto getBzServiceProviderTypeDto() {
		return bzServiceProviderTypeDto;
	}

	public void setBzServiceProviderTypeDto(
			BzServiceProviderTypeDto bzServiceProviderTypeDto) {
		this.bzServiceProviderTypeDto = bzServiceProviderTypeDto;
	}

}
