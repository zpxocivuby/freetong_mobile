package itaf.framework.merchant.dto;

import itaf.framework.base.dto.BaseDto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

/**
 * 
 * 商家信用
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzMerchantRateDto extends BaseDto implements java.io.Serializable {

	private static final long serialVersionUID = 6132430866596046202L;

	private BzMerchantDto bzMerchantDto;
	private Long descriptionScore;
	private Long attitudeScore;
	private Long deliveryScore;
	private Long logisticsScore;

	public BzMerchantRateDto() {
	}

	public BzMerchantDto getBzMerchantDto() {
		return this.bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public Long getDescriptionScore() {
		return descriptionScore;
	}

	public void setDescriptionScore(Long descriptionScore) {
		this.descriptionScore = descriptionScore;
	}

	public Long getAttitudeScore() {
		return attitudeScore;
	}

	public void setAttitudeScore(Long attitudeScore) {
		this.attitudeScore = attitudeScore;
	}

	public Long getDeliveryScore() {
		return deliveryScore;
	}

	public void setDeliveryScore(Long deliveryScore) {
		this.deliveryScore = deliveryScore;
	}

	public Long getLogisticsScore() {
		return logisticsScore;
	}

	public void setLogisticsScore(Long logisticsScore) {
		this.logisticsScore = logisticsScore;
	}

}
