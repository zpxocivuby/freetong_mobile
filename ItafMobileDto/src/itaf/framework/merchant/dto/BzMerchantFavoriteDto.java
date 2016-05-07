package itaf.framework.merchant.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;

/**
 * 
 * 商家收藏
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzMerchantFavoriteDto extends BaseDto {

	private static final long serialVersionUID = -8677446348928163201L;

	private BzConsumerDto bzConsumerDto;
	private BzMerchantDto bzMerchantDto;
	private String type;

	public BzMerchantFavoriteDto() {
		//
	}

	public BzConsumerDto getBzConsumerDto() {
		return bzConsumerDto;
	}

	public void setBzConsumerDto(BzConsumerDto bzConsumerDto) {
		this.bzConsumerDto = bzConsumerDto;
	}

	public BzMerchantDto getBzMerchantDto() {
		return bzMerchantDto;
	}

	public void setBzMerchantDto(BzMerchantDto bzMerchantDto) {
		this.bzMerchantDto = bzMerchantDto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
