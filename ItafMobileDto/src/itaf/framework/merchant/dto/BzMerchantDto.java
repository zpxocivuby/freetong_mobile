package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.cart.dto.BzCartItemDto;
import itaf.framework.platform.dto.SysUserDto;
import itaf.framework.position.dto.BzPositionDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 商家
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzMerchantDto extends SysUserDto {

	private static final long serialVersionUID = -3457288414472781438L;

	private String companyName;
	private String companyAddress;
	private Long serviceCoverage;
	private String merchantCharacteristics;
	private Long merchantCategory;
	private Long certificateStatus;
	private Boolean isFavorited;
	private Float ratingScore;
	private BzPositionDto bzPositionDto;
	private BzServiceProviderTypeDto bzServiceProviderTypeDto;
	private List<BzCartItemDto> bzCartItemDtos = new ArrayList<BzCartItemDto>();

	public BzMerchantDto() {
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	public Long getServiceCoverage() {
		return serviceCoverage;
	}

	public void setServiceCoverage(Long serviceCoverage) {
		this.serviceCoverage = serviceCoverage;
	}

	public String getMerchantCharacteristics() {
		return merchantCharacteristics;
	}

	public void setMerchantCharacteristics(String merchantCharacteristics) {
		this.merchantCharacteristics = merchantCharacteristics;
	}

	public Long getMerchantCategory() {
		return merchantCategory;
	}

	public void setMerchantCategory(Long merchantCategory) {
		this.merchantCategory = merchantCategory;
	}

	public Long getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(Long certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public Boolean getIsFavorited() {
		return isFavorited;
	}

	public void setIsFavorited(Boolean isFavorited) {
		this.isFavorited = isFavorited;
	}

	public Float getRatingScore() {
		return ratingScore;
	}

	public void setRatingScore(Float ratingScore) {
		this.ratingScore = ratingScore;
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

	public List<BzCartItemDto> getBzCartItemDtos() {
		return bzCartItemDtos;
	}

	public void setBzCartItemDtos(List<BzCartItemDto> bzCartItemDtos) {
		this.bzCartItemDtos = bzCartItemDtos;
	}

}
