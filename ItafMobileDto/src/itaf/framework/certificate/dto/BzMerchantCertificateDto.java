package itaf.framework.certificate.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;

/**
 * 
 * 商家认证
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */

public class BzMerchantCertificateDto extends BaseDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7013673085910546042L;
	/**
	 * 
	 */
	private BzApplyDistCertificateDto bzDistCertificateApplyDto;
	private BzApplySellingCertificateDto bzSellingCertificateApplyDto;
	private Long sysUserId;
	private Long certificateType;
	private String companyName;
	private String companyAddress;
	private Long serviceType;
	private Long serviceCoverage;

	public BzMerchantCertificateDto() {
	}

	public BzApplyDistCertificateDto getBzApplyDistCertificateDto() {
		return bzDistCertificateApplyDto;
	}

	public void setBzApplyDistCertificateDto(
			BzApplyDistCertificateDto bzDistCertificateApplyDto) {
		this.bzDistCertificateApplyDto = bzDistCertificateApplyDto;
	}

	public BzApplySellingCertificateDto getBzApplySellingCertificateDto() {
		return bzSellingCertificateApplyDto;
	}

	public void setBzApplySellingCertificateDto(
			BzApplySellingCertificateDto bzSellingCertificateApplyDto) {
		this.bzSellingCertificateApplyDto = bzSellingCertificateApplyDto;
	}

	public Long getSysUserId() {
		return this.sysUserId;
	}

	public void setSysUserId(Long sysUserId) {
		this.sysUserId = sysUserId;
	}

	public Long getCertificateType() {
		return this.certificateType;
	}

	public void setCertificateType(Long certificateType) {
		this.certificateType = certificateType;
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

	public Long getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(Long serviceType) {
		this.serviceType = serviceType;
	}

	public Long getServiceCoverage() {
		return this.serviceCoverage;
	}

	public void setServiceCoverage(Long serviceCoverage) {
		this.serviceCoverage = serviceCoverage;
	}

}
