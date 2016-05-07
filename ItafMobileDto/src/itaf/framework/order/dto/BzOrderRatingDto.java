package itaf.framework.order.dto;

// Generated Sep 2, 2014 4:45:05 PM by Hibernate Tools 3.4.0.CR1

import itaf.framework.base.dto.BaseDto;
import itaf.framework.merchant.dto.BzMerchantRatingDto;
import itaf.framework.product.dto.BzProductRatingDto;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 订单
 * 
 * @author
 * 
 * @UpdateDate 2014年9月4日
 */
public class BzOrderRatingDto extends BaseDto {

	private static final long serialVersionUID = -6447958070729643970L;

	private BzMerchantRatingDto bzMerchantRatingDto;
	private List<BzProductRatingDto> bzProductRatingDtos = new ArrayList<BzProductRatingDto>();

	public BzOrderRatingDto() {
	}

	public BzMerchantRatingDto getBzMerchantRatingDto() {
		return bzMerchantRatingDto;
	}

	public void setBzMerchantRatingDto(BzMerchantRatingDto bzMerchantRatingDto) {
		this.bzMerchantRatingDto = bzMerchantRatingDto;
	}

	public List<BzProductRatingDto> getBzProductRatingDtos() {
		return bzProductRatingDtos;
	}

	public void setBzProductRatingDtos(
			List<BzProductRatingDto> bzProductRatingDtos) {
		this.bzProductRatingDtos = bzProductRatingDtos;
	}

}
