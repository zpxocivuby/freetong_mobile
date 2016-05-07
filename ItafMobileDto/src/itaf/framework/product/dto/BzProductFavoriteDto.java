package itaf.framework.product.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.consumer.dto.BzConsumerDto;

/**
 * 
 * 商品收藏
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzProductFavoriteDto extends BaseDto {

	private static final long serialVersionUID = -5046841827045846579L;

	private BzConsumerDto bzConsumerDto;
	private BzProductDto bzProductDto;
	private String type;

	public BzProductFavoriteDto() {
		//
	}

	public BzConsumerDto getBzConsumerDto() {
		return bzConsumerDto;
	}

	public void setBzConsumerDto(BzConsumerDto bzConsumerDto) {
		this.bzConsumerDto = bzConsumerDto;
	}

	public BzProductDto getBzProductDto() {
		return bzProductDto;
	}

	public void setBzProductDto(BzProductDto bzProductDto) {
		this.bzProductDto = bzProductDto;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
