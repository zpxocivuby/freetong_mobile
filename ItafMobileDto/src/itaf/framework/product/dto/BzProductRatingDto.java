package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;
import itaf.framework.order.dto.BzOrderDto;

/**
 * 
 * 商品评价
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductRatingDto extends BaseDto {

	private static final long serialVersionUID = 648717185536844013L;

	private Long bzProductId;
	private BzProductDto bzProductDto;
	private Long bzConsumerId;
	private BzConsumerDto bzConsumerDto;
	private Long bzOrderId;
	private BzOrderDto bzOrderDto;
	private Long rate;
	private String content;

	public BzProductRatingDto() {
	}

	public Long getBzProductId() {
		return bzProductId;
	}

	public void setBzProductId(Long bzProductId) {
		this.bzProductId = bzProductId;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
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

	public Long getRate() {
		return rate;
	}

	public void setRate(Long rate) {
		this.rate = rate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
