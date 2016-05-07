package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.order.dto.BzOrderDto;

/**
 * 
 * 商家信用评价记录
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzMerchantRatingDto extends BaseDto implements
		java.io.Serializable {

	private static final long serialVersionUID = -5925895801025346391L;

	private Long bzMerchantId;
	private BzMerchantDto bzMerchantDto;
	private Long bzConsumerId;
	private BzConsumerDto bzConsumerDto;
	private Long bzOrderId;
	private BzOrderDto bzOrderDto;
	private Long descriptionScore;
	private Long attitudeScore;
	private Long deliveryScore;
	private Long logisticsScore;

	public BzMerchantRatingDto() {
	}

	public Long getBzMerchantId() {
		return bzMerchantId;
	}

	public void setBzMerchantId(Long bzMerchantId) {
		this.bzMerchantId = bzMerchantId;
	}
	
	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public Long getBzConsumerId() {
		return bzConsumerId;
	}

	public void setBzConsumerId(Long bzConsumerId) {
		this.bzConsumerId = bzConsumerId;
	}
	
	public BzConsumerDto getBzConsumerDto() {
		return bzConsumerDto;
	}

	public void setBzConsumerDto(BzConsumerDto bzConsumerDto) {
		this.bzConsumerDto = bzConsumerDto;
	}

	public Long getBzOrderId() {
		return bzOrderId;
	}

	public void setBzOrderId(Long bzOrderId) {
		this.bzOrderId = bzOrderId;
	}
	
	public BzOrderDto getBzOrderDto() {
		return this.bzOrderDto;
	}

	public void setBzOrderDto(BzOrderDto bzOrderDto) {
		this.bzOrderDto = bzOrderDto;
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
